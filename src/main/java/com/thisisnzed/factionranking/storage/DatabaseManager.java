package com.thisisnzed.factionranking.storage;

import com.thisisnzed.factionranking.Ranking;
import com.thisisnzed.factionranking.storage.impl.SQLHandler;
import lombok.Getter;

public class DatabaseManager {

    @Getter
    private final SQLHandler sqlHandler;

    public DatabaseManager(final Ranking ranking) {
        this.sqlHandler = new SQLHandler(ranking);
    }
}
