package com.initial.utils;

import net.minecraft.entity.*;
import net.minecraft.potion.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;

public class WatchdogUtils
{
    public static float applyCustomFriction(final float speed, final float friction) {
        final float value = speed / 100.0f * friction;
        return value;
    }
    
    public static double getJumpHeight(final double baseJumpHeight) {
        return baseJumpHeight + getJumpBoostModifier() * 0.1f;
    }
    
    public static int getPotionModifier(final EntityLivingBase entity, final Potion potion) {
        final PotionEffect effect = entity.getActivePotionEffect(potion.id);
        return (effect != null) ? (effect.getAmplifier() + 1) : 0;
    }
    
    public static int getJumpBoostModifier() {
        return getPotionModifier(getLocalPlayer(), Potion.jump);
    }
    
    public static EntityPlayerSP getLocalPlayer() {
        return Minecraft.getMinecraft().thePlayer;
    }
    
    public static double getBaseSpeed(final ACType value) {
        double baseSpeed = (value == ACType.MINEPLEX) ? 0.4225 : ((value == ACType.VERUS) ? 0.24 : 0.2873);
        if (Minecraft.getMinecraft().thePlayer.isPotionActive(Potion.moveSpeed)) {
            final int amp = Minecraft.getMinecraft().thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
            baseSpeed *= 1.0 + ((value == ACType.VERUS) ? 0.05 : 0.2) * amp;
        }
        return baseSpeed;
    }
    
    public enum ACType
    {
        VERUS, 
        NCP, 
        SLOTH, 
        AAC, 
        MMC, 
        HYPIXEL, 
        HYPIXEL_GROUND, 
        HYPIXEL_RANKED, 
        MINEPLEX, 
        OLD_AGC;
    }
}
