package com.nz1337.ranking.utils;

import com.earth2me.essentials.api.UserDoesNotExistException;
import org.bukkit.entity.Player;

public class Economy {

    public static double getMoney(final Player player) {
        try {
            return Double.parseDouble(String.valueOf(com.earth2me.essentials.api.Economy.getMoney(player.getName())));
        } catch (final UserDoesNotExistException exception) {
            exception.printStackTrace();
        }
        return 0.0;
    }
}
