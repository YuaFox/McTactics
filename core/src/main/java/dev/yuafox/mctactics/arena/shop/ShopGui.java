package dev.yuafox.mctactics.arena.shop;

import dev.yuafox.mctactics.McTactics;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ShopGui {

    private static Map<ShopGui, Inventory> guis = new HashMap<>();

    public static Map<ShopGui, Inventory> getGuis(){
        return guis;
    }

    public static ShopGui createShop(ShopPersonal shopPersonal){
        ShopGui shopGui = new ShopGui(shopPersonal);
        guis.put(shopGui, shopGui.inventory);
        return  shopGui;
    }

    private ShopPersonal shopPersonal;
    private Inventory inventory;


    private ShopGui(ShopPersonal shopPersonal){
        this.shopPersonal = shopPersonal;
        this.inventory = Bukkit.createInventory(null, 9, "Shop");
    }

    public ShopPersonal getShopPersonal(){
        return this.shopPersonal;
    }

    public void show(Player player){
        this.render();
        player.openInventory(inventory);
    }

    public void render(){
        this.inventory.clear();
        AtomicInteger i = new AtomicInteger();
        Arrays.stream(this.shopPersonal.getEntities()).forEach(
                (e) -> {
                    if(e != null){
                        ItemStack itemStack = new ItemStack(Material.CHICKEN_SPAWN_EGG);
                        itemStack.setAmount(McTactics.SET_TEST.getData(e.getEntityType(),0).rarity().getPrice());
                        ItemMeta itemMeta = itemStack.getItemMeta();
                        itemMeta.setDisplayName(e.getEntityType().toString());
                        itemStack.setItemMeta(itemMeta);
                        this.inventory.setItem(i.get(), itemStack);
                    }
                    i.getAndIncrement();
                }
        );
        this.inventory.setItem(7, new ItemStack(Material.EXPERIENCE_BOTTLE));
        this.inventory.setItem(8, new ItemStack(Material.CHEST));
    }
}
