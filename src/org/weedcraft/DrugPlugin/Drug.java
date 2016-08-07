package org.weedcraft.DrugPlugin;

import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class Drug {
    private ItemStack itemStack;
    private String name;
    private String message;
    private String type;
    private Integer[] effects;
    private Integer[] negatives;
    private int negativeChance;
    private boolean negative;
    private boolean sneak;
    private boolean edible;
    private Set<String> particles;

    public Drug(ItemStack itemStack, String name, String message, String type, Set<String> particles,
                Integer[] effects, Integer[] negatives, int negativeChance, boolean negative, boolean sneak, boolean
                        edible) {
        this.itemStack = itemStack;
        this.name = name;
        this.message = message;
        this.type = type;
        this.particles = particles;
        this.effects = effects;
        this.negatives = negatives;
        this.negativeChance = negativeChance;
        this.negative = negative;
        this.sneak = sneak;
        this.edible = edible;
    }

    /**
     * Get the configured drug's name
     *
     * @return The name of the drug
     */
    public String getName() {
        return name;
    }

    /**
     * Get the configured drug's message
     *
     * @return The message for the drug
     */
    public String getMessage() {
        return message;
    }

    /**
     * Get all particles associated with the drug
     *
     * @return A collection of particles associated with the drug
     */
    public Set<String> getParticles() {
        return particles;
    }

    /**
     * Get all effects associated with the drug
     *
     * @return A collection of effects associated with the drug
     */
    public List<Integer> getEffects() {
        return Arrays.asList(effects);
    }

    /**
     * Get all negatives associated with the drug
     *
     * @return A collection of negatives associated with the drug
     */
    public List<Integer> getNegatives() {
        return Arrays.asList(negatives);
    }

    /**
     * Get the chance for a negative effect to happen
     *
     * @return The negative effect chance
     */
    public int getNegativeChance() {
        return negativeChance;
    }

    /**
     * Get the effect application type (all/random/none)
     *
     * @return The type of effect application (all/random/none)
     */
    public String getType() {
        return type;
    }

    /**
     * Get whether the drug has negatives
     *
     * @return If the drug has negatives
     */
    public boolean isNegative() {
        return negative;
    }

    /**
     * Get whether you need to crouch for the drug
     *
     * @return If the drug requires you to sneak
     */
    public boolean isSneak() {
        return sneak;
    }

    /**
     * Get whether the drug is edible
     *
     * @return If the drug is edible
     */
    public boolean isEdible() {
        return edible;
    }

    /**
     * Get the ItemStack associated with the drug
     *
     * @return The ItemStack associated with the drug
     */
    public ItemStack getItemStack() {
        return itemStack;
    }
}