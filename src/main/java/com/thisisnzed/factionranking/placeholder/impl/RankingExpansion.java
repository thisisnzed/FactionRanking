package com.thisisnzed.factionranking.placeholder.impl;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.thisisnzed.factionranking.sql.DatabaseManager;
import com.thisisnzed.factionranking.sql.impl.RankingHandler;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class RankingExpansion extends PlaceholderExpansion {

    private final DatabaseManager databaseManager;

    public RankingExpansion(final DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public @NotNull String getAuthor() {
        return "thisisnzed";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "factionranking";
    }

    @Override
    public @NotNull String getVersion() {
        return "2.0.0";
    }

    @Override
    public String onPlaceholderRequest(final Player player, final @NotNull String identifier) {
        final FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        if (player == null || fPlayer == null || !fPlayer.hasFaction()) return "?";
        final String tag = fPlayer.getFaction().getTag();
        final RankingHandler rankingHandler = this.databaseManager.getRankingHandler();
        try {
            switch (identifier) {
                case "pos":
                    final int rank = rankingHandler.getRank(tag).get();
                    return rank > 0 ? String.valueOf(rank) : "?";
                case "points":
                    final int points = rankingHandler.getPoints(tag).get();
                    return points > 0 ? String.valueOf(points) : "?";
            }
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
        return "?";
    }
}
