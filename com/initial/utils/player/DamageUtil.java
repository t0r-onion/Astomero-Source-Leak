package com.initial.utils.player;

import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import com.initial.utils.networking.*;
import net.minecraft.network.*;
import net.minecraft.client.entity.*;

public class DamageUtil
{
    public static Minecraft mc;
    
    public static void damageMethodOne() {
        final double damageOffset = 0.060109999030828476;
        final double damageY = 4.957650089636445E-4;
        final double damageYTwo = 0.004957499913871288;
        final double offset = damageOffset;
        final EntityPlayerSP player = Minecraft.getMinecraft().thePlayer;
        final double x = player.posX;
        final double y = player.posY;
        final double z = player.posZ;
        for (int i = 0; i < getMaxFallDist() / (offset - 0.004999999888241291) + 1.0; ++i) {
            PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + offset, z, false));
            PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + damageY, z, false));
            PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + damageYTwo + offset * 1.0E-6, z, false));
        }
        PacketUtil.sendPacketSilent(new C03PacketPlayer(true));
    }
    
    public static void damageVerus() {
        PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(DamageUtil.mc.thePlayer.posX, DamageUtil.mc.thePlayer.posY + 4.1001, DamageUtil.mc.thePlayer.posZ, false));
        PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(DamageUtil.mc.thePlayer.posX, DamageUtil.mc.thePlayer.posY, DamageUtil.mc.thePlayer.posZ, false));
        PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(DamageUtil.mc.thePlayer.posX, DamageUtil.mc.thePlayer.posY, DamageUtil.mc.thePlayer.posZ, true));
        DamageUtil.mc.thePlayer.jump();
    }
    
    public static void damageMethod2(double damage) {
        final Minecraft mc = Minecraft.getMinecraft();
        if (damage > floor_double(mc.thePlayer.getMaxHealth())) {
            damage = floor_double(mc.thePlayer.getMaxHealth());
        }
        final double offset = 0.0625;
        if (mc.thePlayer != null) {
            for (short i = 0; i <= (3.0 + damage) / offset; ++i) {
                PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset / 2.0 * 1.0, mc.thePlayer.posZ, false));
                PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset / 2.0 * 2.0, mc.thePlayer.posZ, false));
                PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, i == (3.0 + damage) / offset));
                PacketUtil.sendPacketSilent(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, i == (3.0 + damage) / offset));
            }
        }
    }
    
    public static int floor_double(final double p_76128_0_) {
        final int var2 = (int)p_76128_0_;
        return (p_76128_0_ < var2) ? (var2 - 1) : var2;
    }
    
    public static float getMaxFallDist() {
        return (float)Minecraft.getMinecraft().thePlayer.getMaxFallHeight();
    }
    
    public static void damageMethodThree() {
        final double x = DamageUtil.mc.thePlayer.posX;
        final double y = DamageUtil.mc.thePlayer.posY;
        final double z = DamageUtil.mc.thePlayer.posZ;
        final float minValue = 3.1f;
        for (int i = 0; i < (int)(minValue / (randomNumber(0.089, 0.0849) - 0.001 - Math.random() * 1.9999999494757503E-4 - Math.random() * 1.9999999494757503E-4) + 18.0); ++i) {
            PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + randomNumber(0.0655, 0.0625) - randomNumber(0.001, 0.01) - Math.random() * 1.9999999494757503E-4, z, false));
            PacketUtil.sendPacketSilent(new C03PacketPlayer.C04PacketPlayerPosition(x, y + Math.random() * 1.9999999494757503E-4, z, false));
        }
        PacketUtil.sendPacketSilent(new C03PacketPlayer(true));
    }
    
    public static double randomNumber(final double max, final double min) {
        return Math.random() * (max - min) + min;
    }
    
    static {
        DamageUtil.mc = Minecraft.getMinecraft();
    }
}
