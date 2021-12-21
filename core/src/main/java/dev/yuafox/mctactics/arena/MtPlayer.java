package dev.yuafox.mctactics.arena;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

public class MtPlayer {

    private Player player;
    private Board board;

    public MtPlayer(Player player){
        this.player = player;
        this.board = null;
    }

    public Player getBukkitPlayer(){
        return this.player;
    }

    public void setBoard(Board board){
        this.board = board;
    }

    @Nullable
    public Board getBoard() {
        return board;
    }
    public void teleportToBoard(){
        this.player.teleport(this.board.getTopCenter());
    }

}
