package dev.yuafox.mctactics.ai.action;

import dev.yuafox.mctactics.entity.EntityBattle;

public interface ActionGoal {

    boolean tick(EntityBattle entity, int currentTick);

}
