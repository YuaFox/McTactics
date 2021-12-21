package dev.yuafox.mctactics.entity;

import dev.yuafox.mctactics.McTactics;
import dev.yuafox.mctactics.ia.action.ActionGoal;
import dev.yuafox.mctactics.ia.action.BattleGoal;
import dev.yuafox.mctactics.ia.target.BasicTargetGoal;
import dev.yuafox.mctactics.ia.target.TargetGoal;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import java.util.LinkedList;
import java.util.List;

public class EntityBattle {

    private final EntityType entityType;
    private LivingEntity entity;

    private EntityBattle target;

    private final List<TargetGoal> targetGoals;
    private final List<ActionGoal> actionGoals;

    private List<EntityBattle> allies;
    private List<EntityBattle> enemies;

    public EntityBattle(EntityType entityType) {
        this.entityType = entityType;
        this.entity = null;
        this.target = null;
        this.targetGoals = new LinkedList<>();
        this.actionGoals = new LinkedList<>();


        // Test
        this.targetGoals.add(new BasicTargetGoal());
        this.actionGoals.add(new BattleGoal());
    }

    /* ----------------+
    |       Mob        |
    +-----------------*/

    public void spawn(Location location){
        if(this.entity != null){
            this.kill();
        }

        this.entity = McTactics.getMobBucket().spawn(this.entityType, location);
    }

    public void kill(){
        this.entity.remove();
        this.entity.setHealth(0);
    }

    public boolean isDead(){
        return this.entity.isDead();
    }

    public Location getLocation(){
        return this.entity.getLocation();
    }

    public void move(double x, double z){
        Vector multiply = new Vector(x, 0.0, z).normalize().multiply(0.4);
        this.entity.setRotation((float) (Math.atan2(x, z) * (180 / Math.PI) + 180), 0f);
        this.entity.setVelocity(multiply);
    }

    public void setTarget(EntityBattle target){
        this.target = target;
    }
    public EntityBattle getTarget() {
        return target;
    }

    public List<ActionGoal> getActionGoals(){
        return this.actionGoals;
    }

    public void damage(){
        this.entity.damage(1d);
    }

    /* ----------------+
    |        IA        |
    +-----------------*/

    public void setAllies(List<EntityBattle> allies) {
        this.allies = allies;
    }

    public List<EntityBattle> getAllies(){
        return this.allies;
    }

    public void setEnemies(List<EntityBattle> enemies) {
        this.enemies = enemies;
    }

    public List<EntityBattle> getEnemies() {
        return enemies;
    }

    public void tick(){
        for(TargetGoal targetGoal : targetGoals){
            if(targetGoal.tick(this))
                break;
        }
        for(ActionGoal actionGoal : actionGoals){
            if(actionGoal.tick(this))
                break;
        }
    }
}