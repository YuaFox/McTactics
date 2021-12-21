package dev.yuafox.mctactics.entity;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

import java.util.HashMap;
import java.util.Map;

public abstract class MobBucket {
    private final Map<EntityType, Class<? extends Mob>> bucket;

    public MobBucket(){
        this.bucket = new HashMap<>();
    }

    public Class<? extends Mob> register(EntityType type, Class<? extends Mob> clazz){
        return this.bucket.put(type, clazz);
    }

    public Class<? extends Mob>  getMob(EntityType type){
        return this.bucket.get(type);
    }

    public abstract LivingEntity spawn(EntityType type, Location location);
}
