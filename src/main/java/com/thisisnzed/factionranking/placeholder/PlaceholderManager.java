package com.thisisnzed.factionranking.placeholder;

import com.thisisnzed.factionranking.placeholder.impl.RankingExpansion;
import com.thisisnzed.factionranking.sql.DatabaseManager;

public class PlaceholderManager {

    private final RankingExpansion rankingExpansion;

    public PlaceholderManager(final DatabaseManager databaseManager) {
        this.rankingExpansion = new RankingExpansion(databaseManager);
    }

    public void register() {
        this.rankingExpansion.register();
    }
}