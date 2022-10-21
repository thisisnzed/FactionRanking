package com.thisisnzed.factionranking.gui.impl;

import com.thisisnzed.factionranking.Ranking;
import com.thisisnzed.factionranking.config.impl.Settings;
import com.thisisnzed.factionranking.gui.Gui;
import com.thisisnzed.factionranking.storage.impl.SQLHandler;
import com.thisisnzed.factionranking.utils.Utils;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class FarmGui extends Gui {

    private final Ranking ranking;
    private final Settings settings;

    public FarmGui(final Ranking ranking) {
        this.ranking = ranking;
        this.settings = ranking.getSettings();
    }

    @Override
    public void open(final Player player) {
        final Inventory inventory = Utils.createInventory(54, this.settings.getFarmingTitle());
        final SQLHandler sqlHandler = this.ranking.getDatabaseManager().getSqlHandler();
        Utils.setSeparators(inventory, true);
        inventory.setItem(4, Utils.getHead(player, sqlHandler));
        inventory.setItem(21, Utils.getFaction(1, sqlHandler, "farm"));
        inventory.setItem(22, Utils.getFaction(2, sqlHandler, "farm"));
        inventory.setItem(23, Utils.getFaction(3, sqlHandler, "farm"));
        inventory.setItem(31, Utils.getBestFactions(sqlHandler, "farm"));
        player.openInventory(inventory);
    }
}