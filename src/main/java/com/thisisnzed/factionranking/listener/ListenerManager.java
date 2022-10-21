package com.thisisnzed.factionranking.listener;

import com.thisisnzed.factionranking.Ranking;
import com.thisisnzed.factionranking.listener.impl.FactionCreate;
import com.thisisnzed.factionranking.listener.impl.FactionDisband;
import com.thisisnzed.factionranking.listener.impl.FactionRename;
import com.thisisnzed.factionranking.listener.impl.InventoryClick;
import com.thisisnzed.factionranking.launcher.Launcher;
import org.bukkit.event.Listener;

public class ListenerManager {

    private final Ranking ranking;

    public ListenerManager(final Ranking ranking) {
        this.ranking = ranking;
    }

    public void registerListeners() {
        this.addListener(new InventoryClick(this.ranking), new FactionCreate(this.ranking), new FactionRename(this.ranking), new FactionDisband(this.ranking));
    }

    private void addListener(final Listener... listeners) {
        final Launcher launcher = this.ranking.getLauncher();
        for (final Listener listener : listeners)
            launcher.getServer().getPluginManager().registerEvents(listener, launcher);
    }
}
