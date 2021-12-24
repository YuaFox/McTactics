package dev.yuafox.mctactics.dynamic.entity;

import dev.yuafox.mctactics.entity.Mob;
import dev.yuafox.mctactics.entity.MobBucket;
import net.minecraft.server.level.WorldServer;
import net.minecraft.world.entity.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class EntityBucket1 extends MobBucket {

    @Override
    public LivingEntity spawn(EntityType type, Location location) {
        try {
            Class<?> mobClass = this.getMob(type);
            Constructor<?> constructor = mobClass.getConstructor(Location.class);
            Mob mobEntity = (Mob) constructor.newInstance(location);
            Entity entity = (Entity) mobEntity;
            WorldServer world = ((CraftWorld) location.getWorld()).getHandle();
            world.addFreshEntity(entity, CreatureSpawnEvent.SpawnReason.DEFAULT);
            return (LivingEntity) entity.getBukkitEntity();
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
    }

}

