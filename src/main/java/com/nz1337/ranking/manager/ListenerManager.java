package com.nz1337.ranking.manager;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.events.FactionCreate;
import com.nz1337.ranking.events.FactionDisband;
import com.nz1337.ranking.events.FactionRename;
import com.nz1337.ranking.events.InventoryClick;
import com.nz1337.ranking.launcher.Launcher;
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
