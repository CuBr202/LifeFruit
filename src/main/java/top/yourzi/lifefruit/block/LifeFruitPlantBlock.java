package top.yourzi.lifefruit.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public class LifeFruitPlantBlock extends FlowerBlock {
    public LifeFruitPlantBlock(Properties properties) {
        super(MobEffects.HEALTH_BOOST, 200, BlockBehaviour.Properties.of()
                .lightLevel(s -> 14)
                .mapColor(MapColor.PLANT)
                .sound(SoundType.GRASS)
                .noCollission()
                .offsetType(BlockBehaviour.OffsetType.XZ)
                .pushReaction(PushReaction.DESTROY));
    }

    @Override
    public boolean mayPlaceOn(BlockState groundState, BlockGetter worldIn, BlockPos pos) {
        return groundState.is(Blocks.MOSS_BLOCK);
    }
}
