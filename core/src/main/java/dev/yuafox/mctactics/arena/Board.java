package dev.yuafox.mctactics.arena;

import dev.yuafox.mctactics.entity.EntityBattle;
import org.bukkit.Location;

import javax.annotation.Nullable;
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
        this.margin = 5D;

        this.corner1Location = cornerLocation;
        this.centerFlyLocation = cornerLocation.clone().add(entityMatrix.length*this.spacing/2, 4, entityMatrix[0].length*this.spacing/2);
        this.corner2Location = cornerLocation.clone().add(entityMatrix.length*this.spacing, 0, entityMatrix[0].length*this.spacing);
    }

    public Location getTopCenter() {
        return this.centerFlyLocation;
    }

    public void setEntity(EntityBattle entity, int x, int z){
        this.entityMatrix[x][z] = entity;
        this.entityList.add(entity);
    }
    public List<EntityBattle> getEntities(){
        return this.entityList;
    }

    public void prepareBoard(boolean reversed, @Nullable EntityBattle[][] entityMatrix){
        Location cornerLocation = reversed ? this.corner2Location : this.corner1Location;
        double spacing = reversed ? -this.spacing : this.spacing;
        if(entityMatrix == null)
            entityMatrix = this.entityMatrix;


        for(int x = 0; x < this.entityMatrix.length; x++){
            for(int z = 0; z < this.entityMatrix[0].length; z++){
                if(entityMatrix[x][z] != null) {
                    entityMatrix[x][z].spawn(
                            cornerLocation.clone().add(spacing + x * spacing, 0, spacing + z * spacing)
                    );
                }
            }
        }
    }

    public void prepareBoard(Board enemyBoard){
        this.prepareBoard(true, enemyBoard.entityMatrix);
    }
}
