package com.tistory.workshop6349.minecraft_gacha;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GachaBoxResult {
    public static List<EntityType> entities = new ArrayList<>();
    public static List<Material> materials = new ArrayList<>();
    public static List<PotionEffectType> effects = new ArrayList<>();
    public static List<Object> objectList = new ArrayList<>();

    static {
        entities.addAll(Arrays.stream(EntityType.values()).toList());
        materials.addAll(Arrays.stream(Material.values()).toList());
        effects.addAll(Arrays.asList(PotionEffectType.values()));
        objectList.addAll(materials);
        objectList.addAll(effects);
        objectList.addAll(entities);
    }

    public GachaBoxResult(Player player, Plugin plugin) {
        Random random = new Random();
        new BukkitRunnable() {
            int count = 0;

            @Override
            public void run() {
                int r = random.nextInt(objectList.size());
                Object o = objectList.get(r);
                String next = String.valueOf(o);

                if (count > 50) {
                    result(player, o);
                    BossBarManagement.overrideBossBar(player);
                    this.cancel();
                }
                else {
                    player.sendTitle("가챠 상자 여는 중", next, 0, 3, 0);
                    count++;
                }
            }
        }.runTaskTimer(plugin, 3L, 3L);
    }

    public void customFireWork(Firework firework, FireworkMeta meta,
            int power, FireworkEffect.Type type, Color color) {
        meta.setPower(power);
        meta.addEffect(FireworkEffect.builder().with(type).withColor(color).flicker(true).build());

        firework.setFireworkMeta(meta);
    }

    public void result(Player player, Object item) {
        player.sendTitle("Selected from Gacha box", String.valueOf(item), 10, 70, 20);
        Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
        FireworkMeta meta = firework.getFireworkMeta();

        if (item instanceof EntityType type) {
            customFireWork(firework, meta, 10, FireworkEffect.Type.CREEPER, Color.BLACK);
            spawnEntity(player, type);
            return;
        }
        if (item instanceof Material material) {
            customFireWork(firework, meta, 10, FireworkEffect.Type.BURST, Color.OLIVE);
            giveItem(player, material);
            return;
        }
        if (item instanceof PotionEffectType effectType) {
            customFireWork(firework, meta, 10, FireworkEffect.Type.STAR, Color.AQUA);
            giveEffect(player, effectType);
        }
    }

    public void spawnEntity(Player player, EntityType e) {
        player.getWorld().spawnEntity(player.getLocation(), e);
    }

    public void giveItem(Player player, Material material) {
        ItemStack itemStack = new ItemStack(material);
        player.getInventory().addItem(itemStack);
    }

    public void giveEffect(Player player, PotionEffectType effectType) {
        player.addPotionEffect(new PotionEffect(effectType, 120, 1, true));
    }

}
