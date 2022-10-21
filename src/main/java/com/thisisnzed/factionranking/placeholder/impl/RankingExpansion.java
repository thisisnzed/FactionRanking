package com.thisisnzed.factionranking.placeholder.impl;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.thisisnzed.factionranking.Ranking;
import com.thisisnzed.factionranking.storage.DatabaseManager;
import com.thisisnzed.factionranking.storage.impl.SQLHandler;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RankingExpansion extends PlaceholderExpansion {

    private final Ranking ranking;

    public RankingExpansion(Ranking ranking) {
        this.ranking = ranking;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return "Najtt";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "factionranking";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(final Player player, final @NotNull String identifier) {
        final FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        if (player == null || fPlayer == null || !fPlayer.hasFaction()) return "?";
        final String tag = fPlayer.getFaction().getTag();
        final DatabaseManager databaseManager = this.ranking.getDatabaseManager();
        final SQLHandler sqlHandler = databaseManager.getSqlHandler();
        switch (identifier) {
            case "rank_global":
                return sqlHandler.getRank(tag, "ranking_global") > 0 ? String.valueOf(sqlHandler.getRank(tag, "ranking_global")) : "?";
            case "rank_pvp":
                return sqlHandler.getRank(tag, "ranking_pvp") > 0 ? String.valueOf(sqlHandler.getRank(tag, "ranking_pvp")) : "?";
            case "rank_farm":
                return sqlHandler.getRank(tag, "ranking_farm") > 0 ? String.valueOf(sqlHandler.getRank(tag, "ranking_farm")) : "?";
        }
        return "?";
    }
}
