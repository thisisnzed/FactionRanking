package com.thisisnzed.factionranking.config;

import com.thisisnzed.factionranking.launcher.Launcher;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.logging.Logger;

public enum FileManager {

    CONFIG("config.yml"), LANG("lang.yml");

    private final Launcher instance = Launcher.getPlugin(Launcher.class);
    @Getter private final String fileName;
    private final File dataFolder;

    FileManager(final String fileName) {
        this.fileName = fileName;
        this.dataFolder = this.instance.getDataFolder();
    }

    public File getFile() {
        return new File(this.dataFolder, this.fileName);
    }

    public void create(final Logger logger) {
        if (this.fileName == null || this.fileName.isEmpty()) {
            throw new IllegalArgumentException("ResourcesPath cannot be null or empty");
        }
        InputStream in = instance.getResource(this.fileName);
        if (in == null) {
            throw new IllegalArgumentException("The resource " + this.fileName + " cannot be found in jar");
        }
        if (!dataFolder.exists() && !dataFolder.mkdir()) {
            logger.severe("Failed to make directory");
        }
        File outFile = getFile();
        try {
            if (!outFile.exists()) {
                logger.info("The " + this.fileName + " wasn't found, creation in progress");
                OutputStream out = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int n;
                while ((n = in.read(buffer)) >= 0) {
                    out.write(buffer, 0, n);
                }
                out.close();
                in.close();
                if (!outFile.exists()) {
                    logger.severe("Unable to copy file");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public FileConfiguration getConfig() {
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public void save(FileConfiguration fileConfiguration) {
        try {
            fileConfiguration.save(getFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
