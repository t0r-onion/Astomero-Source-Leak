package com.initial.utils.movement;

import net.minecraft.client.*;
import net.minecraft.client.entity.*;
import com.initial.utils.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RotationUtils
{
    private static final Minecraft mc;
    
    public static float[] getRotsByPos(final double posX, final double posY, final double posZ) {
        final EntityPlayerSP player = RotationUtils.mc.thePlayer;
        final double x = posX - player.posX;
        final double y = posY - player.posY + player.getEyeHeight();
        final double z = posZ - player.posZ;
        final double dist = MathHelper.sqrt_double(x * x + z * z);
        final float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(y, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    public static float[] getNeededRotations(final Entity entity) {
        final double d0 = entity.posX - Minecraft.getMinecraft().thePlayer.posX;
        final double d2 = entity.posZ - Minecraft.getMinecraft().thePlayer.posZ;
        final double d3 = entity.posY + entity.getEyeHeight() - (Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minY + (Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxY - Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minY));
        final double d4 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        final float f = (float)(MathHelper.func_181159_b(d2, d0) * 180.0 / 3.141592653589793) - 90.0f;
        final float f2 = (float)(-(MathHelper.func_181159_b(d3, d4) * 180.0 / 3.141592653589793));
        return new float[] { f, f2 };
    }
    
    public static float getYawChange(final float yaw, final double posX, final double posZ) {
        final double deltaX = posX - Minecraft.getMinecraft().thePlayer.posX;
        final double deltaZ = posZ - Minecraft.getMinecraft().thePlayer.posZ;
        double yawToEntity = 0.0;
        if (deltaZ < 0.0 && deltaX < 0.0) {
            if (deltaX != 0.0) {
                yawToEntity = 90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
            }
        }
        else if (deltaZ < 0.0 && deltaX > 0.0) {
            if (deltaX != 0.0) {
                yawToEntity = -90.0 + Math.toDegrees(Math.atan(deltaZ / deltaX));
            }
        }
        else if (deltaZ != 0.0) {
            yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
        }
        return MathHelper.wrapAngleTo180_float(-(yaw - (float)yawToEntity));
    }
    
    public static float[] getBowAngles(final Entity entity) {
        final double xDelta = (entity.posX - entity.lastTickPosX) * 0.4;
        final double zDelta = (entity.posZ - entity.lastTickPosZ) * 0.4;
        double d = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity);
        d -= d % 0.8;
        double xMulti = 1.0;
        double zMulti = 1.0;
        final boolean sprint = entity.isSprinting();
        xMulti = d / 0.8 * xDelta * (sprint ? 1.25 : 1.0);
        zMulti = d / 0.8 * zDelta * (sprint ? 1.25 : 1.0);
        final double x = entity.posX + xMulti - Minecraft.getMinecraft().thePlayer.posX;
        final double z = entity.posZ + zMulti - Minecraft.getMinecraft().thePlayer.posZ;
        final double y = Minecraft.getMinecraft().thePlayer.posY + Minecraft.getMinecraft().thePlayer.getEyeHeight() - (entity.posY + entity.getEyeHeight());
        final double dist = Minecraft.getMinecraft().thePlayer.getDistanceToEntity(entity);
        final float yaw = (float)Math.toDegrees(Math.atan2(z, x)) - 90.0f;
        final double d2 = MathHelper.sqrt_double(x * x + z * z);
        final float pitch = (float)(-(Math.atan2(y, d2) * 180.0 / 3.141592653589793)) + (float)dist * 0.11f;
        return new float[] { yaw, -pitch };
    }
    
    public static float getYawToEntity(final Entity entity) {
        final EntityPlayerSP player = WatchdogUtils.getLocalPlayer();
        return getYawBetween(player.rotationYaw, player.posX, player.posZ, entity.posX, entity.posZ);
    }
    
    public static float getYawBetween(final float yaw, final double srcX, final double srcZ, final double destX, final double destZ) {
        final double xDist = destX - srcX;
        final double zDist = destZ - srcZ;
        final float var1 = (float)(StrictMath.atan2(zDist, xDist) * 180.0 / 3.141592653589793) - 90.0f;
        return yaw + MathHelper.wrapAngleTo180_float(var1 - yaw);
    }
    
    public static Vec3 getVectorToEntity(final Entity e) {
        final float[] rots = getRotsByPos(e.posX, e.posY, e.posZ);
        final float yaw = rots[0];
        final float pitch = rots[1];
        final float f = MathHelper.cos(-yaw * 0.017453292f - 3.1415927f);
        final float f2 = MathHelper.sin(-yaw * 0.017453292f - 3.1415927f);
        final float f3 = -MathHelper.cos(-pitch * 0.017453292f);
        final float f4 = MathHelper.sin(-pitch * 0.017453292f);
        return new Vec3(f2 * f3, f4, f * f3);
    }
    
    public static float[] getRotationsWithDir(final EntityLivingBase entityIn, final float hSpeed, final float vSpeed, final float oldYaw, final float oldPitch) {
        final float yaw = updateRotation(oldYaw, getNeededRotations(entityIn)[0], hSpeed);
        final float pitch = updateRotation(oldPitch, getNeededRotations(entityIn)[1], vSpeed);
        return new float[] { yaw, pitch };
    }
    
    public static float[] getRotations(final EntityLivingBase ent) {
        final double x = ent.posX;
        final double z = ent.posZ;
        final double y = ent.posY + ent.getEyeHeight() / 2.0f;
        return getRotationFromPosition(x, z, y);
    }
    
    public static float[] getRotationFromPosition(final double x, final double z, final double y) {
        final double xDiff = x - Minecraft.getMinecraft().thePlayer.posX;
        final double zDiff = z - Minecraft.getMinecraft().thePlayer.posZ;
        final double yDiff = y - Minecraft.getMinecraft().thePlayer.posY - 1.2;
        final double dist = MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
        final float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0 / 3.141592653589793));
        return new float[] { yaw, pitch };
    }
    
    private static float updateRotation(final float currentRotation, final float intendedRotation, final float increment) {
        float f = MathHelper.wrapAngleTo180_float(intendedRotation - currentRotation);
        if (f > increment) {
            f = increment;
        }
        if (f < -increment) {
            f = -increment;
        }
        return currentRotation + f;
    }
    
    public static float[] getNeededRotations(final EntityLivingBase entityIn) {
        final double d0 = entityIn.posX - RotationUtils.mc.thePlayer.posX;
        final double d2 = entityIn.posZ - RotationUtils.mc.thePlayer.posZ;
        final double d3 = entityIn.posY + entityIn.getEyeHeight() - RotationUtils.mc.thePlayer.getEntityBoundingBox().minY + RotationUtils.mc.thePlayer.getEyeHeight();
        final double d4 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        final float f = (float)(MathHelper.func_181159_b(d2, d0) * 180.0 / 3.141592653589793) - 90.0f;
        final float f2 = (float)(-(MathHelper.func_181159_b(d3, d4) * 180.0 / 3.141592653589793));
        return new float[] { f, f2 };
    }
    
    public static float[] getRotationsWithDir(final double x, final double y, final double z, final float hSpeed, final float vSpeed, final float oldYaw, final float oldPitch) {
        final float yaw = updateRotation2(oldYaw, getNeededRotations(x, y, z)[0], hSpeed);
        final float pitch = updateRotation2(oldPitch, getNeededRotations(x, y, z)[1], vSpeed);
        return new float[] { yaw, pitch };
    }
    
    private static float updateRotation2(final float currentRotation, final float intendedRotation, final float increment) {
        float f = MathHelper.wrapAngleTo180_float(intendedRotation - currentRotation);
        if (f > increment) {
            f = increment;
        }
        if (f < -increment) {
            f = -increment;
        }
        return currentRotation + f;
    }
    
    public static float[] getNeededRotations(final double x, final double y, final double z) {
        final double d0 = x - RotationUtils.mc.thePlayer.posX;
        final double d2 = z - RotationUtils.mc.thePlayer.posZ;
        final double d3 = y - RotationUtils.mc.thePlayer.getEntityBoundingBox().minY + RotationUtils.mc.thePlayer.getEyeHeight();
        final double d4 = MathHelper.sqrt_double(d0 * d0 + d2 * d2);
        final float f = (float)(MathHelper.func_181159_b(d2, d0) * 180.0 / 3.141592653589793) - 90.0f;
        final float f2 = (float)(-(MathHelper.func_181159_b(d3, d4) * 180.0 / 3.141592653589793));
        return new float[] { f, f2 };
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
