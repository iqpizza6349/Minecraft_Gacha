package com.tistory.workshop6349.minecraft_gacha.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class CommandManager implements CommandExecutor {

    private final Plugin plugin;

    public CommandManager(Plugin plugin) {
        this.plugin = plugin;
    }

    private int time = 0;
    private BossBar bossBar = null;
    private long addedTime = 0L;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            if (label.equalsIgnoreCase("timer")) {
                if (args.length == 2) {

                    if (args[0].equalsIgnoreCase("add")) {
                        if (bossBar != null) {
                            player.sendMessage("이미 " + bossBar.getTitle() + " 타이머가 진행 중입니다.");
                            return false;
                        }

                        bossBar = Bukkit.createBossBar("timer", BarColor.BLUE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
                        time = Integer.parseInt(args[1]);
                        addedTime = System.currentTimeMillis() / 1000;

                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            bossBar.addPlayer(onlinePlayer);
                        }
                        bossBar.setVisible(true);
                        player.sendMessage("타이머를 생성하였습니다.");
                        run();
                    }
                }
                else {
                    player.sendMessage("잘못된 명령입니다.");
                }
            }
        }
        return false;
    }

    public void run() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {
            if (bossBar != null) {
                double timeLeft = addedTime + time - System.currentTimeMillis() / 1000;
                double progress =  timeLeft / time;

                if (timeLeft <= 0) {
                    bossBar.setVisible(false);
                    bossBar = null;
                    return;
                }

                for (Player player : Bukkit.getOnlinePlayers()) {
                    bossBar.addPlayer(player);
                }

                bossBar.setProgress(progress);

                int sec = (int) timeLeft;
                int min = sec / 60;
                int hour = min / 60;
                sec %= 60;
                min %= 60;

                if (timeLeft > 3600) {
                    bossBar.setTitle(ChatColor.BOLD + "" + hour + "시간 " + min + "분 " + sec + "초 남았습니다.");
                }
                else if (timeLeft > 60) {
                    bossBar.setTitle(ChatColor.BOLD + "" + min + "분 " + sec + "초 남았습니다");
                }
                else {
                    bossBar.setTitle(ChatColor.BOLD + "" + timeLeft + "초 남았습니다.");
                }
            }
        }, 0L, 0L);
    }
}
