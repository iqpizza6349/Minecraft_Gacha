package com.tistory.workshop6349.minecraft_gacha;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class EventListener implements Listener {

    private final Plugin plugin;

    public EventListener(Plugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        event.setJoinMessage("Hello, " + event.getPlayer().getName());
        new BossBarManagement(event.getPlayer(), this.plugin);
    }

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        // 해당 특수 셜커 상자 이벤트 처리
        GachaBox.interactBox(event.getPlayer(), event, this.plugin);
    }

    @EventHandler
    public void onBlockPlaceEvent(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() == Material.SHULKER_BOX
                && player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.LUCK)) {
            event.setCancelled(true);
        }
    }

}
