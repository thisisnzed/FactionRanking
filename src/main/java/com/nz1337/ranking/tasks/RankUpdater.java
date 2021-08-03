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
    private final HashMap<String, Integer> hmGlobal = new HashMap<>();

    public RankUpdater(Ranking ranking) {
        this.ranking = ranking;
        this.sqlHandler = ranking.getDatabaseManager().getSqlHandler();
    }

    @Override
    public void run() {
        for (String table : new String[]{"farm", "pvp"}) {
            final HashMap<String, Integer> sorter = new HashMap<>();
            this.sqlHandler.setTable(table).getAllFactions().forEach(faction -> sorter.put(faction, this.sqlHandler.setTable(table).getPoints(faction)));
            List<String> sorted = new ArrayList<>(sorter.keySet());
            sorted.sort((s1, s2) -> sorter.get(s2).compareTo(sorter.get(s1)));
            for (int i = 0; i < sorter.size(); i++) this.sqlHandler.setTable(table).setRank(sorted.get(i), (i + 1));
        }
        this.sqlHandler.setTable("global").getAllFactions().forEach(faction -> hmGlobal.put(faction, this.sqlHandler.setTable("farm").getPoints(faction) + this.sqlHandler.setTable("pvp").getPoints(faction)));
        List<String> sortedGlobal = new ArrayList<>(hmGlobal.keySet());
        sortedGlobal.sort((s1, s2) -> hmGlobal.get(s2).compareTo(hmGlobal.get(s1)));
        for (int i = 0; i < hmGlobal.size(); i++) {
            this.sqlHandler.setTable("global").setRank(sortedGlobal.get(i), (i + 1));
            this.sqlHandler.setTable("global").setPoints(sortedGlobal.get(i), hmGlobal.get(sortedGlobal.get(i)));
        }
        Lang.LEADERBOARD_UPDATED.getList().forEach(e -> Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', e).replace("%timer%", String.valueOf(ranking.getSettings().getTimeUpdater()))));
    }
}
