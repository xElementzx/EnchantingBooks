package me.elementz.enchantingbooks.mixin.core.inventory.container;

import me.elementz.enchantingbooks.item.CustomEnchantedBookItem;
import me.elementz.enchantingbooks.item.ItemRegistration;
import net.minecraft.inventory.container.GrindstoneContainer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = GrindstoneContainer.class)
public abstract class GrindstoneContainerMixin {
    
    @Redirect(
            method = "updateRecipeOutput",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"
            )
    )
    private Item onUpdateRecipeOutputGetItem(ItemStack itemStack) {
        if (itemStack.getItem() instanceof CustomEnchantedBookItem) {
            return Items.ENCHANTED_BOOK;
        }
        
        return itemStack.getItem();
    }
    
    @Redirect(
            method = "removeEnchantments",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/item/ItemStack;getItem()Lnet/minecraft/item/Item;"
            )
    )
    private Item onRemoveEnchantmentsGetItem(ItemStack itemStack) {
        if (itemStack.getItem() instanceof CustomEnchantedBookItem) {
            return Items.ENCHANTED_BOOK;
        }
        
        return itemStack.getItem();
    }
    
    @Inject(
            method = "removeEnchantments",
            at = @At(
                    value = "RETURN"
            ),
            cancellable = true
    )
    private void onRemoveEnchantmentsReturn(ItemStack itemStack, int damage, int count, CallbackInfoReturnable<ItemStack> callbackInfoReturnable) {
        if (callbackInfoReturnable.getReturnValue().getItem() != Items.BOOK) {
            return;
        }
        
        Item item;
        if (itemStack.getItem() == ItemRegistration.ENCHANTED_IRON_BOOK.get()) {
            item = ItemRegistration.IRON_BOOK.get();
        } else if (itemStack.getItem() == ItemRegistration.ENCHANTED_GOLDEN_BOOK.get()) {
            item = ItemRegistration.GOLDEN_BOOK.get();
        } else if (itemStack.getItem() == ItemRegistration.ENCHANTED_DIAMOND_BOOK.get()) {
            item = ItemRegistration.DIAMOND_BOOK.get();
        } else if (itemStack.getItem() == ItemRegistration.ENCHANTED_NETHERITE_BOOK.get()) {
            item = ItemRegistration.NETHERITE_BOOK.get();
        } else {
            item = itemStack.getItem();
        }
        
        ItemStack modifiedItemStack = new ItemStack(item);
        if (itemStack.hasDisplayName()) {
            modifiedItemStack.setDisplayName(itemStack.getDisplayName());
        }
        
        callbackInfoReturnable.setReturnValue(modifiedItemStack);
    }
}