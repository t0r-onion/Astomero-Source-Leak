package com.initial.notifications;

import com.initial.utils.player.*;
import com.initial.font.*;
import net.minecraft.client.*;
import java.awt.*;
import net.minecraft.util.*;
import net.minecraft.client.gui.*;
import com.initial.utils.render.*;

public class Notification
{
    private NotificationType type;
    private String title;
    private String messsage;
    private long start;
    private long fadedIn;
    private long fadeOut;
    public long end;
    MCFontRenderer font;
    public Timerrrr timer;
    
    public Notification(final NotificationType type, final String title, final String messsage, final int length) {
        this.timer = new Timerrrr();
        this.type = type;
        this.title = title;
        this.messsage = messsage;
        this.font = new MCFontRenderer(Fonts.fontFromTTF(new ResourceLocation("Desync/fonts/SF-Pro.ttf"), 18.0f, 0), true, true);
        this.timer.reset();
        this.end = 2000L;
    }
    
    public void show() {
        this.start = System.currentTimeMillis();
    }
    
    public boolean isShown() {
        return this.getTime() <= this.end;
    }
    
    long getTime() {
        return System.currentTimeMillis() - this.start;
    }
    
    public void render(final int count) {
        final Minecraft mc = Minecraft.getMinecraft();
        final ScaledResolution sr = new ScaledResolution(mc);
        final int width = 120;
        final double offset = width;
        final int height = 0;
        final long time = this.getTime();
        final float barThickness = 3.0f;
        final Color color = new Color(0, 0, 0, 200);
        Color color2 = new Color(0, 140, 255);
        if (this.type == NotificationType.INFO) {
            color2 = new Color(255, 255, 255);
        }
        else if (this.type == NotificationType.WARNING) {
            color2 = new Color(255, 255, 0);
        }
        else if (this.type == NotificationType.ERROR) {
            color2 = new Color(204, 0, 18);
        }
        else if (this.type == NotificationType.SUCCESS) {
            color2 = new Color(0, 255, 0);
        }
        final double x = sr.getScaledWidth() - 45 - this.font.getStringWidth(this.messsage);
        final double y = sr.getScaledHeight() - 47 * count + 2;
        final double w = sr.getScaledWidth() - 5;
        final double h = 30.0;
        final float health = (float)this.timer.getTime();
        double hpPercentage = health / this.end;
        hpPercentage = MathHelper.clamp_double(hpPercentage, 0.0, 1.0);
        final double hpWidth = (45 + this.font.getStringWidth(this.messsage)) * hpPercentage;
        final double progress = AnimationUtil.INSTANCE.animate(hpWidth, 5.0, (double)(this.end / 1000L));
        Gui.drawRect(x, y, w, y + h, color.getRGB());
        Gui.drawRect(x, y + 30.0, x + progress, y + 28.0, color2.getRGB());
        this.font.drawStringWithShadow("§f" + this.title, (float)x + 28.0f, (float)y + 5.0f, -1);
        this.font.drawStringWithShadow("§7" + this.messsage, (float)x + 28.0f, (float)y + 15.0f, -1);
        if (this.type == NotificationType.INFO) {
            DrawUtil.drawBorderedRoundedRect((float)x + 4.0f, (float)y + 5.0f, (float)x + 22.0f, (float)y + 22.0f, 19.0f, 2.0f, -1, new Color(0, 0, 0, 0).getRGB());
            mc.fontRendererObj.drawString("i", x + 12.5, y + 9.5, new Color(0, 0, 0, 255).getRGB());
        }
        else if (this.type == NotificationType.WARNING) {
            mc.fontRendererObj.drawString("\u26a0", x + 12.5, y + 9.5, new Color(255, 255, 0, 255).getRGB());
        }
        else if (this.type == NotificationType.ERROR) {
            mc.fontRendererObj.drawString("\u26a0", x + 10.0, y + 9.5, new Color(204, 0, 18, 255).getRGB());
        }
        else if (this.type == NotificationType.SUCCESS) {
            mc.fontRendererObj.drawString("\u2714", x + 9.5, y + 9.5, new Color(0, 255, 0, 255).getRGB());
        }
    }
}
