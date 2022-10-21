package com.thisisnzed.factionranking.launcher;

import com.thisisnzed.factionranking.Ranking;
import org.bukkit.plugin.java.JavaPlugin;

public class Launcher extends JavaPlugin {

    private Launch launch;

    @Override
    public void onEnable() {
        this.launch = new Ranking();
        this.launch.launch(this, getClassLoader());
    }

    @Override
    public void onDisable() {
        this.launch.shutdown();
    }
}