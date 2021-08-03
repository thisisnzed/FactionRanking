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

    public SQLHandler(Ranking ranking) {
        this.ranking = ranking;
    }

    public SQLHandler setTable(String table) {
        this.table = !table.contains("ranking_") ? "ranking_" + table : table;
        return this;
    }

    public boolean isExists(String tag) {
        Connection connection = ranking.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + this.table + "` WHERE `name`=?");
            preparedStatement.setString(1, tag);
            if (preparedStatement.executeQuery().next()) return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void register(String tag) {
        Connection connection = ranking.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + this.table + "` WHERE `name`=?");
            preparedStatement.setString(1, tag);
            preparedStatement.executeQuery().next();
            if (!isExists(tag)) {
                PreparedStatement insert = connection.prepareStatement("INSERT INTO `" + this.table + "` (`name`,`points`,`rank`) VALUE (?,?,?)");
                insert.setString(1, tag);
                insert.setInt(2, 0);
                insert.setInt(3, -1);
                insert.executeUpdate();
                System.out.println("[Ranking] (" + table + ") A new faction has been registered. Tag: " + tag);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setName(String oldName, String factionName) {
        if (!isExists(oldName)) return;
        Connection connection = ranking.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `" + this.table + "` SET `name`=? WHERE `name`=?");
            preparedStatement.setString(1, factionName);
            preparedStatement.setString(2, oldName);
            preparedStatement.executeUpdate();
            if (isExists(oldName)) delete(oldName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getName(String tag) {
        register(tag);
        Connection connection = ranking.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + this.table + "` WHERE `name`=?");
            preparedStatement.setString(1, tag);
            ResultSet results = preparedStatement.executeQuery();
            results.next();
            return results.getString("name");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "Aucune";
    }

    public void addPoints(String tag, int amount) {
        register(tag);
        Connection connection = ranking.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `" + this.table + "` SET `points`=? WHERE `name`=?");
            preparedStatement.setInt(1, getPoints(tag) + amount);
            preparedStatement.setString(2, tag);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removePoints(String tag, int amount) {
        register(tag);
        Connection connection = ranking.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `" + this.table + "` SET `points`=? WHERE `name`=?");
            preparedStatement.setInt(1, getPoints(tag) - amount);
            preparedStatement.setString(2, tag);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getPoints(String tag) {
        Connection connection = ranking.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + this.table + "` WHERE `name`=?");
            preparedStatement.setString(1, tag);
            ResultSet results = preparedStatement.executeQuery();
            if (results.next()) return results.getInt("points");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public void setPoints(String tag, int amount) {
        register(tag);
        Connection connection = ranking.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `" + this.table + "` SET `points`=? WHERE `name`=?");
            preparedStatement.setInt(1, amount);
            preparedStatement.setString(2, tag);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setRank(String tag, int rank) {
        register(tag);
        Connection connection = ranking.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE `" + this.table + "` SET `rank`=? WHERE `name`=?");
            preparedStatement.setInt(1, rank);
            preparedStatement.setString(2, tag);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getRank(String tag) {
        register(tag);
        Connection connection = ranking.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + this.table + "` WHERE `name`=?");
            preparedStatement.setString(1, tag);
            ResultSet results = preparedStatement.executeQuery();
            results.next();
            return results.getInt("rank");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return -1;
    }

    public void delete(String tag) {
        if (!isExists(tag)) return;
        Connection connection = ranking.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM `" + this.table + "` WHERE `" + this.table + "`.`name`=?");
            preparedStatement.setString(1, tag);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getFactionAt(int rank) {
        Connection connection = ranking.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + this.table + "` WHERE `rank`=?");
            preparedStatement.setInt(1, rank);
            ResultSet results = preparedStatement.executeQuery();
            if (results.next()) return results.getString("name");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return "Aucune";
    }

    public ArrayList<String> getAllFactions() {
        Connection connection = ranking.getConnection();
        ArrayList<String> factions = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM `" + this.table + "`");
            ResultSet results = preparedStatement.executeQuery();
            while (results.next()) factions.add(results.getString("name"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return factions;
    }
}