package dev.yuafox.mctactics.arena.timeline;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Stage implements Iterable<Round> {

    private List<Round> rounds;

    public Stage(){
        this.rounds = new LinkedList<>();
    }

    public Stage addRound(Round round) {
        this.rounds.add(round);
        return this;
    }

    @NotNull
    @Override
    public Iterator<Round> iterator() {
        return rounds.iterator();
    }
}
