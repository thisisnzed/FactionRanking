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

public class EventGUI {

    private final Ranking ranking;
    private final Settings settings;

    public EventGUI(Ranking ranking) {
        this.ranking = ranking;
        this.settings = ranking.getSettings();
    }

    public void open(Player player) {
        Inventory inventory = GUIUtils.createInventory(54, this.settings.getPvpTitle());
        SQLHandler sqlHandler = this.ranking.getDatabaseManager().getSqlHandler();
        GUIUtils.setSeparators(inventory, true);
        inventory.setItem(4, GUIUtils.getHead(player, sqlHandler));
        inventory.setItem(21, GUIUtils.getFaction(1, sqlHandler, "pvp"));
        inventory.setItem(22, GUIUtils.getFaction(2, sqlHandler, "pvp"));
        inventory.setItem(23, GUIUtils.getFaction(3, sqlHandler, "pvp"));
        inventory.setItem(31, GUIUtils.getBestFactions(sqlHandler, "pvp"));
        player.openInventory(inventory);
    }
}