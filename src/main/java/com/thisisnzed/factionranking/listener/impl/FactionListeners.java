package com.thisisnzed.factionranking.listener.impl;

import com.massivecraft.factions.event.FactionCreateEvent;
import com.massivecraft.factions.event.FactionDisbandEvent;
import com.massivecraft.factions.event.FactionRenameEvent;
import com.thisisnzed.factionranking.plugin.Launcher;
import com.thisisnzed.factionranking.sql.DatabaseManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FactionListeners implements Listener {

    private final DatabaseManager databaseManager;
    private final Launcher launcher;

    public FactionListeners(final Launcher launcher, final DatabaseManager databaseManager) {
        this.launcher = launcher;
        this.databaseManager = databaseManager;
    }

    @EventHandler
    public void onFactionCreate(final FactionCreateEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(this.launcher, () -> this.databaseManager.getRankingHandler().register(event.getFaction().getTag()));
    }

    @EventHandler
    public void onFactionDisband(final FactionDisbandEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(this.launcher, () -> this.databaseManager.getRankingHandler().delete(event.getFaction().getTag()));
    }

    @EventHandler
    public void onFactionRename(final FactionRenameEvent event) {
        Bukkit.getScheduler().runTaskAsynchronously(this.launcher, () -> this.databaseManager.getRankingHandler().setName(event.getOldFactionTag(), event.getFactionTag()));
    }
}
