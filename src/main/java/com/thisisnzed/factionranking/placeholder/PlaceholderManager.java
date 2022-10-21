package com.thisisnzed.factionranking.placeholder;

import com.thisisnzed.factionranking.Ranking;
import com.thisisnzed.factionranking.placeholder.impl.RankingExpansion;

public class PlaceholderManager {

    public PlaceholderManager(final Ranking ranking) {
        new RankingExpansion(ranking).register();
    }
}
