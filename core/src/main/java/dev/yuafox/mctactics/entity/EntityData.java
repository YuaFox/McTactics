package dev.yuafox.mctactics.entity;

public class EntityData {
    public double HEALTH_CURRENT;
    public double HEALTH_MAX;
    public double MANA_CURRENT;
    public double MANA_MAX;
    public double ATTACK_DAMAGE;
    public double ATTACK_SPEED;
    public double ATTACK_RANGE;
    public double POWER;
    public double ARMOR;
    public double RESIST;

    public EntityData(double HEALTH_CURRENT,
     double HEALTH_MAX,
     double MANA_CURRENT,
     double MANA_MAX,
     double ATTACK_DAMAGE,
     double ATTACK_SPEED,
     double ATTACK_RANGE,
     double POWER,
     double ARMOR,
     double RESIST){
        this.HEALTH_CURRENT = HEALTH_CURRENT;
        this.HEALTH_MAX = HEALTH_MAX;
        this.MANA_CURRENT = MANA_CURRENT;
        this.MANA_MAX = MANA_MAX;
        this.ATTACK_DAMAGE = ATTACK_DAMAGE;
        this.ATTACK_SPEED = ATTACK_SPEED;
        this.ATTACK_RANGE = ATTACK_RANGE;
        this.POWER = POWER;
        this.ARMOR = ARMOR;
        this.RESIST = RESIST;
    }
}
