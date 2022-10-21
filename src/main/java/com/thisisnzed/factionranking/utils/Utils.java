package com.thisisnzed.factionranking.utils;

import com.massivecraft.factions.FPlayers;
import com.thisisnzed.factionranking.config.impl.Settings;
import com.thisisnzed.factionranking.storage.impl.SQLHandler;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Utils {

    @Setter
    private static Settings settings;

    public static ItemStack getFaction(final int rank, final SQLHandler sqlHandler, final String table) {
        final String faction = sqlHandler.getFactionAt(rank, "ranking_" + table);
        ArrayList<String> lore = new ArrayList<>();
        settings.getTopLore().forEach(e -> lore.add(ChatColor.translateAlternateColorCodes('&', e.replace("%points%", String.valueOf(sqlHandler.getPoints(faction, "ranking_" + table))).replace("%position%", String.valueOf(rank)).replace("%faction%", faction))));
        return new ItemBuilder(Material.PAPER).setDisplayName(ChatColor.translateAlternateColorCodes('&', settings.getTopName()).replace("%faction%", faction).replace("%position%", String.valueOf(rank))).setLore(lore).build();
    }

    public static Inventory createInventory(final int size, final String title) {
        return Bukkit.createInventory(null, size, "§7" + ChatColor.translateAlternateColorCodes('&', title));
    }

    public static void setSeparators(final Inventory inventory, final boolean isRanking) {
        for (int separator : getSeparators(isRanking))
            inventory.setItem(separator, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 3).setDisplayName(" ").build());
    }

    public static int[] getSeparators(boolean isRanking) {
        return isRanking ? new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53} : new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
    }

    public static ItemStack getHead(final Player player, final SQLHandler sqlHandler) {
        String playerName = player.getName();
        String faction = FPlayers.getInstance().getByPlayer(player).getFaction().getTag();
        ArrayList<String> lore = new ArrayList<>();
        settings.getSkullLore().forEach(e -> lore.add(ChatColor.translateAlternateColorCodes('&', e.replace("%money%", String.valueOf((int) Economy.getMoney(player))).replace("%points%", String.valueOf(sqlHandler.getPoints(faction, "ranking_global"))).replace("%position%", String.valueOf(sqlHandler.getRank(faction, "ranking_global"))).replace("%faction%", faction))));
        return new ItemBuilder(Material.SKULL_ITEM, (short) 3).setHead(playerName).setDisplayName(ChatColor.translateAlternateColorCodes('&', settings.getSkullName()).replace("%player%", playerName)).setLore(lore).build();
    }

    public static ItemStack getBestFactions(final SQLHandler sqlHandler, final String table) {
        ArrayList<String> lore = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            String faction = sqlHandler.getFactionAt(i, "ranking_" + table);
            lore.add(ChatColor.translateAlternateColorCodes('&', settings.getBestFactionsLoreByLine().replace("%points%", String.valueOf(sqlHandler.getPoints(faction, "ranking_" + table))).replace("%faction%", faction).replace("%position%", String.valueOf(i))));
        }
        return new ItemBuilder(Material.BOOK).setDisplayName(ChatColor.translateAlternateColorCodes('&', settings.getBestFactionsName())).setLore(lore).build();
    }
}
