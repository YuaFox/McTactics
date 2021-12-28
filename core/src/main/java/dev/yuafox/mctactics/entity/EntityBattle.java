package dev.yuafox.mctactics.entity;

import dev.yuafox.mctactics.McTactics;
import dev.yuafox.mctactics.arena.Arena;
import dev.yuafox.mctactics.arena.Board;
import dev.yuafox.mctactics.arena.BoardLocation;
import dev.yuafox.mctactics.arena.battle.DamageType;
import dev.yuafox.mctactics.entity.collection.MobData;
import dev.yuafox.mctactics.ai.action.ActionGoal;
import dev.yuafox.mctactics.ai.action.AttackIfRangeGoal;
import dev.yuafox.mctactics.ai.action.MoveToEntityGoal;
import dev.yuafox.mctactics.ai.target.BasicTargetGoal;
import dev.yuafox.mctactics.ai.target.TargetGoal;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class EntityBattle {

    private static final Map<LivingEntity, EntityBattle> entityMap = new HashMap<>();

    @Nullable
    public static EntityBattle getEntityBattle(LivingEntity entity){
        return entityMap.get(entity);
    }

    private final EntityType entityType;
    private LivingEntity entity;

    private EntityBattle target;

    private final List<TargetGoal> targetGoals;
    private final List<ActionGoal> actionGoals;

    private List<EntityBattle> allies;
    private List<EntityBattle> enemies;

    // Stats
    private double health_max;
    private double health_current;

    private double damage;
    private double attack_delay;
    private double attack_next;

    private double armor;

    private Player attach;
    private int task;


    // Location
    private Board board;
    private BoardLocation boardLocation;
    private int benchLocation;

    private List<ItemStack> drops;

    public EntityBattle(EntityType entityType) {
        this(entityType, null);
    }

    public EntityBattle(EntityType entityType, Board board) {
        this.entityType = entityType;
        this.entity = null;
        this.target = null;
        this.targetGoals = new LinkedList<>();
        this.actionGoals = new LinkedList<>();

        this.board = board;

        this.drops = new LinkedList<>();

        this.benchLocation = -1;

        // Test
        this.targetGoals.add(new BasicTargetGoal());
        this.actionGoals.add(new AttackIfRangeGoal());
        this.actionGoals.add(new MoveToEntityGoal());
    }

    /* ----------------+
    |       Mob        |
    +-----------------*/
    public EntityType getEntityType() {
        return this.entityType;
    }

    public void spawn(Location location){
        if(this.entity != null){
            this.kill(null, false, false);
        }

        this.entity = McTactics.getMobBucket().spawn(this.entityType, location);
        this.entity.setMaxHealth(1500);
        this.entity.setHealth(1500);
        this.entity.setCustomNameVisible(true);

        MobData stats = McTactics.SET_TEST.getData(entityType, 0);
        this.health_max = stats.health();
        this.health_current = stats.health();
        this.damage = stats.damage();
        this.attack_delay = stats.attack_delay();
        this.attack_next = 0;
        this.armor = stats.armor();

        this.entity.setCustomName(McTactics.renderHeathBar((int)(this.health_current*100/this.health_max)));

        entityMap.put(this.entity, this);
    }

    public void kill(@Nullable EntityBattle killer, boolean agony, boolean dropLoot){
        if(agony){
            this.entity.damage(2000);
        }else {
            this.entity.remove();
            this.entity.setHealth(0);
        }

        if(dropLoot && !this.drops.isEmpty() && killer != null){
            McTactics.spawnFireworks(this.getLocation(), 0);
            Player bukkitPlayer = killer.getBoard().getPlayer().getBukkitPlayer();
            this.drops.forEach(bukkitPlayer.getInventory()::addItem);
            bukkitPlayer.playSound(bukkitPlayer.getLocation(), Sound.ENTITY_ITEM_PICKUP, 1f, 1f);
        }

        entityMap.remove(this.entity);
    }

    public boolean isDead(){
        return this.entity.isDead();
    }

    public Location getLocation(){
        return this.entity.getLocation();
    }
    public double getDistance(EntityBattle target){ return this.entity.getLocation().distance(target.entity.getLocation()); }

    public void move(double x, double z){
        Vector multiply = new Vector(x, 0.0, z).normalize().multiply(0.2);
        this.entity.setRotation((float) (Math.atan2(x, z) * (180 / Math.PI) + 180), 0f);
        this.entity.setVelocity(multiply);
    }

    public void setTarget(@Nullable EntityBattle target){
        this.target = target;
    }

    @Nullable
    public EntityBattle getTarget() {
        return target;
    }

    public List<ActionGoal> getActionGoals(){
        return this.actionGoals;
    }

    public void attachPlayer(Player player){
        this.attach = player;
        if(attach == null){
            this.entity.setGravity(true);
            Bukkit.getScheduler().cancelTask(this.task);
        }else{
            this.entity.setGravity(false);
            this.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(McTactics.PLUGIN, new Runnable(){
                @Override
                public void run() {
                    entity.teleport(player.getLocation().add(player.getLocation().add(0,1.5,0).getDirection().multiply(new Vector(2, 0, 2))));
                }
            }, 0L, 1L);

        }
    }

    public boolean isAttached(){
        return this.attach != null;
    }

    /* ----------------+
    |       Drops      |
    +-----------------*/

    public EntityBattle addDrop(ItemStack itemStack){
        this.drops.add(itemStack);
        return this;
    }

    /* ----------------+
    |      Location    |
    +-----------------*/

    public BoardLocation getBoardLocation() {
        return boardLocation;
    }

    public void setBoardLocation(BoardLocation boardLocation) {
        this.boardLocation = boardLocation;
    }

    public Board getBoard(){
        return this.board;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    public int getBenchLocation() {
        return this.benchLocation;
    }

    public void setBenchLocation(int benchLocation) {
        this.benchLocation = benchLocation;
    }

    /* ----------------+
    |      Combat      |
    +-----------------*/

    @ApiStatus.Internal
    public void damage(@Nullable EntityBattle source, DamageType type, double amount){
        double amount_mod = switch (type) {
            case PHYSICAL -> amount * (100d / (100d + this.armor));
            default -> amount;
        };
        this.health_current -= amount_mod;
        this.entity.setCustomName(McTactics.renderHeathBar((int)(this.health_current*100/this.health_max)));
        this.entity.damage(1);
        if(this.health_current <= 0) this.kill(source,true, true);
    }

    public boolean basicAttack(EntityBattle target, int tick){
        if(tick >= this.attack_next){
            target.damage(this, DamageType.PHYSICAL, this.damage);
            this.attack_next = tick + this.attack_delay;
            return true;
        }else return false;
    }

    public double getBasicAttackRange(){
        return 2;
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

    public void tick(int currentTick){
        for(TargetGoal targetGoal : targetGoals){
            if(targetGoal.tick(this, currentTick))
                break;
        }
        for(ActionGoal actionGoal : actionGoals){
            if(actionGoal.tick(this, currentTick))
                break;
        }
    }
}