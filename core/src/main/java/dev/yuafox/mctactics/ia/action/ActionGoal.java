package dev.yuafox.mctactics.ia.action;

import dev.yuafox.mctactics.entity.EntityBattle;

public interface ActionGoal {

    boolean tick(EntityBattle entity);

}
