package com.initial.clickguis;

import java.awt.*;
import org.lwjgl.opengl.*;
import com.initial.modules.*;
import com.initial.utils.render.*;
import net.minecraft.client.gui.*;
import com.initial.*;
import net.minecraft.util.*;
import com.initial.settings.impl.*;
import com.initial.settings.*;
import java.util.*;

public class ClickGuiScreenTest extends GuiScreen
{
    public Category draggingCategory;
    public int startX;
    public int startY;
    public StringSet stringSet;
    public DoubleSet numb;
    public double diff;
    public double transX;
    public double transY;
    int categoryTextColor;
    
    public ClickGuiScreenTest() {
        this.diff = 2.0;
        this.categoryTextColor = new Color(15724527).getRGB();
    }
    
    public Color colorSexooo() {
        return new Color(15658734);
    }
    
    public Color gradientCol() {
        return this.getGradientOffset(new Color(10987431), new Color(7829367), Math.abs(System.currentTimeMillis() / 30L) / 50.0);
    }
    
    public boolean hovered(final float left, final float top, final float right, final float bottom, final int mouseX, final int mouseY) {
        return mouseX >= left && mouseY >= top && mouseX < right && mouseY < bottom;
    }
    
    @Override
    public void initGui() {
        this.transX = 0.0;
        this.transY = 0.0;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        final ScaledResolution sr = new ScaledResolution(this.mc);
        GL11.glPushMatrix();
        GL11.glScaled(1.0, Math.min(this.transY, 1.0), 1.0);
        this.transY += 0.075;
        this.handleGUI(mouseX, mouseY, -1, 'T', -1, HandleType.RENDER);
        GL11.glPopMatrix();
    }
    
    public void mouseClicked(final int mouseX, final int mouseY, final int button) {
        this.handleGUI(mouseX, mouseY, button, 'T', -1, HandleType.BUTTON_PRESSED);
    }
    
    public void mouseReleased(final int mouseX, final int mouseY, final int button) {
        this.handleGUI(mouseX, mouseY, button, 'T', -1, HandleType.BUTTON_RELEASED);
    }
    
    public void keyTyped(final char typedChar, final int keyCode) {
        this.handleGUI(0, 0, -1, typedChar, keyCode, HandleType.KEY_TYPED);
    }
    
    int moduleTextColor(final Module m) {
        if (m.isToggled()) {
            return this.colorSexooo().getRGB();
        }
        return new Color(11908533).getRGB();
    }
    
    int settingNameColor(final Module m) {
        return new Color(13487565).getRGB();
    }
    
    int modeValColor(final Module m) {
        return this.gradientCol().getRGB();
    }
    
    int subCategoryTextColor(final Module m) {
        return -1;
    }
    
    int categoryRectColor(final Category c) {
        return new Color(255, 150, 200).getRGB();
    }
    
    int modRectColor(final Module m, final boolean hovered) {
        if (hovered) {
            return new Color(-1727724283, true).getRGB();
        }
        return new Color(-1207630587, true).getRGB();
    }
    
    int settingOutLineColor(final Module m) {
        return new Color(789516).getRGB();
    }
    
    int sliderColor(final Module m, final boolean hovered) {
        return this.colorSexooo().getRGB();
    }
    
    int settingRectColor(final boolean hovered, final Category c) {
        if (hovered) {
            return new Color(-1711144446, true).getRGB();
        }
        return new Color(1862534148, true).getRGB();
    }
    
    int settingOnCategoryRectColor(final boolean hovered, final Category c) {
        if (hovered) {
            return new Color(1795424256, true).getRGB();
        }
        return new Color(1241777156, true).getRGB();
    }
    
    int outlineColor(final Category c) {
        return this.gradientCol().getRGB();
    }
    
    private void drawGradient() {
        final Color refColor = new Color(ColorUtil.waveRainbow(2.0f, 0.5f, 1.0f, 0L));
        final int firstColor = new Color(refColor.getRed(), refColor.getGreen(), refColor.getGreen(), 33).getRGB();
        final int secondColor = new Color(refColor.getRed(), refColor.getGreen(), refColor.getGreen(), 0).getRGB();
        Gui.drawGradientRect(0.0, ClickGuiScreenTest.height - 100, ClickGuiScreenTest.width, ClickGuiScreenTest.height, secondColor, firstColor);
    }
    
    public void handleGUI(final int mouseX, final int mouseY, final int button, final char keyChar, final int keyCode, final HandleType handleType) {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
        if (handleType == HandleType.BUTTON_RELEASED && button == 0) {
            this.draggingCategory = null;
            this.numb = null;
        }
        if (this.draggingCategory != null) {
            this.draggingCategory.posX = mouseX - this.startX;
            this.draggingCategory.posY = mouseY - this.startY;
        }
        double countY = 0.0;
        final int incrementVal = 26;
        final int staticHeight = 14;
        int count2 = 20;
        for (final Category c : Category.values()) {
            final float offset = (float)(c.posX + count2);
            final float top = c.posY + 12.0f + 0.0f;
            final float width = 118.0f;
            final float right = offset + width;
            final boolean categoryhovered = this.hovered(offset, top, right, top + staticHeight, mouseX, mouseY);
            count2 += 125;
            if (handleType == HandleType.BUTTON_PRESSED && categoryhovered && button == 0) {
                this.draggingCategory = c;
                this.startX = mouseX - c.posX;
                this.startY = mouseY - c.posY;
                return;
            }
            if (handleType == HandleType.BUTTON_PRESSED && categoryhovered && button == 1) {
                c.expanded = !c.expanded;
                return;
            }
            double count3 = 0.0;
            final String categoryText = c.name;
            Gui.drawRect(offset - 1.0f, top + 1.0f, offset + width + 1.0f, top + staticHeight, this.categoryRectColor(c));
            if (!c.expanded) {}
            final double textDif = 17.0;
            this.fontRendererObj.drawCenteredStringWithShadow(categoryText, offset + width / 2.4f, top + 4.1f, this.categoryTextColor);
            if (c.expanded) {
                for (final Module m : Astomero.instance.moduleManager.getModulesByCategory(c)) {
                    final boolean hoveringModule = this.hovered(offset, top + staticHeight + (float)count3, offset + width, (float)(top + staticHeight + count3 + staticHeight), mouseX, mouseY);
                    if (handleType == HandleType.BUTTON_PRESSED && hoveringModule && button == 0) {
                        m.toggle();
                        return;
                    }
                    if (handleType == HandleType.BUTTON_PRESSED && hoveringModule && button == 1) {
                        if (!m.getSettings().isEmpty()) {
                            for (Module module : Astomero.instance.moduleManager.getModules()) {}
                        }
                        m.scaleFactor = 0.0;
                        m.setExpanded(!m.isExpanded());
                        return;
                    }
                    Gui.drawRect(offset, top + staticHeight + count3, offset + width, top + staticHeight + count3 + staticHeight, this.modRectColor(m, hoveringModule));
                    this.fontRendererObj.drawStringWithShadow(m.getName(), offset + 4.0f, 2.0f + top + textDif + count3, this.moduleTextColor(m));
                    if (!m.getSettings().isEmpty()) {
                        this.fontRendererObj.drawStringWithShadow(m.isExpanded() ? ">" : "v", offset + width - 9.0f, 2.0f + top + textDif + count3, this.categoryTextColor);
                    }
                    count3 += staticHeight;
                    if (!m.isExpanded()) {
                        continue;
                    }
                    GL11.glPushMatrix();
                    GL11.glTranslated(0.0, count3 + staticHeight * 2, 0.0);
                    GL11.glScaled(1.0, m.scaleFactor, 1.0);
                    GL11.glTranslated(0.0, -(count3 + staticHeight * 2), 0.0);
                    if (m.scaleFactor < 1.0) {
                        final Module module2 = m;
                        module2.scaleFactor += 0.04;
                    }
                    else {
                        m.scaleFactor = 1.0;
                    }
                    for (final Setting s : m.getSettings()) {
                        final int sliderShadow = new Color(1828716544, true).getRGB();
                        this.diff = 0.5;
                        final double difference = 7.0;
                        final double doubleSetDifference = 3.0;
                        double increment = 13.0;
                        final boolean hoveringSetting = this.hovered(offset, (float)(top + staticHeight + count3), offset + width, (float)(top + increment + (float)count3 + staticHeight), mouseX, mouseY);
                        if (s instanceof SubCategory) {
                            Gui.drawRect(offset + this.diff, top + staticHeight + count3, offset + width - this.diff, top + increment + count3 + staticHeight, this.settingRectColor(false, m.getCategory()));
                            this.fontRendererObj.drawCenteredStringWithShadow(s.name, offset + width / 2.0f, (float)(2.0f + top + textDif - staticHeight - increment + count3), this.subCategoryTextColor(m));
                            count3 += increment;
                        }
                        if (s instanceof BooleanSet) {
                            final BooleanSet booleanSet = (BooleanSet)s;
                            if (handleType == HandleType.BUTTON_PRESSED && hoveringSetting && (button == 0 || button == 1)) {
                                booleanSet.toggle();
                            }
                            Gui.drawRect(offset + this.diff, top + staticHeight + count3, offset + width - this.diff, top + increment + count3 + staticHeight, this.settingRectColor(hoveringSetting, m.getCategory()));
                            this.fontRendererObj.drawStringWithShadow("... " + s.name, 14.0f + offset + difference, 22.0f + top + textDif - staticHeight - increment + count3, this.settingNameColor(m));
                            final int borderColor = new Color(7237230).getRGB();
                            final double cleft = offset + this.diff + 5.0;
                            final double cright = offset + this.diff + 14.0;
                            final double ctop = top + staticHeight + count3 + 2.0;
                            final double cbottom = top + increment + count3 + staticHeight - 2.0;
                            final double lineWidth = 0.5;
                            Gui.drawRect(cleft, ctop, cright, ctop + lineWidth, borderColor);
                            Gui.drawRect(cleft, cbottom - lineWidth, cright, cbottom, borderColor);
                            Gui.drawRect(cleft, cbottom, cleft + lineWidth, ctop, borderColor);
                            Gui.drawRect(cright - lineWidth, cbottom, cright, ctop, borderColor);
                            final double enabledDifference = 2.0;
                            if (booleanSet.isEnabled()) {
                                Gui.drawRect(cleft + enabledDifference, ctop + enabledDifference, cright - enabledDifference, cbottom - enabledDifference, this.sliderColor(m, false));
                            }
                            count3 += increment;
                        }
                        if (s instanceof DoubleSet) {
                            final DoubleSet numberSet = (DoubleSet)s;
                            increment = 15.0;
                            final float percent = (float)((numberSet.getValue() - numberSet.getMin()) / (numberSet.getMax() - numberSet.getMin()));
                            final float numberWidth = percent * width;
                            if (this.numb != null && this.numb == numberSet) {
                                final double mousePercent = Math.min(1.0f, Math.max(0.0f, (mouseX - offset) / width));
                                final double newValue = mousePercent * (numberSet.getMax() - numberSet.getMin()) + numberSet.getMin();
                                numberSet.setValue(newValue);
                            }
                            Gui.drawRect(offset + this.diff, top + staticHeight + count3, offset + width - this.diff, top + increment + count3 + staticHeight, this.settingRectColor(hoveringSetting, m.getCategory()));
                            Gui.drawRect(Math.min(Math.max(offset + numberWidth - this.diff, doubleSetDifference + offset + this.diff), offset + width - this.diff - doubleSetDifference), top + 25.0f + count3, offset + width - this.diff - doubleSetDifference, top + 26.0f + count3, sliderShadow);
                            Gui.drawRect(Math.min(offset + numberWidth - this.diff, doubleSetDifference + offset + this.diff), top + 25.0f + count3, Math.min(offset + numberWidth - this.diff, offset + width - this.diff - doubleSetDifference), top + 26.0f + count3, this.sliderColor(m, hoveringSetting));
                            final String val = numberSet.getValue() + numberSet.getSuffix();
                            if (handleType == HandleType.BUTTON_PRESSED && hoveringSetting && button == 0) {
                                this.numb = numberSet;
                            }
                            this.fontRendererObj.drawStringWithShadow(s.name + ": " + EnumChatFormatting.WHITE + val, offset + difference, -1.0f + top + textDif - staticHeight - increment + count3, this.settingNameColor(m));
                            count3 += increment;
                        }
                        if (s instanceof ModeSet) {
                            final ModeSet modeSet = (ModeSet)s;
                            if (handleType == HandleType.BUTTON_PRESSED && hoveringSetting && button == 0) {
                                modeSet.positiveCycle();
                            }
                            if (handleType == HandleType.BUTTON_PRESSED && hoveringSetting && button == 1) {
                                modeSet.negativeCycle();
                            }
                            Gui.drawRect(offset + this.diff, top + staticHeight + count3, offset + width - this.diff, top + increment + count3 + staticHeight, this.settingRectColor(hoveringSetting, m.getCategory()));
                            final String fullString = s.name + " ... " + EnumChatFormatting.WHITE + modeSet.getMode().toUpperCase();
                            this.fontRendererObj.drawStringWithShadow(fullString, offset + difference, 2.0f + top + textDif - staticHeight - increment + count3, this.settingNameColor(m));
                            count3 += increment;
                        }
                        if (s instanceof StringSet) {
                            final StringSet strs = (StringSet)s;
                            Gui.drawRect(offset + this.diff, top + staticHeight + count3, offset + width - this.diff, top + increment + count3 + staticHeight, this.settingRectColor(hoveringSetting, m.getCategory()));
                            this.fontRendererObj.drawStringWithShadow(s.name + ": " + strs.getText(), offset + difference, 2.0f + top + textDif - staticHeight - increment + count3, this.settingNameColor(m));
                            if (handleType == HandleType.BUTTON_PRESSED && button == 0) {
                                if (hoveringSetting) {
                                    this.stringSet = strs;
                                }
                                else {
                                    this.stringSet = null;
                                }
                            }
                            if (this.stringSet != null && handleType == HandleType.KEY_TYPED) {
                                if (keyCode == 54 || keyCode == 157 || keyCode == 42 || keyCode == 58 || keyCode == 29 || keyCode == 56 || keyCode == 219 || keyCode == 184 || keyCode == 221 || keyCode == 1) {
                                    return;
                                }
                                if (keyCode == 14) {
                                    if (strs.text.isEmpty()) {
                                        return;
                                    }
                                    strs.text = strs.text.substring(0, strs.text.length() - 1);
                                }
                                else {
                                    final StringBuilder sb = new StringBuilder();
                                    final StringSet set = strs;
                                    set.text = sb.append(set.text).append(keyChar).toString();
                                }
                            }
                            count3 += increment;
                        }
                        if (s instanceof ModuleCategory) {
                            final ModuleCategory category = (ModuleCategory)s;
                            if (handleType == HandleType.BUTTON_PRESSED && hoveringSetting && (button == 0 || button == 1)) {
                                category.setExpanded(!category.isExpanded());
                            }
                            Gui.drawRect(offset + this.diff, top + staticHeight + count3, offset + width - this.diff, top + increment + count3 + staticHeight, this.settingRectColor(hoveringSetting, m.getCategory()));
                            this.fontRendererObj.drawCenteredStringWithShadow(s.name, offset + width / 2.0f, (float)(2.5 + top + textDif - staticHeight - increment + count3), category.isExpanded() ? this.subCategoryTextColor(m) : new Color(15987699).getRGB());
                            count3 += increment;
                            if (!category.isExpanded()) {
                                continue;
                            }
                            for (final Setting setOnCat : category.settingsOnCat) {
                                this.diff = 2.0;
                                increment = 13.0;
                                final boolean hoveringCat = this.hovered(offset, (float)(top + staticHeight + count3), offset + width, (float)(top + increment + (float)count3 + staticHeight), mouseX, mouseY);
                                if (setOnCat instanceof BooleanSet) {
                                    final BooleanSet booleanSet2 = (BooleanSet)setOnCat;
                                    if (handleType == HandleType.BUTTON_PRESSED && hoveringCat && (button == 0 || button == 1)) {
                                        booleanSet2.toggle();
                                    }
                                    Gui.drawRect(offset + this.diff, top + staticHeight + count3, offset + width - this.diff, top + increment + count3 + staticHeight, this.settingOnCategoryRectColor(hoveringCat, m.getCategory()));
                                    this.fontRendererObj.drawStringWithShadow("... " + setOnCat.name, 14.0f + offset + difference, 2.0f + top + textDif - staticHeight - increment + count3, this.settingNameColor(m));
                                    final int borderColor2 = new Color(7237230).getRGB();
                                    final double cleft2 = offset + this.diff + 5.0 + difference - 6.0;
                                    final double cright2 = offset + this.diff + 14.0 + difference - 6.0;
                                    final double ctop2 = top + staticHeight + count3 + 2.0;
                                    final double cbottom2 = top + increment + count3 + staticHeight - 2.0;
                                    final double lineWidth2 = 0.5;
                                    Gui.drawRect(cleft2, ctop2, cright2, ctop2 + lineWidth2, borderColor2);
                                    Gui.drawRect(cleft2, cbottom2 - lineWidth2, cright2, cbottom2, borderColor2);
                                    Gui.drawRect(cleft2, cbottom2, cleft2 + lineWidth2, ctop2, borderColor2);
                                    Gui.drawRect(cright2 - lineWidth2, cbottom2, cright2, ctop2, borderColor2);
                                    final double enabledDifference2 = 2.0;
                                    if (booleanSet2.isEnabled()) {
                                        Gui.drawRect(cleft2 + enabledDifference2, ctop2 + enabledDifference2, cright2 - enabledDifference2, cbottom2 - enabledDifference2, this.sliderColor(m, false));
                                    }
                                    count3 += increment;
                                }
                                if (setOnCat instanceof DoubleSet) {
                                    final DoubleSet numberSet2 = (DoubleSet)setOnCat;
                                    increment = 15.0;
                                    final float percent2 = (float)((numberSet2.getValue() - numberSet2.getMin()) / (numberSet2.getMax() - numberSet2.getMin()));
                                    final float numberWidth2 = percent2 * width;
                                    if (this.numb != null && this.numb == numberSet2) {
                                        final double mousePercent2 = Math.min(1.0f, Math.max(0.0f, (mouseX - offset) / width));
                                        final double newValue2 = mousePercent2 * (numberSet2.getMax() - numberSet2.getMin()) + numberSet2.getMin();
                                        numberSet2.setValue(newValue2);
                                    }
                                    Gui.drawRect(offset + this.diff, top + staticHeight + count3, offset + width - this.diff, top + increment + count3 + staticHeight, this.settingOnCategoryRectColor(hoveringCat, m.getCategory()));
                                    Gui.drawRect(Math.min(Math.max(offset + numberWidth2 - this.diff, doubleSetDifference + offset + this.diff), offset + width - this.diff - doubleSetDifference), top + 25.0f + count3, offset + width - this.diff - doubleSetDifference, top + 26.0f + count3, sliderShadow);
                                    Gui.drawRect(Math.min(offset + numberWidth2 - this.diff, doubleSetDifference + offset + this.diff), top + 25.0f + count3, Math.min(offset + numberWidth2 - this.diff, offset + width - this.diff - doubleSetDifference), top + 26.0f + count3, this.sliderColor(m, hoveringCat));
                                    final String val2 = numberSet2.getValue() + numberSet2.getSuffix();
                                    if (handleType == HandleType.BUTTON_PRESSED && hoveringCat && button == 0) {
                                        this.numb = numberSet2;
                                    }
                                    this.fontRendererObj.drawStringWithShadow(setOnCat.name + ": " + EnumChatFormatting.WHITE + val2, offset + difference, 0.0f + top + textDif - staticHeight - increment + count3, this.settingNameColor(m));
                                    count3 += increment;
                                }
                                if (setOnCat instanceof ModeSet) {
                                    final ModeSet modeSet2 = (ModeSet)setOnCat;
                                    if (handleType == HandleType.BUTTON_PRESSED && hoveringCat && button == 0) {
                                        modeSet2.positiveCycle();
                                    }
                                    if (handleType == HandleType.BUTTON_PRESSED && hoveringCat && button == 1) {
                                        modeSet2.negativeCycle();
                                    }
                                    Gui.drawRect(offset + this.diff, top + staticHeight + count3, offset + width - this.diff, top + increment + count3 + staticHeight, this.settingOnCategoryRectColor(hoveringCat, m.getCategory()));
                                    final String fullString2 = setOnCat.name + " ... " + EnumChatFormatting.WHITE + modeSet2.getMode().toUpperCase();
                                    this.fontRendererObj.drawStringWithShadow(fullString2, offset + difference, 2.0f + top + textDif - staticHeight - increment + count3, this.settingNameColor(m));
                                    count3 += increment;
                                }
                            }
                        }
                    }
                    GL11.glPopMatrix();
                }
            }
            countY += incrementVal;
        }
        if (handleType == HandleType.BUTTON_PRESSED && button == 0) {
            this.draggingCategory = null;
        }
    }
    
    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }
    
    public Color getGradientOffset(final Color color1, final Color color2, double offset) {
        if (offset > 1.0) {
            final double left = offset % 1.0;
            final int off = (int)offset;
            offset = ((off % 2 == 0) ? left : (1.0 - left));
        }
        final double inverse_percent = 1.0 - offset;
        final int redPart = (int)(color1.getRed() * inverse_percent + color2.getRed() * offset);
        final int greenPart = (int)(color1.getGreen() * inverse_percent + color2.getGreen() * offset);
        final int bluePart = (int)(color1.getBlue() * inverse_percent + color2.getBlue() * offset);
        return new Color(redPart, greenPart, bluePart);
    }
    
    enum HandleType
    {
        BUTTON_PRESSED, 
        BUTTON_RELEASED, 
        RENDER, 
        KEY_TYPED;
    }
}
