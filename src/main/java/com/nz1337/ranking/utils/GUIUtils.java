package com.nz1337.ranking.utils;

import com.massivecraft.factions.FPlayers;
import com.nz1337.ranking.configs.Settings;
import com.nz1337.ranking.storage.SQLHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class GUIUtils {

    private static Settings settings;

    public static ItemStack getFaction(int rank, SQLHandler sqlHandler, String table) {
        final String faction = sqlHandler.setTable(table).getFactionAt(rank);
        ArrayList<String> lore = new ArrayList<>();
        settings.getTopLore().forEach(e -> lore.add(ChatColor.translateAlternateColorCodes('&', e.replace("%points%", String.valueOf(sqlHandler.setTable(table).getPoints(faction))).replace("%position%", String.valueOf(rank)).replace("%faction%", faction))));
        return new ItemBuilder(Material.PAPER).setDisplayName(ChatColor.translateAlternateColorCodes('&', settings.getTopName()).replace("%faction%", faction).replace("%position%", String.valueOf(rank))).setLore(lore).build();
    }

    public static Inventory createInventory(int size, String title) {
        return Bukkit.createInventory(null, size, "§7" + ChatColor.translateAlternateColorCodes('&', title));
    }

    public static void setSeparators(Inventory inventory, boolean isRanking) {
        for (int separator : getSeparators(isRanking))
            inventory.setItem(separator, new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 3).setDisplayName(" ").build());
    }

    public static int[] getSeparators(boolean isRanking) {
        return isRanking ? new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53} : new int[]{0, 1, 2, 3, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};
    }

    public static ItemStack getHead(Player player, SQLHandler sqlHandler) {
        String playerName = player.getName();
        String faction = FPlayers.getInstance().getByPlayer(player).getFaction().getTag();
        ArrayList<String> lore = new ArrayList<>();
        settings.getSkullLore().forEach(e -> lore.add(ChatColor.translateAlternateColorCodes('&', e.replace("%money%", String.valueOf((int) Economy.getMoney(player))).replace("%points%", String.valueOf(sqlHandler.setTable("global").getPoints(faction))).replace("%position%", String.valueOf(sqlHandler.setTable("global").getRank(faction))).replace("%faction%", faction))));
        return new ItemBuilder(Material.SKULL_ITEM, (short) 3).setHead(playerName).setDisplayName(ChatColor.translateAlternateColorCodes('&', settings.getSkullName()).replace("%player%", playerName)).setLore(lore).build();
    }

    public static ItemStack getBestFactions(SQLHandler sqlHandler, String table) {
        ArrayList<String> lore = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            String faction = sqlHandler.setTable(table).getFactionAt(i);
            lore.add(ChatColor.translateAlternateColorCodes('&', settings.getBestFactionsLoreByLine().replace("%points%", String.valueOf(sqlHandler.setTable(table).getPoints(faction))).replace("%faction%", faction).replace("%position%", String.valueOf(i))));
        }
        return new ItemBuilder(Material.BOOK).setDisplayName(ChatColor.translateAlternateColorCodes('&', settings.getBestFactionsName())).setLore(lore).build();
    }

    public static void setSettings(Settings settingsClass) {
        settings = settingsClass;
    }
}
