package me.elementz.enchantingbooks.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

public class CustomBlock extends Block {


    private final float blockEnchantPower;

    public CustomBlock(Properties properties, float blockEnchantPower) {
        super(properties);
        this.blockEnchantPower = blockEnchantPower;
    }

    @Override
    public float getEnchantPowerBonus(BlockState state, IWorldReader world, BlockPos pos) {
        return blockEnchantPower;
    }
}