package me.elementz.enchantingbooks.mixin.core.inventory.container;

import me.elementz.enchantingbooks.item.CustomEnchantedBookItem;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net/minecraft/inventory/container/GrindstoneContainer$2")
public abstract class GrindstoneContainer_2Mixin {
    
    @Inject(
            method = "isItemValid(Lnet/minecraft/item/ItemStack;)Z",
            at = @At(
                    value = "HEAD"
            ),
            cancellable = true
    )
    private void onIsItemValid(ItemStack itemStack, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (itemStack.getItem() instanceof CustomEnchantedBookItem) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }
}