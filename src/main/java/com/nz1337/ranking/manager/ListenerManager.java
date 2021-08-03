package com.nz1337.ranking.manager;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.events.*;
import com.nz1337.ranking.launcher.Launcher;
import org.bukkit.event.Listener;

public class ListenerManager {

    private final Ranking ranking;

    public ListenerManager(Ranking ranking) {
        this.ranking = ranking;
    }

    public void registerListeners() {
        addListener(new InventoryClick(this.ranking), new FactionCreate(this.ranking), new FactionRename(this.ranking), new FactionDisband(this.ranking));
    }

    private void addListener(Listener... listeners) {
        Launcher launcher = this.ranking.getLauncher();
        for (Listener listener : listeners) launcher.getServer().getPluginManager().registerEvents(listener, launcher);
    }
}
