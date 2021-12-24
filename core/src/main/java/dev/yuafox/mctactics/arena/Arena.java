package dev.yuafox.mctactics.arena;

import dev.yuafox.mctactics.McTactics;
import dev.yuafox.mctactics.arena.timeline.Round;
import dev.yuafox.mctactics.arena.timeline.Stage;
import dev.yuafox.mctactics.arena.timeline.rounds.RoundBattlePvE;
import dev.yuafox.mctactics.arena.timeline.rounds.RoundStart;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Arena {

    public static Arena arena;

    private final Map<Player, MtPlayer> players;
    private ArenaStatus status;

    private BossBar bossBar;

    private int task;

    public Arena(){
        this.players = new HashMap<>();
        status = ArenaStatus.CREATE;
        this.bossBar = Bukkit.createBossBar("countdown", BarColor.PURPLE, BarStyle.SOLID);
        bossBar.setVisible(true);

        arena = this;
    }

    public void addPlayer(MtPlayer player){
        players.put(player.getBukkitPlayer(), player);
    }

    public Map<Player, MtPlayer> getPlayers() {
        return players;
    }

    public void broadcastMessage(String message){
        players.forEach((p,v) -> p.sendMessage(message));
    }
    public void bossBarStatus(String phase, int countdown, int maxCountdown){
        bossBar.setTitle(phase+ChatColor.RESET+" "+countdown+"s");
        bossBar.setProgress((float)countdown/(float)maxCountdown);
    }

    public void startGame() {
        this.broadcastMessage("Starting!");
        this.status = ArenaStatus.COUNTDOWN;
        players.forEach((p,v) -> {
            bossBar.addPlayer(p);
        });

        Stage stage = new Stage()
                .addRound(new RoundStart(this))
                .addRound(new RoundBattlePvE(this))
                .addRound(new RoundBattlePvE(this));

        this.task = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(McTactics.PLUGIN, new Runnable(){

            private Iterator<Round> roundIterator = stage.iterator();
            private Round roundCurrent = null;
            private int i = 0;

            @Override
            public void run() {
                if(i == 0){
                    if(roundCurrent != null)
                        roundCurrent.end();

                    if(roundIterator.hasNext()){
                        roundCurrent = roundIterator.next();
                        roundCurrent.start();
                        i = roundCurrent.getTime();
                    }
                }
                if(roundCurrent != null)
                    roundCurrent.second(i);
                else
                    broadcastMessage("Ended!");
                --this.i;
            }
        }, 0L, 20L);
    }
}
