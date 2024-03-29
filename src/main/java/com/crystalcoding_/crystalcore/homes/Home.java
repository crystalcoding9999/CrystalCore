package com.crystalcoding_.crystalcore.homes;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class Home {
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;
    private final String world;

    public Home(double x, double y, double z, float yaw, float pitch, String world) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
        this.world = world;
    }

    public Home(Location loc) {
        this.x = loc.getX();
        this.y = loc.getY();
        this.z = loc.getZ();
        this.yaw = loc.getYaw();
        this.pitch = loc.getPitch();
        this.world = loc.getWorld().getName();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getYaw() {
        return yaw;
    }

    public double getPitch() {
        return pitch;
    }

    public String getWorld() {
        return world;
    }

    public Location getLoc() {
        World w = Bukkit.getWorld(world);
        if (w == null) return null;

        return new Location(w,x,y,z,yaw,pitch);
    }
}
