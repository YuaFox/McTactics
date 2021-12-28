package dev.yuafox.mctactics.entity;

public enum Rarity {
    COMMON(29, 1), UNCOMMON(22, 2), RARE(18, 3), EPIC(12, 4), LEGENDARY(10, 5);

    private final int poolSize;
    private final int price;

    Rarity(int poolSize, int price){
        this.poolSize = poolSize;
        this.price = price;
    }

    public int getPoolSize(){ return this.poolSize; }
    public int getPrice(){ return this.price; }
}
