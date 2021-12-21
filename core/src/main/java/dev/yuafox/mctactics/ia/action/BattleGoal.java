package dev.yuafox.mctactics.ia.action;

import dev.yuafox.mctactics.entity.EntityBattle;
import org.bukkit.Location;

public class BattleGoal implements ActionGoal {

    @Override
    public boolean tick(EntityBattle entity) {
        EntityBattle target = entity.getTarget();
        Location moveTo = target.getLocation().subtract(entity.getLocation());
        if(Math.abs(moveTo.getX()) + Math.abs(moveTo.getZ()) > 2.0)
            entity.move(moveTo.getX(), moveTo.getZ());
        else
            target.damage();
        return false;
    }
}
