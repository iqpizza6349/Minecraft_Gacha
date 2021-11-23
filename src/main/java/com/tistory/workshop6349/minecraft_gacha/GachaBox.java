package com.tistory.workshop6349.minecraft_gacha;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class GachaBox {

    // 가챠 상자와 상호작용 이후, 다시 보스바가 작동해야함
    public static ItemStack gachaBox = new ItemStack(Material.SHULKER_BOX);
    public static ItemMeta gachaBoxMeta = gachaBox.getItemMeta();

    public static void createGachaBox(Player player) {
        gachaBoxMeta.addEnchant(Enchantment.LUCK, 1, false);
        gachaBoxMeta.setDisplayName("가챠 상자");
        gachaBox.setItemMeta(gachaBoxMeta);
        player.getInventory().addItem(gachaBox);
    }

    public static void interactBox(Player player, PlayerInteractEvent event, Plugin plugin) {
        if (player.getInventory().getItemInMainHand().getType() == Material.SHULKER_BOX
                && player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.LUCK)
                && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            if (player.getGameMode() == GameMode.CREATIVE
                    || player.getGameMode() == GameMode.SPECTATOR
                    || player.getGameMode() == GameMode.ADVENTURE) {
                player.sendMessage("This plugin is for only SURVIVAL");
                return;
            }
            openBox(player, plugin); // 가챠 상자 작동
            event.setCancelled(true);
        }
    }

    public static void openBox(Player player, Plugin plugin) {
        // 랜덤 클래스 사용하여 랜덤하게 작동
        player.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
        new GachaBoxResult(player, plugin);
    }

}
