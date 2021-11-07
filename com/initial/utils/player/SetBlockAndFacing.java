package com.initial.utils.player;

import net.minecraft.client.*;
import net.minecraft.init.*;
import com.initial.utils.*;
import net.minecraft.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class SetBlockAndFacing
{
    Minecraft mc;
    private BlockPos currentPos;
    
    public SetBlockAndFacing() {
        this.mc = Minecraft.getMinecraft();
    }
    
    private void SetBlockAndFacing(final BlockPos var1) {
        if (this.mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, -1, 0);
            final EnumFacing up = EnumFacing.UP;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(-1, 0, 0);
            final EnumFacing east = EnumFacing.EAST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(1, 0, 0);
            final EnumFacing west = EnumFacing.WEST;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, -1);
            final EnumFacing south = EnumFacing.SOUTH;
        }
        else if (this.mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock() != Blocks.air) {
            this.currentPos = var1.add(0, 0, 1);
            final EnumFacing north = EnumFacing.NORTH;
        }
        else {
            this.currentPos = null;
        }
    }
    
    public static class BlockUtil
    {
        private static Minecraft mc;
        
        public static void placeHeldItemUnderPlayer() {
            final BlockPos blockPos = new BlockPos(BlockUtil.mc.thePlayer.posX, BlockUtil.mc.thePlayer.getEntityBoundingBox().minY - 1.0, BlockUtil.mc.thePlayer.posZ);
            final Vec3d vec = new Vec3d(blockPos).addVector(0.4000000059604645, 0.4000000059604645, 0.4000000059604645);
            BlockUtil.mc.playerController.onPlayerRightClick(BlockUtil.mc.thePlayer, BlockUtil.mc.theWorld, null, blockPos, EnumFacing.UP, vec.scale(0.4));
        }
        
        public static float[] getDirectionToBlock(final int var0, final int var1, final int var2, final EnumFacing var3) {
            final EntityEgg var4 = new EntityEgg(BlockUtil.mc.theWorld);
            var4.posX = var0 + 0.5;
            var4.posY = var1 + 0.5;
            var4.posZ = var2 + 0.5;
            final EntityEgg entityEgg = var4;
            entityEgg.posX += var3.getDirectionVec().getX() * 0.25;
            final EntityEgg entityEgg2 = var4;
            entityEgg2.posY += var3.getDirectionVec().getY() * 0.25;
            final EntityEgg entityEgg3 = var4;
            entityEgg3.posZ += var3.getDirectionVec().getZ() * 0.25;
            return getDirectionToEntity(var4);
        }
        
        private static float[] getDirectionToEntity(final Entity var0) {
            return new float[] { getYaw(var0) + BlockUtil.mc.thePlayer.rotationYaw, getPitch(var0) + BlockUtil.mc.thePlayer.rotationPitch };
        }
        
        public static float[] getRotationNeededForBlock(final EntityPlayer paramEntityPlayer, final BlockPos pos) {
            final double d1 = pos.getX() - paramEntityPlayer.posX;
            final double d2 = pos.getY() + 0.5 - paramEntityPlayer.posY + paramEntityPlayer.getEyeHeight();
            final double d3 = pos.getZ() - paramEntityPlayer.posZ;
            final double d4 = Math.sqrt(d1 * d1 + d3 * d3);
            final float f1 = (float)(Math.atan2(d3, d1) * 180.0 / 3.141592653589793) - 90.0f;
            final float f2 = (float)(-(Math.atan2(d2, d4) * 180.0 / 3.141592653589793));
            return new float[] { f1, f2 };
        }
        
        public static Rotation limitAngleChange(final Rotation currentRotation, final Rotation targetRotation, final float turnSpeed) {
            final float yawDifference = getAngleDifference(targetRotation.getYaw(), currentRotation.getYaw());
            final float pitchDifference = getAngleDifference(targetRotation.getPitch(), currentRotation.getPitch());
            return new Rotation(currentRotation.getYaw() + ((yawDifference > turnSpeed) ? turnSpeed : Math.max(yawDifference, -turnSpeed)), currentRotation.getPitch() + ((pitchDifference > turnSpeed) ? turnSpeed : Math.max(pitchDifference, -turnSpeed)));
        }
        
        private static float getAngleDifference(final float a, final float b) {
            return ((a - b) % 360.0f + 540.0f) % 360.0f - 180.0f;
        }
        
        public static float getYaw(final Entity var0) {
            final double var = var0.posX - BlockUtil.mc.thePlayer.posX;
            final double var2 = var0.posZ - BlockUtil.mc.thePlayer.posZ;
            double var3;
            if (var2 < 0.0 && var < 0.0) {
                var3 = 90.0 + Math.toDegrees(Math.atan(var2 / var));
            }
            else if (var2 < 0.0 && var > 0.0) {
                var3 = -90.0 + Math.toDegrees(Math.atan(var2 / var));
            }
            else {
                var3 = Math.toDegrees(-Math.atan(var / var2));
            }
            return MathHelper.wrapAngleTo180_float(-(BlockUtil.mc.thePlayer.rotationYaw - (float)var3));
        }
        
        public static float getPitch(final Entity var0) {
            final double var = var0.posX - BlockUtil.mc.thePlayer.posX;
            final double var2 = var0.posZ - BlockUtil.mc.thePlayer.posZ;
            final double var3 = var0.posY - 1.6 + var0.getEyeHeight() - BlockUtil.mc.thePlayer.posY;
            final double var4 = MathHelper.sqrt_double(var * var + var2 * var2);
            final double var5 = -Math.toDegrees(Math.atan(var3 / var4));
            return -MathHelper.wrapAngleTo180_float(BlockUtil.mc.thePlayer.rotationPitch - (float)var5);
        }
        
        static {
            BlockUtil.mc = Minecraft.getMinecraft();
        }
    }
}
