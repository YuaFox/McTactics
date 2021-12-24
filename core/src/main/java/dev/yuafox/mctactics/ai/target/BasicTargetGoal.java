package dev.yuafox.mctactics.ai.target;

import dev.yuafox.mctactics.entity.EntityBattle;

public class BasicTargetGoal implements TargetGoal {


    @Override
    public boolean tick(EntityBattle entity, int currentTick) {

        EntityBattle target = null;
        double distance = 10000;

        for(EntityBattle e : entity.getEnemies()){
            double cdistance = entity.getLocation().distance(e.getLocation());
            if(cdistance < distance){
                distance = cdistance;
                target = e;
            }
        }

        entity.setTarget(target);
        return true;
    }
}
