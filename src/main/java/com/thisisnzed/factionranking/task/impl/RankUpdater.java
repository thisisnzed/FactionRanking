package com.thisisnzed.factionranking.task.impl;

import com.thisisnzed.factionranking.Ranking;
import com.thisisnzed.factionranking.config.impl.Lang;
import com.thisisnzed.factionranking.storage.impl.SQLHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class RankUpdater extends BukkitRunnable {

    private final Ranking ranking;
    private final SQLHandler sqlHandler;
    private final HashMap<String, Integer> hmGlobal;

    public RankUpdater(final Ranking ranking) {
        this.ranking = ranking;
        this.sqlHandler = ranking.getDatabaseManager().getSqlHandler();
        this.hmGlobal = new HashMap<>();
    }

    @Override
    public void run() {
        for (final String table : new String[]{"farm", "pvp"}) {
            final HashMap<String, Integer> sorter = new HashMap<>();
            this.sqlHandler.getAllFactions("ranking_" + table).forEach(faction -> sorter.put(faction, this.sqlHandler.getPoints(faction, "ranking_" + table)));
            final List<String> sorted = new ArrayList<>(sorter.keySet());
            sorted.sort((s1, s2) -> sorter.get(s2).compareTo(sorter.get(s1)));
            for (int i = 0; i < sorter.size(); i++) this.sqlHandler.setRank(sorted.get(i), (i + 1), "ranking_" + table);
        }
        this.sqlHandler.getAllFactions("ranking_global").forEach(faction -> this.hmGlobal.put(faction, this.sqlHandler.getPoints(faction, "ranking_farm") + this.sqlHandler.getPoints(faction, "ranking_pvp")));
        final List<String> sortedGlobal = new ArrayList<>(this.hmGlobal.keySet());
        sortedGlobal.sort((s1, s2) -> this.hmGlobal.get(s2).compareTo(this.hmGlobal.get(s1)));
        for (int i = 0; i < this.hmGlobal.size(); i++) {
            this.sqlHandler.setRank(sortedGlobal.get(i), (i + 1), "ranking_global");
            this.sqlHandler.setPoints(sortedGlobal.get(i), this.hmGlobal.get(sortedGlobal.get(i)), "ranking_global");
        }
        Lang.LEADERBOARD_UPDATED.getList().forEach(e -> Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', e).replace("%timer%", String.valueOf(this.ranking.getSettings().getTimeUpdater()))));
    }
}
