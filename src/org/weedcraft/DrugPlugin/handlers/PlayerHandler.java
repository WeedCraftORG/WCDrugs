package org.weedcraft.DrugPlugin.handlers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.weedcraft.DrugPlugin.Drug;
import org.weedcraft.DrugPlugin.WCDrugs;
import org.weedcraft.DrugPlugin.events.DrugTakenEvent;
import org.weedcraft.DrugPlugin.events.PreDrugTakenEvent;
import org.weedcraft.DrugPlugin.particles.ParticleEffect;

import java.util.*;

public class PlayerHandler implements Listener {

    private final WCDrugs plugin;
    private ArrayList<String> isJump = new ArrayList<>();
    private ArrayList<String> drunk = new ArrayList<>();
    private ArrayList<String> heartAttack = new ArrayList<>();
    private ArrayList<String> noPlace = new ArrayList<>();
    private HashMap<String, Integer> onDrugs = new HashMap<>();
    private HashMap<String, Integer> particleTimers = new HashMap<>();
    private HashMap<String, Integer> drugTimers = new HashMap<>();
    private HashMap<String, Long> cooldownTimes = new HashMap<>();

    public PlayerHandler(WCDrugs plugin) {
        this.plugin = plugin;
    }

    public void doDrug(final Player p, Drug drug) {
        // Do drug cooldown
        if (cooldownTimes.containsKey(p.getName())) {
            if (cooldownTimes.get(p.getName()) - System.currentTimeMillis() > 1000) {
                p.sendMessage(plugin.colorize(plugin.config.getString("Chat.Self.Cooldown").replaceAll("%time%", String
                        .valueOf((int) (cooldownTimes.get(p.getName()) - System.currentTimeMillis()) / 1000))));
                return;
            }
        }
        cooldownTimes.put(p.getName(), System.currentTimeMillis() + (plugin.config.getInt("Options.DrugCooldown")
                * 1000));
        // Call PreDrugTakenEvent and set variables in case they were changed
        PreDrugTakenEvent preDrugTakenEvent = new PreDrugTakenEvent(p, drug);
        Bukkit.getServer().getPluginManager().callEvent(preDrugTakenEvent);
        drug = preDrugTakenEvent.getDrug();
        // Make sure it's not cancelled
        if (!preDrugTakenEvent.isCancelled()) {
            // Call DrugTakenEvent
            if (!drug.getType().equalsIgnoreCase("none")) {
                Bukkit.getServer().getPluginManager().callEvent(new DrugTakenEvent(p, drug, doNegatives(p, drug),
                        doEffects(p, drug)));
                doRemoveDrug(p);
                doMessage(p, drug);
                doParticles(p, drug);
            }
            // Add to noPlace if enabled
            if (plugin.config.getBoolean("Options.EnablePlaceProtection")) {
                noPlace.add(p.getName());
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					@Override
					public void run() {
						noPlace.remove(p.getName());
					}
				}, 10L);
            }
        }
    }

    @SuppressWarnings("deprecation")
    private void doRemoveDrug(final Player p) {
        final ItemStack drug = p.getItemInHand();
        if (drug.getAmount() > 1) {
            drug.setAmount(drug.getAmount() - 1);
        } else {
            Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				@Override
				public void run() {
				    p.getInventory().removeItem(drug);
				    p.updateInventory();
				}
			});
        }
    }

    private void doMessage(Player p, Drug drug) {
        boolean hasMessage = !drug.getMessage().equalsIgnoreCase("");
        p.sendMessage(plugin.colorize((hasMessage) ? drug.getMessage() : plugin.config.getString("Chat.Self.TakeDrugs")
                .replaceAll("%drugname%", drug.getName())));
    }

    private void doParticles(final Player p, Drug drug) {
        if (drug.getParticles().size() > 0) {
            String path = (drug.getItemStack().getDurability() == 0) ? "Drugs." + drug.getItemStack().getType() + ""
                    + ".Particles" : "Drugs." + drug.getItemStack().getType() + ":" + drug.getItemStack()
                    .getDurability()

                    + ".Particles";
            for (String s : plugin.config.getConfigurationSection(path).getKeys(false)) {
                String pPath = path + "." + s + ".";
                final int particleAmount = plugin.config.getInt(pPath + "Amount");
                final String particleLocation = plugin.config.getString(pPath + "Location");
                boolean particleRepeat = plugin.config.getBoolean(pPath + "Repeat");
                final boolean particleVisible = plugin.config.getBoolean(pPath + "Visible");
                final ParticleEffect particle = ParticleEffect.fromName(s);
                // Offset-X,Y,Z,Speed,Amount,Location(center),Range
                if (particle != null) {
                    if (!particleRepeat) {
                        float visibility = (particleVisible) ? 20F : 1.65F;
                        switch (particleLocation.toLowerCase()) {
                            case "body":
                                particle.display(0.3F, 0.3F, 0.3F, 0.05F, particleAmount, p.getLocation(), visibility);
                                break;
                            case "head":
                                particle.display(0.3F, 0.3F, 0.3F, 0.05F, particleAmount, p.getEyeLocation(),
                                        visibility);
                                break;
                            case "feet":
                                particle.display(0.3F, 0.3F, 0.3F, 0.05F, particleAmount, p.getLocation().add(0, -0.5,
                                        0), visibility);
                                break;
                        }
                    } else {
                        int particleInterval = plugin.config.getInt(pPath + "Interval");
                        if (particleTimers.containsKey(p.getName())) {
                            Bukkit.getScheduler().cancelTask(particleTimers.get(p.getName()));
                            particleTimers.remove(p.getName());
                        }
                        particleTimers.put(p.getName(), Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
							@Override
							public void run() {
							    if (onDrugs.containsKey(p.getName())) {
							        float visibility = (particleVisible) ? 20F : 1.65F;
							        switch (particleLocation.toLowerCase()) {
							            case "body":
							                particle.display(0.3F, 0.3F, 0.3F, 0.05F, particleAmount, p.getLocation(),
							                        visibility);
							                break;
							            case "head":
							                particle.display(0.3F, 0.3F, 0.3F, 0.05F, particleAmount, p.getEyeLocation(),
							                        visibility);
							                break;
							            case "feet":
							                particle.display(0.3F, 0.3F, 0.3F, 0.05F, particleAmount, p.getLocation().add(0,
							                        -0.5, 0), visibility);
							                break;
							        }
							    } else {
							        Bukkit.getScheduler().cancelTask(particleTimers.get(p.getName()));
							        particleTimers.remove(p.getName());
							    }
							}
						}, 0L, particleInterval));
                    }
                }
            }
        }
    }

    private ArrayList<Integer> doEffects(Player p, Drug drug) {
        ArrayList<Integer> effectsGiven = new ArrayList<>();
        switch (drug.getType().toLowerCase()) {
            case "all":
                // All
                for (int effect : drug.getEffects()) {
                    effectsGiven.add(effect);
                    // Set max time & power
                    int maxPower = plugin.config.getInt("Effects." + getEffectName(effect) + ".MaxPower");
                    int minPower = plugin.config.getInt("Effects." + getEffectName(effect) + ".MinPower");
                    int maxTime = plugin.config.getInt("Effects." + getEffectName(effect) + ".MaxTime") * 20;
                    int minTime = plugin.config.getInt("Effects." + getEffectName(effect) + ".MinTime") * 20;
                    int power = new Random().nextInt((maxPower - minPower) + 1) + minPower;
                    int time = new Random().nextInt((maxTime - minTime) + 1) + minTime;
                    applyEffect(p, effect, time, power);
                    removeEffects(p, time);
                }
                break;
            case "random":
                // Random
                int totalEffects = drug.getEffects().size();
                int random = new Random().nextInt(totalEffects);
                int effect = drug.getEffects().get(random);
                effectsGiven.add(effect);
                // Set max time & power
                int maxPower = plugin.config.getInt("Effects." + getEffectName(effect) + ".MaxPower");
                int minPower = plugin.config.getInt("Effects." + getEffectName(effect) + ".MinPower");
                int maxTime = plugin.config.getInt("Effects." + getEffectName(effect) + ".MaxTime") * 20;
                int minTime = plugin.config.getInt("Effects." + getEffectName(effect) + ".MinTime") * 20;
                int power = new Random().nextInt((maxPower - minPower) + 1) + minPower;
                int time = new Random().nextInt((maxTime - minTime) + 1) + minTime;
                applyEffect(p, effect, time, power);
                removeEffects(p, time);
                break;
        }
        return effectsGiven;
    }

    private boolean doNegatives(Player p, Drug drug) {
        if (drug.isNegative()) {
            Random ran = new Random();
            int negChance = drug.getNegativeChance();
            int random = ran.nextInt(100);
            int currentNeg = 0;
            while (!drug.getNegatives().contains(currentNeg)) {
                currentNeg = ran.nextInt(4);
            }
            if (random > 100) {
                random = 100;
            } else if (random < 1) {
                random = 1;
            }
            if (random <= negChance) {
                switch (currentNeg) {
                    case 1:
                        doPuke(p, drug);
                        return true;
                    case 2:
                        doBurning(p, drug);
                        return true;
                    case 3:
                        doHeartAttack(p, drug);
                        return true;
                    case 4:
                        doOverdose(p, drug);
                        return true;
                }
            }
        }
        return false;
    }

    private void applyEffect(Player p, int i, int time, int power) {
        /* All potion effects here:
         http://www.minecraftwiki.net/wiki/Status_effect */

        switch (i) {
            case 0:
                // Portal Effect
                doNausea(p, time, power);
                break;
            case 1:
                // Zoom-In & Walk Slow
                doWalkSlow(p, time, power);
                break;
            case 2:
                // Zoom-Out & Walk Fast
                doWalkFast(p, time, power);
                break;
            case 3:
                // Blind
                doBlindness(p, time, power);
                break;
            case 4:
                // Hunger
                doHunger(p, time, power);
                break;
            case 5:
                // High Jump
                doHighJump(p, time, power);
                break;
            case 6:
                // Sickness & Slower Hitting
                doFatigue(p, time, power);
                break;
            case 7:
                // Drunk
                doDrunk(p, time);
                break;
            case 8:
                // Healing
                doHealing(p, time, power);
                break;
            case 9:
                // Regeneration
                doRegeneration(p, time, power);
                break;
            case 10:
                // Resistance
                doResistance(p, time, power);
                break;
            case 11:
                // Fire Resistance
                doFireResistance(p, time, power);
                break;
            case 12:
                // Water breathing
                doWaterBreathing(p, time, power);
                break;
            case 13:
                // Invisibility
                doInvisibility(p, time, power);
                break;
            case 14:
                // Night Vision
                doNightVision(p, time, power);
                break;
            case 15:
                // Poison
                doPoison(p, time, power);
                break;
            case 16:
                // Wither Poison
                doWitherPoison(p, time, power);
                break;
            case 17:
                // Absorbtion
                doAbsorption(p, time, power);
                break;
            case 18:
                // Saturation
                doSaturation(p, time, power);
                break;
        }
    }


    private void doNausea(Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.CONFUSION))
            p.removePotionEffect(PotionEffectType.CONFUSION);
        p.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, time, power));
    }

    private void doWalkSlow(final Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.SLOW))
            p.removePotionEffect(PotionEffectType.SLOW);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, time, power));
    }

    private void doWalkFast(final Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.SPEED))
            p.removePotionEffect(PotionEffectType.SPEED);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, time, power));
    }

    private void doBlindness(final Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.BLINDNESS))
            p.removePotionEffect(PotionEffectType.BLINDNESS);
        p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, time, power));
        p.canSee(p);
    }

    private void doHunger(final Player p, int time, int power) {
        final int currFood = p.getFoodLevel();
        if (p.hasPotionEffect(PotionEffectType.HUNGER))
            p.removePotionEffect(PotionEffectType.HUNGER);
        p.addPotionEffect(new PotionEffect(PotionEffectType.HUNGER, time, power));
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				p.setFoodLevel(currFood / 2);
			}
		}, time);
    }

    private void doHighJump(final Player p, int time, int power) {
        isJump.add(p.getName());
        if (p.hasPotionEffect(PotionEffectType.JUMP))
            p.removePotionEffect(PotionEffectType.JUMP);
        p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, time, power));
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				isJump.remove(p.getName());
			}
		});
    }

    private void doFatigue(final Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.SLOW_DIGGING))
            p.removePotionEffect(PotionEffectType.SLOW_DIGGING);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW_DIGGING, time, power));
        p.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, time, power));
    }

    private void doHealing(final Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.HEAL))
            p.removePotionEffect(PotionEffectType.HEAL);
        p.addPotionEffect(new PotionEffect(PotionEffectType.HEAL, time, power));
    }

    private void doRegeneration(final Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.REGENERATION))
            p.removePotionEffect(PotionEffectType.REGENERATION);
        p.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, time, power));
    }

    private void doResistance(final Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.DAMAGE_RESISTANCE))
            p.removePotionEffect(PotionEffectType.DAMAGE_RESISTANCE);
        p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, time, power));
    }

    private void doFireResistance(final Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.FIRE_RESISTANCE))
            p.removePotionEffect(PotionEffectType.FIRE_RESISTANCE);
        p.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, time, power));
    }

    private void doWaterBreathing(final Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.WATER_BREATHING))
            p.removePotionEffect(PotionEffectType.WATER_BREATHING);
        p.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, time, power));
    }

    private void doInvisibility(final Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.INVISIBILITY))
            p.removePotionEffect(PotionEffectType.INVISIBILITY);
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, time, power));
    }

    private void doNightVision(final Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION))
            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
        p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, time, power));
    }

    private void doPoison(final Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.POISON))
            p.removePotionEffect(PotionEffectType.POISON);
        p.addPotionEffect(new PotionEffect(PotionEffectType.POISON, time, power));
    }

    private void doWitherPoison(final Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.WITHER))
            p.removePotionEffect(PotionEffectType.WITHER);
        p.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, time, power));
    }

    private void doAbsorption(final Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.ABSORPTION))
            p.removePotionEffect(PotionEffectType.ABSORPTION);
        p.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, time, power));
    }

    private void doSaturation(final Player p, int time, int power) {
        if (p.hasPotionEffect(PotionEffectType.SATURATION))
            p.removePotionEffect(PotionEffectType.SATURATION);
        p.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, time, power));
    }

    private void doDrunk(final Player p, int time) {
        drunk.add(p.getName());
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				drunk.remove(p.getName());
			}
		}, time);
    }

    private void doPuke(Player p, Drug drug) {
        String puke = plugin.config.getString("Chat.Broadcast.Puke")
                .replaceAll("%drugname%", drug.getName()).replaceAll("%playername%", p.getName());
        if (plugin.config.getBoolean("Options.EnableBroadcastMessages")) {
            Bukkit.broadcastMessage(plugin.colorize(puke));
        } else {
            p.sendMessage(plugin.colorize(puke));
        }
        ItemStack[] inventory = p.getInventory().getContents();
        p.getInventory().clear();
        for (ItemStack item : inventory) {
            if (item != null) {
                p.getWorld().dropItemNaturally(p.getLocation(), item);
            }
        }
        p.updateInventory();
    }

    private void doBurning(Player p, Drug drug) {
        String hot = plugin.config.getString("Chat.Broadcast.Burning")
                .replaceAll("%drugname%", drug.getName()).replaceAll("%playername%", p.getName());
        if (plugin.config.getBoolean("Options.EnableBroadcastMessages")) {
            Bukkit.broadcastMessage(plugin.colorize(hot));
        } else {
            p.sendMessage(plugin.colorize(hot));
        }
        p.setFireTicks(200);
    }

    private void doOverdose(Player p, Drug drug) {
        String death = plugin.config.getString("Chat.Broadcast.Death").replaceAll("%drugname%",
                plugin.config.getString(drug.getName())).replaceAll("%playername%", p.getName());
        if (plugin.config.getBoolean("Options.EnableBroadcastMessages")) {
            Bukkit.broadcastMessage(plugin.colorize(death));
        } else {
            p.sendMessage(plugin.colorize(death));
        }
        p.setHealth(0);
    }

    private void doHeartAttack(final Player p, Drug drug) {
        String message = plugin.config.getString("Chat.Broadcast.HeartAttack").replaceAll(
                "%drugname%", drug.getName()).replaceAll("%playername%", p.getName());
        if (plugin.config.getBoolean("Options.EnableBroadcastMessages")) {
            Bukkit.broadcastMessage(plugin.colorize(message));
        } else {
            p.sendMessage(plugin.colorize(message));
        }
        heartAttack.add(p.getName());
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				heartAttack.remove(p.getName());
			}
		}, 100);
        if (p.getHealth() < 2) {
            p.setHealth(2);
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
			    if (heartAttack.contains(p.getName())) {
			        if (p.getHealth() < 2) {
			            p.setHealth(2);
			        } else {
			            p.setHealth(1);
			        }
			    }
			}
		}, 0L, 3L);
    }

    private void removeEffects(final Player p, int time) {
        time = time / 20;
        if (!onDrugs.containsKey(p.getName())) {
            onDrugs.put(p.getName(), time);
            doDrugTimer(p);
        } else {
            if (time > onDrugs.get(p.getName())) {
                onDrugs.put(p.getName(), time);
            }
        }
    }

    private void doDrugTimer(final Player p) {
        drugTimers.put(p.getName(), Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
			    if (onDrugs.containsKey(p.getName())) {
			        onDrugs.put(p.getName(), onDrugs.get(p.getName()) - 1);
			        if (onDrugs.get(p.getName()) <= 0) {
			            onDrugs.remove(p.getName());
			            p.sendMessage(plugin.colorize(plugin.config.getString("Chat.Self.Sober")));
			            Bukkit.getScheduler().cancelTask(drugTimers.get(p.getName()));
			            drugTimers.remove(p.getName());
			        }
			    } else {
			        Bukkit.getScheduler().cancelTask(drugTimers.get(p.getName()));
			        drugTimers.remove(p.getName());
			    }
			}
		}, 20L, 20L));
    }

    /**
     * @return A collection of all drunk players
     */
    public Collection<String> getDrunk() {
        return drunk;
    }

    /**
     * @return A collection of all players on drugs & their times
     */
    public Map<String, Integer> getOnDrugs() {
        return onDrugs;
    }

    /**
     * @return A collection of all players having a heart attack
     */
    public Collection<String> getHeartAttack() {
        return heartAttack;
    }

    /**
     * @return A collection of all players that have high-jump
     */
    public Collection<String> getIsJump() {
        return isJump;
    }

    /**
     * @return A collection of all players who can't place blocks
     */
    public Collection<String> getNoPlace() {
        return noPlace;
    }

    private String getEffectName(int effectId) {
        switch (effectId) {
            case 0:
                return "Nausea";
            case 1:
                return "SlowWalk";
            case 2:
                return "FastWalk";
            case 3:
                return "Blindness";
            case 4:
                return "Hunger";
            case 5:
                return "HighJump";
            case 6:
                return "SlowHit";
            case 7:
                return "Drunk";
            case 8:
                return "Healing";
            case 9:
                return "Regeneration";
            case 10:
                return "Resistance";
            case 11:
                return "FireResistance";
            case 12:
                return "WaterBreathing";
            case 13:
                return "Invisibility";
            case 14:
                return "NightVision";
            case 15:
                return "Poison";
            case 16:
                return "WitherPoison";
            case 17:
                return "Absorption";
            case 18:
                return "Saturation";
        }
        return null;
    }
}
