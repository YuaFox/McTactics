package dev.yuafox.mctactics.dynamic.entity;

import dev.yuafox.mctactics.entity.Mob;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.animal.EntityChicken;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;

public class EntityChickenCustom extends EntityChicken implements Mob {

    public EntityChickenCustom(Location loc) {
        super(EntityTypes.l, ((CraftWorld) loc.getWorld()).getHandle());

        this.b(loc.getX(), loc.getY(), loc.getZ());

        this.r(false); // Can Pick up Loot
        this.u(true); // Aggressive
    }

    @Override
    public void u() {
        // Make entity Dumb
    }

    @Override
    protected void fo() {

    }
}