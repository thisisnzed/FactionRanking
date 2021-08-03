package com.nz1337.ranking.manager;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.gui.FarmGUI;
import com.nz1337.ranking.gui.GlobalGUI;
import com.nz1337.ranking.gui.MainGUI;
import com.nz1337.ranking.gui.EventGUI;

public class GuiManager {

    private final FarmGUI farmGUI;
    private final EventGUI eventGUI;
    private final MainGUI mainGUI;
    private final GlobalGUI globalGUI;

    public GuiManager(Ranking ranking) {
        this.farmGUI = new FarmGUI(ranking);
        this.eventGUI = new EventGUI(ranking);
        this.mainGUI = new MainGUI(ranking);
        this.globalGUI = new GlobalGUI(ranking);
    }

    public FarmGUI getFarmGUI() {
        return this.farmGUI;
    }

    public EventGUI getEventGUI() {
        return this.eventGUI;
    }

    public MainGUI getMainGUI() {
        return this.mainGUI;
    }

    public GlobalGUI getGlobalGUI() {
        return this.globalGUI;
    }
}