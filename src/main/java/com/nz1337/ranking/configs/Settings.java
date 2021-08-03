package com.nz1337.ranking.configs;

import java.util.ArrayList;

public class Settings {

    private String host, port, database, user, password, permission, farmingTitle, pvpTitle, mainTitle, mainItemPvp, mainItemFarm, mainItemGlobal, globalTitle, topName, skullName, bestFactionsLoreByLine, bestFactionsName;
    private int timeUpdater;
    private ArrayList<String> topLore, skullLore, mainItemFarmLore, mainItemGlobalLore, mainItemPvpLore;

    public String getBestFactionsLoreByLine() {
        return bestFactionsLoreByLine;
    }

    public void setBestFactionsLoreByLine(String bestFactionsLoreByLine) {
        this.bestFactionsLoreByLine = bestFactionsLoreByLine;
    }

    public String getBestFactionsName() {
        return bestFactionsName;
    }

    public void setBestFactionsName(String bestFactionsName) {
        this.bestFactionsName = bestFactionsName;
    }

    public ArrayList<String> getMainItemFarmLore() {
        return mainItemFarmLore;
    }

    public void setMainItemFarmLore(ArrayList<String> mainItemFarmLore) {
        this.mainItemFarmLore = mainItemFarmLore;
    }

    public ArrayList<String> getMainItemGlobalLore() {
        return mainItemGlobalLore;
    }

    public void setMainItemGlobalLore(ArrayList<String> mainItemGlobalLore) {
        this.mainItemGlobalLore = mainItemGlobalLore;
    }

    public ArrayList<String> getMainItemPvpLore() {
        return mainItemPvpLore;
    }

    public void setMainItemPvpLore(ArrayList<String> mainItemPvpLore) {
        this.mainItemPvpLore = mainItemPvpLore;
    }

    public ArrayList<String> getSkullLore() {
        return skullLore;
    }

    public void setSkullLore(ArrayList<String> skullLore) {
        this.skullLore = skullLore;
    }

    public String getSkullName() {
        return skullName;
    }

    public void setSkullName(String skullName) {
        this.skullName = skullName;
    }

    public String getTopName() {
        return topName;
    }

    public void setTopName(String topName) {
        this.topName = topName;
    }

    public ArrayList<String> getTopLore() {
        return topLore;
    }

    public void setTopLore(ArrayList<String> topLore) {
        this.topLore = topLore;
    }

    public String getMainItemGlobal() {
        return mainItemGlobal;
    }

    public void setMainItemGlobal(String mainItemGlobal) {
        this.mainItemGlobal = mainItemGlobal;
    }

    public String getGlobalTitle() {
        return globalTitle;
    }

    public void setGlobalTitle(String globalTitle) {
        this.globalTitle = globalTitle;
    }

    public int getTimeUpdater() {
        return timeUpdater;
    }

    public void setTimeUpdater(int timeUpdater) {
        this.timeUpdater = timeUpdater;
    }

    public String getMainItemPvp() {
        return mainItemPvp;
    }

    public void setMainItemPvp(String mainItemPvp) {
        this.mainItemPvp = mainItemPvp;
    }

    public String getMainItemFarm() {
        return mainItemFarm;
    }

    public void setMainItemFarm(String mainItemFarm) {
        this.mainItemFarm = mainItemFarm;
    }

    public String getPvpTitle() {
        return pvpTitle;
    }

    public String getMainTitle() {
        return mainTitle;
    }

    public void setPvpTitle(String pvpTitle) {
        this.pvpTitle = pvpTitle;
    }

    public void setMainTitle(String mainTitle) {
        this.mainTitle = mainTitle;
    }

    public String getFarmingTitle() {
        return farmingTitle;
    }

    public void setFarmingTitle(String farmingTitle) {
        this.farmingTitle = farmingTitle;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
