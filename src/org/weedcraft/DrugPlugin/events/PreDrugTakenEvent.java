package org.weedcraft.DrugPlugin.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.weedcraft.DrugPlugin.Drug;

public class PreDrugTakenEvent extends Event implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private Drug drug;
    private Player player;
    private boolean cancelled;

    public PreDrugTakenEvent(Player p, Drug drug) {
        this.player = p;
        this.drug = drug;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    /**
     * Is the drug taking being cancelled
     *
     * @return If the drug taking is being cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Cancel the taking of the drug
     *
     * @param cancel Whether the drug taking should be cancelled
     */
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    /**
     * Get the drug being taken
     *
     * @return The drug being taken
     */
    public Drug getDrug() {
        return drug;
    }

    /**
     * Set the drug being taken
     *
     * @param drug The Drug being taken
     */
    public void setDrug(Drug drug) {
        this.drug = drug;
    }

    /**
     * Get the player using the Drug
     *
     * @return The player using the drug
     */
    public Player getPlayer() {
        return player;
    }
}
