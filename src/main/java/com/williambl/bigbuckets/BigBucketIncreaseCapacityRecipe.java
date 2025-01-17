package com.williambl.bigbuckets;

import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidAttributes;

public class BigBucketIncreaseCapacityRecipe extends SpecialRecipe {
    public BigBucketIncreaseCapacityRecipe(ResourceLocation idIn) {
        super(idIn);
    }

    @Override
    public boolean matches(CraftingInventory inv, World worldIn) {
        int i = 0;
        ItemStack bigBucketStack = ItemStack.EMPTY;

        for (int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack stackInSlot = inv.getStackInSlot(j);
            if (!stackInSlot.isEmpty()) {
                if (stackInSlot.getItem() == BigBuckets.BIG_BUCKET_ITEM.get()) {
                    if (bigBucketStack.isEmpty())
                        bigBucketStack = stackInSlot;
                    else
                        return false;
                } else {
                    if (stackInSlot.getItem() == Items.BUCKET)
                        i++;
                    else {
                        return false;
                    }
                }
            }
        }

        return !bigBucketStack.isEmpty() && i > 0;
    }

    @Override
    public ItemStack getCraftingResult(CraftingInventory inv) {
        int i = 0;
        ItemStack bigBucketStack = ItemStack.EMPTY;

        for (int j = 0; j < inv.getSizeInventory(); ++j) {
            ItemStack stackInSlot = inv.getStackInSlot(j);
            if (!stackInSlot.isEmpty()) {
                if (stackInSlot.getItem() == BigBuckets.BIG_BUCKET_ITEM.get()) {
                    bigBucketStack = stackInSlot.copy();
                } else {
                    if (stackInSlot.getItem() == Items.BUCKET)
                        i++;
                }
            }
        }

        BigBuckets.BIG_BUCKET_ITEM.get().setCapacity(bigBucketStack, BigBuckets.BIG_BUCKET_ITEM.get().getCapacity(bigBucketStack) + i * FluidAttributes.BUCKET_VOLUME);
        return bigBucketStack;
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return BigBuckets.BIG_BUCKET_INCREASE_CAPACITY_RECIPE_SERIALIZER.get();
    }

    /**
     * Used to determine if this recipe can fit in a grid of the given width/height
     */
    @Override
    public boolean canFit(int width, int height) {
        return width * height >= 2;
    }
}