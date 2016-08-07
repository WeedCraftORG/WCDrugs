package org.weedcraft.DrugPlugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.weedcraft.DrugPlugin.Drug;

import java.util.ArrayList;

public class DrugTakenEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private Player player;
    private Drug drug;
    private boolean wasNegative;
    private ArrayList<Integer> effectsGiven;

    public DrugTakenEvent(Player player, Drug drug, boolean wasNegative, ArrayList<Integer> effectsGiven) {
        this.player = player;
        this.drug = drug;
        this.wasNegative = wasNegative;
        this.effectsGiven = effectsGiven;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Get the player who took the drug
     *
     * @return The player who took the drug
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Get the type of drug taken
     *
     * @return The Drug the player took
     */
    public Drug getDrug() {
        return drug;
    }

    /**
     * Get whether the player was given a negative effect
     *
     * @return If the player was given a negative effect from the drug
     */
    public boolean hadNegative() {
        return wasNegative;
    }

    /**
     * Get an ArrayList of the effects given to the player
     *
     * @return An ArrayList of all the effects given to the player
     */
    public ArrayList<Integer> getEffectsGiven() {
        return effectsGiven;
    }
}
