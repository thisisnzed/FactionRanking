package com.thisisnzed.factionranking.listener.impl;

import com.massivecraft.factions.event.FactionRenameEvent;
import com.thisisnzed.factionranking.Ranking;
import com.thisisnzed.factionranking.storage.DatabaseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.stream.Stream;

public class FactionRename implements Listener {

    private final DatabaseManager databaseManager;

    public FactionRename(final Ranking ranking) {
        this.databaseManager = ranking.getDatabaseManager();
    }

    @EventHandler
    public void onFactionRename(final FactionRenameEvent event) {
        Stream.of("pvp", "farm", "global").forEach(table -> this.databaseManager.getSqlHandler().setName(event.getOldFactionTag(), event.getFactionTag(), "ranking_" + table));
    }
}