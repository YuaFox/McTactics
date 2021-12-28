package dev.yuafox.mctactics.event;

import dev.yuafox.mctactics.arena.Arena;
import dev.yuafox.mctactics.arena.BoardLocation;
import dev.yuafox.mctactics.arena.MtPlayer;
import dev.yuafox.mctactics.arena.shop.ShopGui;
import dev.yuafox.mctactics.arena.shop.ShopPersonal;
import dev.yuafox.mctactics.entity.EntityBattle;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class PlayerEvents implements Listener {

    @EventHandler
    public void onInventoryClickItem(InventoryClickEvent event) {
        Inventory inventory = event.getClickedInventory();
        if(!ShopGui.getGuis().containsValue(inventory)) return;
        MtPlayer mtPlayer = Arena.arena.getPlayers().get(event.getWhoClicked());
        ShopPersonal shopPersonal = mtPlayer.getBoard().getShopPersonal();
        if(event.getSlot() < 5) {
            if (shopPersonal.buyEntity(event.getSlot())) {
                mtPlayer.getBukkitPlayer().playSound(mtPlayer.getBukkitPlayer().getLocation(), Sound.ENTITY_VILLAGER_TRADE, 1f, 1f);
            } else {
                mtPlayer.getBukkitPlayer().playSound(mtPlayer.getBukkitPlayer().getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f);
            }
        }else{
            shopPersonal.rerollStock();

        }

        event.setCancelled(true);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        if (e.getAction() == Action.RIGHT_CLICK_AIR) {
            MtPlayer mtPlayer = Arena.arena.getPlayers().get(e.getPlayer());
            mtPlayer.getBoard().getShopGui().show(e.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent e) {
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
        if(boardLocation != null){
            if(entityBattle.isAttached()) {
                entityBattle.attachPlayer(null);
                mtPlayer.getBoard().setEntity(entityBattle,boardLocation);
                mtPlayer.getBoard().prepareBoard(false, null);
            }else {
                entityBattle.attachPlayer(player);
                mtPlayer.getBoard().removeEntity(entityBattle);
            }
        }else{
            int benchLocation = mtPlayer.getBoard().getBenchLocation(entity.getLocation());
            if(benchLocation == -1) return;
            if(entityBattle.isAttached()) {
                entityBattle.attachPlayer(null);
                mtPlayer.getBoard().setEntityBench(entityBattle,benchLocation);
                mtPlayer.getBoard().prepareBoard(false, null);
            }else {
                entityBattle.attachPlayer(player);
                mtPlayer.getBoard().removeEntityBench(entityBattle);
            }
        }
    }
}
