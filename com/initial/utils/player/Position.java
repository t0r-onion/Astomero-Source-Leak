package com.initial.utils.player;

import net.minecraft.util.*;

public class Position
{
    double x;
    double y;
    double z;
    
    public Position(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public double getX() {
        return this.x;
    }
    
    public void setX(final double x) {
        this.x = x;
    }
    
    public double getY() {
        return this.y;
    }
    
    public void setY(final double y) {
        this.y = y;
    }
    
    public double getZ() {
        return this.z;
    }
    
    public void setZ(final double z) {
        this.z = z;
    }
    
    public float getDistanceToPosition(final Position entityIn) {
        final float f = (float)(this.getX() - entityIn.getX());
        final float f2 = (float)(this.getY() - entityIn.getY());
        final float f3 = (float)(this.getZ() - entityIn.getZ());
        return MathHelper.sqrt_float(f * f + f2 * f2 + f3 * f3);
    }
}
