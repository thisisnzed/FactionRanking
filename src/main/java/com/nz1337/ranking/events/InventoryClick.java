package com.nz1337.ranking.events;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.configs.Settings;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {

    private final Ranking ranking;
    private final Settings settings;

    public InventoryClick(final Ranking ranking) {
        this.ranking = ranking;
        this.settings = ranking.getSettings();
    }

    @EventHandler
    public void onHotbarSwap(final InventoryClickEvent event) {
        final Inventory inv = event.getInventory();
        if (inv == null || inv.getName() == null) return;
        final String inventoryName = inv.getName();
        if ((inventoryName.equals(this.colorize(this.settings.getMainTitle())) || inventoryName.equals(this.colorize(this.settings.getGlobalTitle())) || inventoryName.equals(this.colorize(this.settings.getFarmingTitle())) || inventoryName.equals(this.colorize(this.settings.getPvpTitle()))) && event.getAction() == InventoryAction.HOTBAR_SWAP)
            event.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final ItemStack currentItem = event.getCurrentItem();
        final Inventory inv = event.getInventory();
        if (currentItem == null || inv == null || inv.getName() == null || currentItem.getItemMeta() == null) return;
        final String currentItemName = currentItem.getItemMeta().getDisplayName();
        if (currentItemName == null) return;
        final String invName = inv.getName();
        if (invName.equals(this.colorize(this.settings.getMainTitle()))) {
            event.setCancelled(true);
            if (currentItemName.equals(this.colorize(this.settings.getMainItemFarm()))) this.ranking.getGuiManager().getFarmGUI().open(player);
            if (currentItemName.equals(this.colorize(this.settings.getMainItemPvp()))) this.ranking.getGuiManager().getEventGUI().open(player);
            if (currentItemName.equals(this.colorize(this.settings.getMainItemGlobal()))) this.ranking.getGuiManager().getGlobalGUI().open(player);
        } else if (invName.equals(this.colorize(this.settings.getPvpTitle())) || invName.equals(this.colorize(this.settings.getFarmingTitle())) || invName.equals(this.colorize(this.settings.getGlobalTitle())))
            event.setCancelled(true);
    }

    private String colorize(final String s) {
        return "??7" + ChatColor.translateAlternateColorCodes('&', s);
    }
}
