package com.thisisnzed.factionranking.command;

import com.thisisnzed.factionranking.plugin.Plugin;
import com.thisisnzed.factionranking.utils.CommandResult;
import org.bukkit.command.CommandSender;

public abstract class Command {

    protected final Plugin plugin;
    protected CommandSender commandSender;
    protected String[] args;
    public String name;
    private final String description;

    public Command(final Plugin plugin, final String name, final String description) {
        this.plugin = plugin;
        this.name = name;
        this.description = description;
        this.args = null;
    }

    public void prePerform(final CommandSender commandSender, final String[] args) {
        this.commandSender = commandSender;
        this.args = args;
        this.perform();
    }

    protected abstract CommandResult perform();

    protected String getDescription() {
        return this.description == null ? "no description" : this.description;
    }

    protected Plugin getPlugin() {
        return this.plugin;
    }
}
