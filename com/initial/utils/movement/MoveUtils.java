package com.initial.utils.movement;

import net.minecraft.client.*;
import net.minecraft.potion.*;
import net.minecraft.client.entity.*;
import com.initial.events.impl.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import java.util.*;

public class MoveUtils
{
    public static double BUNNY_SLOPE;
    public static double WATCHDOG_BUNNY_SLOPE;
    public static double SPRINTING_MOD;
    public static double ICE_MOD;
    public static List<Double> frictionValues;
    public static double MIN_DIF;
    public static double MAX_DIST;
    public static double WALK_SPEED;
    public static double SWIM_MOD;
    public static double[] DEPTH_STRIDER_VALUES;
    public static double SNEAKING_MOD;
    public static double AIR_FRICTION;
    public static double WATER_FRICTION;
    public static double LAVA_FRICTION;
    public static double BUNNY_DIV_FRICTION;
    public static Minecraft mc;
    
    public static boolean isOnGround() {
        return MoveUtils.mc.thePlayer.onGround && MoveUtils.mc.thePlayer.isCollidedVertically;
    }
    
    public static double defaultSpeed() {
        double baseSpeed = 0.2873;
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amplifier = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    public static BlockPos getHypixelBlockpos(final String str) {
        int val = 89;
        if (str != null && str.length() > 1) {
            final char[] chs = str.toCharArray();
            for (int lenght = chs.length, i = 0; i < lenght; ++i) {
                val += chs[i] * str.length() * str.length() + str.charAt(0) + str.charAt(1);
            }
            val /= str.length();
        }
        return new BlockPos(val, -val % 255, val);
    }
    
    public static void setTpSpeedAndUpdate(final double speed) {
        final double dX = -Math.sin(getDirection()) * speed;
        final double dZ = Math.cos(getDirection()) * speed;
        MoveUtils.mc.thePlayer.setPositionAndUpdate(MoveUtils.mc.thePlayer.posX + dX, MoveUtils.mc.thePlayer.posY, MoveUtils.mc.thePlayer.posZ + dZ);
    }
    
    public static void setX(final double x) {
        setPos(x, MoveUtils.mc.thePlayer.posY, MoveUtils.mc.thePlayer.posZ);
    }
    
    public static void setZ(final double z) {
        setPos(MoveUtils.mc.thePlayer.posX, 0.0, MoveUtils.mc.thePlayer.posZ);
    }
    
    public static void setY(final double y) {
        setPos(MoveUtils.mc.thePlayer.posX, y, MoveUtils.mc.thePlayer.posZ);
    }
    
    public static void setSpeed(final EventMotion moveEvent, final double moveSpeed, final float pseudoYaw, final double pseudoStrafe, final double pseudoForward) {
        double forward = pseudoForward;
        double strafe = pseudoStrafe;
        float yaw = pseudoYaw;
        if (pseudoForward != 0.0) {
            if (pseudoStrafe > 0.0) {
                yaw = pseudoYaw + ((pseudoForward > 0.0) ? -45 : 45);
            }
            else if (pseudoStrafe < 0.0) {
                yaw = pseudoYaw + ((pseudoForward > 0.0) ? 45 : -45);
            }
            strafe = 0.0;
            if (pseudoForward > 0.0) {
                forward = 1.0;
            }
            else if (pseudoForward < 0.0) {
                forward = -1.0;
            }
        }
        if (strafe > 0.0) {
            strafe = 1.0;
        }
        else if (strafe < 0.0) {
            strafe = -1.0;
        }
        final double mx = Math.cos(Math.toRadians(yaw + 90.0f));
        final double mz = Math.sin(Math.toRadians(yaw + 90.0f));
        MoveUtils.mc.thePlayer.motionX = forward * moveSpeed * mx + strafe * moveSpeed * mz;
        MoveUtils.mc.thePlayer.motionZ = forward * moveSpeed * mz - strafe * moveSpeed * mx;
    }
    
    public static void setSpeed(final EventMotion moveEvent, final double moveSpeed) {
        setSpeed(moveEvent, moveSpeed, MoveUtils.mc.thePlayer.rotationYaw, MoveUtils.mc.thePlayer.movementInput.moveStrafe, MoveUtils.mc.thePlayer.movementInput.moveForward);
    }
    
    public static void strafe(final double speed) {
        final float a = MoveUtils.mc.thePlayer.rotationYaw * 0.017453292f;
        final float l = MoveUtils.mc.thePlayer.rotationYaw * 0.017453292f - 4.712389f;
        final float r = MoveUtils.mc.thePlayer.rotationYaw * 0.017453292f + 4.712389f;
        final float rf = MoveUtils.mc.thePlayer.rotationYaw * 0.017453292f + 0.5969026f;
        final float lf = MoveUtils.mc.thePlayer.rotationYaw * 0.017453292f - 0.5969026f;
        final float lb = MoveUtils.mc.thePlayer.rotationYaw * 0.017453292f - 2.3876104f;
        final float rb = MoveUtils.mc.thePlayer.rotationYaw * 0.017453292f + 2.3876104f;
        if (MoveUtils.mc.gameSettings.keyBindForward.pressed) {
            if (MoveUtils.mc.gameSettings.keyBindLeft.pressed && !MoveUtils.mc.gameSettings.keyBindRight.pressed) {
                final EntityPlayerSP thePlayer = MoveUtils.mc.thePlayer;
                thePlayer.motionX -= MathHelper.sin(lf) * speed;
                final EntityPlayerSP thePlayer2 = MoveUtils.mc.thePlayer;
                thePlayer2.motionZ += MathHelper.cos(lf) * speed;
            }
            else if (MoveUtils.mc.gameSettings.keyBindRight.pressed && !MoveUtils.mc.gameSettings.keyBindLeft.pressed) {
                final EntityPlayerSP thePlayer3 = MoveUtils.mc.thePlayer;
                thePlayer3.motionX -= MathHelper.sin(rf) * speed;
                final EntityPlayerSP thePlayer4 = MoveUtils.mc.thePlayer;
                thePlayer4.motionZ += MathHelper.cos(rf) * speed;
            }
            else {
                final EntityPlayerSP thePlayer5 = MoveUtils.mc.thePlayer;
                thePlayer5.motionX -= MathHelper.sin(a) * speed;
                final EntityPlayerSP thePlayer6 = MoveUtils.mc.thePlayer;
                thePlayer6.motionZ += MathHelper.cos(a) * speed;
            }
        }
        else if (MoveUtils.mc.gameSettings.keyBindBack.pressed) {
            if (MoveUtils.mc.gameSettings.keyBindLeft.pressed && !MoveUtils.mc.gameSettings.keyBindRight.pressed) {
                final EntityPlayerSP thePlayer7 = MoveUtils.mc.thePlayer;
                thePlayer7.motionX -= MathHelper.sin(lb) * speed;
                final EntityPlayerSP thePlayer8 = MoveUtils.mc.thePlayer;
                thePlayer8.motionZ += MathHelper.cos(lb) * speed;
            }
            else if (MoveUtils.mc.gameSettings.keyBindRight.pressed && !MoveUtils.mc.gameSettings.keyBindLeft.pressed) {
                final EntityPlayerSP thePlayer9 = MoveUtils.mc.thePlayer;
                thePlayer9.motionX -= MathHelper.sin(rb) * speed;
                final EntityPlayerSP thePlayer10 = MoveUtils.mc.thePlayer;
                thePlayer10.motionZ += MathHelper.cos(rb) * speed;
            }
            else {
                final EntityPlayerSP thePlayer11 = MoveUtils.mc.thePlayer;
                thePlayer11.motionX += MathHelper.sin(a) * speed;
                final EntityPlayerSP thePlayer12 = MoveUtils.mc.thePlayer;
                thePlayer12.motionZ -= MathHelper.cos(a) * speed;
            }
        }
        else if (MoveUtils.mc.gameSettings.keyBindLeft.pressed && !MoveUtils.mc.gameSettings.keyBindRight.pressed && !MoveUtils.mc.gameSettings.keyBindForward.pressed && !MoveUtils.mc.gameSettings.keyBindBack.pressed) {
            final EntityPlayerSP thePlayer13 = MoveUtils.mc.thePlayer;
            thePlayer13.motionX += MathHelper.sin(l) * speed;
            final EntityPlayerSP thePlayer14 = MoveUtils.mc.thePlayer;
            thePlayer14.motionZ -= MathHelper.cos(l) * speed;
        }
        else if (MoveUtils.mc.gameSettings.keyBindRight.pressed && !MoveUtils.mc.gameSettings.keyBindLeft.pressed && !MoveUtils.mc.gameSettings.keyBindForward.pressed && !MoveUtils.mc.gameSettings.keyBindBack.pressed) {
            final EntityPlayerSP thePlayer15 = MoveUtils.mc.thePlayer;
            thePlayer15.motionX += MathHelper.sin(r) * speed;
            final EntityPlayerSP thePlayer16 = MoveUtils.mc.thePlayer;
            thePlayer16.motionZ -= MathHelper.cos(r) * speed;
        }
    }
    
    public static void setMotion(final double speed) {
        double forward = MoveUtils.mc.thePlayer.movementInput.moveForward;
        double strafe = MoveUtils.mc.thePlayer.movementInput.moveStrafe;
        float yaw = MoveUtils.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            MoveUtils.mc.thePlayer.motionX = 0.0;
            MoveUtils.mc.thePlayer.motionZ = 0.0;
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                }
                else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            MoveUtils.mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f));
            MoveUtils.mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f));
        }
    }
    
    public static void setMotion(final EventMove event, final double speed) {
        double forward = MoveUtils.mc.thePlayer.movementInput.moveForward;
        double strafe = MoveUtils.mc.thePlayer.movementInput.moveStrafe;
        float yaw = MoveUtils.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            event.setX(MoveUtils.mc.thePlayer.motionX = 0.0);
            event.setZ(MoveUtils.mc.thePlayer.motionZ = 0.0);
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                }
                else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            event.setX(MoveUtils.mc.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            event.setZ(MoveUtils.mc.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }
    
    public static boolean checkTeleport(final double x, final double y, final double z, final double distBetweenPackets) {
        final double distx = MoveUtils.mc.thePlayer.posX - x;
        final double disty = MoveUtils.mc.thePlayer.posY - y;
        final double distz = MoveUtils.mc.thePlayer.posZ - z;
        final double dist = Math.sqrt(MoveUtils.mc.thePlayer.getDistanceSq(x, y, z));
        final double distanceEntreLesPackets = distBetweenPackets;
        final double nbPackets = (double)(Math.round(dist / distanceEntreLesPackets + 0.49999999999) - 1L);
        double xtp = MoveUtils.mc.thePlayer.posX;
        double ytp = MoveUtils.mc.thePlayer.posY;
        double ztp = MoveUtils.mc.thePlayer.posZ;
        for (int i = 1; i < nbPackets; ++i) {
            final double xdi = (x - MoveUtils.mc.thePlayer.posX) / nbPackets;
            xtp += xdi;
            final double zdi = (z - MoveUtils.mc.thePlayer.posZ) / nbPackets;
            ztp += zdi;
            final double ydi = (y - MoveUtils.mc.thePlayer.posY) / nbPackets;
            ytp += ydi;
            final AxisAlignedBB bb = new AxisAlignedBB(xtp - 0.3, ytp, ztp - 0.3, xtp + 0.3, ytp + 1.8, ztp + 0.3);
            if (!MoveUtils.mc.theWorld.getCollidingBoundingBoxes(MoveUtils.mc.thePlayer, bb).isEmpty()) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isOnGround(final double height) {
        return !MoveUtils.mc.theWorld.getCollidingBoundingBoxes(MoveUtils.mc.thePlayer, MoveUtils.mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty();
    }
    
    public static int getJumpEffect() {
        if (MoveUtils.mc.thePlayer.isPotionActive(Potion.jump)) {
            return MoveUtils.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
        }
        return 0;
    }
    
    public static int getSpeedEffect() {
        if (MoveUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            return MoveUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
        }
        return 0;
    }
    
    public static Block getBlockUnderPlayer(final EntityPlayer inPlayer, final double height) {
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ)).getBlock();
    }
    
    public static Block getBlockAtPosC(final double x, final double y, final double z) {
        final EntityPlayer inPlayer = Minecraft.getMinecraft().thePlayer;
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(inPlayer.posX + x, inPlayer.posY + y, inPlayer.posZ + z)).getBlock();
    }
    
    public static float getDistanceToGround(final Entity e) {
        if (MoveUtils.mc.thePlayer.isCollidedVertically && MoveUtils.mc.thePlayer.onGround) {
            return 0.0f;
        }
        float a = (float)e.posY;
        while (a > 0.0f) {
            final int[] stairs = { 53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180 };
            final int[] exemptIds = { 6, 27, 28, 30, 31, 32, 37, 38, 39, 40, 50, 51, 55, 59, 63, 65, 66, 68, 69, 70, 72, 75, 76, 77, 83, 92, 93, 94, 104, 105, 106, 115, 119, 131, 132, 143, 147, 148, 149, 150, 157, 171, 175, 176, 177 };
            final Block block = MoveUtils.mc.theWorld.getBlockState(new BlockPos(e.posX, a - 1.0f, e.posZ)).getBlock();
            if (!(block instanceof BlockAir)) {
                if (Block.getIdFromBlock(block) == 44 || Block.getIdFromBlock(block) == 126) {
                    return ((float)(e.posY - a - 0.5) < 0.0f) ? 0.0f : ((float)(e.posY - a - 0.5));
                }
                int[] arrayOfInt1;
                for (int j = (arrayOfInt1 = stairs).length, i = 0; i < j; ++i) {
                    final int id = arrayOfInt1[i];
                    if (Block.getIdFromBlock(block) == id) {
                        return ((float)(e.posY - a - 1.0) < 0.0f) ? 0.0f : ((float)(e.posY - a - 1.0));
                    }
                }
                for (int j = (arrayOfInt1 = exemptIds).length, i = 0; i < j; ++i) {
                    final int id = arrayOfInt1[i];
                    if (Block.getIdFromBlock(block) == id) {
                        return ((float)(e.posY - a) < 0.0f) ? 0.0f : ((float)(e.posY - a));
                    }
                }
                return (float)(e.posY - a + block.getBlockBoundsMaxY() - 1.0);
            }
            else {
                --a;
            }
        }
        return 0.0f;
    }
    
    public static float[] getRotationsBlock(final BlockPos block, final EnumFacing face) {
        final double x = block.getX() + 0.5 - MoveUtils.mc.thePlayer.posX + face.getFrontOffsetX() / 2.0;
        final double z = block.getZ() + 0.5 - MoveUtils.mc.thePlayer.posZ + face.getFrontOffsetZ() / 2.0;
        final double y = block.getY() + 0.5;
        final double d1 = MoveUtils.mc.thePlayer.posY + MoveUtils.mc.thePlayer.getEyeHeight() - y;
        final double d2 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(Math.atan2(d1, d2) * 180.0 / 3.141592653589793);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return new float[] { yaw, pitch };
    }
    
    public static boolean isBlockAboveHead() {
        final AxisAlignedBB bb = new AxisAlignedBB(MoveUtils.mc.thePlayer.posX - 0.3, MoveUtils.mc.thePlayer.posY + MoveUtils.mc.thePlayer.getEyeHeight(), MoveUtils.mc.thePlayer.posZ + 0.3, MoveUtils.mc.thePlayer.posX + 0.3, MoveUtils.mc.thePlayer.posY + 2.5, MoveUtils.mc.thePlayer.posZ - 0.3);
        return !MoveUtils.mc.theWorld.getCollidingBoundingBoxes(MoveUtils.mc.thePlayer, bb).isEmpty();
    }
    
    public static boolean isCollidedH(final double dist) {
        final AxisAlignedBB bb = new AxisAlignedBB(MoveUtils.mc.thePlayer.posX - 0.3, MoveUtils.mc.thePlayer.posY + 2.0, MoveUtils.mc.thePlayer.posZ + 0.3, MoveUtils.mc.thePlayer.posX + 0.3, MoveUtils.mc.thePlayer.posY + 3.0, MoveUtils.mc.thePlayer.posZ - 0.3);
        return !MoveUtils.mc.theWorld.getCollidingBoundingBoxes(MoveUtils.mc.thePlayer, bb.offset(0.3 + dist, 0.0, 0.0)).isEmpty() || !MoveUtils.mc.theWorld.getCollidingBoundingBoxes(MoveUtils.mc.thePlayer, bb.offset(-0.3 - dist, 0.0, 0.0)).isEmpty() || !MoveUtils.mc.theWorld.getCollidingBoundingBoxes(MoveUtils.mc.thePlayer, bb.offset(0.0, 0.0, 0.3 + dist)).isEmpty() || !MoveUtils.mc.theWorld.getCollidingBoundingBoxes(MoveUtils.mc.thePlayer, bb.offset(0.0, 0.0, -0.3 - dist)).isEmpty();
    }
    
    public static void setMoveSpeed(final EventMotion event, final double moveSpeed) {
        final MovementInput movementInput = MoveUtils.mc.thePlayer.movementInput;
        double moveForward = movementInput.moveForward;
        double moveStrafe = movementInput.moveStrafe;
        double yaw = MoveUtils.mc.thePlayer.rotationYaw;
        if (moveForward == 0.0 && moveStrafe == 0.0) {
            event.setX(0.0);
            event.setZ(0.0);
        }
        else {
            if (moveStrafe > 0.0) {
                moveStrafe = 1.0;
            }
            else if (moveStrafe < 0.0) {
                moveStrafe = -1.0;
            }
            if (moveForward != 0.0) {
                if (moveStrafe > 0.0) {
                    yaw += ((moveForward > 0.0) ? -45 : 45);
                }
                else if (moveStrafe < 0.0) {
                    yaw += ((moveForward > 0.0) ? 45 : -45);
                }
                moveStrafe = 0.0;
                if (moveForward > 0.0) {
                    moveForward = 1.0;
                }
                else if (moveForward < 0.0) {
                    moveForward = -1.0;
                }
            }
            final double cos = Math.cos(Math.toRadians(yaw + 90.0));
            final double sin = Math.sin(Math.toRadians(yaw + 90.0));
            event.setX(moveForward * moveSpeed * cos + moveStrafe * moveSpeed * sin);
            event.setZ(moveForward * moveSpeed * sin - moveStrafe * moveSpeed * cos);
        }
    }
    
    public static double getDirection() {
        float rotationYaw = MoveUtils.mc.thePlayer.rotationYaw;
        if (MoveUtils.mc.thePlayer.moveForward < 0.0f) {
            rotationYaw += 180.0f;
        }
        float forward = 1.0f;
        if (MoveUtils.mc.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else if (MoveUtils.mc.thePlayer.moveForward > 0.0f) {
            forward = 0.5f;
        }
        if (MoveUtils.mc.thePlayer.moveStrafing > 0.0f) {
            rotationYaw -= 90.0f * forward;
        }
        if (MoveUtils.mc.thePlayer.moveStrafing < 0.0f) {
            rotationYaw += 90.0f * forward;
        }
        return Math.toRadians(rotationYaw);
    }
    
    public static boolean isMoving() {
        return MoveUtils.mc.thePlayer != null && (MoveUtils.mc.thePlayer.movementInput.moveForward != 0.0f || MoveUtils.mc.thePlayer.movementInput.moveStrafe != 0.0f);
    }
    
    public static void strafe(final float speed) {
        if (!isMoving()) {
            return;
        }
        final double yaw = getDirection();
        MoveUtils.mc.thePlayer.motionX = -Math.sin(yaw) * speed;
        MoveUtils.mc.thePlayer.motionZ = Math.cos(yaw) * speed;
    }
    
    public static void strafe() {
        strafe(getSpeed());
    }
    
    public static float getSpeed() {
        return (float)Math.sqrt(MoveUtils.mc.thePlayer.motionX * MoveUtils.mc.thePlayer.motionX + MoveUtils.mc.thePlayer.motionZ * MoveUtils.mc.thePlayer.motionZ);
    }
    
    public static float getSpeed(final EntityLivingBase e) {
        return (float)Math.sqrt((e.posX - e.prevPosX) * (e.posX - e.prevPosX) + (e.posZ - e.prevPosZ) * (e.posZ - e.prevPosZ));
    }
    
    public static boolean isBlockAbovePlayer() {
        return !(MoveUtils.mc.theWorld.getBlockState(new BlockPos(MoveUtils.mc.thePlayer.posX, MoveUtils.mc.thePlayer.getEntityBoundingBox().maxY + 0.41999998688697815, MoveUtils.mc.thePlayer.posZ)).getBlock() instanceof BlockAir);
    }
    
    public static void setPos(final double x, final double y, final double z) {
        MoveUtils.mc.thePlayer.setPosition(x, y, z);
    }
    
    public static void setPosPlus(final double x, final double y, final double z) {
        MoveUtils.mc.thePlayer.setPosition(MoveUtils.mc.thePlayer.posX + x, MoveUtils.mc.thePlayer.posY + y, MoveUtils.mc.thePlayer.posZ + z);
    }
    
    public static void setPosPlusUpdate(final double x, final double y, final double z) {
        MoveUtils.mc.thePlayer.setPositionAndUpdate(MoveUtils.mc.thePlayer.posX + x, MoveUtils.mc.thePlayer.posY + y, MoveUtils.mc.thePlayer.posZ + z);
    }
    
    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.2875;
        if (MoveUtils.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            baseSpeed *= 1.0 + 0.2 * (MoveUtils.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1);
        }
        return baseSpeed;
    }
    
    public static double getJumpBoostModifier(double baseJumpHeight) {
        if (MoveUtils.mc.thePlayer.isPotionActive(Potion.jump)) {
            final int amplifier = MoveUtils.mc.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
            baseJumpHeight += (amplifier + 1) * 0.1f;
        }
        return baseJumpHeight;
    }
    
    static {
        MoveUtils.BUNNY_SLOPE = 0.66;
        MoveUtils.WATCHDOG_BUNNY_SLOPE = MoveUtils.BUNNY_SLOPE * 0.96;
        MoveUtils.SPRINTING_MOD = 1.3;
        MoveUtils.ICE_MOD = 2.5;
        MoveUtils.frictionValues = new ArrayList<Double>();
        MoveUtils.MIN_DIF = 1.0E-4;
        MoveUtils.MAX_DIST = 2.15 - MoveUtils.MIN_DIF;
        MoveUtils.WALK_SPEED = 0.221;
        MoveUtils.SWIM_MOD = 0.115 / MoveUtils.WALK_SPEED;
        MoveUtils.DEPTH_STRIDER_VALUES = new double[] { 1.0, 0.1645 / MoveUtils.SWIM_MOD / MoveUtils.WALK_SPEED, 0.1995 / MoveUtils.SWIM_MOD / MoveUtils.WALK_SPEED, 1.0 / MoveUtils.SWIM_MOD };
        MoveUtils.SNEAKING_MOD = 0.13 / MoveUtils.WALK_SPEED;
        MoveUtils.AIR_FRICTION = 0.98;
        MoveUtils.WATER_FRICTION = 0.89;
        MoveUtils.LAVA_FRICTION = 0.535;
        MoveUtils.BUNNY_DIV_FRICTION = 160.0 - MoveUtils.MIN_DIF;
        MoveUtils.mc = Minecraft.getMinecraft();
    }
}
