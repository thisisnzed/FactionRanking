package com.nz1337.ranking.storage;

import com.nz1337.ranking.Ranking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SQLHandler {

    private final Ranking ranking;
    private String table;

    public SQLHandler(final Ranking ranking) {
        this.ranking = ranking;
    }

    public SQLHandler setTable(final String table) {
        this.table = !table.contains("ranking_") ? "ranking_" + table : table;
        return this;
    }

    public boolean isExists(final String tag) {
        final Connection connection = this.ranking.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + this.table + "` WHERE `name`=?");
            preparedStatement.setString(1, tag);
            if (preparedStatement.executeQuery().next()) return true;
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public void register(final String tag) {
        final Connection connection = this.ranking.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + this.table + "` WHERE `name`=?");
            preparedStatement.setString(1, tag);
            preparedStatement.executeQuery().next();
            if (!this.isExists(tag)) {
                final PreparedStatement insert = connection.prepareStatement("INSERT INTO `" + this.table + "` (`name`,`points`,`rank`) VALUE (?,?,?)");
                insert.setString(1, tag);
                insert.setInt(2, 0);
                insert.setInt(3, -1);
                insert.executeUpdate();
                System.out.println("[Ranking] (" + this.table + ") A new faction has been registered. Tag: " + tag);
            }
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void setName(final String oldName, final String factionName) {
        if (!this.isExists(oldName)) return;
        final Connection connection = this.ranking.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `" + this.table + "` SET `name`=? WHERE `name`=?");
            preparedStatement.setString(1, factionName);
            preparedStatement.setString(2, oldName);
            preparedStatement.executeUpdate();
            if (this.isExists(oldName)) this.delete(oldName);
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
    }

    public String getName(final String tag) {
        this.register(tag);
        final Connection connection = this.ranking.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + this.table + "` WHERE `name`=?");
            preparedStatement.setString(1, tag);
            final ResultSet results = preparedStatement.executeQuery();
            results.next();
            return results.getString("name");
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
        return "Aucune";
    }

    public void addPoints(final String tag, final int amount) {
        this.register(tag);
        final Connection connection = this.ranking.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `" + this.table + "` SET `points`=? WHERE `name`=?");
            preparedStatement.setInt(1, this.getPoints(tag) + amount);
            preparedStatement.setString(2, tag);
            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void removePoints(final String tag, final int amount) {
        this.register(tag);
        final Connection connection = this.ranking.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `" + this.table + "` SET `points`=? WHERE `name`=?");
            preparedStatement.setInt(1, this.getPoints(tag) - amount);
            preparedStatement.setString(2, tag);
            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
    }

    public int getPoints(final String tag) {
        final Connection connection = this.ranking.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + this.table + "` WHERE `name`=?");
            preparedStatement.setString(1, tag);
            final ResultSet results = preparedStatement.executeQuery();
            if (results.next()) return results.getInt("points");
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public void setPoints(final String tag, final int amount) {
        this.register(tag);
        final Connection connection = this.ranking.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `" + this.table + "` SET `points`=? WHERE `name`=?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, tag);
            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void setRank(final String tag, final int rank) {
        this.register(tag);
        final Connection connection = this.ranking.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `" + this.table + "` SET `rank`=? WHERE `name`=?");
            preparedStatement.setInt(1, rank);
            preparedStatement.setString(2, tag);
            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
    }

    public int getRank(final String tag) {
        this.register(tag);
        final Connection connection = this.ranking.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + this.table + "` WHERE `name`=?");
            preparedStatement.setString(1, tag);
            final ResultSet results = preparedStatement.executeQuery();
            results.next();
            return results.getInt("rank");
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
        return -1;
    }

    public void delete(String tag) {
        if (!this.isExists(tag)) return;
        final Connection connection = this.ranking.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `" + this.table + "` WHERE `" + this.table + "`.`name`=?");
            preparedStatement.setString(1, tag);
            preparedStatement.executeUpdate();
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
    }

    public String getFactionAt(final int rank) {
        final Connection connection = this.ranking.getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + this.table + "` WHERE `rank`=?");
            preparedStatement.setInt(1, rank);
            final ResultSet results = preparedStatement.executeQuery();
            if (results.next()) return results.getString("name");
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
        return "Aucune";
    }

    public ArrayList<String> getAllFactions() {
        final Connection connection = this.ranking.getConnection();
        final ArrayList<String> factions = new ArrayList<>();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + this.table + "`");
            final ResultSet results = preparedStatement.executeQuery();
            while (results.next()) factions.add(results.getString("name"));
        } catch (final SQLException exception) {
            exception.printStackTrace();
        }
        return factions;
    }
}