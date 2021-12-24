package dev.yuafox.mctactics;

import dev.yuafox.mctactics.entity.MobBucket;
import dev.yuafox.mctactics.entity.collection.MobSet;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

public class McTactics {
    public static Plugin PLUGIN = null;
    public static Logger LOGGER = null;
    public static String BUKKIT_VERSION_CODE = null;

    public static MobSet SET_TEST = null;

    private static MobBucket mobBucket;

    public static void registerMobBucket(MobBucket bucket){
        mobBucket = bucket;
    }

    @Nullable
    public static MobBucket getMobBucket(){
        return mobBucket;
    }

    public static void spawnFireworks(@NotNull Location location, int power){
        Firework fw = (Firework) location.getWorld().spawnEntity(location, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        fwm.setPower(power);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.RED).flicker(true).build());
        fw.setFireworkMeta(fwm);
        fw.detonate();
    }
}
