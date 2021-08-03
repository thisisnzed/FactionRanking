package com.nz1337.ranking;

import com.nz1337.ranking.commands.CommandManager;
import com.nz1337.ranking.configs.Settings;
import com.nz1337.ranking.launcher.Launch;
import com.nz1337.ranking.launcher.Launcher;
import com.nz1337.ranking.manager.*;
import com.nz1337.ranking.utils.GUIUtils;
import org.bukkit.Bukkit;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.CustomClassLoaderConstructor;
import org.yaml.snakeyaml.introspector.BeanAccess;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Ranking implements Launch {

    private Launcher launcher;
    private CommandManager commandManager;
    private ListenerManager listenerManager;
    private DatabaseManager databaseManager;
    private TaskManager taskManager;
    private GuiManager guiManager;
    private PlaceholderManager placeholderManager;
    private Settings settings;
    private String port, host, database, username, password;
    private Connection connection;

    @Override
    public void launch(Launcher launcher, ClassLoader classLoader) {
        this.launcher = launcher;
        this.createFiles(launcher.getLogger(), classLoader);
        Bukkit.getScheduler().runTaskAsynchronously(launcher, () -> {
            this.databaseManager = new DatabaseManager(this);
            this.commandManager = new CommandManager(this);
            this.listenerManager = new ListenerManager(this);
            this.commandManager.registerCommands();
            this.listenerManager.registerListeners();
            this.guiManager = new GuiManager(this);
            this.taskManager = new TaskManager(this);
            this.sqlInitializer();
            this.getTaskManager().getRankUpdater().runTaskTimerAsynchronously(getLauncher(), 60L, (getSettings().getTimeUpdater() * 1200L));
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) this.placeholderManager = new PlaceholderManager(this);
            GUIUtils.setSettings(settings);
        });
    }

    @Override
    public void shutdown() {
        System.out.println("[Ranking] " + getDatabaseManager().getSqlHandler().setTable("global").getAllFactions().size() + " factions have been unloaded!");
        Bukkit.getScheduler().cancelTasks(launcher);
    }

    private void sqlInitializer() {
        database = settings.getDatabase();
        username = settings.getUser();
        password = settings.getPassword();
        try {
            synchronized (this) {
                if (getConnection() != null && !getConnection().isClosed()) return;
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://" + this.settings.getHost() + ":" + this.settings.getPort();
                setConnection(DriverManager.getConnection(url + "?useUnicode=true&characterEncoding=utf8", this.username, this.password));
                PreparedStatement db = getConnection().prepareStatement("CREATE DATABASE IF NOT EXISTS " + this.database);
                db.execute();
                setConnection(DriverManager.getConnection(url + "/" + this.database + "?useUnicode=true&characterEncoding=utf8", this.username, this.password));
                String[] statements = {"CREATE TABLE IF NOT EXISTS `ranking_global` (`name` VARCHAR(36), `points` INTEGER, `rank` INTEGER, PRIMARY KEY (`name`))", "CREATE TABLE IF NOT EXISTS `ranking_pvp` (`name` VARCHAR(36), `points` INTEGER, `rank` INTEGER, PRIMARY KEY (`name`))", "CREATE TABLE IF NOT EXISTS `ranking_farm` (`name` VARCHAR(36), `points` INTEGER, `rank` INTEGER, PRIMARY KEY (`name`))"};
                for (String statement : statements) getConnection().prepareStatement(statement).execute();
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createFiles(Logger logger, ClassLoader classLoader) {
        FileManager config = FileManager.CONFIG;
        FileManager lang = FileManager.LANG;
        lang.create(logger);
        config.create(logger);
        try (final Reader reader = Files.newBufferedReader(config.getFile().toPath(), StandardCharsets.UTF_8)) {
            Yaml yaml = new Yaml(new CustomClassLoaderConstructor(classLoader));
            yaml.setBeanAccess(BeanAccess.FIELD);
            this.settings = yaml.loadAs(reader, Settings.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public GuiManager getGuiManager() {
        return this.guiManager;
    }

    public TaskManager getTaskManager() {
        return this.taskManager;
    }

    public DatabaseManager getDatabaseManager() {
        return databaseManager;
    }

    public Launcher getLauncher() {
        return this.launcher;
    }

    public Settings getSettings() {
        return this.settings;
    }
}
