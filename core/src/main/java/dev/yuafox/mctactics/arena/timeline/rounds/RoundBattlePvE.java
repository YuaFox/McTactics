package dev.yuafox.mctactics.arena.timeline.rounds;

import dev.yuafox.mctactics.arena.Arena;
import dev.yuafox.mctactics.arena.timeline.Round;
import dev.yuafox.mctactics.entity.EntityBattle;
import dev.yuafox.mctactics.arena.Board;
import dev.yuafox.mctactics.arena.battle.Battle;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

public class RoundBattlePvE extends Round {

    private Battle battle;

    public RoundBattlePvE(Arena arena) {
        super(arena);
    }


    @Override
    public int getTime() {
        return 30;
    }

    @Override
    public void start() {
        this.getArena().getPlayers().forEach((p,v) -> {
            Board enemy = new Board(new Location(p.getWorld(), 0, -60, 0));
            enemy.setEntity(new EntityBattle(EntityType.CHICKEN), 0, 0);
            this.battle = new Battle(v.getBoard(), enemy);
            this.battle.prepare();
        });
    }

    @Override
    public void second(int time) {
        this.getArena().bossBarStatus(ChatColor.RED+"BATTLE", time, 30);
    }

    @Override
    public void end() {
        this.battle.end();
    }
}
