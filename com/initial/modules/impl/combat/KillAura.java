package com.initial.modules.impl.combat;

import com.initial.utils.player.*;
import com.initial.settings.impl.*;
import com.initial.modules.*;
import com.initial.settings.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import com.initial.utils.movement.*;
import net.minecraft.entity.*;
import com.initial.utils.networking.*;
import net.minecraft.network.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import com.initial.events.*;
import java.util.concurrent.*;
import java.util.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import com.initial.events.impl.*;
import org.lwjgl.opengl.*;
import java.awt.*;
import net.minecraft.client.gui.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.entity.*;
import com.initial.utils.render.*;
import net.minecraft.client.network.*;

public class KillAura extends Module
{
    public static EntityLivingBase target;
    boolean verusBlocking;
    List<EntityLivingBase> loaded;
    TimeHelper switchTimer;
    TimeHelper timer;
    float[] serversideRotations;
    float oldYaw;
    float oldPitch;
    int index;
    int doggoStatus;
    double doggoUpAndDown;
    public static ScaledResolution sr;
    private double animHealth;
    private double width;
    public DoubleSet maxCPS;
    public DoubleSet minCPS;
    public DoubleSet Range;
    public ModeSet targetMode;
    public ModeSet priority;
    public BooleanSet AutoBlock;
    public ModeSet AutoBlockMode;
    public BooleanSet Rotations;
    public ModeSet RotationsMode;
    public BooleanSet TargetHud;
    public ModeSet TargetHudMode;
    public BooleanSet KeepSprint;
    public BooleanSet players;
    public BooleanSet invisible;
    public BooleanSet villagers;
    public BooleanSet teams;
    public BooleanSet mobs;
    public BooleanSet died;
    boolean direction;
    boolean blocking;
    
    public KillAura() {
        super("KillAura", 0, Category.COMBAT);
        this.loaded = new ArrayList<EntityLivingBase>();
        this.switchTimer = new TimeHelper();
        this.timer = new TimeHelper();
        this.index = 0;
        this.maxCPS = new DoubleSet("Max CPS", 12.0, 1.0, 20.0, 1.0);
        this.minCPS = new DoubleSet("Min CPS", 8.0, 1.0, 20.0, 1.0);
        this.Range = new DoubleSet("Range", 4.0, 1.0, 10.0, 1.0);
        this.targetMode = new ModeSet("Mode", "Single", new String[] { "Single", "Switch", "Multi" });
        this.priority = new ModeSet("Priority", "Angle", new String[] { "Health", "Angle", "Distance" });
        this.AutoBlock = new BooleanSet("Auto Block", true);
        this.AutoBlockMode = new ModeSet("AB Mode", "None", new String[] { "None", "Fake", "Verus", "Vanilla", "Watchdog" });
        this.Rotations = new BooleanSet("Rotations", true);
        this.RotationsMode = new ModeSet("Mode", "Normal", new String[] { "Normal", "Smooth", "Verus" });
        this.TargetHud = new BooleanSet("TargetHUD", true);
        this.TargetHudMode = new ModeSet("Mode", "Novoline Old", new String[] { "Novoline Old", "Astolfo Old", "Astolfo" });
        this.KeepSprint = new BooleanSet("Keep Sprint", true);
        this.players = new BooleanSet("Players", true);
        this.invisible = new BooleanSet("Invisibles", false);
        this.villagers = new BooleanSet("Villagers", false);
        this.teams = new BooleanSet("Teams", false);
        this.mobs = new BooleanSet("Mobs", false);
        this.died = new BooleanSet("Died", false);
        this.direction = false;
        this.addSettings(this.maxCPS, this.minCPS, this.Range, this.targetMode, this.priority, this.AutoBlock, this.AutoBlockMode, this.Rotations, this.RotationsMode, this.TargetHud, this.TargetHudMode, this.KeepSprint, this.players, this.invisible, this.villagers, this.teams, this.mobs, this.died);
        this.verusBlocking = false;
        this.doggoUpAndDown = 0.0;
        this.doggoStatus = 0;
        this.oldPitch = 0.0f;
        this.oldYaw = 0.0f;
    }
    
    private boolean isBlockUnder() {
        for (int i = (int)(this.mc.thePlayer.posY - 1.0); i > 0; --i) {
            final BlockPos pos = new BlockPos(this.mc.thePlayer.posX, i, this.mc.thePlayer.posZ);
            if (!(this.mc.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)) {
                return true;
            }
        }
        return false;
    }
    
    @EventTarget
    public void onMotionEvent(final EventMotion event) {
        if (!this.isBlockUnder() || this.mc.thePlayer.isCollidedHorizontally) {
            this.direction = !this.direction;
        }
        this.setDisplayName("Kill Aura §7" + this.targetMode.getMode());
        if (event.isPre()) {
            if (KillAura.target != null && this.AutoBlock.isEnabled()) {
                final String mode = this.AutoBlockMode.getMode();
                switch (mode) {
                    case "Vanilla": {
                        if (this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
                            this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getHeldItem(), 140);
                            this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.getHeldItem());
                            break;
                        }
                        break;
                    }
                    case "Verus": {
                        if (KillAura.localPlayer.ticksExisted % 2 == 0) {
                            this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getCurrentEquippedItem(), this.mc.thePlayer.getCurrentEquippedItem().getMaxItemUseDuration());
                            this.verusBlocking = true;
                        }
                        this.verusBlocking = false;
                        break;
                    }
                    case "Watchdog": {
                        if (this.mc.thePlayer.inventory.getCurrentItem() != null && this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
                            this.mc.thePlayer.setItemInUse(this.mc.thePlayer.getCurrentEquippedItem(), this.mc.thePlayer.getCurrentEquippedItem().getMaxItemUseDuration());
                            if (KillAura.target != null) {
                                PacketUtil.sendPacket(new C02PacketUseEntity(KillAura.target, RotationUtils.getVectorToEntity(KillAura.target)));
                                PacketUtil.sendPacket(new C02PacketUseEntity(KillAura.target, C02PacketUseEntity.Action.INTERACT));
                            }
                            PacketUtil.sendPacket(new C08PacketPlayerBlockPlacement(new BlockPos(-0.5534147541, -0.5534147541, -0.5534147541), 255, Minecraft.getMinecraft().thePlayer.inventory.getCurrentItem(), 0.0f, 0.0f, 0.0f));
                            break;
                        }
                        break;
                    }
                }
            }
            final String mode2 = this.priority.getMode();
            switch (mode2) {
                case "Health": {
                    KillAura.target = this.getHealthPriority();
                    break;
                }
                case "Angle": {
                    KillAura.target = this.getAnglePriority();
                    break;
                }
            }
            final String mode3 = this.targetMode.getMode();
            switch (mode3) {
                case "Single": {
                    KillAura.target = this.getClosest(this.Range.getValue());
                    if (KillAura.target == null) {
                        break;
                    }
                    if (!this.KeepSprint.isEnabled()) {
                        this.mc.thePlayer.setSprinting(false);
                        this.mc.gameSettings.keyBindSprint.pressed = false;
                    }
                    if (this.mc.thePlayer.getDistanceToEntity(KillAura.target) < this.Range.getValue()) {
                        final boolean block = this.AutoBlock.isEnabled();
                        final float[] rots = this.getRotations(KillAura.target, event);
                        event.setYaw(rots[0]);
                        event.setPitch(rots[1]);
                        if (this.timer.hasReached(randomClickDelay(this.minCPS.getValue(), this.maxCPS.getValue()))) {
                            if (block) {
                                this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                            }
                            this.blocking = false;
                            this.mc.thePlayer.swingItem();
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(KillAura.target, C02PacketUseEntity.Action.ATTACK));
                            this.timer.reset();
                        }
                        break;
                    }
                    break;
                }
                case "Switch": {
                    this.loaded = this.getTargets();
                    if (this.loaded.size() == 0) {
                        return;
                    }
                    if (this.switchTimer.hasReached(400L)) {
                        if (this.index < this.loaded.size() - 1) {
                            ++this.index;
                        }
                        else {
                            this.index = 0;
                        }
                        this.switchTimer.reset();
                    }
                    KillAura.target = this.loaded.get(this.index);
                    if (KillAura.target == null) {
                        break;
                    }
                    if (!this.KeepSprint.isEnabled()) {
                        this.mc.thePlayer.setSprinting(false);
                        this.mc.gameSettings.keyBindSprint.pressed = false;
                    }
                    if (this.mc.thePlayer.getDistanceToEntity(KillAura.target) < this.Range.getValue()) {
                        final boolean block = this.AutoBlock.isEnabled();
                        final float[] rots = this.getRotations(KillAura.target, event);
                        event.setYaw(rots[0]);
                        event.setPitch(rots[1]);
                        if (this.timer.hasReached(randomClickDelay(this.minCPS.getValue(), this.maxCPS.getValue()))) {
                            if (block) {
                                this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                            }
                            this.blocking = false;
                            this.mc.thePlayer.swingItem();
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(KillAura.target, C02PacketUseEntity.Action.ATTACK));
                            this.timer.reset();
                        }
                        break;
                    }
                    break;
                }
                case "Multi": {
                    this.loaded = this.getTargets();
                    if (this.loaded.size() == 0) {
                        return;
                    }
                    if (this.switchTimer.hasReached(1L)) {
                        if (this.index < this.loaded.size() - 1) {
                            ++this.index;
                        }
                        else {
                            this.index = 0;
                        }
                        this.switchTimer.reset();
                    }
                    KillAura.target = this.loaded.get(this.index);
                    if (KillAura.target == null) {
                        break;
                    }
                    if (!this.KeepSprint.isEnabled()) {
                        this.mc.thePlayer.setSprinting(false);
                        this.mc.gameSettings.keyBindSprint.pressed = false;
                    }
                    if (this.mc.thePlayer.getDistanceToEntity(KillAura.target) >= this.Range.getValue()) {
                        break;
                    }
                    final boolean block = this.AutoBlock.isEnabled();
                    final float[] rots = this.getRotations(KillAura.target, event);
                    event.setYaw(rots[0]);
                    event.setPitch(rots[1]);
                    if (this.timer.hasReached(randomClickDelay(this.minCPS.getValue(), this.maxCPS.getValue()))) {
                        if (block) {
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                        }
                        this.blocking = false;
                        this.mc.thePlayer.swingItem();
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(KillAura.target, C02PacketUseEntity.Action.ATTACK));
                        this.timer.reset();
                        break;
                    }
                    break;
                }
            }
        }
        if (this.loaded.isEmpty() && !this.targetMode.is("Single")) {
            KillAura.target = null;
        }
    }
    
    public static long randomClickDelay(final double minCPS, final double maxCPS) {
        return (long)(Math.random() * (1000.0 / minCPS - 1000.0 / maxCPS + 1.0) + 1000.0 / maxCPS);
    }
    
    public float[] getRotations(final EntityLivingBase e, final EventMotion event) {
        final String mode = this.RotationsMode.getMode();
        switch (mode) {
            case "Normal": {
                return RotationUtils.getRotations(e);
            }
            case "Smooth": {
                final float[] targetYaw = RotationUtils.getRotations(e);
                float yaw = 0.0f;
                final float speed = (float)ThreadLocalRandom.current().nextDouble(1.5, 2.2);
                final float yawDifference = event.getLastYaw() - targetYaw[0];
                yaw = event.getLastYaw() - yawDifference / speed;
                float pitch = 0.0f;
                final float pitchDifference = event.getLastPitch() - targetYaw[1];
                pitch = event.getLastPitch() - pitchDifference / speed;
                return new float[] { yaw, pitch };
            }
            case "Verus": {
                if (this.doggoUpAndDown == 0.7 || this.doggoUpAndDown > 0.7) {
                    this.doggoStatus = 1;
                }
                if (this.doggoUpAndDown == -0.7 || this.doggoUpAndDown < -0.7) {
                    this.doggoStatus = 0;
                }
                if (this.doggoStatus == 0) {
                    this.doggoUpAndDown += 0.12;
                }
                if (this.doggoStatus == 1) {
                    this.doggoUpAndDown -= 0.12;
                }
                return RotationUtils.getRotsByPos(KillAura.target.posX + ThreadLocalRandom.current().nextDouble(0.1, 0.6), KillAura.target.posY + 0.92 + this.doggoUpAndDown, KillAura.target.posZ - ThreadLocalRandom.current().nextDouble(0.1, 0.6));
            }
            default: {
                return null;
            }
        }
    }
    
    public EntityLivingBase getHealthPriority() {
        final List<EntityLivingBase> entities = new ArrayList<EntityLivingBase>();
        for (final Entity e : this.mc.theWorld.loadedEntityList) {
            if (e instanceof EntityLivingBase) {
                final EntityLivingBase player = (EntityLivingBase)e;
                if (this.mc.thePlayer.getDistanceToEntity(player) >= this.Range.getValue() || !this.canAttack(player)) {
                    continue;
                }
                entities.add(player);
            }
        }
        entities.sort((o1, o2) -> (int)(o1.getHealth() - o2.getHealth()));
        if (entities.isEmpty()) {
            return null;
        }
        return entities.get(0);
    }
    
    public EntityLivingBase getAnglePriority() {
        final List<EntityLivingBase> entities = new ArrayList<EntityLivingBase>();
        for (final Entity e : this.mc.theWorld.loadedEntityList) {
            if (e instanceof EntityLivingBase) {
                final EntityLivingBase player = (EntityLivingBase)e;
                if (this.mc.thePlayer.getDistanceToEntity(player) >= this.Range.getValue() || !this.canAttack(player)) {
                    continue;
                }
                entities.add(player);
            }
        }
        final float[] rot1;
        final float[] rot2;
        entities.sort((o1, o2) -> {
            rot1 = RotationUtils.getRotations(o1);
            rot2 = RotationUtils.getRotations(o2);
            return (int)(this.mc.thePlayer.rotationYaw - rot1[0] - (this.mc.thePlayer.rotationYaw - rot2[0]));
        });
        if (entities.isEmpty()) {
            return null;
        }
        return entities.get(0);
    }
    
    private EntityLivingBase getClosest(final double range) {
        double dist = range;
        EntityLivingBase target = null;
        for (final Object object : this.mc.theWorld.loadedEntityList) {
            final Entity entity = (Entity)object;
            if (entity instanceof EntityLivingBase) {
                final EntityLivingBase player = (EntityLivingBase)entity;
                if (!this.canAttack(player)) {
                    continue;
                }
                final double currentDist = this.mc.thePlayer.getDistanceToEntity(player);
                if (currentDist > dist) {
                    continue;
                }
                dist = currentDist;
                target = player;
            }
        }
        return target;
    }
    
    public List<EntityLivingBase> getTargets() {
        final List<EntityLivingBase> load = new ArrayList<EntityLivingBase>();
        for (final Entity e : this.mc.theWorld.loadedEntityList) {
            if (e instanceof EntityLivingBase) {
                final EntityLivingBase entity = (EntityLivingBase)e;
                if (!this.canAttack(entity) || this.mc.thePlayer.getDistanceToEntity(entity) >= this.Range.getValue()) {
                    continue;
                }
                load.add(entity);
            }
        }
        return load;
    }
    
    public boolean canAttack(final EntityLivingBase player) {
        if (player == this.mc.thePlayer) {
            return false;
        }
        if (player instanceof EntityPlayer || player instanceof EntityAnimal || player instanceof EntityMob || player instanceof EntityVillager) {
            if (player instanceof EntityPlayer && !this.players.isEnabled()) {
                return false;
            }
            if (player instanceof EntityAnimal && !this.mobs.isEnabled()) {
                return false;
            }
            if (player instanceof EntityMob && !this.mobs.isEnabled()) {
                return false;
            }
            if (player instanceof EntityVillager && !this.villagers.isEnabled()) {
                return false;
            }
            if (!player.isEntityAlive() && !this.died.isEnabled()) {
                return false;
            }
        }
        return (!(player instanceof EntityPlayer) || !AntiBot.isBot((EntityPlayer)player)) && (!(player instanceof EntityPlayer) || !isTeam(this.mc.thePlayer, (EntityPlayer)player) || !this.teams.isEnabled()) && (!player.isInvisible() || this.invisible.isEnabled());
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        this.timer.reset();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        final String mode = this.AutoBlockMode.getMode();
        switch (mode) {
            case "Verus": {
                if (this.verusBlocking) {
                    PacketUtil.sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    break;
                }
                break;
            }
        }
    }
    
    public static boolean isTeam(final EntityPlayer e, final EntityPlayer e2) {
        if (e2.getTeam() != null && e.getTeam() != null) {
            final Character target = e2.getDisplayName().getFormattedText().charAt(1);
            final Character player = e.getDisplayName().getFormattedText().charAt(1);
            return target.equals(player);
        }
        return true;
    }
    
    @EventTarget
    public void onRender(final Event2D event) {
        if (this.TargetHud.isEnabled()) {
            if (KillAura.target == null || !KillAura.target.isEntityAlive()) {
                return;
            }
            final String mode = this.TargetHudMode.getMode();
            switch (mode) {
                case "Novoline Old": {
                    if (KillAura.target instanceof EntityPlayer) {
                        final ScaledResolution sr = new ScaledResolution(this.mc);
                        final EntityPlayer entityPlayer = (EntityPlayer)KillAura.target;
                        break;
                    }
                    break;
                }
                case "Astolfo": {
                    if (KillAura.target instanceof EntityPlayer) {
                        GL11.glPushMatrix();
                        this.width = 107.5;
                        GL11.glTranslated((double)(GuiScreen.width / 2 - 40), (double)(GuiScreen.height / 2 + 20), (double)(GuiScreen.width / 2));
                        Gui.drawRect(-22.5, 0.0, 155.0, 63.0, new Color(0.12f, 0.12f, 0.12f, 0.9f).getRGB());
                        GL11.glTranslatef(-22.0f, -2.2f, 0.0f);
                        this.mc.fontRendererObj.drawStringWithShadow(KillAura.target.getName(), 40.0, 8.399999618530273, -1);
                        GuiInventory.drawEntityOnScreenWithoutName(19.0f, 60.0f, 25, KillAura.target.rotationYaw, -KillAura.target.rotationPitch, KillAura.target);
                        GlStateManager.pushMatrix();
                        GlStateManager.scale(2.5f, 2.5f, 1.5f);
                        this.mc.fontRendererObj.drawStringWithShadow(Math.round(KillAura.target.getHealth() / 99.0f * 100.0f) + " \u2764", 16.0, 9.0, new Color(255, 88, 88).getRGB());
                        GlStateManager.popMatrix();
                        this.animHealth += (KillAura.target.getHealth() - this.animHealth) / 32.0 * 0.7;
                        if (this.animHealth < 0.0 || this.animHealth > KillAura.target.getMaxHealth()) {
                            this.animHealth = KillAura.target.getHealth();
                        }
                        else {
                            Gui.drawRect(40.0, 51.0, this.width + 35.0, 60.0, new Color(1, 1, 1, 100).getRGB());
                            Gui.drawRect(40.0, 51.0, (int)(this.animHealth / KillAura.target.getMaxHealth() * this.width + 35.0), 60.0, new Color(255, 88, 88).getRGB());
                        }
                        GL11.glScalef(2.0f, 2.0f, 2.0f);
                        GL11.glPopMatrix();
                        break;
                    }
                    break;
                }
                case "Astolfo Old": {
                    if (KillAura.target instanceof EntityPlayer) {
                        final ScaledResolution sr = new ScaledResolution(this.mc);
                        double hpPercentage = KillAura.target.getHealth() / KillAura.target.getMaxHealth();
                        final float scaledWidth = (float)sr.getScaledWidth();
                        final float scaledHeight = (float)sr.getScaledHeight();
                        final EntityPlayer player = (EntityPlayer)KillAura.target;
                        if (hpPercentage > 1.0) {
                            hpPercentage = 1.0;
                        }
                        else if (hpPercentage < 0.0) {
                            hpPercentage = 0.0;
                        }
                        final NetworkPlayerInfo networkPlayerInfo = this.mc.getNetHandler().getPlayerInfo(KillAura.target.getUniqueID());
                        Render2DUtils.drawRect2(scaledWidth / 2.0f - 200.0f, scaledHeight / 2.0f - 42.0f, scaledWidth / 2.0f - 200.0f + 40.0f + ((this.mc.fontRendererObj.getStringWidth(player.getName()) > 105) ? (this.mc.fontRendererObj.getStringWidth(player.getName()) - 10) : 105), scaledHeight / 2.0f - 2.0f, new Color(0, 0, 0, 150).getRGB());
                        Render2DUtils.drawFace((int)scaledWidth / 2 - 196, (int)(scaledHeight / 2.0f - 38.0f), 8.0f, 8.0f, 8, 8, 32, 32, 64.0f, 64.0f, (AbstractClientPlayer)player);
                        this.mc.fontRendererObj.drawStringWithShadow(player.getName(), scaledWidth / 2.0f - 196.0f + 40.0f, scaledHeight / 2.0f - 36.0f, -1);
                        Render2DUtils.drawRect2(scaledWidth / 2.0f - 196.0f + 40.0f, scaledHeight / 2.0f - 26.0f, (float)(scaledWidth / 2.0f - 196.0f + 40.0f + 87.5), scaledHeight / 2.0f - 14.0f, new Color(0, 0, 0).getRGB());
                        Render2DUtils.drawRect2(scaledWidth / 2.0f - 196.0f + 40.0f, scaledHeight / 2.0f - 26.0f, (float)(scaledWidth / 2.0f - 196.0f + 40.0f + hpPercentage * 1.25 * 70.0), scaledHeight / 2.0f - 14.0f, ColorUtil.getHealthColor(player).getRGB());
                        this.mc.fontRendererObj.drawStringWithShadow(String.format("%.1f", player.getHealth()), scaledWidth / 2.0f - 196.0f + 40.0f + 36.0f, scaledHeight / 2.0f - 23.0f, ColorUtil.getHealthColor(player).getRGB());
                        this.mc.fontRendererObj.drawStringWithShadow("Ping: §7" + ((networkPlayerInfo == null) ? "0ms" : (networkPlayerInfo.responseTime + "ms")), scaledWidth / 2.0f - 196.0f + 40.0f, scaledHeight / 2.0f - 12.0f, -1);
                        break;
                    }
                    break;
                }
            }
        }
    }
}
