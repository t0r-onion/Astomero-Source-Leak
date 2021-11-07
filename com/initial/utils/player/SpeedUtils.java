package com.initial.utils.player;

import net.minecraft.client.*;
import com.initial.events.impl.*;
import net.minecraft.entity.player.*;
import com.initial.utils.movement.*;

public class SpeedUtils
{
    private static final Minecraft mc;
    
    public static boolean isMoving() {
        return SpeedUtils.mc.thePlayer.moveForward != 0.0f || SpeedUtils.mc.thePlayer.moveStrafing != 0.0f;
    }
    
    public static void setMoveSpeed(final EventMove aab, final double moveSpeed) {
        float forward = SpeedUtils.mc.thePlayer.movementInput.moveForward;
        float strafe = SpeedUtils.mc.thePlayer.movementInput.moveStrafe;
        float yaw = SpeedUtils.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            SpeedUtils.mc.thePlayer.motionX = 0.0;
            SpeedUtils.mc.thePlayer.motionZ = 0.0;
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0f) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0f) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0f;
                if (forward > 0.0f) {
                    forward = 1.0f;
                }
                else if (forward < 0.0f) {
                    forward = -1.0f;
                }
            }
            final double xDist = forward * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0f));
            final double zDist = forward * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0f));
            aab.setX(xDist);
            aab.setZ(zDist);
        }
    }
    
    public static void setMoveSpeed(final double moveSpeed) {
        float forward = SpeedUtils.mc.thePlayer.movementInput.moveForward;
        float strafe = SpeedUtils.mc.thePlayer.movementInput.moveStrafe;
        float yaw = SpeedUtils.mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            SpeedUtils.mc.thePlayer.motionX = 0.0;
            SpeedUtils.mc.thePlayer.motionZ = 0.0;
        }
        final int d = 45;
        if (forward != 0.0) {
            if (strafe > 0.0) {
                yaw += ((forward > 0.0) ? (-d) : d);
            }
            else if (strafe < 0.0) {
                yaw += ((forward > 0.0) ? d : (-d));
            }
            strafe = 0.0f;
            if (forward > 0.0) {
                forward = 1.0f;
            }
            else if (forward < 0.0) {
                forward = -1.0f;
            }
        }
        final double xDist = forward * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0f));
        final double zDist = forward * moveSpeed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * moveSpeed * Math.cos(Math.toRadians(yaw + 90.0f));
        SpeedUtils.mc.thePlayer.motionX = xDist;
        SpeedUtils.mc.thePlayer.motionZ = zDist;
    }
    
    public static Float getDir(final EntityPlayer player) {
        double f = player.moveForward;
        final double s = player.moveStrafing;
        double y = player.rotationYaw;
        final double st = 45.0;
        if (s != 0.0 && f == 0.0) {
            y += 360.0;
            if (s > 0.0) {
                y -= 89.0;
            }
            else if (s < 0.0) {
                y += 89.0;
            }
            f = 0.0;
        }
        else if (f > 0.0) {
            if (s > 0.0) {
                y -= 45.0;
            }
            if (s < 0.0) {
                y += 45.0;
            }
        }
        else {
            if (s > 0.0) {
                y += 45.0;
            }
            if (s < 0.0) {
                y -= 45.0;
            }
        }
        if (f < 0.0) {
            y -= 180.0;
        }
        y *= 0.01746532879769802;
        return (float)y;
    }
    
    public static void setSpeed(final float d) {
        double yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
        final boolean isMoving = Minecraft.getMinecraft().thePlayer.moveForward != 0.0f || Minecraft.getMinecraft().thePlayer.moveStrafing != 0.0f;
        final boolean isMovingForward = Minecraft.getMinecraft().thePlayer.moveForward > 0.0f;
        final boolean isMovingBackward = Minecraft.getMinecraft().thePlayer.moveForward < 0.0f;
        final boolean isMovingRight = Minecraft.getMinecraft().thePlayer.moveStrafing > 0.0f;
        final boolean isMovingLeft = Minecraft.getMinecraft().thePlayer.moveStrafing < 0.0f;
        final boolean isMovingSideways = isMovingLeft || isMovingRight;
        final boolean isMovingStraight = isMovingForward || isMovingBackward;
        if (isMoving) {
            if (isMovingForward && !isMovingSideways) {
                yaw += 0.0;
            }
            else if (isMovingBackward && !isMovingSideways) {
                yaw += 180.0;
            }
            else if (isMovingForward && isMovingLeft) {
                yaw += 45.0;
            }
            else if (isMovingForward) {
                yaw -= 45.0;
            }
            else if (!isMovingStraight && isMovingLeft) {
                yaw += 90.0;
            }
            else if (!isMovingStraight && isMovingRight) {
                yaw -= 90.0;
            }
            else if (isMovingBackward && isMovingLeft) {
                yaw += 135.0;
            }
            else if (isMovingBackward) {
                yaw -= 135.0;
            }
            yaw = Math.toRadians(yaw);
            Minecraft.getMinecraft().thePlayer.motionX = -Math.sin(yaw) * d;
            Minecraft.getMinecraft().thePlayer.motionZ = Math.cos(yaw) * d;
        }
    }
    
    public static void setPlayerSpeed(final double speed) {
        if (!isMoving()) {
            return;
        }
        SpeedUtils.mc.thePlayer.motionX = -Math.sin(MovementUtils.getDirection()) * speed;
        SpeedUtils.mc.thePlayer.motionZ = Math.cos(MovementUtils.getDirection()) * speed;
    }
    
    static {
        mc = Minecraft.getMinecraft();
    }
}
