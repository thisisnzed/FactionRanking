package com.nz1337.ranking.gui;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.configs.Settings;
import com.nz1337.ranking.storage.SQLHandler;
import com.nz1337.ranking.utils.GUIUtils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class FarmGUI {

    private final Ranking ranking;
    private final Settings settings;

    public FarmGUI(final Ranking ranking) {
        this.ranking = ranking;
        this.settings = ranking.getSettings();
    }

    public void open(final Player player) {
        final Inventory inventory = GUIUtils.createInventory(54, this.settings.getFarmingTitle());
        final SQLHandler sqlHandler = this.ranking.getDatabaseManager().getSqlHandler();
        GUIUtils.setSeparators(inventory, true);
        inventory.setItem(4, GUIUtils.getHead(player, sqlHandler));
        inventory.setItem(21, GUIUtils.getFaction(1, sqlHandler, "farm"));
        inventory.setItem(22, GUIUtils.getFaction(2, sqlHandler, "farm"));
        inventory.setItem(23, GUIUtils.getFaction(3, sqlHandler, "farm"));
        inventory.setItem(31, GUIUtils.getBestFactions(sqlHandler, "farm"));
        player.openInventory(inventory);
    }
}