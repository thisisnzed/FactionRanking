package com.thisisnzed.factionranking.sql;

import com.thisisnzed.factionranking.file.FileManager;
import com.thisisnzed.factionranking.sql.impl.RankingHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Getter
@Setter
public class DatabaseManager {

    private Connection connection;

    private final RankingHandler rankingHandler;

    public DatabaseManager() {
        this.rankingHandler = new RankingHandler(this);
    }

    public void initialize() {
        final FileConfiguration fileConfiguration = FileManager.CONFIG.getConfig();
        final String database = fileConfiguration.getString("storage.database");
        final String username = fileConfiguration.getString("storage.user");
        final String password = fileConfiguration.getString("storage.password");
        try {
            synchronized (this) {
                if (this.getConnection() != null && !this.getConnection().isClosed()) return;
                Class.forName("com.mysql.jdbc.Driver");
                final String url = "jdbc:mysql://" + fileConfiguration.getString("storage.host") + ":" + fileConfiguration.getInt("storage.port");
                this.setConnection(DriverManager.getConnection(url + "?useUnicode=true&characterEncoding=utf8&autoReconnect=true", username, password));
                final PreparedStatement prepareStatement = this.getConnection().prepareStatement("CREATE DATABASE IF NOT EXISTS " + database);
                prepareStatement.execute();
                this.setConnection(DriverManager.getConnection(url + "/" + database + "?useUnicode=true&characterEncoding=utf8", username, password));
                this.getConnection().prepareStatement("CREATE TABLE IF NOT EXISTS `ranking` (`name` VARCHAR(36), `points` INTEGER, `rank` INTEGER, PRIMARY KEY (`name`))").execute();
            }
        } catch (final SQLException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }
    }
}
