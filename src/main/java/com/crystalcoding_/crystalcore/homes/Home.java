package com.crystalcoding_.crystalcore.homes;

public class Home {
    private final double x;
    private final double y;
    private final double z;
    private final double yaw;
    private final double pitch;

    public Home(double x, double y, double z, double yaw, double pitch) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.yaw = yaw;
        this.pitch = pitch;
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
}
