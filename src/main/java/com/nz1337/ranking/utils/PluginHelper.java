package com.nz1337.ranking.utils;

import com.nz1337.ranking.configs.Lang;
import com.nz1337.ranking.launcher.Launcher;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.event.Event;
import org.bukkit.plugin.*;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class PluginHelper {

    private static final PluginManager pm = Bukkit.getServer().getPluginManager();

    public static void load(CommandSender player, String pluginName, Launcher launcher) {
        Plugin[] plugins2;
        for (int length = (plugins2 = pm.getPlugins()).length, i = 0; i < length; ++i) {
            final Plugin pl = plugins2[i];
            if (pl.getName().toLowerCase().startsWith(pluginName.toLowerCase())) {
                Bukkit.getLogger().info("Loaded: " + pl.getName());
                return;
            }
        }
        String name = "";
        final String path = launcher.getDataFolder().getParent();
        final File folder = new File(path);
        final ArrayList<File> files = new ArrayList<>();
        final File[] listOfFiles = folder.listFiles();
        File[] array;
        player.sendMessage(Lang.COMMAND_PREFIX.get() + Lang.COMMAND_RELOAD_LOAD.get());
        for (int length2 = (Objects.requireNonNull(array = listOfFiles)).length, j = 0; j < length2; ++j) {
            final File compare = array[j];
            if (compare.isFile()) {
                try {
                    name = launcher.getPluginLoader().getPluginDescription(compare).getName();
                } catch (InvalidDescriptionException e2) {
                    Bukkit.getLogger().info("ErrorA " + compare.getName());
                }
                if (name.toLowerCase().startsWith(pluginName.toLowerCase())) {
                    files.add(compare);
                    try {
                        pm.loadPlugin(compare);
                    } catch (UnknownDependencyException | InvalidPluginException | InvalidDescriptionException e3) {
                        e3.printStackTrace();
                        return;
                    }
                }
            }
        }
        final Plugin[] plugins = pm.getPlugins();
        Plugin[] array2;
        for (int length3 = (array2 = plugins).length, k = 0; k < length3; ++k) {
            final Plugin pl2 = array2[k];
            for (final File compare2 : files) {
                try {
                    if (!pl2.getName()
                            .equalsIgnoreCase(launcher.getPluginLoader().getPluginDescription(compare2).getName())) {
                        continue;
                    }
                    pm.enablePlugin(pl2);
                } catch (InvalidDescriptionException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void unload(CommandSender player, String pluginName) {
        pluginName = pluginName.toLowerCase().trim();
        final SimplePluginManager spm = (SimplePluginManager) pm;
        SimpleCommandMap commandMap = null;
        List<Plugin> plugins = null;
        Map<String, Plugin> lookupNames = null;
        Map<String, Command> knownCommands = null;
        Map<Event, SortedSet<RegisteredListener>> listeners = null;
        boolean reloadlisteners = true;
        try {
            if (spm != null) {
                player.sendMessage(Lang.COMMAND_PREFIX.get() + Lang.COMMAND_RELOAD_UNLOAD.get());
                final Field pluginsField = spm.getClass().getDeclaredField("plugins");
                pluginsField.setAccessible(true);
                plugins = (List<Plugin>) pluginsField.get(spm);
                final Field lookupNamesField = spm.getClass().getDeclaredField("lookupNames");
                lookupNamesField.setAccessible(true);
                lookupNames = (Map<String, Plugin>) lookupNamesField.get(spm);
                try {
                    final Field listenersField = spm.getClass().getDeclaredField("listeners");
                    listenersField.setAccessible(true);
                    listeners = (Map<Event, SortedSet<RegisteredListener>>) listenersField.get(spm);
                } catch (Exception e5) {
                    reloadlisteners = false;
                }
                final Field commandMapField = spm.getClass().getDeclaredField("commandMap");
                commandMapField.setAccessible(true);
                commandMap = (SimpleCommandMap) commandMapField.get(spm);
                final Field knownCommandsField = commandMap.getClass().getDeclaredField("knownCommands");
                knownCommandsField.setAccessible(true);
                knownCommands = (Map<String, Command>) knownCommandsField.get(commandMap);
            }
        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException e) {
            e.printStackTrace();
        }
        boolean in = false;
        Plugin[] plugins2;
        assert pm != null;
        for (int length = (plugins2 = pm.getPlugins()).length, i = 0; i < length; ++i) {
            final Plugin pl = plugins2[i];
            if (pl.getName().toLowerCase().startsWith(pluginName.toLowerCase())) {
                pm.disablePlugin(pl);
                if (plugins != null) {
                    plugins.remove(pl);
                }
                if (lookupNames != null) {
                    lookupNames.remove(pl.getName());
                }
                if (listeners != null) {
                    for (final SortedSet<RegisteredListener> set : listeners.values())
                        set.removeIf(value -> value.getPlugin() == pl);
                }
                if (commandMap != null) {
                    assert knownCommands != null;
                    final Iterator<Map.Entry<String, Command>> it2 = knownCommands.entrySet().iterator();
                    while (it2.hasNext()) {
                        final Map.Entry<String, Command> entry = it2.next();
                        if (entry.getValue() instanceof PluginCommand) {
                            final PluginCommand c = (PluginCommand) entry.getValue();
                            if (c.getPlugin() != pl)
                                continue;
                            c.unregister(commandMap);
                            it2.remove();
                        }
                    }
                }
                Plugin[] plugins3;
                for (int length2 = (plugins3 = pm.getPlugins()).length, j = 0; j < length2; ++j) {
                    final Plugin plu = plugins3[j];
                    if (plu.getDescription().getDepend() != null)
                        for (final String depend : plu.getDescription().getDepend())
                            if (depend.equalsIgnoreCase(pl.getName()))
                                unload(player, plu.getName());
                }
                in = true;
                break;
            }
        }
        if (!in) Bukkit.getLogger().info("Unload error: " + pluginName);
        System.gc();
    }
}