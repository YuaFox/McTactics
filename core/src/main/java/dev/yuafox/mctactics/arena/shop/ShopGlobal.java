package dev.yuafox.mctactics.arena.shop;

import dev.yuafox.mctactics.McTactics;
import dev.yuafox.mctactics.entity.EntityBattle;
import dev.yuafox.mctactics.entity.Rarity;
import dev.yuafox.mctactics.entity.collection.MobSet;

import java.util.*;

public class ShopGlobal {

    Map<Rarity, List<EntityBattle>> shopElements = new HashMap<>();

    public ShopGlobal(MobSet set){
        Arrays.stream(Rarity.values()).forEach(
                (r) -> {
                    List<EntityBattle> l = new ArrayList<>();
                    int poolSize = r.getPoolSize();
                    set.forEach(
                            (e, d) -> {
                                for(int i = 0; i < poolSize; i++)
                                    l.add(new EntityBattle(e));
                            }
                    );
                    shopElements.put(r, l);
                }
        );
    }

    public synchronized EntityBattle[] getSomeSock(){
        EntityBattle[] entities = new EntityBattle[5];
        Random random = new Random();
        for(int i = 0; i < 5; i++) {
            List<EntityBattle> entityBattles = shopElements.get(Rarity.COMMON);
            entities[i] = entityBattles.remove(random.nextInt(entityBattles.size()));
        }
        return entities;
    }

    public synchronized void putSomeSock(EntityBattle[] entities){
        for(int i = 0; i < 5; i++) {
            if(entities[i] != null){
                this.shopElements
                        .get(McTactics.SET_TEST.getData(entities[i].getEntityType(), 0).rarity())
                        .add(entities[i]);
            }
        }
    }
}
