package dev.yuafox.mctactics;

import dev.yuafox.mctactics.entity.MobBucket;
import dev.yuafox.mctactics.entity.collection.MobSet;
import org.bukkit.plugin.Plugin;
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
}
