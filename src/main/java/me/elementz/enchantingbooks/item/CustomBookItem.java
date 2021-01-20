package me.elementz.enchantingbooks.item;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.BookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;

public class CustomBookItem extends BookItem {

    private final int itemEnchantability;

    public CustomBookItem(Properties properties, int itemEnchantability) {
        super(properties);
        this.itemEnchantability = itemEnchantability;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.isAllowedOnBooks();
    }

    public static ListNBT getEnchantments(ItemStack stack) {
        CompoundNBT compoundnbt = stack.getTag();
        return compoundnbt != null ? compoundnbt.getList("StoredEnchantments", 10) : new ListNBT();
    }

    @Override
    public int getItemEnchantability() {
        return itemEnchantability;
    }
}