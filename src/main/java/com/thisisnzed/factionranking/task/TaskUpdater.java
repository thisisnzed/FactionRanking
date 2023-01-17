package com.thisisnzed.factionranking.task;

import com.thisisnzed.factionranking.sql.DatabaseManager;
import com.thisisnzed.factionranking.sql.impl.RankingHandler;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.atomic.AtomicInteger;

public class TaskUpdater extends BukkitRunnable {

    private final DatabaseManager databaseManager;

    public TaskUpdater(final DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public void run() {
        final RankingHandler rankingHandler = this.databaseManager.getRankingHandler();
        final AtomicInteger position = new AtomicInteger(1);
        rankingHandler.getAllFactions().forEach(factionName -> rankingHandler.setRank(factionName, position.getAndIncrement()));
    }
}