package dev.yuafox.mctactics.arena.shop;

import dev.yuafox.mctactics.McTactics;
import dev.yuafox.mctactics.arena.Board;
import dev.yuafox.mctactics.entity.EntityBattle;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.jetbrains.annotations.Nullable;

public class ShopPersonal {

    private final ShopGlobal shop;
    private final Board board;

    private EntityBattle[] entities;

    public ShopPersonal(ShopGlobal shop, Board board){
        this.shop = shop;
        this.board = board;
    }

    public void getStock(){
        this.entities = this.shop.getSomeSock();
    }
    private void removeStock(){
        this.shop.putSomeSock(this.entities);
    }

    public void rerollStock(){
        PlayerInventory inventory = this.board.getPlayer().getBukkitPlayer().getInventory();
        if(inventory.contains(Material.EMERALD, 2)) {
            inventory.removeItem(new ItemStack(Material.EMERALD, 2));
            this.removeStock();
            this.getStock();
            this.board.getShopGui().render();
        }
    }

    public EntityBattle[] getEntities(){
        return this.entities;
    }

    public boolean buyEntity(int slot){
        if(slot < 0 || slot >= 5) return false;
        EntityBattle entity = this.entities[slot];

        if(entity != null){
            int price = McTactics.SET_TEST.getData(entity.getEntityType(), 0).rarity().getPrice();
            PlayerInventory inventory = this.board.getPlayer().getBukkitPlayer().getInventory();

            if(inventory.contains(Material.EMERALD, price)){

                boolean added = this.board.addEntityBench(entity);
                if(added) {
                    inventory.removeItem(new ItemStack(Material.EMERALD, price));
                    this.entities[slot] = null;
                    this.board.getShopGui().render();
                    this.board.prepareBoard();
                    return true;
                }else return false;
            }else return false;
        }else{
            return false;
        }
    }

}
