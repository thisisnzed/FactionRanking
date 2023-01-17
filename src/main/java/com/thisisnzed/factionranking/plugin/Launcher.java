package com.thisisnzed.factionranking.plugin;

import com.thisisnzed.factionranking.FRanking;
import com.thisisnzed.factionranking.command.Command;
import com.thisisnzed.factionranking.command.CommandManager;
import com.thisisnzed.factionranking.file.FileManager;
import com.thisisnzed.factionranking.listener.ListenerManager;
import com.thisisnzed.factionranking.sql.DatabaseManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Launcher extends JavaPlugin {

    private Plugin plugin;
    protected CommandManager commandManager;
    protected ListenerManager listenerManager;
    @Getter
    private DatabaseManager databaseManager;

    @Override
    public void onEnable() {
        this.commandManager = new CommandManager(this);
        this.listenerManager = new ListenerManager(this);
        this.databaseManager = new DatabaseManager();

        FileManager.CONFIG.create(super.getLogger());
        this.databaseManager.initialize();

        this.plugin = new FRanking();
        this.plugin.launch(this);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelAllTasks();
    }

    public void registerCommand(final Command command) {
        this.commandManager.registerCommand(command);
    }

    public void registerListeners(final Listener... listener) {
        this.listenerManager.addListeners(listener);
    }
}