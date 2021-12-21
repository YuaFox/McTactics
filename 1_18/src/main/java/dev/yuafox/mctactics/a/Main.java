package dev.yuafox.mctactics.a;

import dev.yuafox.mctactics.McTactics;
import dev.yuafox.mctactics.a.entity.EntityBucket1;
import dev.yuafox.mctactics.a.entity.EntityChickenCustom;
import dev.yuafox.mctactics.arena.Arena;
import dev.yuafox.mctactics.arena.MtPlayer;
import dev.yuafox.mctactics.a.entity.EntityFoxCustom;
import dev.yuafox.mctactics.entity.Mob;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class Main extends JavaPlugin implements CommandExecutor, Listener {

    @Override
    public void onEnable() {
        McTactics.PLUGIN = this;
        McTactics.LOGGER = this.getLogger();
        McTactics.BUKKIT_VERSION_CODE = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        Bukkit.getPluginManager().registerEvents(this,this);

        EntityBucket1 bucket = new EntityBucket1();
        bucket.register(EntityType.FOX, EntityFoxCustom.class);
        bucket.register(EntityType.CHICKEN, EntityChickenCustom.class);
        McTactics.registerMobBucket(bucket);
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;

        Arena arena = new Arena();
        arena.addPlayer(new MtPlayer(p));
        arena.startGame();

        return false;
    }
}
