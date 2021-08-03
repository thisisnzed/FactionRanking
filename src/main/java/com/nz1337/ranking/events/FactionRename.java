package com.nz1337.ranking.events;

import com.massivecraft.factions.event.FactionRenameEvent;
import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.manager.DatabaseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionRename implements Listener {

    private final DatabaseManager databaseManager;

    public FactionRename(Ranking ranking) {
        this.databaseManager = ranking.getDatabaseManager();
    }

    @EventHandler
    public void onFactionRename(FactionRenameEvent event) {
        for (String table : new String[]{"pvp", "farm", "global"}) this.databaseManager.getSqlHandler().setTable(table).setName(event.getOldFactionTag(), event.getFactionTag());
    }
}