package com.initial.ui;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.shader.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;
import java.awt.*;

public class LoadingScreen
{
    public static ResourceLocation resLoc;
    public static int max;
    public static int prog;
    
    public static void update() {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().getLanguageManager() == null) {
            return;
        }
        final ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
        final Framebuffer fb = new Framebuffer(sr.getScaledWidth() * sr.getScaleFactor(), sr.getScaledHeight() * sr.getScaleFactor(), true);
        fb.bindFramebuffer(false);
        GlStateManager.matrixMode(5889);
        GlStateManager.loadIdentity();
        GlStateManager.ortho(0.0, sr.getScaledWidth(), sr.getScaledHeight(), 0.0, 1000.0, 3000.0);
        GlStateManager.matrixMode(5888);
        GlStateManager.loadIdentity();
        GlStateManager.translate(0.0f, 0.0f, -2000.0f);
        GlStateManager.disableLighting();
        GlStateManager.disableFog();
        GlStateManager.disableDepth();
        GlStateManager.enableTexture2D();
        if (LoadingScreen.resLoc == null) {
            LoadingScreen.resLoc = new ResourceLocation("Desync/loadingBgBlur.jpg");
        }
        Minecraft.getMinecraft().getTextureManager().bindTexture(LoadingScreen.resLoc);
        GlStateManager.resetColor();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
        Gui.drawScaledCustomSizeModalRect(0.0, 0.0, 0.0f, 0.0f, 1920.0, 1080.0, sr.getScaledWidth(), sr.getScaledHeight(), 1920.0f, 1080.0f);
        GlStateManager.textureState[GlStateManager.activeTextureUnit].textureName = -1;
        Gui.drawRect(0.0, sr.getScaledHeight() - 3, sr.getScaledWidth(), sr.getScaledHeight(), new Color(0, 0, 0, 162).getRGB());
        Gui.drawRect(0.0, sr.getScaledHeight() - 3, LoadingScreen.prog / LoadingScreen.max * sr.getScaledWidth(), sr.getScaledHeight(), new Color(180, 142, 255).getRGB());
        fb.unbindFramebuffer();
        fb.framebufferRender(sr.getScaledWidth() * sr.getScaleFactor(), sr.getScaledHeight() * sr.getScaleFactor());
        Minecraft.getMinecraft().updateDisplay();
    }
    
    public static void updateProgress() {
        ++LoadingScreen.prog;
        update();
    }
    
    static {
        LoadingScreen.max = 18;
        LoadingScreen.prog = 0;
    }
}
