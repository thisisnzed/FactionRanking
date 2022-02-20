package com.nz1337.ranking.manager;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.storage.SQLHandler;
import lombok.Getter;

public class DatabaseManager {

    @Getter
    private final SQLHandler sqlHandler;

    public DatabaseManager(final Ranking ranking) {
        this.sqlHandler = new SQLHandler(ranking);
    }
}
