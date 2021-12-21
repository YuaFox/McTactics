package dev.yuafox.mctactics;

import dev.yuafox.mctactics.entity.MobBucket;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.logging.Logger;

public class McTactics {
    public static Plugin PLUGIN = null;
    public static Logger LOGGER = null;
    public static String BUKKIT_VERSION_CODE = null;

    private static MobBucket mobBucket;

    public static void registerMobBucket(MobBucket bucket){
        mobBucket = bucket;
    }

    @Nullable
    public static MobBucket getMobBucket(){
        return mobBucket;
    }
}
