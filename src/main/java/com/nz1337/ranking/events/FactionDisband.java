package com.nz1337.ranking.events;

import com.massivecraft.factions.event.FactionDisbandEvent;
import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.manager.DatabaseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionDisband implements Listener {

    private final DatabaseManager databaseManager;

    public FactionDisband(final Ranking ranking) {
        this.databaseManager = ranking.getDatabaseManager();
    }

    @EventHandler
    public void onFactionDisband(final FactionDisbandEvent event) {
        for (final String table : new String[]{"pvp", "farm", "global"}) this.databaseManager.getSqlHandler().setTable(table).delete(event.getFaction().getTag());
    }
}
