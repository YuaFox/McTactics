package dev.yuafox.mctactics.arena.timeline.rounds;

import dev.yuafox.mctactics.arena.Arena;
import dev.yuafox.mctactics.arena.MtPlayer;
import dev.yuafox.mctactics.arena.timeline.Round;
import dev.yuafox.mctactics.entity.EntityBattle;
import dev.yuafox.mctactics.arena.Board;
import dev.yuafox.mctactics.arena.battle.Battle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class RoundBattlePvE extends Round {

    private Map<MtPlayer, Battle> battles;

    public RoundBattlePvE(Arena arena) {
        super(arena);
        battles = new HashMap<>(8);
    }

    @Override
    public int getTime() {
        return 60;
    }

    @Override
    public void start() {
        this.getArena().getPlayers().forEach((p,v) -> {
            Board enemy = new Board(null, new Location(Bukkit.getWorld("world"), 0, 0, 0));
            enemy.setEntity(new EntityBattle(EntityType.CHICKEN, this.getArena(), null).addDrop(new ItemStack(Material.EMERALD)), 0, 0);
            enemy.setEntity(new EntityBattle(EntityType.CHICKEN, this.getArena(), null), 1, 0);
            enemy.setEntity(new EntityBattle(EntityType.CHICKEN, this.getArena(), null), 0, 1);

            Battle battle = new Battle(v.getBoard(), enemy);
            battle.prepare();
            battles.put(v, battle);
        });


    }

    @Override
    public void second(int time) {
        if(time == 30) {
            this.battles.values().forEach(Battle::start);
        }

        if(time > 30){
            this.getArena().bossBarStatus(ChatColor.RED+"PREPARING", time-30, 30);
        }else{
            this.getArena().bossBarStatus(ChatColor.RED+"BATTLE", time, 30);
        }

    }

    @Override
    public void end() {
        this.battles.values().forEach(Battle::end);
    }
}
