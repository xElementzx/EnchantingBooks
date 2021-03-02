package me.elementz.enchantingbooks.mixin.core.enchantment;

import me.elementz.enchantingbooks.item.CustomEnchantedBookItem;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.ListNBT;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(value = EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {
    
    @Inject(
            method = "getEnchantments",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private static void onGetEnchantments(ItemStack itemStack, CallbackInfoReturnable<Map<Enchantment, Integer>> callbackInfoReturnable) {
        if (!(itemStack.getItem() instanceof CustomEnchantedBookItem)) {
            return;
        }
        
        ListNBT listNBT = EnchantedBookItem.getEnchantments(itemStack);
        Map<Enchantment, Integer> enchantments = EnchantmentHelper.deserializeEnchantments(listNBT);
        callbackInfoReturnable.setReturnValue(enchantments);
    }
    
    @Redirect(
            method = "setEnchantments",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"
            )
    )
    private static Item onSetEnchantments(ItemStack itemStack) {
        if (itemStack.getItem() instanceof CustomEnchantedBookItem) {
            return Items.ENCHANTED_BOOK;
        }
        
        return itemStack.getItem();
    }
}