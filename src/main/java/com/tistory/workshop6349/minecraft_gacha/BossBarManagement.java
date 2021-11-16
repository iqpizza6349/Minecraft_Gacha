package com.tistory.workshop6349.minecraft_gacha;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class BossBarManagement {

    public Plugin plugin;
    public static int time = 60;

    public static final HashMap<UUID, Long> timer = new HashMap<>(); // 고유 아이디, addedTime
    public static final HashMap<UUID, BossBar> userBossBars = new HashMap<>(); // 고유 아이디, BossBar

    public BossBarManagement(Player player, Plugin plugin) {
        this.plugin = plugin;

        overrideBossBar(player);
        run(player);
    }

    public static void overrideBossBar(Player player) {
        timer.put(player.getUniqueId(), System.currentTimeMillis() / 1000); // 초기값
        BossBar bossBar = Bukkit.createBossBar("timer", BarColor.BLUE, BarStyle.SOLID, BarFlag.PLAY_BOSS_MUSIC);
        bossBar.setVisible(true);
        bossBar.addPlayer(player);
        userBossBars.put(player.getUniqueId(), bossBar);
    }

    public BossBar getBossBar(Player player) {
        return userBossBars.get(player.getUniqueId());
    }

    public Long getAddedTime(Player player) {
        return timer.get(player.getUniqueId());
    }

    public void run(Player player) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            BossBar bossBar = getBossBar(player);
            long addedTime = getAddedTime(player);
            // 아직 가챠를 사용하지 않았다면 -> 보스바 null
            // 가챠 사용 당시에 보스바를 재정의

            if (bossBar != null && addedTime >= 0) {
                double timeLeft = addedTime + time - System.currentTimeMillis() / 1000;
                double progress = timeLeft / time;

                if (timeLeft <= 0) {
                    bossBar.setVisible(false);
                    userBossBars.replace(player.getUniqueId(), null);
                    timer.replace(player.getUniqueId(), -1L);
                    GachaBox.createGachaBox(player);
                    return;
                }
                bossBar.addPlayer(player);
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
