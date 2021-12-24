package dev.yuafox.mctactics.arena;

import dev.yuafox.mctactics.entity.EntityBattle;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Board {

    private final Location corner1Location;
    private final Location centerFlyLocation;
    private final Location corner2Location;

    private final EntityBattle[][] entityMatrix;
    private final List<EntityBattle> entityList;
    private final double spacing;
    private final double margin;

    public Board(Location cornerLocation){
        this.entityMatrix = new EntityBattle[8][4];
        this.entityList = new ArrayList<>();
        this.spacing = 2D;
        this.margin = 4D;

        this.corner1Location = cornerLocation;
        this.centerFlyLocation = cornerLocation.clone().add(entityMatrix.length*this.spacing/2, 4, entityMatrix[0].length*this.spacing/2);
        this.corner2Location = cornerLocation.clone().add(entityMatrix.length*this.spacing, 0, entityMatrix[0].length*this.spacing*2+this.margin*2+this.spacing);
    }

    public Location getTopCenter() {
        return this.centerFlyLocation;
    }

    @Nullable
    public BoardLocation getBoardLocation(Location location){
        Location result = location.clone().subtract(this.corner1Location).subtract(0,0,this.margin);
        result.multiply(1D/this.spacing);
        int x = (int) result.getX();
        int z = (int) result.getZ();
        if(x >= 0 && z >= 0)
            return new BoardLocation(x, z);
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

    public List<EntityBattle> getEntities(){
        return this.entityList;
    }

    public void prepareBoard(boolean reversed, @Nullable EntityBattle[][] entityMatrix){
        Location cornerLocation = reversed ? this.corner2Location : this.corner1Location;
        double spacing = reversed ? -this.spacing : this.spacing;
        double margin = reversed ? -this.margin : this.margin;
        if(entityMatrix == null)
            entityMatrix = this.entityMatrix;


        for(int x = 0; x < this.entityMatrix.length; x++){
            for(int z = 0; z < this.entityMatrix[0].length; z++){
                if(entityMatrix[x][z] != null) {
                    entityMatrix[x][z].spawn(
                            cornerLocation.clone().add(spacing + x * spacing, 0, margin + spacing + z * spacing)
                    );
                }
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
