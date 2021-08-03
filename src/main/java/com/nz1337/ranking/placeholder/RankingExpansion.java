package com.nz1337.ranking.placeholder;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.manager.DatabaseManager;
import com.nz1337.ranking.storage.SQLHandler;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;

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
    @Nonnull
    public String getAuthor() {
        return "Najtt";
    }

    @Override
    @Nonnull
    public String getIdentifier() {
        return "factionranking";
    }

    @Override
    @Nonnull
    public String getVersion() {
        return "1.0";
    }

    @Override
    public String onPlaceholderRequest(Player player, @Nonnull String identifier) {
        final FPlayer fPlayer = FPlayers.getInstance().getByPlayer(player);
        if (player == null || fPlayer == null || !fPlayer.hasFaction()) return "?";
        final String tag = fPlayer.getFaction().getTag();
        final DatabaseManager databaseManager = ranking.getDatabaseManager();
        final SQLHandler sqlHandler = databaseManager.getSqlHandler();
        if (identifier.equals("globalrank")) return sqlHandler.setTable("global").getRank(tag) > 0 ? String.valueOf(sqlHandler.setTable("global").getRank(tag)) : "?";
        if (identifier.equals("pvprank")) return sqlHandler.setTable("pvp").getRank(tag) > 0 ? String.valueOf(sqlHandler.setTable("pvp").getRank(tag)) : "?";
        if (identifier.equals("farmrank")) return sqlHandler.setTable("farm").getRank(tag) > 0 ? String.valueOf(sqlHandler.setTable("farm").getRank(tag)) : "?";
        return "?";
    }
}
