package com.nz1337.ranking.manager;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.tasks.RankUpdater;
import lombok.Getter;

public class TaskManager {

    @Getter
    private final RankUpdater rankUpdater;

    public TaskManager(final Ranking ranking) {
        this.rankUpdater = new RankUpdater(ranking);
    }
}
