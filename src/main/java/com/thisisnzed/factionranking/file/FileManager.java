package com.thisisnzed.factionranking.file;


import com.thisisnzed.factionranking.plugin.Launcher;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Logger;

public enum FileManager {

    CONFIG("config.yml");

    private final Launcher instance;
    private final String fileName;
    public final File dataFolder, file;
    private YamlConfiguration config;

    FileManager(final String fileName) {
        this.instance = Launcher.getPlugin(Launcher.class);
        this.fileName = fileName;
        this.dataFolder = this.instance.getDataFolder();
        this.file = new File(this.dataFolder, this.fileName);
        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void create(final Logger logger) {
        if (this.fileName == null || this.fileName.isEmpty()) {
            throw new IllegalArgumentException("ResourcesPath cannot be null or empty");
        }
        final InputStream in = this.instance.getResource(this.fileName);
        if (in == null) {
            throw new IllegalArgumentException("The resource " + this.fileName + " cannot be found in jar");
        }
        if (!this.dataFolder.exists() && !this.dataFolder.mkdir()) {
            logger.severe("Failed to make directory");
        }
        final File outFile = this.file;
        try {
            if (!outFile.exists()) {
                logger.info("The " + this.fileName + " wasn't found, creation in progress");
                final OutputStream out = new FileOutputStream(outFile);
                final byte[] buffer = new byte[1024];
                int n;
                while ((n = in.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
                out.close();
                in.close();
                this.config = YamlConfiguration.loadConfiguration(this.file);
                if (!outFile.exists()) {
                    logger.severe("Unable to copy file");
                }
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public FileConfiguration getConfig() {
        return this.config;
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (final IOException exception) {
            exception.printStackTrace();
        }

    }

    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(this.file);
        this.save();
    }
}
