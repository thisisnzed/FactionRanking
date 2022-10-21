package com.thisisnzed.factionranking.gui;

import com.thisisnzed.factionranking.Ranking;
import com.thisisnzed.factionranking.gui.impl.PvpGui;
import com.thisisnzed.factionranking.gui.impl.FarmGui;
import com.thisisnzed.factionranking.gui.impl.GlobalGui;
import com.thisisnzed.factionranking.gui.impl.MainGui;
import lombok.Getter;

public class GuiManager {

    @Getter
    private final FarmGui farmGUI;
    @Getter
    private final PvpGui pvpGui;
    @Getter
    private final MainGui mainGUI;
    @Getter
    private final GlobalGui globalGUI;

    public GuiManager(final Ranking ranking) {
        this.farmGUI = new FarmGui(ranking);
        this.pvpGui = new PvpGui(ranking);
        this.mainGUI = new MainGui(ranking);
        this.globalGUI = new GlobalGui(ranking);
    }
}