package com.initial.utils.render;

import net.minecraft.client.renderer.culling.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;
import net.minecraft.entity.*;

public class VisualHelper
{
    private static final Frustum frustum;
    
    public static double interp(final double newPos, final double oldPos) {
        return oldPos + (newPos - oldPos) * Minecraft.getMinecraft().timer.renderPartialTicks;
    }
    
    public static void drawRect(final double x, final double y, final double width, final double height, final int color) {
        Gui.drawRect(x, y, x + width, y + height, color);
    }
    
    public static boolean isInFrustumView(final Entity ent) {
        final Entity current = Minecraft.getMinecraft().getRenderViewEntity();
        VisualHelper.frustum.setPosition(interp(current.posX, current.lastTickPosX), interp(current.posY, current.lastTickPosY), interp(current.posZ, current.lastTickPosZ));
        return VisualHelper.frustum.isBoundingBoxInFrustum(ent.getEntityBoundingBox()) || ent.ignoreFrustumCheck;
    }
    
    static {
        frustum = new Frustum();
    }
}
