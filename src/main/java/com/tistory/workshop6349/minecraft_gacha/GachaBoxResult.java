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
    public static List<String> entities = new ArrayList<>();
    public static List<Material> materials = new ArrayList<>();
    public static List<PotionEffectType> effects = new ArrayList<>();
    public static List<Object> objectList = new ArrayList<>();

    static {
        /////////////UnHarmful/////////////
        entities.add("Pig");
        entities.add("Sheep");
        entities.add("Cow");
        entities.add("Mooshroom");
        entities.add("Chicken");
        entities.add("Ocelot");
        entities.add("Cat");
        entities.add("Rabbit");
        entities.add("Turtle");
        entities.add("Fox");
        entities.add("Strider");
        entities.add("Axolotl");
        entities.add("Parrot");
        entities.add("Squid");
        entities.add("Glow_squid");
        entities.add("Snow_golem");
        entities.add("Bat");
        entities.add("Cod");
        entities.add("Salmon");
        entities.add("Tropical_fish");
        entities.add("Wandering_Trader");
        /////////////neutrality/////////////
        entities.add("Wolf");
        entities.add("Llama");
        entities.add("Panda");
        entities.add("Bee");
        entities.add("Goat");
        entities.add("Spider");
        entities.add("Cave_spider");
        entities.add("Polar_bear");
        entities.add("Pufferfish");
        entities.add("Dolphin");
        /////////////Harmful/////////////
        entities.add("Zombie");
        entities.add("Husk");
        entities.add("Drowned");
        entities.add("Skeleton");
        entities.add("Wither_skeleton");
        entities.add("Stray");
        entities.add("Slime");
        entities.add("Magma_cube");
        entities.add("Guardian");
        entities.add("Elder_guardian");
        entities.add("Vindicator");
        entities.add("Evoker");
        entities.add("Vex");
        entities.add("Pillager");
        entities.add("Ravager");
        entities.add("Creeper");
        entities.add("Ghast");
        entities.add("Phantom");
        entities.add("Piglin");
        entities.add("Piglin_brute");
        entities.add("Silverfish");
        entities.add("Blaze");
        entities.add("Witch");
        entities.add("Endermite");
        entities.add("Shulker");
        entities.add("Hoglin");
        entities.add("Zoglin");
        //////////////////////////////
        materials.addAll(Arrays.stream(Material.values()).toList());
        effects.addAll(Arrays.asList(PotionEffectType.values()));
        objectList.addAll(materials);
        objectList.addAll(effects);
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
        firework.detonate();
    }

    public void result(Player player, Object item) {
        player.sendTitle("Selected from Gacha box", String.valueOf(item), -1, -1, -1);
        Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK);
        FireworkMeta meta = firework.getFireworkMeta();

        if (item instanceof String entity) {
            customFireWork(firework, meta, 2, FireworkEffect.Type.CREEPER, Color.BLACK);
            spawnEntity(player, entity);
            return;
        }
        if (item instanceof Material material) {
            customFireWork(firework, meta, 3, FireworkEffect.Type.BURST, Color.OLIVE);
            giveItem(player, material);
            return;
        }
        if (item instanceof PotionEffectType effectType) {
            customFireWork(firework, meta, 5, FireworkEffect.Type.STAR, Color.AQUA);
            giveEffect(player, effectType);
        }
    }

    public void spawnEntity(Player player, String e) {
        switch (e) {
            case "Pig" -> player.getWorld().spawn(player.getLocation(), Pig.class);
            case "Sheep" -> player.getWorld().spawn(player.getLocation(), Sheep.class);
            case "Cow" -> player.getWorld().spawn(player.getLocation(), Cow.class);
            case "Chicken" -> player.getWorld().spawn(player.getLocation(), Chicken.class);
            case "Ocelot" -> player.getWorld().spawn(player.getLocation(), Ocelot.class);
            case "Cat" -> player.getWorld().spawn(player.getLocation(), Cat.class);
            case "Rabbit" -> player.getWorld().spawn(player.getLocation(), Rabbit.class);
            case "Turtle" -> player.getWorld().spawn(player.getLocation(), Turtle.class);
            case "Fox" -> player.getWorld().spawn(player.getLocation(), Fox.class);
            case "Strider" -> player.getWorld().spawn(player.getLocation(), Strider.class);
            case "Axolotl" -> player.getWorld().spawn(player.getLocation(), Axolotl.class);
            case "Parrot" -> player.getWorld().spawn(player.getLocation(), Parrot.class);
            case "Squid" -> player.getWorld().spawn(player.getLocation(), Squid.class);
            case "Glow_squid" -> player.getWorld().spawn(player.getLocation(), GlowSquid.class);
            case "Snow_golem" -> player.getWorld().spawn(player.getLocation(), Snowman.class);
            case "Bat" -> player.getWorld().spawn(player.getLocation(), Bat.class);
            case "Cod" -> player.getWorld().spawn(player.getLocation(), Cod.class);
            case "Salmon" -> player.getWorld().spawn(player.getLocation(), Salmon.class);
            case "Tropical_fish" -> player.getWorld().spawn(player.getLocation(), TropicalFish.class);
            case "Wandering_Trader" -> player.getWorld().spawn(player.getLocation(), WanderingTrader.class);
            case "Wolf" -> player.getWorld().spawn(player.getLocation(), Wolf.class);
            case "Llama" -> player.getWorld().spawn(player.getLocation(), Llama.class);
            case "Panda" -> player.getWorld().spawn(player.getLocation(), Panda.class);
            case "Bee" -> player.getWorld().spawn(player.getLocation(), Bee.class);
            case "Goat" -> player.getWorld().spawn(player.getLocation(), Goat.class);
            case "Spider" -> player.getWorld().spawn(player.getLocation(), Spider.class);
            case "Cave_spider" -> player.getWorld().spawn(player.getLocation(), CaveSpider.class);
            case "Polar_bear" -> player.getWorld().spawn(player.getLocation(), PolarBear.class);
            case "Pufferfish" -> player.getWorld().spawn(player.getLocation(), PufferFish.class);
            case "Dolphin" -> player.getWorld().spawn(player.getLocation(), Dolphin.class);
            case "Zombie" -> player.getWorld().spawn(player.getLocation(), Zombie.class);
            case "Husk" -> player.getWorld().spawn(player.getLocation(), Husk.class);
            case "Drowned" -> player.getWorld().spawn(player.getLocation(), Drowned.class);
            case "Skeleton" -> player.getWorld().spawn(player.getLocation(), Skeleton.class);
            case "Wither_skeleton" -> player.getWorld().spawn(player.getLocation(), WitherSkeleton.class);
            case "Stray" -> player.getWorld().spawn(player.getLocation(), Stray.class);
            case "Slime" -> player.getWorld().spawn(player.getLocation(), Slime.class);
            case "Magma_cube" -> player.getWorld().spawn(player.getLocation(), MagmaCube.class);
            case "Guardian" -> player.getWorld().spawn(player.getLocation(), Guardian.class);
            case "Elder_guardian" -> player.getWorld().spawn(player.getLocation(), ElderGuardian.class);
            case "Vindicator" -> player.getWorld().spawn(player.getLocation(), Vindicator.class);
            case "Evoker" -> player.getWorld().spawn(player.getLocation(), Evoker.class);
            case "Vex" -> player.getWorld().spawn(player.getLocation(), Vex.class);
            case "Pillager" -> player.getWorld().spawn(player.getLocation(), Pillager.class);
            case "Ravager" -> player.getWorld().spawn(player.getLocation(), Ravager.class);
            case "Creeper" -> player.getWorld().spawn(player.getLocation(), Creeper.class);
            case "Ghast" -> player.getWorld().spawn(player.getLocation(), Ghast.class);
            case "Phantom" -> player.getWorld().spawn(player.getLocation(), Phantom.class);
            case "Piglin" -> player.getWorld().spawn(player.getLocation(), Piglin.class);
            case "Piglin_brute" -> player.getWorld().spawn(player.getLocation(), PiglinBrute.class);
            case "Silverfish" -> player.getWorld().spawn(player.getLocation(), Silverfish.class);
            case "Blaze" -> player.getWorld().spawn(player.getLocation(), Blaze.class);
            case "Witch" -> player.getWorld().spawn(player.getLocation(), Witch.class);
            case "Endermite" -> player.getWorld().spawn(player.getLocation(), Endermite.class);
            case "Shulker" -> player.getWorld().spawn(player.getLocation(), Shulker.class);
            case "Hoglin" -> player.getWorld().spawn(player.getLocation(), Hoglin.class);
            case "Zoglin" -> player.getWorld().spawn(player.getLocation(), Zoglin.class);

            default -> player.getWorld().spawn(player.getLocation(), EnderDragon.class);
        }
    }

    public void giveItem(Player player, Material material) {
        ItemStack itemStack = new ItemStack(material);
        player.getInventory().addItem(itemStack);
    }

    public void giveEffect(Player player, PotionEffectType effectType) {
        player.addPotionEffect(new PotionEffect(effectType, 120, 1, true));
    }

}
