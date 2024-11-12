package top.yourzi.lifefruit.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.ItemHandlerHelper;
import top.yourzi.lifefruit.register.LFItems;
import top.yourzi.lifefruit.sound.LFSounds;

public class EnderDragonFruitCropBlock extends CropBlock {
    private static final int MAX_AGE = 3;
    private static final int MaxGrowthTime = (int) (1200 / 68.27);
    private static final IntegerProperty AGE = BlockStateProperties.AGE_3;
    private static final IntegerProperty GrowthTime = IntegerProperty.create("growth_time", 0, MaxGrowthTime);
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 6.0D, 12.0D),
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 10.0D, 12.0D),
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 12.0D, 12.0D),
            Block.box(4.0D, 0.0D, 4.0D, 12.0D, 14.0D, 12.0D)
    };

    public EnderDragonFruitCropBlock(Properties properties) {
        super(BlockBehaviour.Properties.of().noOcclusion().noCollission().sound(SoundType.CROP));
        this.registerDefaultState(this.defaultBlockState().setValue(GrowthTime, 0));
    }


    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        super.use(state, level, pos, player, hand, hit);
        ItemStack itemstack = player.getItemInHand(hand);
        if (!level.isClientSide && itemstack.is(Items.DRAGON_BREATH)) {
            player.swing(hand, true);
            level.setBlockAndUpdate(pos, state.setValue(GrowthTime, 17));
            level.playSeededSound(null, pos.getX(), pos.getY(), pos.getZ(),
                    LFSounds.WATER_SOUND.get(), SoundSource.BLOCKS, 1f, 1f, 0);
            if (!player.isCreative()) {
                itemstack.shrink(1);
                ItemHandlerHelper.giveItemToPlayer(player, new ItemStack(Items.GLASS_BOTTLE));
            }
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.CONSUME;
    }




    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, RandomSource source) {
        if (getGrowthTime(state) > 0) {
            for(int $$4 = 0; $$4 < 2; ++$$4) {
                int $$5 = source.nextInt(2) * 2 - 1;
                int $$6 = source.nextInt(2) * 2 - 1;
                double $$7 = (double)pos.getX() + 0.5 + 0.25 * (double)$$5;
                double $$8 = ((float)pos.getY() + source.nextFloat() - 0.25);
                double $$9 = (double)pos.getZ() + 0.5 + 0.25 * (double)$$6;
                double $$10 = (double)(source.nextFloat() * (float)$$5) / 70;
                double $$11 = ((double)source.nextFloat() - 0.5) * 0.125 / 20;
                double $$12 = (double)(source.nextFloat() * (float)$$6) / 70;
                level.addParticle(ParticleTypes.DRAGON_BREATH, $$7, $$8, $$9, $$10, $$11, $$12);
            }
        }
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int age = this.getAge(state);
        int time = this.getGrowthTime(state);

        if ((level.isAreaLoaded(pos, 1)
                && level.dimension().equals(Level.END)
                && getGrowthTime(state) > 0)
                && (level.getRawBrightness(pos, 0) >= 9)
                && (age < this.getMaxAge())
                &&  (ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(15) == 0))
        )
            {
                if (getAge(state) == 2){
                    level.setBlock(pos, this.getStateForGrowth(0,age + 1), 2);
                } else {
                    level.setBlock(pos, this.getStateForGrowth(time - 1, age + 1), 2);
                    ForgeHooks.onCropsGrowPost(level, pos, state);
                }
        } else if (time <= 0) {
            level.setBlock(pos, this.getStateForGrowth(0, age), 2);
        }else
        {
            level.setBlock(pos, this.getStateForGrowth(time - 1, age), 2);
        }

    }

    public BlockState getStateForGrowth(int time, int age) {
        return (BlockState)this.defaultBlockState().setValue(this.getGrowthTimeProperty(), time).setValue(this.getAgeProperty(), age);
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader pLevel, BlockPos pPos, BlockState pState, boolean pIsClient) {
        return false;
    }


    @Override
    protected int getBonemealAgeIncrease(Level level) {
        return Mth.nextInt(level.random, 1, 1);
    }

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter p_52298_, BlockPos p_52299_, CollisionContext p_52300_) {
        return SHAPE_BY_AGE[this.getAge(pState)];
    }

    @Override
    public int getMaxAge() {
        return MAX_AGE;
    }

    public int getGrowthTime(BlockState pState) {
        return pState.getValue(this.getGrowthTimeProperty());
    }



    @Override
    protected boolean mayPlaceOn(BlockState p_52302_, BlockGetter p_52303_, BlockPos p_52304_) {
        return p_52302_.is(Blocks.OBSIDIAN);
    }

    @Override
    public boolean canSurvive(BlockState pState, LevelReader pLevel, BlockPos pPos) {
        BlockPos blockpos = pPos.below();
        if (pState.getBlock() == this) {
            return pLevel.getBlockState(blockpos).canSustainPlant(pLevel, blockpos, Direction.UP, this);
        }
        return this.mayPlaceOn(pLevel.getBlockState(blockpos), pLevel, blockpos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(AGE);
        pBuilder.add(GrowthTime);
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }


    public IntegerProperty getGrowthTimeProperty() {
        return GrowthTime;
    }



    @Override
    protected ItemLike getBaseSeedId() {
        return LFItems.DRAGON_FRUIT.get();
    }
}
