package dev.yuafox.mctactics.event;

import dev.yuafox.mctactics.arena.Arena;
import dev.yuafox.mctactics.arena.BoardLocation;
import dev.yuafox.mctactics.arena.MtPlayer;
import dev.yuafox.mctactics.entity.EntityBattle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PlayerHitEvent implements Listener {

    @EventHandler
    public void onPlayerAttacked(EntityDamageByEntityEvent e) {
        if(e.getDamager().getType() != EntityType.PLAYER) return;
        Player player = (Player) e.getDamager();
        Entity entity = e.getEntity();
        if(!(entity instanceof LivingEntity)) return;
        EntityBattle entityBattle = EntityBattle.getEntityBattle((LivingEntity) e.getEntity());
        MtPlayer mtPlayer = Arena.arena.getPlayers().get(player);
        if(mtPlayer == null || entityBattle == null) return;
        e.setCancelled(true);
        if(mtPlayer.getBoard() != null && mtPlayer.getBoard() != entityBattle.getBoard()) return;
        BoardLocation boardLocation = mtPlayer.getBoard().getBoardLocation(entity.getLocation());
        if(boardLocation == null) return;

        if(entityBattle.isAttached()) {
            entityBattle.attachPlayer(null);
            mtPlayer.getBoard().setEntity(entityBattle,boardLocation);
            mtPlayer.getBoard().prepareBoard(false, null);
        }else {
            entityBattle.attachPlayer(player);
            mtPlayer.getBoard().removeEntity(entityBattle);
        }
    }
}
