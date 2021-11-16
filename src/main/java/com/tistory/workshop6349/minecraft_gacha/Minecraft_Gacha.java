package com.tistory.workshop6349.minecraft_gacha;

import com.tistory.workshop6349.minecraft_gacha.command.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class Minecraft_Gacha extends JavaPlugin {

    ConsoleCommandSender consoleSender = Bukkit.getConsoleSender();

    @Override
    public void onEnable() {
        // Plugin startup logic
        consoleSender.sendMessage("[ activate plugin ]");
        getServer().getPluginManager().registerEvents(new EventListener(this), this);
        Objects.requireNonNull(getCommand("timer")).setExecutor(new CommandManager(this));
        BossBarManagement.userBossBars.clear();
        BossBarManagement.timer.clear();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        consoleSender.sendMessage("[ shutting down plugin ]");
    }
}
