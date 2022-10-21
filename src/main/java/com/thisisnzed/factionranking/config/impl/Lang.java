package com.thisisnzed.factionranking.config.impl;

import com.thisisnzed.factionranking.config.FileManager;
import org.bukkit.ChatColor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Lang {

    COMMAND_NO_PERMISSION,
    COMMAND_NO_SUB,
    COMMAND_ONLY_PLAYER,
    COMMAND_ERROR,
    COMMAND_HELP,
    COMMAND_PREFIX,
    COMMAND_ADD_MISSING,
    COMMAND_ADD_INVALID_VALUE,
    COMMAND_ADD_INVALID_TYPE,
    COMMAND_ADD_OFFLINE,
    COMMAND_ADD_WILDERNESS,
    COMMAND_ADD_SUCCESS,
    COMMAND_RELOAD_UNLOAD,
    COMMAND_RELOAD_LOAD,
    COMMAND_REMOVE_MISSING,
    COMMAND_REMOVE_INVALID_VALUE,
    COMMAND_REMOVE_INVALID_TYPE,
    COMMAND_REMOVE_OFFLINE,
    COMMAND_REMOVE_WILDERNESS,
    COMMAND_REMOVE_SUCCESS,
    LEADERBOARD_UPDATED;

    private static final Map<Lang, String> values = new HashMap<>();

    static {
        for (Lang lang : values()) values.put(lang, lang.getFromFile());
    }

    public String get() {
        return values.get(this);
    }

    public List<String> getList() {
        return FileManager.LANG.getConfig().getStringList(name().toLowerCase().replace("_", "-"));
    }

    private String getFromFile() {
        String value = FileManager.LANG.getConfig().getString(name().toLowerCase().replace("_", "-"));
        if (value == null) value = "";
        return ChatColor.translateAlternateColorCodes('&', value);
    }
}
