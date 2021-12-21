package dev.yuafox.mctactics.ia.target;

import dev.yuafox.mctactics.entity.EntityBattle;

public class BasicTargetGoal implements TargetGoal {

    @Override
    public boolean tick(EntityBattle entity) {
        entity.setTarget(entity.getEnemies().get(0));
        return false;
    }
}
