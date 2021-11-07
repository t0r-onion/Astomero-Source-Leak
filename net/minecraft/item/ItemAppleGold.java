package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.potion.*;
import net.minecraft.creativetab.*;
import java.util.*;

public class ItemAppleGold extends ItemFood
{
    public ItemAppleGold(final int amount, final float saturation, final boolean isWolfFood) {
        super(amount, saturation, isWolfFood);
        this.setHasSubtypes(true);
    }
    
    @Override
    public boolean hasEffect(final ItemStack stack) {
        return stack.getMetadata() > 0;
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack stack) {
        return (stack.getMetadata() == 0) ? EnumRarity.RARE : EnumRarity.EPIC;
    }
    
    @Override
    protected void onFoodEaten(final ItemStack stack, final World worldIn, final EntityPlayer player) {
        if (!worldIn.isRemote) {
            player.addPotionEffect(new PotionEffect(Potion.absorption.id, 2400, 0));
        }
        if (stack.getMetadata() > 0) {
            if (!worldIn.isRemote) {
                player.addPotionEffect(new PotionEffect(Potion.regeneration.id, 600, 4));
                player.addPotionEffect(new PotionEffect(Potion.resistance.id, 6000, 0));
                player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 6000, 0));
            }
        }
        else {
            super.onFoodEaten(stack, worldIn, player);
        }
    }
    
    @Override
    public void getSubItems(final Item itemIn, final CreativeTabs tab, final List<ItemStack> subItems) {
        subItems.add(new ItemStack(itemIn, 1, 0));
        subItems.add(new ItemStack(itemIn, 1, 1));
    }
}
