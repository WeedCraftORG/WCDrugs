package org.weedcraft.DrugPlugin.particles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <b>ParticleEffect Library</b>
 * <p>
 * This library was created by @DarkBlade12 and allows you to display all Minecraft particle effects on a Bukkit server
 * <p>
 * You are welcome to use it, modify it and redistribute it under the following conditions:
 * <ul>
 * <li>Don't claim this class as your own
 * <li>Don't remove this disclaimer
 * </ul>
 * <p>
 * Special thanks:
 * <ul>
 * <li>@microgeek (original idea, names and packet parameters)
 * <li>@ShadyPotato (1.8 names, ids and packet parameters)
 * <li>@RingOfStorms (particle behavior)
 * <li>@Cybermaxke (particle behavior)
 * </ul>
 * <p>
 * <i>It would be nice if you provide credit to me if you use this class in a published project</i>
 *
 * @author DarkBlade12
 * @version 1.7
 */
@SuppressWarnings("unused")
public enum ParticleEffect {
    
    
	  EXPLOSION_NORMAL("explode", 0, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  EXPLOSION_LARGE("largeexplode", 1, -1, new ParticleProperty[0]),  EXPLOSION_HUGE("hugeexplosion", 2, -1, new ParticleProperty[0]),  FIREWORKS_SPARK("fireworksSpark", 3, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  WATER_BUBBLE("bubble", 4, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_WATER }),  WATER_SPLASH("splash", 5, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  WATER_WAKE("wake", 6, 7, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  SUSPENDED("suspended", 7, -1, new ParticleProperty[] { ParticleProperty.REQUIRES_WATER }),  SUSPENDED_DEPTH("depthSuspend", 8, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  CRIT("crit", 9, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  CRIT_MAGIC("magicCrit", 10, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  SMOKE_NORMAL("smoke", 11, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  SMOKE_LARGE("largesmoke", 12, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  SPELL("spell", 13, -1, new ParticleProperty[0]),  SPELL_INSTANT("instantSpell", 14, -1, new ParticleProperty[0]),  SPELL_MOB("mobSpell", 15, -1, new ParticleProperty[] { ParticleProperty.COLORABLE }),  SPELL_MOB_AMBIENT("mobSpellAmbient", 16, -1, new ParticleProperty[] { ParticleProperty.COLORABLE }),  SPELL_WITCH("witchMagic", 17, -1, new ParticleProperty[0]),  DRIP_WATER("dripWater", 18, -1, new ParticleProperty[0]),  DRIP_LAVA("dripLava", 19, -1, new ParticleProperty[0]),  VILLAGER_ANGRY("angryVillager", 20, -1, new ParticleProperty[0]),  VILLAGER_HAPPY("happyVillager", 21, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  TOWN_AURA("townaura", 22, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  NOTE("note", 23, -1, new ParticleProperty[] { ParticleProperty.COLORABLE }),  PORTAL("portal", 24, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  ENCHANTMENT_TABLE("enchantmenttable", 25, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  FLAME("flame", 26, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  LAVA("lava", 27, -1, new ParticleProperty[0]),  FOOTSTEP("footstep", 28, -1, new ParticleProperty[0]),  CLOUD("cloud", 29, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  REDSTONE("reddust", 30, -1, new ParticleProperty[] { ParticleProperty.COLORABLE }),  SNOWBALL("snowballpoof", 31, -1, new ParticleProperty[0]),  SNOW_SHOVEL("snowshovel", 32, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL }),  SLIME("slime", 33, -1, new ParticleProperty[0]),  HEART("heart", 34, -1, new ParticleProperty[0]),  BARRIER("barrier", 35, 8, new ParticleProperty[0]),  ITEM_CRACK("iconcrack", 36, -1, new ParticleProperty[] { ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA }),  BLOCK_CRACK("blockcrack", 37, -1, new ParticleProperty[] { ParticleProperty.REQUIRES_DATA }),  BLOCK_DUST("blockdust", 38, 7, new ParticleProperty[] { ParticleProperty.DIRECTIONAL, ParticleProperty.REQUIRES_DATA }),  WATER_DROP("droplet", 39, 8, new ParticleProperty[0]),  ITEM_TAKE("take", 40, 8, new ParticleProperty[0]),  MOB_APPEARANCE("mobappearance", 41, 8, new ParticleProperty[0]);
    
    

    private static final Map<String, ParticleEffect> NAME_MAP = new HashMap<>();
    private static final Map<Integer, ParticleEffect> ID_MAP = new HashMap<>();
    private final String name;
    private final int id;
    private final int requiredVersion;
    private final List<ParticleProperty> properties;

    // Initialize map for quick name and id lookup
    static {
        for (ParticleEffect effect : values()) {
            NAME_MAP.put(effect.name, effect);
            ID_MAP.put(effect.id, effect);
        }
    }

    /**
     * Construct a new particle effect
     *
     * @param name            Name of this particle effect
     * @param id              Id of this particle effect
     * @param requiredVersion Version which is required (1.x)
     * @param properties      Properties of this particle effect
     */
    ParticleEffect(String name, int id, int requiredVersion, ParticleProperty... properties) {
        this.name = name;
        this.id = id;
        this.requiredVersion = requiredVersion;
        this.properties = Arrays.asList(properties);
    }


    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }


    public int getRequiredVersion() {
        return requiredVersion;
    }


    public boolean hasProperty(ParticleProperty property) {
        return properties.contains(property);
    }


    public boolean isSupported() {
        return requiredVersion == -1 || ParticlePacket.getVersion() >= requiredVersion;
    }

    public static ParticleEffect fromName(String name) {
        for (Map.Entry<String, ParticleEffect> entry : NAME_MAP.entrySet()) {
            if (!entry.getKey().equalsIgnoreCase(name)) {
                continue;
            }
            return entry.getValue();
        }
        return null;
    }


    public static ParticleEffect fromId(int id) {
        for (Map.Entry<Integer, ParticleEffect> entry : ID_MAP.entrySet()) {
            if (entry.getKey() != id) {
                continue;
            }
            return entry.getValue();
        }
        return null;
    }


    private static boolean isWater(Location location) {
        Material material = location.getBlock().getType();
        return material == Material.WATER || material == Material.STATIONARY_WATER;
    }

    
    private static boolean isLongDistance(Location location, List<Player> players) {
        for (Player player : players) {
            if (player.getLocation().distanceSquared(location) < 65536) {
                continue;
            }
            return true;
        }
        return false;
    }


    private static boolean isDataCorrect(ParticleEffect effect, ParticleData data) {
        return ((effect == BLOCK_CRACK || effect == BLOCK_DUST) && data instanceof BlockData) || (effect ==
                ITEM_CRACK && data instanceof ItemData);
    }


    private static boolean isColorCorrect(ParticleEffect effect, ParticleColor color) {
        return ((effect == SPELL_MOB || effect == SPELL_MOB_AMBIENT || effect == REDSTONE) && color instanceof
                OrdinaryColor) || (effect == NOTE && color instanceof NoteColor);
    }


    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, double
            range) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 256, null).sendTo(center, range);
    }


    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center,
                        List<Player> players) throws ParticleVersionException, ParticleDataException,
            IllegalArgumentException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), null)
                .sendTo(center, players);
    }


    public void display(float offsetX, float offsetY, float offsetZ, float speed, int amount, Location center, Player
            ... players) throws ParticleVersionException, ParticleDataException, IllegalArgumentException {
        display(offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
    }


    public void display(Vector direction, float speed, Location center, double range) throws
            ParticleVersionException, ParticleDataException, IllegalArgumentException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (!hasProperty(ParticleProperty.DIRECTIONAL)) {
            throw new IllegalArgumentException("This particle effect is not directional");
        }
        if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, direction, speed, range > 256, null).sendTo(center, range);
    }


    public void display(Vector direction, float speed, Location center, List<Player> players) throws
            ParticleVersionException, ParticleDataException, IllegalArgumentException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect requires additional data");
        }
        if (!hasProperty(ParticleProperty.DIRECTIONAL)) {
            throw new IllegalArgumentException("This particle effect is not directional");
        }
        if (hasProperty(ParticleProperty.REQUIRES_WATER) && !isWater(center)) {
            throw new IllegalArgumentException("There is no water at the center location");
        }
        new ParticlePacket(this, direction, speed, isLongDistance(center, players), null).sendTo(center, players);
    }


    public void display(Vector direction, float speed, Location center, Player... players) throws
            ParticleVersionException, ParticleDataException, IllegalArgumentException {
        display(direction, speed, center, Arrays.asList(players));
    }


    public void display(ParticleColor color, Location center, double range) throws ParticleVersionException,
            ParticleColorException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!hasProperty(ParticleProperty.COLORABLE)) {
            throw new ParticleColorException("This particle effect is not colorable");
        }
        if (!isColorCorrect(this, color)) {
            throw new ParticleColorException("The particle color type is incorrect");
        }
        new ParticlePacket(this, color, range > 256).sendTo(center, range);
    }


    public void display(ParticleColor color, Location center, List<Player> players) throws ParticleVersionException,
            ParticleColorException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!hasProperty(ParticleProperty.COLORABLE)) {
            throw new ParticleColorException("This particle effect is not colorable");
        }
        if (!isColorCorrect(this, color)) {
            throw new ParticleColorException("The particle color type is incorrect");
        }
        new ParticlePacket(this, color, isLongDistance(center, players)).sendTo(center, players);
    }


    public void display(ParticleColor color, Location center, Player... players) throws ParticleVersionException,
            ParticleColorException {
        display(color, center, Arrays.asList(players));
    }


    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount,
                        Location center, double range) throws ParticleVersionException, ParticleDataException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!isDataCorrect(this, data)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, range > 256, data).sendTo(center, range);
    }


    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount,
                        Location center, List<Player> players) throws ParticleVersionException, ParticleDataException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!isDataCorrect(this, data)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, offsetX, offsetY, offsetZ, speed, amount, isLongDistance(center, players), data)
                .sendTo(center, players);
    }


    public void display(ParticleData data, float offsetX, float offsetY, float offsetZ, float speed, int amount,
                        Location center, Player... players) throws ParticleVersionException, ParticleDataException {
        display(data, offsetX, offsetY, offsetZ, speed, amount, center, Arrays.asList(players));
    }


    public void display(ParticleData data, Vector direction, float speed, Location center, double range) throws
            ParticleVersionException, ParticleDataException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!isDataCorrect(this, data)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, direction, speed, range > 256, data).sendTo(center, range);
    }


    public void display(ParticleData data, Vector direction, float speed, Location center, List<Player> players)
            throws ParticleVersionException, ParticleDataException {
        if (!isSupported()) {
            throw new ParticleVersionException("This particle effect is not supported by your server version");
        }
        if (!hasProperty(ParticleProperty.REQUIRES_DATA)) {
            throw new ParticleDataException("This particle effect does not require additional data");
        }
        if (!isDataCorrect(this, data)) {
            throw new ParticleDataException("The particle data type is incorrect");
        }
        new ParticlePacket(this, direction, speed, isLongDistance(center, players), data).sendTo(center, players);
    }


    public void display(ParticleData data, Vector direction, float speed, Location center, Player... players) throws
            ParticleVersionException, ParticleDataException {
        display(data, direction, speed, center, Arrays.asList(players));
    }


    public enum ParticleProperty {

        REQUIRES_WATER,
 
        REQUIRES_DATA,

        DIRECTIONAL,

        COLORABLE
    }


    public static abstract class ParticleData {
        private final Material material;
        private final byte data;
        private final int[] packetData;


        @SuppressWarnings("deprecation")
        public ParticleData(Material material, byte data) {
            this.material = material;
            this.data = data;
            this.packetData = new int[]{material.getId(), data};
        }

 
        public Material getMaterial() {
            return material;
        }


        public byte getData() {
            return data;
        }


        public int[] getPacketData() {
            return packetData;
        }


        public String getPacketDataString() {
            return "_" + packetData[0] + "_" + packetData[1];
        }
    }


    public static final class ItemData extends ParticleData {

        public ItemData(Material material, byte data) {
            super(material, data);
        }
    }


    public static final class BlockData extends ParticleData {

        public BlockData(Material material, byte data) throws IllegalArgumentException {
            super(material, data);
            if (!material.isBlock()) {
                throw new IllegalArgumentException("The material is not a block");
            }
        }
    }


    public static abstract class ParticleColor {

        public abstract float getValueX();

        public abstract float getValueY();

        public abstract float getValueZ();
    }


    public static final class OrdinaryColor extends ParticleColor {
        private final int red;
        private final int green;
        private final int blue;

        public OrdinaryColor(int red, int green, int blue) throws IllegalArgumentException {
            if (red < 0) {
                throw new IllegalArgumentException("The red value is lower than 0");
            }
            if (red > 255) {
                throw new IllegalArgumentException("The red value is higher than 255");
            }
            this.red = red;
            if (green < 0) {
                throw new IllegalArgumentException("The green value is lower than 0");
            }
            if (green > 255) {
                throw new IllegalArgumentException("The green value is higher than 255");
            }
            this.green = green;
            if (blue < 0) {
                throw new IllegalArgumentException("The blue value is lower than 0");
            }
            if (blue > 255) {
                throw new IllegalArgumentException("The blue value is higher than 255");
            }
            this.blue = blue;
        }

        public int getRed() {
            return red;
        }

        public int getGreen() {
            return green;
        }

        public int getBlue() {
            return blue;
        }

        @Override
        public float getValueX() {
            return (float) red / 255F;
        }

        @Override
        public float getValueY() {
            return (float) green / 255F;
        }

        @Override
        public float getValueZ() {
            return (float) blue / 255F;
        }
    }

    public static final class NoteColor extends ParticleColor {
        private final int note;

        public NoteColor(int note) throws IllegalArgumentException {
            if (note < 0) {
                throw new IllegalArgumentException("The note value is lower than 0");
            }
            if (note > 24) {
                throw new IllegalArgumentException("The note value is higher than 24");
            }
            this.note = note;
        }

        @Override
        public float getValueX() {
            return (float) note / 24F;
        }

        @Override
        public float getValueY() {
            return 0;
        }

        @Override
        public float getValueZ() {
            return 0;
        }

    }

    private static final class ParticleDataException extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleDataException(String message) {
            super(message);
        }
    }

    private static final class ParticleColorException extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleColorException(String message) {
            super(message);
        }
    }

    private static final class ParticleVersionException extends RuntimeException {
        private static final long serialVersionUID = 3203085387160737484L;

        public ParticleVersionException(String message) {
            super(message);
        }
    }

    public static final class ParticlePacket {
        private static int version;
        private static Class<?> enumParticle;
        private static Constructor<?> packetConstructor;
        private static Method getHandle;
        private static Field playerConnection;
        private static Method sendPacket;
        private static boolean initialized;
        private final ParticleEffect effect;
        private final float offsetX;
        private final float offsetY;
        private final float offsetZ;
        private final float speed;
        private final int amount;
        private final boolean longDistance;
        private final ParticleData data;
        private Object packet;

        public ParticlePacket(ParticleEffect effect, float offsetX, float offsetY, float offsetZ, float speed, int
                amount, boolean longDistance, ParticleData data) throws IllegalArgumentException {
            initialize();
            if (speed < 0) {
                throw new IllegalArgumentException("The speed is lower than 0");
            }
            if (amount < 0) {
                throw new IllegalArgumentException("The amount is lower than 0");
            }
            this.effect = effect;
            this.offsetX = offsetX;
            this.offsetY = offsetY;
            this.offsetZ = offsetZ;
            this.speed = speed;
            this.amount = amount;
            this.longDistance = longDistance;
            this.data = data;
        }

        public ParticlePacket(ParticleEffect effect, Vector direction, float speed, boolean longDistance,
                              ParticleData data) throws IllegalArgumentException {
            this(effect, (float) direction.getX(), (float) direction.getY(), (float) direction.getZ(), speed, 0,
                    longDistance, data);
        }

        public ParticlePacket(ParticleEffect effect, ParticleColor color, boolean longDistance) {
            this(effect, color.getValueX(), color.getValueY(), color.getValueZ(), 1, 0, longDistance, null);
        }
        
        
        public static void initialize()
        {
          if (initialized) {
            return;
          }
          try
          {
            version = Integer.parseInt(ReflectionUtils.PackageType.getServerVersion().split("_")[1]);
            if (version > 7) {
              enumParticle = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("EnumParticle");
            }
            Class class_ = ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass(version < 7 ? "Packet63WorldParticles" : "PacketPlayOutWorldParticles");
            packetConstructor = ReflectionUtils.getConstructor(class_, new Class[0]);
            getHandle = ReflectionUtils.getMethod("CraftPlayer", ReflectionUtils.PackageType.CRAFTBUKKIT_ENTITY, "getHandle", new Class[0]);
            playerConnection = ReflectionUtils.getField("EntityPlayer", ReflectionUtils.PackageType.MINECRAFT_SERVER, false, "playerConnection");
            sendPacket = ReflectionUtils.getMethod(playerConnection.getType(), "sendPacket", new Class[] { ReflectionUtils.PackageType.MINECRAFT_SERVER.getClass("Packet") });
          }
          catch (Exception exception)
          {
            throw new VersionIncompatibleException("Your current bukkit version seems to be incompatible with this library", exception);
          }
          initialized = true;
        }
        
        
        
        

        public static int getVersion() {
            return version;
        }

        public static boolean isInitialized() {
            return initialized;
        }

        private void initializePacket(Location location) {
            if (packet != null) {
              return;
            }
            try {
              packet = packetConstructor.newInstance(new Object[0]);
              if (version < 8) {
                String string = effect.getName();
                if (data != null) {
                  string = string + data.getPacketDataString();
                }
                ReflectionUtils.setValue(packet, true, "a", string);
              } else {
                ReflectionUtils.setValue(packet, true, "a", enumParticle.getEnumConstants()[effect.getId()]);
                ReflectionUtils.setValue(packet, true, "j", Boolean.valueOf(longDistance));
                if (data != null)
                {
                  int[] arrn2 = data.getPacketData();
                  int[] arrn; int[] arrn1; if (effect == ParticleEffect.ITEM_CRACK) {
                    arrn = arrn2;
                  } else {
                    int[] arrn3 = new int[1];
                    arrn = arrn3;
                    arrn2[0] |= arrn2[1] << 12;
                  }
                  ReflectionUtils.setValue(packet, true, "k", arrn);
                }
              }
              ReflectionUtils.setValue(packet, true, "b", Float.valueOf((float)location.getX()));
              ReflectionUtils.setValue(packet, true, "c", Float.valueOf((float)location.getY()));
              ReflectionUtils.setValue(packet, true, "d", Float.valueOf((float)location.getZ()));
              ReflectionUtils.setValue(packet, true, "e", Float.valueOf(offsetX));
              ReflectionUtils.setValue(packet, true, "f", Float.valueOf(offsetY));
              ReflectionUtils.setValue(packet, true, "g", Float.valueOf(offsetZ));
              ReflectionUtils.setValue(packet, true, "h", Float.valueOf(speed));
              ReflectionUtils.setValue(packet, true, "i", Integer.valueOf(amount));
            }
            catch (Exception var2_4) {
              throw new PacketInstantiationException("Packet instantiation failed", var2_4);
            }
          }


        public void sendTo(Location center, Player player) throws PacketInstantiationException, PacketSendingException {
            initializePacket(center);
            try {
                sendPacket.invoke(playerConnection.get(getHandle.invoke(player)), packet);
            } catch (Exception exception) {
                throw new PacketSendingException("Failed to send the packet to player '" + player.getName() + "'",
                        exception);
            }
        }


        public void sendTo(Location center, List<Player> players) throws IllegalArgumentException {
            if (players.isEmpty()) {
                throw new IllegalArgumentException("The player list is empty");
            }
            for (Player player : players) {
                sendTo(center, player);
            }
        }


        @SuppressWarnings("deprecation")
        public void sendTo(Location center, double range) throws IllegalArgumentException {
            if (range < 1) {
                throw new IllegalArgumentException("The range is lower than 1");
            }
            String worldName = center.getWorld().getName();
            double squared = range * range;
            for (Player player : Bukkit.getOnlinePlayers()) {
                if (!player.getWorld().getName().equals(worldName) || player.getLocation().distanceSquared(center) >
                        squared) {
                    continue;
                }
                sendTo(center, player);
            }
        }


        private static final class VersionIncompatibleException extends RuntimeException {
            private static final long serialVersionUID = 3203085387160737484L;


            public VersionIncompatibleException(String message, Throwable cause) {
                super(message, cause);
            }
        }


        private static final class PacketInstantiationException extends RuntimeException {
            private static final long serialVersionUID = 3203085387160737484L;

            public PacketInstantiationException(String message, Throwable cause) {
                super(message, cause);
            }
        }

        private static final class PacketSendingException extends RuntimeException {
            private static final long serialVersionUID = 3203085387160737484L;

            public PacketSendingException(String message, Throwable cause) {
                super(message, cause);
            }
        }
    }
}