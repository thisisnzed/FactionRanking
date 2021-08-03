package com.nz1337.ranking.launcher;

import com.nz1337.ranking.Ranking;
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