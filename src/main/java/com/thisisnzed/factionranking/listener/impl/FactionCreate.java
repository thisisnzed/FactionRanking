package com.thisisnzed.factionranking.listener.impl;

import com.massivecraft.factions.event.FactionCreateEvent;
import com.thisisnzed.factionranking.Ranking;
import com.thisisnzed.factionranking.storage.DatabaseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.stream.Stream;

public class FactionCreate implements Listener {

    private final DatabaseManager databaseManager;

    public FactionCreate(final Ranking ranking) {
        this.databaseManager = ranking.getDatabaseManager();
    }

    @EventHandler
    public void onFactionCreate(final FactionCreateEvent event) {
        Stream.of("pvp", "farm", "global").forEach(table -> this.databaseManager.getSqlHandler().register(event.getFaction().getTag(), "ranking_" + table));
    }
}