package me.elementz.enchantingbooks;

import me.elementz.enchantingbooks.blocks.BlockRegistration;
import me.elementz.enchantingbooks.config.Config;
import me.elementz.enchantingbooks.item.CustomBookItem;
import me.elementz.enchantingbooks.item.CustomEnchantedBookItem;
import me.elementz.enchantingbooks.item.ItemRegistration;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.container.EnchantmentContainer;
import net.minecraft.inventory.container.GrindstoneContainer;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EnchantingBooks.ID)
public class EnchantingBooks {

    public static final String ID = "enchantingbooks";
    public static final String VERSION = "@version@";
    private static final Logger LOGGER = LogManager.getLogger();

    public EnchantingBooks() {
        // Load the config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.COMMON_CONFIG);
        Config.loadConfig(Config.COMMON_CONFIG, FMLPaths.CONFIGDIR.get().resolve("enchantingbooks-common.toml"));
        // Register Items
        ItemRegistration.register();
        // Register Blocks
        BlockRegistration.register();
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        LOGGER.info("[Enchanting Books] - Hit Pre Init - " + VERSION);
    }

    @SubscribeEvent
    public void onEnchantmentChange(PlayerContainerEvent.Open event) {
        if (!(event.getContainer() instanceof EnchantmentContainer)) {
            return;
        }

        EnchantmentContainer container = (EnchantmentContainer) event.getContainer();
        Slot slot = container.inventorySlots.get(0);
        Inventory inventory = (Inventory) slot.inventory;
        inventory.addListener(invBasic -> {
            ItemStack itemStack = invBasic.getStackInSlot(0);
            if (!(itemStack.getItem() instanceof CustomBookItem) || !itemStack.isEnchanted()) {
                return;
            }

            Map<Enchantment, Integer> enchantments = getEnchantments(itemStack);
            if (itemStack.getItem() == ItemRegistration.IRON_BOOK.get()) {
                itemStack = new ItemStack(ItemRegistration.ENCHANTED_IRON_BOOK.get(), 1);
            } else if (itemStack.getItem() == ItemRegistration.GOLDEN_BOOK.get()) {
                itemStack = new ItemStack(ItemRegistration.ENCHANTED_GOLDEN_BOOK.get(), 1);
            } else if (itemStack.getItem() == ItemRegistration.DIAMOND_BOOK.get()) {
                itemStack = new ItemStack(ItemRegistration.ENCHANTED_DIAMOND_BOOK.get(), 1);
            } else if (itemStack.getItem() == ItemRegistration.NETHERITE_BOOK.get()) {
                itemStack = new ItemStack(ItemRegistration.ENCHANTED_NETHERITE_BOOK.get(), 1);
            }
            setEnchantments(enchantments, itemStack);
            invBasic.setInventorySlotContents(0, itemStack);
        });
    }

    @SubscribeEvent
    public void onGrindstoneChange(PlayerContainerEvent.Open event) {
        if (!(event.getContainer() instanceof GrindstoneContainer)) {
            return;
        }
    }

    @SubscribeEvent
    public void onAnvilChange(AnvilUpdateEvent event) {
        ItemStack itemStackLeft = event.getLeft();
        ItemStack itemStackRight = event.getRight();
        int i = 0;
        int j = 0;
        int k = 0;
        boolean flag = false;
        if ((itemStackRight.getItem() instanceof CustomEnchantedBookItem || itemStackLeft.getItem() instanceof CustomEnchantedBookItem)) {

            Map<Enchantment, Integer> enchantmentsLeft = getEnchantments(itemStackLeft);
            Map<Enchantment, Integer> enchantmentsRight = getEnchantments(itemStackRight);
            if (enchantmentsRight.isEmpty()) {
                return;
            }

            if (itemStackRight.getItem() instanceof EnchantedBookItem && !enchantmentsRight.isEmpty()) {
                flag = true;
            } else if (itemStackRight.getItem() instanceof CustomEnchantedBookItem && !enchantmentsRight.isEmpty()) {
                flag = true;
            } else {
                flag = false;
            }
            //flag = itemStackRight.getItem() instanceof CustomEnchantedBookItem && !enchantmentsRight.isEmpty();
            ItemStack itemStackOut = itemStackLeft.copy();
            boolean flag2 = false;
            boolean flag3 = false;

            j = j + itemStackOut.getRepairCost() + (itemStackRight.isEmpty() ? 0 : itemStackRight.getRepairCost());

            for (Enchantment enchantment1 : enchantmentsRight.keySet()) {
                if (enchantment1 != null) {
                    int i2 = enchantmentsLeft.getOrDefault(enchantment1, 0);
                    int j2 = enchantmentsRight.get(enchantment1);
                    j2 = i2 == j2 ? j2 + 1 : Math.max(j2, i2);
                    boolean flag1 = enchantment1.canApply(itemStackOut);
                    if (event.getPlayer().isCreative() || itemStackLeft.getItem() instanceof CustomEnchantedBookItem || itemStackLeft.getItem() instanceof EnchantedBookItem) {
                        flag1 = true;
                    }

                    for (Enchantment enchantment : enchantmentsLeft.keySet()) {
                        if (enchantment != enchantment1 && !enchantment1.isCompatibleWith(enchantment)) {
                            flag1 = false;
                            ++i;
                        }
                    }

                    if (!flag1) {
                        flag3 = true;
                    } else {
                        flag2 = true;
                        if (j2 > enchantment1.getMaxLevel()) {
                            j2 = enchantment1.getMaxLevel();
                        }

                        enchantmentsLeft.put(enchantment1, j2);
                        int k3 = 0;
                        switch (enchantment1.getRarity()) {
                            case COMMON:
                                k3 = 1;
                                break;
                            case UNCOMMON:
                                k3 = 2;
                                break;
                            case RARE:
                                k3 = 4;
                                break;
                            case VERY_RARE:
                                k3 = 8;
                        }

                        if (flag) {
                            k3 = Math.max(1, k3 / 2);
                        }

                        i += k3 * j2;
                        if (itemStackOut.getCount() > 1) {
                            i = 40;
                        }
                    }
                }
            }
            if (flag3 && !flag2) {
                return;
            }

            if (StringUtils.isBlank(event.getName())) {
                if (itemStackOut.hasDisplayName()) {
                    k = 1;
                    i += k;
                    itemStackOut.clearCustomName();
                }
            } else if (!event.getName().equals(itemStackOut.getDisplayName().getString())) {
                k = 1;
                i += k;
                itemStackOut.setDisplayName(new StringTextComponent(event.getName()));
            }


            if (flag && !itemStackOut.isBookEnchantable(itemStackRight)) itemStackOut = ItemStack.EMPTY;

            event.setCost(j + i);
            if (i <= 0) {
                itemStackOut = ItemStack.EMPTY;
            }

            if (k == i && k > 0 && event.getCost() >= 40) {
                event.setCost(39);
            }

            if (event.getCost() >= 40 && !event.getPlayer().isCreative()) {
                itemStackOut = ItemStack.EMPTY;
            }

            if (!itemStackOut.isEmpty()) {
                int k2 = itemStackOut.getRepairCost();
                if (!itemStackRight.isEmpty() && k2 < itemStackRight.getRepairCost()) {
                    k2 = itemStackRight.getRepairCost();
                }

                if (k != i || k == 0) {
                    k2 = getNewRepairCost(k2);
                }

                itemStackOut.setRepairCost(k2);
                setEnchantments(enchantmentsLeft, itemStackOut);
            }
            event.setOutput(itemStackOut);
        }
    }

    public static int getNewRepairCost(int oldRepairCost) {
        return oldRepairCost * 2 + 1;
    }

    public static void setEnchantments(Map<Enchantment, Integer> enchMap, ItemStack stack) {
        ListNBT listnbt = new ListNBT();

        for (Map.Entry<Enchantment, Integer> entry : enchMap.entrySet()) {
            Enchantment enchantment = entry.getKey();
            if (enchantment != null) {
                int i = entry.getValue();
                CompoundNBT compoundnbt = new CompoundNBT();
                compoundnbt.putString("id", String.valueOf((Object) Registry.ENCHANTMENT.getKey(enchantment)));
                compoundnbt.putShort("lvl", (short) i);
                listnbt.add(compoundnbt);
                if (stack.getItem() instanceof CustomEnchantedBookItem) {
                    CustomEnchantedBookItem.addEnchantment(stack, new EnchantmentData(enchantment, i));
                } else if (stack.getItem() instanceof EnchantedBookItem) {
                    EnchantedBookItem.addEnchantment(stack, new EnchantmentData(enchantment, i));
                }
            }
        }

        if (listnbt.isEmpty()) {
            stack.removeChildTag("Enchantments");
        } else if (!(stack.getItem() instanceof CustomEnchantedBookItem) && !(stack.getItem() instanceof EnchantedBookItem)) {
            stack.setTagInfo("Enchantments", listnbt);
        }
    }

    public static Map<Enchantment, Integer> getEnchantments(ItemStack stack) {
        if (stack.getItem() instanceof CustomEnchantedBookItem) {
            ListNBT listnbt = stack.getItem() instanceof CustomEnchantedBookItem ? CustomEnchantedBookItem.getEnchantments(stack) : stack.getEnchantmentTagList();
            return EnchantmentHelper.deserializeEnchantments(listnbt);
        } else {
            ListNBT listnbt = stack.getItem() instanceof EnchantedBookItem ? EnchantedBookItem.getEnchantments(stack) : stack.getEnchantmentTagList();
            return EnchantmentHelper.deserializeEnchantments(listnbt);
        }
    }

}
