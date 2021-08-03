package com.nz1337.ranking.manager;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.storage.SQLHandler;

public class DatabaseManager {

    private final SQLHandler sqlHandler;

    public DatabaseManager(Ranking ranking) {
        this.sqlHandler = new SQLHandler(ranking);
    }

    public SQLHandler getSqlHandler() {
        return sqlHandler;
    }
}
