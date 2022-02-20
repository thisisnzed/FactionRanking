package com.nz1337.ranking.manager;

import com.nz1337.ranking.Ranking;
import com.nz1337.ranking.gui.EventGUI;
import com.nz1337.ranking.gui.FarmGUI;
import com.nz1337.ranking.gui.GlobalGUI;
import com.nz1337.ranking.gui.MainGUI;
import lombok.Getter;

public class GuiManager {

    @Getter
    private final FarmGUI farmGUI;
    @Getter
    private final EventGUI eventGUI;
    @Getter
    private final MainGUI mainGUI;
    @Getter
    private final GlobalGUI globalGUI;

    public GuiManager(final Ranking ranking) {
        this.farmGUI = new FarmGUI(ranking);
        this.eventGUI = new EventGUI(ranking);
        this.mainGUI = new MainGUI(ranking);
        this.globalGUI = new GlobalGUI(ranking);
    }
}