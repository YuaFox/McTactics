package dev.yuafox.mctactics.ai.target;

import dev.yuafox.mctactics.entity.EntityBattle;

public interface TargetGoal {

    boolean tick(EntityBattle entity,int currentTick);

}
