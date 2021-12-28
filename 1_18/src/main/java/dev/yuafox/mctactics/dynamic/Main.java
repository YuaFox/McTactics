package dev.yuafox.mctactics.dynamic;

import dev.yuafox.mctactics.McTactics;
import dev.yuafox.mctactics.dynamic.entity.EntityBucket1;
import dev.yuafox.mctactics.dynamic.entity.EntityChickenCustom;
import dev.yuafox.mctactics.arena.Arena;
import dev.yuafox.mctactics.arena.MtPlayer;
import dev.yuafox.mctactics.dynamic.entity.EntityFoxCustom;
import dev.yuafox.mctactics.entity.Rarity;
import dev.yuafox.mctactics.entity.collection.MobData;
import dev.yuafox.mctactics.entity.collection.MobSet;
import dev.yuafox.mctactics.event.MobDropEvent;
import dev.yuafox.mctactics.event.PlayerEvents;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.item.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class Main extends JavaPlugin implements CommandExecutor, Listener {

    @Override
    public void onEnable() {
        McTactics.PLUGIN = this;
        McTactics.LOGGER = this.getLogger();
        McTactics.BUKKIT_VERSION_CODE = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        Bukkit.getPluginManager().registerEvents(new PlayerEvents(),this);
        Bukkit.getPluginManager().registerEvents(new MobDropEvent(),this);

        EntityBucket1 bucket = new EntityBucket1();
        bucket.register(EntityType.FOX, EntityFoxCustom.class);
        bucket.register(EntityType.CHICKEN, EntityChickenCustom.class);
        McTactics.registerMobBucket(bucket);

        McTactics.SET_TEST = new MobSet();
        McTactics.SET_TEST.register(EntityType.FOX, Arrays.asList(new MobData(Rarity.COMMON, 20, 5, 30, 20)));
        McTactics.SET_TEST.register(EntityType.CHICKEN, Arrays.asList(new MobData(Rarity.COMMON,10, 2, 15, 15)));
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
