package org.weedcraft.DrugPlugin.handlers;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.weedcraft.DrugPlugin.Drug;
import org.weedcraft.DrugPlugin.WCDrugs;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class DrugHandler {

    private WCDrugs plugin;
    private HashMap<ItemStack, Drug> drugs = new HashMap<>();
    private FileConfiguration config;

    public DrugHandler(WCDrugs plugin) {
        this.plugin = plugin;
        this.config = plugin.config;
    }

    /**
     * Get Drug instance from ItemStack
     *
     * @param item The ItemStack
     * @return The Drug instance
     */
    public Drug getDrug(ItemStack item) {
        for (Drug drug : getDrugs()) {
            Material drugMat = drug.getItemStack().getType();
            short drugDmg = drug.getItemStack().getDurability();
            if (drugMat == item.getType() && drugDmg == item.getDurability()) {
                return drug;
            }
        }
        return null;
    }

    /**
     * Get Drug instance from Material
     *
     * @param mat The Material
     * @return The Drug instance
     */
    public Drug getDrug(Material mat, short dmg) {
        for (Drug drug : getDrugs()) {
            Material drugMat = drug.getItemStack().getType();
            short drugDmg = drug.getItemStack().getDurability();
            if (drugMat == mat && drugDmg == dmg) {
                return drug;
            }
        }
        return null;
    }

    /**
     * Get Drug instance from name
     *
     * @param name The name
     * @return The drug instance
     */
    public Drug getDrug(String name) {
        for (Drug drug : getDrugs()) {
            if (drug.getName().equalsIgnoreCase(name)) {
                return drug;
            }
        }
        return null;
    }

    /**
     * Get the list of Drugs
     *
     * @return A collection of Drugs
     */
    public Collection<Drug> getDrugs() {
        return drugs.values();
    }

    /**
     * Check if the ItemStack is a Drug
     *
     * @param item The ItemStack
     * @return If the ItemStack is a Drug
     */
    public boolean isDrug(ItemStack item) {
        for (Drug drug : getDrugs()) {
            Material mat = drug.getItemStack().getType();
            short dmg = drug.getItemStack().getDurability();
            if (mat.name().equalsIgnoreCase(item.getType().name()) && dmg == item.getDurability()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the Material is a Drug
     *
     * @param mat The Material
     * @return If the Material is a Drug
     */
    public boolean isDrug(Material mat, short dmg) {
        for (Drug drug : getDrugs()) {
            Material drugMat = drug.getItemStack().getType();
            short drugDmg = drug.getItemStack().getDurability();
            if (drugMat.name().equalsIgnoreCase(mat.name()) && drugDmg == dmg) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gather a collection full of Drugs
     */
    @SuppressWarnings("unchecked")
	public int gatherDrugs() {
        for (String key : config.getConfigurationSection("Drugs").getKeys(false)) {
            String path = "Drugs." + key + ".";
            if (!drugProblem(key)) {
                Material mat = Material.getMaterial(key.split(":")[0]);
                short dmg = (key.split(":").length == 1) ? 0 : Short.parseShort(key.split(":")[1]);
                ItemStack item = new ItemStack(mat, 0, dmg);
                String name = config.getString(path + "DrugName");
                String message = (config.getString(path + "Message") != null) ? config.getString(path + "Message") : "";
                String type = config.getString(path + "Type");
                // Load particles
                Set<? extends Object> particles = (config.getConfigurationSection(path + "Particles") != null) ? config
                        .getConfigurationSection(path + "Particles").getKeys(false) : new HashSet<>();
                // Load effects
                String[] sEffects = config.getString(path + "Effect").replaceAll(" ", "").split(",");
                Integer[] effects = new Integer[sEffects.length];
                for (int i = 0; i < sEffects.length; i++) {
                    effects[i] = Integer.parseInt(sEffects[i]);
                }
                // Load negatives
                String[] sNegatives = config.getString(path + "Negatives").replaceAll(" ", "").split(",");
                Integer[] negatives = new Integer[sNegatives.length];
                for (int i = 0; i < sNegatives.length; i++) {
                    negatives[i] = Integer.parseInt(sNegatives[i]);
                }
                int negChance = (config.getInt(path + "NegChance") != 0) ? config.getInt(path + "NegChance") : 0;
                @SuppressWarnings("unused")
				boolean smoke = config.getBoolean(path + "Smoke");
                boolean negative = (negChance != 0);
                boolean sneak = config.getBoolean(path + "MustSneak");
                boolean edible = item.getType().isEdible() || item.getType().name().equalsIgnoreCase("POTION");
                drugs.put(item, new Drug(item, name, message, type, (Set<String>) particles, effects, negatives, negChance,
                        negative, sneak, edible));
            } else {
                plugin.log.info("Problem loading drug '" + key + "'!");
            }
        }
        return drugs.size();
    }

    /**
     * Check for problems with the current drug in the config
     *
     * @param key The path to the current drug
     * @return If there's a problem with any of the drug's vars
     */
    private boolean drugProblem(String key) {
        String path = "Drugs." + key + ".";
        return config.getString(path + "DrugName") == null || config.getString(path + "Effect") == null ||
                config.getString(path + "Negatives") == null || config.getString(path + "Type") == null;
    }

    /**
     * Clear the collection of drugs
     */
    public void clearDrugs() {
        drugs.clear();
    }
}
