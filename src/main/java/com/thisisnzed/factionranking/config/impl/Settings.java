package com.thisisnzed.factionranking.config.impl;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class Settings {

    private String host, port, database, user, password, permission, farmingTitle, pvpTitle, mainTitle, mainItemPvp, mainItemFarm, mainItemGlobal, globalTitle, topName, skullName, bestFactionsLoreByLine, bestFactionsName;
    private int timeUpdater;
    private ArrayList<String> topLore, skullLore, mainItemFarmLore, mainItemGlobalLore, mainItemPvpLore;
}
