package com.nz1337.ranking.tasks;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.configs.Lang;
import com.nz1337.ranking.storage.SQLHandler;
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
            this.sqlHandler.setTable(table).getAllFactions().forEach(faction -> sorter.put(faction, this.sqlHandler.setTable(table).getPoints(faction)));
            final List<String> sorted = new ArrayList<>(sorter.keySet());
            sorted.sort((s1, s2) -> sorter.get(s2).compareTo(sorter.get(s1)));
            for (int i = 0; i < sorter.size(); i++) this.sqlHandler.setTable(table).setRank(sorted.get(i), (i + 1));
        }
        this.sqlHandler.setTable("global").getAllFactions().forEach(faction -> this.hmGlobal.put(faction, this.sqlHandler.setTable("farm").getPoints(faction) + this.sqlHandler.setTable("pvp").getPoints(faction)));
        final List<String> sortedGlobal = new ArrayList<>(this.hmGlobal.keySet());
        sortedGlobal.sort((s1, s2) -> this.hmGlobal.get(s2).compareTo(this.hmGlobal.get(s1)));
        for (int i = 0; i < this.hmGlobal.size(); i++) {
            this.sqlHandler.setTable("global").setRank(sortedGlobal.get(i), (i + 1));
            this.sqlHandler.setTable("global").setPoints(sortedGlobal.get(i), this.hmGlobal.get(sortedGlobal.get(i)));
        }
        Lang.LEADERBOARD_UPDATED.getList().forEach(e -> Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', e).replace("%timer%", String.valueOf(this.ranking.getSettings().getTimeUpdater()))));
    }
}
