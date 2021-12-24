package dev.yuafox.mctactics.arena.timeline;

import dev.yuafox.mctactics.arena.Arena;
import dev.yuafox.mctactics.arena.Board;

public abstract class Round {

    private final Arena arena;

    public Round(Arena arena){
        this.arena = arena;
    }

    public Arena getArena() {
        return arena;
    }

    public abstract int getTime();

    public abstract void start();
    public abstract void second(int time);
    public abstract void end();

}
