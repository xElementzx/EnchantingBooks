package me.elementz.enchantingbooks.util;

import me.elementz.enchantingbooks.item.ItemRegistration;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

/**
 * Provides a Simple way to create an ItemGroup
 * <p>
 * Created by covers1624 on 7/11/2016.
 * Stolen from CCL https://github.com/TheCBProject/CodeChickenLib/blob/master/src/main/java/codechicken/lib/gui/SimpleItemGroup.java by xElementzx on 20/01/2021 with permission from Covers1624.
 */

public class CustomItemGroup extends ItemGroup {
    private final Supplier<ItemStack> stackSupplier;

    public CustomItemGroup(String label, Supplier<ItemStack> stackSupplier) {
        super(label);
        this.stackSupplier = stackSupplier;
    }

    @Nonnull
    @Override
    public ItemStack createIcon() {
        return stackSupplier.get();
    }
    public static final ItemGroup ENCHANTING_BOOKS = new CustomItemGroup("enchanting_books", () -> new ItemStack(ItemRegistration.ENCHANTED_NETHERITE_BOOK.get()));
}
