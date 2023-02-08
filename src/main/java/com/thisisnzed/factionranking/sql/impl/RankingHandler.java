package com.thisisnzed.factionranking.sql.impl;

import com.thisisnzed.factionranking.sql.DatabaseManager;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

public class RankingHandler {

    private final DatabaseManager databaseManager;

    public RankingHandler(final DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }


    public boolean isExists(final String tag) {
        final Connection connection = this.databaseManager.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT `name` FROM `ranking` WHERE `name`=?");
            preparedStatement.setString(1, tag);
            if (preparedStatement.executeQuery().next()) return true;
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public void register(final String tag) {
        final Connection connection = this.databaseManager.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT `name` FROM `ranking` WHERE `name`=?");
            preparedStatement.setString(1, tag);
            preparedStatement.executeQuery().next();
            if (!this.isExists(tag)) {
                final PreparedStatement insert = connection.prepareStatement("INSERT INTO `ranking` (`name`,`points`,`rank`) VALUE (?,?,?)");
                insert.setString(1, tag);
                insert.setInt(2, 0);
                insert.setInt(3, -1);
                insert.executeUpdate();
                Bukkit.getLogger().info("A new faction has been registered. Tag: " + tag);
            }
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void addPoints(final String tag, final int amount) {
        try {
            this.setPoints(tag, this.getPoints(tag).get() + amount);
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }

    public void removePoints(final String tag, final int amount) {
        try {
            this.setPoints(tag, this.getPoints(tag).get() - amount);
        } catch (final Exception exception) {
            exception.printStackTrace();
        }
    }

    public void setName(final String tag, final String name) {
        final Connection connection = this.databaseManager.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `ranking` SET `name`=? WHERE `name`=?");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, tag);
            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void setPoints(final String tag, final int amount) {
        this.register(tag);
        final Connection connection = this.databaseManager.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `ranking` SET `points`=? WHERE `name`=?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, tag);
            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void setRank(final String tag, final int rank) {
        this.register(tag);
        final Connection connection = this.databaseManager.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `ranking` SET `rank`=? WHERE `name`=?");
            preparedStatement.setInt(1, rank);
            preparedStatement.setString(2, tag);
            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
    }

    public CompletableFuture<Integer> getRank(final String tag) {
        return CompletableFuture.supplyAsync(() -> {
            final Connection connection = this.databaseManager.getConnection();
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT `rank` FROM `ranking` WHERE `name`=?");
                preparedStatement.setString(1, tag);
                final ResultSet results = preparedStatement.executeQuery();
                results.next();
                return results.getInt("rank");
            } catch (final SQLException exception) {
                exception.printStackTrace();
            }
            return -1;
        });
    }

    public CompletableFuture<Integer> getPoints(final String tag) {
        return CompletableFuture.supplyAsync(() -> {
            final Connection connection = this.databaseManager.getConnection();
            try {
                final PreparedStatement preparedStatement = connection.prepareStatement("SELECT `points` FROM `ranking` WHERE `name`=?");
                preparedStatement.setString(1, tag);
                final ResultSet results = preparedStatement.executeQuery();
                if (results.next()) return results.getInt("points");
            } catch (final SQLException exception) {
                exception.printStackTrace();
            }
            return 0;
        });
    }

    public void delete(String tag) {
        if (!this.isExists(tag)) return;
        final Connection connection = this.databaseManager.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `ranking` WHERE `ranking`.`name`=?");
            preparedStatement.setString(1, tag);
            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
    }

    public String getFactionAt(final int rank) {
        final Connection connection = this.databaseManager.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT `name` FROM `ranking` WHERE `rank`=?");
            preparedStatement.setInt(1, rank);
            final ResultSet results = preparedStatement.executeQuery();
            if (results.next()) return results.getString("name");
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
        return "Aucune";
    }

    public ArrayList<String> getAllFactions(final int limit) {
        final Connection connection = this.databaseManager.getConnection();
        final ArrayList<String> factions = new ArrayList<>();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT `name` FROM `ranking` ORDER BY `points` DESC LIMIT ?");
            preparedStatement.setInt(1, limit);
            final ResultSet results = preparedStatement.executeQuery();
            while (results.next()) factions.add(results.getString("name"));
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
        return factions;
    }

    public ArrayList<String> getAllFactions() {
        return this.getAllFactions(Integer.MAX_VALUE);
    }
}