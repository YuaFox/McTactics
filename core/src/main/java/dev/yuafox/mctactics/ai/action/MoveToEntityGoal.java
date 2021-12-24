package dev.yuafox.mctactics.ai.action;

import dev.yuafox.mctactics.entity.EntityBattle;
import org.bukkit.Location;

public class MoveToEntityGoal implements ActionGoal {
    @Override
    public boolean tick(EntityBattle entity, int currentTick) {
        EntityBattle target = entity.getTarget();
        if(target == null) return false;
        Location moveTo = target.getLocation().subtract(entity.getLocation());
        entity.move(moveTo.getX(), moveTo.getZ());
        return true;
    }
}
