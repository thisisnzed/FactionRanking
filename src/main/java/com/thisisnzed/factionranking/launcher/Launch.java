package com.thisisnzed.factionranking.launcher;

public interface Launch {

    void shutdown();

    void launch(Launcher launcher, ClassLoader classLoader);

}
