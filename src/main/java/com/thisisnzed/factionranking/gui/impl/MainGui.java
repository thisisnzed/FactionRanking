package com.thisisnzed.factionranking.gui.impl;

import com.thisisnzed.factionranking.Ranking;
import com.thisisnzed.factionranking.config.impl.Settings;
import com.thisisnzed.factionranking.gui.Gui;
import com.thisisnzed.factionranking.storage.impl.SQLHandler;
import com.thisisnzed.factionranking.utils.Utils;
import com.thisisnzed.factionranking.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class MainGui extends Gui {

    private final Ranking ranking;
    private final Settings settings;
    private final ArrayList<String> pvpLore;
    private final ArrayList<String> farmLore;
    private final ArrayList<String> globalLore;

    public MainGui(final Ranking ranking) {
        this.ranking = ranking;
        this.settings = ranking.getSettings();
        this.pvpLore = new ArrayList<>();
        this.farmLore = new ArrayList<>();
        this.globalLore = new ArrayList<>();
        this.settings.getMainItemPvpLore().forEach(e -> this.pvpLore.add(ChatColor.translateAlternateColorCodes('&', e)));
        this.settings.getMainItemFarmLore().forEach(e -> this.farmLore.add(ChatColor.translateAlternateColorCodes('&', e)));
        this.settings.getMainItemGlobalLore().forEach(e -> this.globalLore.add(ChatColor.translateAlternateColorCodes('&', e)));
    }

    @Override
    public void open(final Player player) {
        final Inventory inventory = Utils.createInventory(45, this.settings.getMainTitle());
        final SQLHandler sqlHandler = this.ranking.getDatabaseManager().getSqlHandler();
        Utils.setSeparators(inventory, false);
        inventory.setItem(4, Utils.getHead(player, sqlHandler));
        inventory.setItem(20, new ItemBuilder(Material.IRON_SWORD).setDisplayName("§7" + ChatColor.translateAlternateColorCodes('&', this.settings.getMainItemPvp())).setLore(this.pvpLore).build());
        inventory.setItem(22, new ItemBuilder(Material.BOOK).setDisplayName("§7" + ChatColor.translateAlternateColorCodes('&', this.settings.getMainItemGlobal())).setLore(this.globalLore).build());
        inventory.setItem(24, new ItemBuilder(Material.WHEAT).setDisplayName("§7" + ChatColor.translateAlternateColorCodes('&', this.settings.getMainItemFarm())).setLore(this.farmLore).build());
        player.openInventory(inventory);
    }
}
