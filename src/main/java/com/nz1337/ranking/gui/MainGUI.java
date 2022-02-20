package com.nz1337.ranking.gui;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.configs.Settings;
import com.nz1337.ranking.storage.SQLHandler;
import com.nz1337.ranking.utils.GUIUtils;
import com.nz1337.ranking.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class MainGUI {

    private final Ranking ranking;
    private final Settings settings;
    private final ArrayList<String> pvpLore;
    private final ArrayList<String> farmLore;
    private final ArrayList<String> globalLore;

    public MainGUI(final Ranking ranking) {
        this.ranking = ranking;
        this.settings = ranking.getSettings();
        this.pvpLore = new ArrayList<>();
        this.farmLore = new ArrayList<>();
        this.globalLore = new ArrayList<>();
        this.settings.getMainItemPvpLore().forEach(e -> this.pvpLore.add(ChatColor.translateAlternateColorCodes('&', e)));
        this.settings.getMainItemFarmLore().forEach(e -> this.farmLore.add(ChatColor.translateAlternateColorCodes('&', e)));
        this.settings.getMainItemGlobalLore().forEach(e -> this.globalLore.add(ChatColor.translateAlternateColorCodes('&', e)));
    }

    public void open(final Player player) {
        final Inventory inventory = GUIUtils.createInventory(45, this.settings.getMainTitle());
        final SQLHandler sqlHandler = this.ranking.getDatabaseManager().getSqlHandler();
        GUIUtils.setSeparators(inventory, false);
        inventory.setItem(4, GUIUtils.getHead(player, sqlHandler));
        inventory.setItem(20, new ItemBuilder(Material.IRON_SWORD).setDisplayName("§7" + ChatColor.translateAlternateColorCodes('&', this.settings.getMainItemPvp())).setLore(this.pvpLore).build());
        inventory.setItem(22, new ItemBuilder(Material.BOOK).setDisplayName("§7" + ChatColor.translateAlternateColorCodes('&', this.settings.getMainItemGlobal())).setLore(this.globalLore).build());
        inventory.setItem(24, new ItemBuilder(Material.WHEAT).setDisplayName("§7" + ChatColor.translateAlternateColorCodes('&', this.settings.getMainItemFarm())).setLore(this.farmLore).build());
        player.openInventory(inventory);
    }
}
