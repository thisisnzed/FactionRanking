package com.thisisnzed.factionranking.listener.impl;

import com.massivecraft.factions.event.FactionDisbandEvent;
import com.thisisnzed.factionranking.Ranking;
import com.thisisnzed.factionranking.storage.DatabaseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.stream.Stream;

public class FactionDisband implements Listener {

    private final DatabaseManager databaseManager;

    public FactionDisband(final Ranking ranking) {
        this.databaseManager = ranking.getDatabaseManager();
    }

    @EventHandler
    public void onFactionDisband(final FactionDisbandEvent event) {
        Stream.of("pvp", "farm", "global").forEach(table -> this.databaseManager.getSqlHandler().delete(event.getFaction().getTag(), "ranking_" + table));
    }
}
