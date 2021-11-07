package com.initial.utils.player;

import net.minecraft.client.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.util.*;

public class ScaffoldUtils
{
    static Minecraft mc;
    
    public static boolean canIItemBePlaced(final Item item) {
        return Item.getIdFromItem(item) != 116 && Item.getIdFromItem(item) != 30 && Item.getIdFromItem(item) != 31 && Item.getIdFromItem(item) != 175 && Item.getIdFromItem(item) != 28 && Item.getIdFromItem(item) != 27 && Item.getIdFromItem(item) != 66 && Item.getIdFromItem(item) != 157 && Item.getIdFromItem(item) != 31 && Item.getIdFromItem(item) != 6 && Item.getIdFromItem(item) != 31 && Item.getIdFromItem(item) != 32 && Item.getIdFromItem(item) != 140 && Item.getIdFromItem(item) != 390 && Item.getIdFromItem(item) != 37 && Item.getIdFromItem(item) != 38 && Item.getIdFromItem(item) != 39 && Item.getIdFromItem(item) != 40 && Item.getIdFromItem(item) != 69 && Item.getIdFromItem(item) != 50 && Item.getIdFromItem(item) != 75 && Item.getIdFromItem(item) != 76 && Item.getIdFromItem(item) != 54 && Item.getIdFromItem(item) != 130 && Item.getIdFromItem(item) != 146 && Item.getIdFromItem(item) != 342 && Item.getIdFromItem(item) != 12 && Item.getIdFromItem(item) != 77 && Item.getIdFromItem(item) != 143 && Item.getIdFromItem(item) != 46;
    }
    
    public static boolean canItemBePlaced(final ItemStack item) {
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 116) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 30) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 31) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 175) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 28) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 27) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 66) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 157) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 31) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 6) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 31) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 32) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 140) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 390) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 37) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 38) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 39) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 40) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 69) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 50) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 75) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 76) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 54) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 130) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 146) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 342) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 12) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 77) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 143) {
            return false;
        }
        item.getItem();
        if (Item.getIdFromItem(item.getItem()) == 46) {
            return false;
        }
        item.getItem();
        return Item.getIdFromItem(item.getItem()) != 145;
    }
    
    public static int getBlockCount() {
        int blockCount = 0;
        for (int i = 0; i < 45; ++i) {
            if (ScaffoldUtils.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack is = ScaffoldUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                final Item item = is.getItem();
                if (is.getItem() instanceof ItemBlock && canIItemBePlaced(item)) {
                    blockCount += is.stackSize;
                }
            }
        }
        return blockCount;
    }
    
    public static int findBlockSlot() {
        int slot = -1;
        int highestStack = -1;
        for (int i = 36; i < 45; ++i) {
            final ItemStack stack = ScaffoldUtils.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemBlock && canIItemBePlaced(stack.getItem()) && stack.stackSize > 0 && stack.stackSize > highestStack) {
                slot = i;
                highestStack = stack.stackSize;
            }
        }
        return slot;
    }
    
    public static float[] getRotations(final BlockPos block, final EnumFacing face) {
        final double x = block.getX() + 0.5 - ScaffoldUtils.mc.thePlayer.posX + face.getFrontOffsetX() / 2.0;
        final double z = block.getZ() + 0.5 - ScaffoldUtils.mc.thePlayer.posZ + face.getFrontOffsetZ() / 2.0;
        final double y = block.getY() + 0.5;
        final double d1 = ScaffoldUtils.mc.thePlayer.posY + ScaffoldUtils.mc.thePlayer.getEyeHeight() - y;
        final double d2 = MathHelper.sqrt_double(x * x + z * z);
        float yaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        final float pitch = (float)(Math.atan2(d1, d2) * 180.0 / 3.141592653589793);
        if (yaw < 0.0f) {
            yaw += 360.0f;
        }
        return new float[] { yaw, pitch };
    }
    
    public static int grabBlockSlot() {
        int slot = -1;
        int highestStack = -1;
        boolean didGetHotbar = false;
        for (int i = 0; i < 36; ++i) {
            final ItemStack itemStack = ScaffoldUtils.mc.thePlayer.inventory.mainInventory[i];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && canItemBePlaced(itemStack) && itemStack.stackSize > 0) {
                if (ScaffoldUtils.mc.thePlayer.inventory.mainInventory[i].stackSize > highestStack && i < 9) {
                    highestStack = ScaffoldUtils.mc.thePlayer.inventory.mainInventory[i].stackSize;
                    slot = i;
                    if (slot == getLastHotbarSlot()) {
                        didGetHotbar = true;
                    }
                }
                if (i > 8 && !didGetHotbar) {
                    final int hotbarNum = getFreeHotbarSlot();
                    if (hotbarNum != -1 && ScaffoldUtils.mc.thePlayer.inventory.mainInventory[i].stackSize > highestStack) {
                        highestStack = ScaffoldUtils.mc.thePlayer.inventory.mainInventory[i].stackSize;
                        slot = i;
                    }
                }
            }
        }
        if (slot > 8) {
            final int hotbarNum2 = getFreeHotbarSlot();
            if (hotbarNum2 == -1) {
                return -1;
            }
            ScaffoldUtils.mc.playerController.windowClick(ScaffoldUtils.mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum2, 2, ScaffoldUtils.mc.thePlayer);
        }
        return slot;
    }
    
    public static int getLastHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; ++k) {
            final ItemStack itemStack = ScaffoldUtils.mc.thePlayer.inventory.mainInventory[k];
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock && canItemBePlaced(itemStack) && itemStack.stackSize > 1) {
                hotbarNum = k;
            }
        }
        return hotbarNum;
    }
    
    public static int getFreeHotbarSlot() {
        int hotbarNum = -1;
        for (int k = 0; k < 9; ++k) {
            if (ScaffoldUtils.mc.thePlayer.inventory.mainInventory[k] == null) {
                hotbarNum = k;
            }
            else {
                hotbarNum = 7;
            }
        }
        return hotbarNum;
    }
    
    private Vec3 grabPosition(final BlockPos position, final EnumFacing facing) {
        final Vec3 offset = new Vec3(facing.getDirectionVec().getX() / 2.0, facing.getDirectionVec().getY() / 2.0 - 5.0, facing.getDirectionVec().getZ() / 2.0);
        final Vec3 point = new Vec3(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
        return point.add(offset);
    }
    
    public static float clampRotation() {
        float rotationYaw = ScaffoldUtils.mc.thePlayer.rotationYaw;
        float n = 1.0f;
        if (ScaffoldUtils.mc.thePlayer.movementInput.moveForward < 0.0f) {
            rotationYaw += 180.0f;
            n = -0.5f;
        }
        else if (ScaffoldUtils.mc.thePlayer.movementInput.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (ScaffoldUtils.mc.thePlayer.movementInput.moveStrafe > 0.0f) {
            rotationYaw -= 90.0f * n;
        }
        if (ScaffoldUtils.mc.thePlayer.movementInput.moveStrafe < 0.0f) {
            rotationYaw += 90.0f * n;
        }
        return rotationYaw * 0.017453292f;
    }
    
    static {
        ScaffoldUtils.mc = Minecraft.getMinecraft();
    }
}
