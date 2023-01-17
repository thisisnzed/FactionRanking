package com.thisisnzed.factionranking.listener;

import com.thisisnzed.factionranking.plugin.Launcher;
import org.bukkit.event.Listener;

import java.util.Arrays;

public class ListenerManager {

    private final Launcher launcher;

    public ListenerManager(final Launcher launcher) {
        this.launcher = launcher;
    }

    public void addListeners(final Listener... listeners) {
        Arrays.stream(listeners).forEach(listener -> this.launcher.getServer().getPluginManager().registerEvents(listener, this.launcher));
    }
}