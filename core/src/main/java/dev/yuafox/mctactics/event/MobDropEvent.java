package dev.yuafox.mctactics.event;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobDropEvent implements Listener {

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event){
        event.getDrops().clear();
        event.setDroppedExp(0);
    }

}
