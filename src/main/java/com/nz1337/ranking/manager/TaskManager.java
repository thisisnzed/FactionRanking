package com.nz1337.ranking.manager;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.tasks.RankUpdater;

public class TaskManager {

    private final RankUpdater rankUpdater;

    public TaskManager(Ranking ranking) {
        this.rankUpdater = new RankUpdater(ranking);
    }

    public RankUpdater getRankUpdater() {
        return this.rankUpdater;
    }

}
