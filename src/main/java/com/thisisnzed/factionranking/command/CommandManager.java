package com.thisisnzed.factionranking.command;

import com.thisisnzed.factionranking.plugin.Launcher;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.concurrent.ConcurrentLinkedQueue;

public class CommandManager implements CommandExecutor {

    private final Launcher launcher;
    private final ConcurrentLinkedQueue<Command> commands;

    public CommandManager(final Launcher launcher) {
        this.launcher = launcher;
        this.commands = new ConcurrentLinkedQueue<>();
    }

    public void registerCommand(final Command command) {
        this.launcher.getCommand(command.name).setExecutor(this);
        this.commands.add(command);
    }

    @Override
    public boolean onCommand(final CommandSender commandSender, final org.bukkit.command.Command command, final String s, final String[] strings) {
        this.commands.stream().filter(cmd -> command.getName().equalsIgnoreCase(cmd.name)).findFirst().ifPresent(target -> target.prePerform(commandSender, strings));
        return false;
    }
}
