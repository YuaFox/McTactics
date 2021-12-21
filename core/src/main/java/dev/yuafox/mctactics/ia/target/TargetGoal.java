package dev.yuafox.mctactics.ia.target;

import dev.yuafox.mctactics.entity.EntityBattle;

public interface TargetGoal {

    boolean tick(EntityBattle entity);

}
