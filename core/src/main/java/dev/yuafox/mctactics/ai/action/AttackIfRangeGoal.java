package dev.yuafox.mctactics.ai.action;

import dev.yuafox.mctactics.entity.EntityBattle;

public class AttackIfRangeGoal implements ActionGoal {
    @Override
    public boolean tick(EntityBattle entity, int currentTick) {
        EntityBattle target = entity.getTarget();
        if(target == null) return false;
        if(entity.getDistance(target) <= entity.getBasicAttackRange()){
            entity.basicAttack(target, currentTick);
            return true;
        }else return false;
    }
}
