package com.nz1337.ranking.events;

import com.massivecraft.factions.event.FactionCreateEvent;
import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.manager.DatabaseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionCreate implements Listener {

    private final DatabaseManager databaseManager;

    public FactionCreate(Ranking ranking) {
        this.databaseManager = ranking.getDatabaseManager();
    }

    @EventHandler
    public void onFactionCreate(FactionCreateEvent event) {
        for (String table : new String[]{"pvp", "farm", "global"}) this.databaseManager.getSqlHandler().setTable(table).register(event.getFaction().getTag());
    }
}