package dev.yuafox.mctactics.arena.timeline;

import dev.yuafox.mctactics.arena.Arena;

import java.util.LinkedList;
import java.util.List;

public class Timeline {

    List<Stage> stages;

    public Timeline(Arena arena){
        this.stages = new LinkedList<>();
    }

    public Timeline addStage(Stage stage) {
        this.stages.add(stage);
        return this;
    }


}
