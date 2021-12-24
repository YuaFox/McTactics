package dev.yuafox.mctactics.entity.collection;

import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class MobSet {

    private final Map<EntityType, List<MobData>> data;

    public MobSet(){
        this.data = new HashMap<>();
    }

    public void register(EntityType type, List<MobData> mobData){
        data.put(type, mobData);
    }

    @Nullable
    public MobData getStats(EntityType type, int level){
        List<MobData> mobData = data.get(type);
        if(mobData == null) return null;
        if(mobData.size() <= level) return null;
        return mobData.get(level);
    }
}