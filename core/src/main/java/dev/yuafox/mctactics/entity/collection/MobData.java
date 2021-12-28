package dev.yuafox.mctactics.entity.collection;

import dev.yuafox.mctactics.entity.Rarity;

public record MobData(Rarity rarity, double health, double damage, double attack_delay, double armor) {}