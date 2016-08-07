package org.weedcraft.DrugPlugin.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.weedcraft.DrugPlugin.WCDrugs;
import org.weedcraft.DrugPlugin.handlers.ConfigHandler;
import org.weedcraft.DrugPlugin.handlers.DrugHandler;
import org.weedcraft.DrugPlugin.handlers.PlayerHandler;

public class DMUConfigReloadEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private DrugHandler dh;
    private PlayerHandler ph;
    private ConfigHandler ch;

    public DMUConfigReloadEvent() {
        this.dh = WCDrugs.getDrugHandler();
        this.ph = WCDrugs.getPlayerHandler();
        this.ch = WCDrugs.getConfigHandler();
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public DrugHandler getDrugHandler() {
        return dh;
    }

    public PlayerHandler getPlayerHandler() {
        return ph;
    }

    public ConfigHandler getConfigHandler() {
        return ch;
    }
}
