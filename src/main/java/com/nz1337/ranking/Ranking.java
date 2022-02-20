package com.nz1337.ranking;

import com.nz1337.ranking.commands.CommandManager;
import com.nz1337.ranking.configs.Settings;
import com.nz1337.ranking.launcher.Launch;
import com.nz1337.ranking.launcher.Launcher;
import com.nz1337.ranking.manager.*;
import com.nz1337.ranking.utils.GUIUtils;
import lombok.Getter;
import lombok.Setter;
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
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class Ranking implements Launch {

    @Getter
    private Launcher launcher;
    private CommandManager commandManager;
    private ListenerManager listenerManager;
    @Getter
    private DatabaseManager databaseManager;
    @Getter
    private TaskManager taskManager;
    @Getter
    private GuiManager guiManager;
    @Getter
    private PlaceholderManager placeholderManager;
    @Getter
    private Settings settings;
    @Getter
    private String database, username, password;
    @Getter
    @Setter
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
            this.getTaskManager().getRankUpdater().runTaskTimerAsynchronously(this.getLauncher(), 60L, (this.getSettings().getTimeUpdater() * 1200L));
            if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
                this.placeholderManager = new PlaceholderManager(this);
            GUIUtils.setSettings(this.settings);
        });
    }

    @Override
    public void shutdown() {
        System.out.println("[Ranking] " + this.getDatabaseManager().getSqlHandler().setTable("global").getAllFactions().size() + " factions have been unloaded!");
        Bukkit.getScheduler().cancelTasks(this.launcher);
    }

    private void sqlInitializer() {
        this.database = this.settings.getDatabase();
        this.username = this.settings.getUser();
        this.password = this.settings.getPassword();
        try {
            synchronized (this) {
                if (this.getConnection() != null && !this.getConnection().isClosed()) return;
                Class.forName("com.mysql.jdbc.Driver");
                final String url = "jdbc:mysql://" + this.settings.getHost() + ":" + this.settings.getPort();
                this.setConnection(DriverManager.getConnection(url + "?useUnicode=true&characterEncoding=utf8", this.username, this.password));
                final PreparedStatement prepareStatement = this.getConnection().prepareStatement("CREATE DATABASE IF NOT EXISTS " + this.database);
                prepareStatement.execute();
                this.setConnection(DriverManager.getConnection(url + "/" + this.database + "?useUnicode=true&characterEncoding=utf8", this.username, this.password));
                for (final String statement : Arrays.asList("CREATE TABLE IF NOT EXISTS `ranking_global` (`name` VARCHAR(36), `points` INTEGER, `rank` INTEGER, PRIMARY KEY (`name`))", "CREATE TABLE IF NOT EXISTS `ranking_pvp` (`name` VARCHAR(36), `points` INTEGER, `rank` INTEGER, PRIMARY KEY (`name`))", "CREATE TABLE IF NOT EXISTS `ranking_farm` (`name` VARCHAR(36), `points` INTEGER, `rank` INTEGER, PRIMARY KEY (`name`))")) {
                    this.getConnection().prepareStatement(statement).execute();
                }
            }
        } catch (final SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }

    private void createFiles(final Logger logger, final ClassLoader classLoader) {
        final FileManager config = FileManager.CONFIG;
        final FileManager lang = FileManager.LANG;
        lang.create(logger);
        config.create(logger);
        try (final Reader reader = Files.newBufferedReader(config.getFile().toPath(), StandardCharsets.UTF_8)) {
            final Yaml yaml = new Yaml(new CustomClassLoaderConstructor(classLoader));
            yaml.setBeanAccess(BeanAccess.FIELD);
            this.settings = yaml.loadAs(reader, Settings.class);
        } catch (final IOException exception) {
            exception.printStackTrace();
        }
    }
}
