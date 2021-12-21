package dev.yuafox.mctactics.arena.battle;

import dev.yuafox.mctactics.McTactics;
import dev.yuafox.mctactics.entity.EntityBattle;
import dev.yuafox.mctactics.arena.Board;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;

public class Battle {

    private final Board board1;
    private final Board board2;

    List<EntityBattle> board1Alive;
    List<EntityBattle> board2Alive;

    int taskId;

    public Battle(Board board1, Board board2){
        this.board1 = board1;
        this.board2 = board2;
    }

    public void prepare(){
        board1.prepareBoard(false, null);
        board1.prepareBoard(board2);

        this.board1Alive = new ArrayList<>(board1.getEntities());
        this.board2Alive = new ArrayList<>(board2.getEntities());

        System.out.println(board1Alive.toString());
        System.out.println(board2Alive.toString());

        board1.getEntities().forEach((e) -> {
            e.setAllies(board1Alive);
            e.setEnemies(board2Alive);
        });

        board2.getEntities().forEach((e) -> {
            e.setAllies(board2Alive);
            e.setEnemies(board1Alive);
        });

        this.taskId = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(McTactics.PLUGIN, () -> {
            board1Alive.forEach(EntityBattle::tick);
            board2Alive.forEach(EntityBattle::tick);
        }, 0L, 1L);

    }

    public void end(){
        Bukkit.getServer().getScheduler().cancelTask(taskId);
    }

}
