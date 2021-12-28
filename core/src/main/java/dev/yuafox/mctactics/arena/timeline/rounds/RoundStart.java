package dev.yuafox.mctactics.arena.timeline.rounds;

import dev.yuafox.mctactics.arena.Arena;
import dev.yuafox.mctactics.arena.Board;
import dev.yuafox.mctactics.arena.timeline.Round;
import dev.yuafox.mctactics.entity.EntityBattle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class RoundStart extends Round {


    public RoundStart(Arena arena) {
        super(arena);
    }

    @Override
    public int getTime() {
        return 30;
    }

    @Override
    public void start() {
        this.getArena().getPlayers().forEach((p,v) -> {
            Board board = new Board(v, new Location(p.getWorld(), 0.5, -60, 0.5));
            board.setEntity(new EntityBattle(EntityType.FOX, board), 0, 0);
            board.setEntity(new EntityBattle(EntityType.FOX, board), 1, 0);

            v.setBoard(board);
            v.teleportToBoard();
            board.prepareBoard(false, null);

            // Shop
            board.getShopPersonal().getStock();
        });
    }

    @Override
    public void second(int time) {
        this.getArena().bossBarStatus(ChatColor.BLUE+"GAME STARTING...", time, 30);
    }

    @Override
    public void end() {

    }
}
