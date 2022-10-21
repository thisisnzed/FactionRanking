package com.thisisnzed.factionranking.task;

import com.thisisnzed.factionranking.Ranking;
import com.thisisnzed.factionranking.task.impl.RankUpdater;
import lombok.Getter;

public class TaskManager {

    @Getter
    private final RankUpdater rankUpdater;

    public TaskManager(final Ranking ranking) {
        this.rankUpdater = new RankUpdater(ranking);
    }
}
