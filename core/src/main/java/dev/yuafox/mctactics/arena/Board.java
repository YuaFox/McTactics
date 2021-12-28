package dev.yuafox.mctactics.arena;

import dev.yuafox.mctactics.McTactics;
import dev.yuafox.mctactics.arena.shop.ShopGui;
import dev.yuafox.mctactics.arena.shop.ShopPersonal;
import dev.yuafox.mctactics.entity.EntityBattle;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private MtPlayer player;

    private final Location corner1Location;
    private final Location centerFlyLocation;
    private final Location corner2Location;

    private final EntityBattle[][] entityMatrix;
    private final List<EntityBattle> entityList;
    private final double spacing;
    private final double margin;

    private final ShopPersonal shopPersonal;
    private final ShopGui shopGui;
    private final EntityBattle[] benchMatrix;
    private final List<EntityBattle> benchList;

    public Board(@Nullable MtPlayer player, Location cornerLocation){
        this.player = player;

        this.entityMatrix = new EntityBattle[8][4];
        this.entityList = new ArrayList<>();
        this.spacing = 2D;
        this.margin = 4D;

        this.corner1Location = cornerLocation;
        this.centerFlyLocation = cornerLocation.clone().add(entityMatrix.length*this.spacing/2, 4, entityMatrix[0].length*this.spacing/2);
        this.corner2Location = cornerLocation.clone().add(entityMatrix.length*this.spacing, 0, entityMatrix[0].length*this.spacing*2+this.margin*2+this.spacing);

        this.shopPersonal = new ShopPersonal(Arena.arena.shop, this);
        this.shopGui = ShopGui.createShop(this.shopPersonal);

        this.benchMatrix = new EntityBattle[8];
        this.benchList = new ArrayList<>();
    }

    /*-----------------+
    |      Bench       |
    +-----------------*/

    public boolean addEntityBench(@NotNull EntityBattle entity){
        for (int x = 0; x < this.entityMatrix.length; x++) {
            if (benchMatrix[x] == null) {
                this.setEntityBench(entity, x);
                return true;
            }
        }
        return false;
    }

    public void setEntityBench(@NotNull EntityBattle entity, int x){
        this.benchMatrix[x] = entity;
        this.benchList.add(entity);
        entity.setBenchLocation(x);
        entity.setBoard(this);
    }

    public boolean removeEntityBench(EntityBattle entity){
        if(this.benchList.contains(entity)) {
            this.benchList.remove(entity);
            this.benchMatrix[ entity.getBenchLocation()] = null;
            return true;
        }else{
            return false;
        }
    }

    public int getBenchLocation(Location location){
        Location result = location.clone().subtract(this.corner1Location).add(-1, 0, -2);

        result.multiply(1D/this.spacing);
        int x = (int) result.getX();
        if(x >= 0 && Math.abs(result.getZ()) < 0.5f)
            return x;
        else return -1;
    }

    public Location getTopCenter() {
        return this.centerFlyLocation;
    }

    @Nullable
    public BoardLocation getBoardLocation(Location location){
        Location result = location.clone().subtract(this.corner1Location).subtract(1,0,this.margin+1);
        result.multiply(1D/this.spacing);
        double x = result.getX();
        double z = result.getZ();
        if(x >= 0d && z >= 0d)
            return new BoardLocation((int)x, (int)z);
        else return null;
    }

    public void setEntity(EntityBattle entity, int x, int z){
        this.setEntity(entity, new BoardLocation(x, z));
    }

    public void setEntity(@NotNull EntityBattle entity, BoardLocation location){
        this.entityMatrix[location.x()][location.z()] = entity;
        this.entityList.add(entity);
        entity.setBoardLocation(location);
    }

    public boolean removeEntity(EntityBattle entity){
        if(this.entityList.contains(entity)) {
            this.entityList.remove(entity);
            BoardLocation boardLocation = entity.getBoardLocation();
            this.entityMatrix[boardLocation.x()][boardLocation.z()] = null;
            return true;
        }else{
            return false;
        }
    }

    public MtPlayer getPlayer(){
        return this.player;
    }

    public List<EntityBattle> getEntities(){
        return this.entityList;
    }

    @NotNull
    public ShopGui getShopGui() {
        return this.shopGui;
    }

    @NotNull
    public ShopPersonal getShopPersonal() {
        return this.shopPersonal;
    }

    public void prepareBoard(boolean reversed, @Nullable EntityBattle[][] entityMatrix) {
        Location cornerLocation = reversed ? this.corner2Location : this.corner1Location;
        double spacing = reversed ? -this.spacing : this.spacing;
        double margin = reversed ? -this.margin : this.margin;
        if (entityMatrix == null)
            entityMatrix = this.entityMatrix;


        for (int x = 0; x < this.entityMatrix.length; x++) {
            for (int z = 0; z < this.entityMatrix[0].length; z++) {
                if (entityMatrix[x][z] != null) {
                    entityMatrix[x][z].spawn(
                            cornerLocation.clone().add(spacing + x * spacing, 0, margin + spacing + z * spacing)
                    );
                }
            }
        }
        if(!reversed)
        for (int x = 0; x < this.entityMatrix.length; x++) {
            if (benchMatrix[x] != null) {
                benchMatrix[x].spawn(
                        cornerLocation.clone().add(spacing + x * spacing, 0, spacing)
                );
            }
        }
    }

    public void prepareBoard(Board enemyBoard){
        this.prepareBoard(true, enemyBoard.entityMatrix);
    }
    public void prepareBoard(){
        this.prepareBoard(false, null);
    }
}
