package com.nz1337.ranking.manager;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.placeholder.RankingExpansion;

public class PlaceholderManager {

    public PlaceholderManager(Ranking ranking) {
        new RankingExpansion(ranking).register();
    }
}
