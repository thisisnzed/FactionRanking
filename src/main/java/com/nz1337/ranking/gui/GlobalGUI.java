package com.nz1337.ranking.gui;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.configs.Settings;
import com.nz1337.ranking.storage.SQLHandler;
import com.nz1337.ranking.utils.GUIUtils;
import com.nz1337.ranking.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Arrays;

public class GlobalGUI {

    private final Ranking ranking;
    private final Settings settings;

    public GlobalGUI(final Ranking ranking) {
        this.ranking = ranking;
        this.settings = ranking.getSettings();
    }

    public void open(final Player player) {
        final Inventory inventory = GUIUtils.createInventory(54, this.settings.getGlobalTitle());
        final SQLHandler sqlHandler = this.ranking.getDatabaseManager().getSqlHandler();
        GUIUtils.setSeparators(inventory, true);
        inventory.setItem(4, GUIUtils.getHead(player, sqlHandler));
        inventory.setItem(21, GUIUtils.getFaction(1, sqlHandler, "global"));
        inventory.setItem(22, GUIUtils.getFaction(2, sqlHandler, "global"));
        inventory.setItem(23, GUIUtils.getFaction(3, sqlHandler, "global"));
        inventory.setItem(31, GUIUtils.getBestFactions(sqlHandler, "global"));
        player.openInventory(inventory);
    }
}