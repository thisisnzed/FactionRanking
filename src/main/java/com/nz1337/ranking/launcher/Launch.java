package com.nz1337.ranking.launcher;

public interface Launch {

    void shutdown();

    void launch(Launcher launcher, ClassLoader classLoader);

}
