package com.thisisnzed.factionranking.command;

import com.thisisnzed.factionranking.Ranking;
import org.bukkit.command.CommandSender;

import java.io.IOException;

public abstract class SubCommandManager {

    public abstract void execute(Ranking ranking, CommandSender sender, String[] args) throws IOException;

    public abstract String name();

    public abstract String info();

    public abstract String[] aliases();
}