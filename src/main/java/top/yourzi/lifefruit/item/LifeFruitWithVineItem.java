package top.yourzi.lifefruit.item;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.ItemHandlerHelper;
import top.yourzi.lifefruit.register.LFItems;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class LifeFruitWithVineItem extends Item {
    public LifeFruitWithVineItem(Properties properties) {
        super(properties);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> pTooltipComponents, TooltipFlag flagIn) {

        pTooltipComponents.add(Component.translatable("tooltip.lifefruit.life_fruit_with_vine"));

        super.appendHoverText(stack,worldIn, pTooltipComponents, flagIn);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        InteractionHand otherHand =
                hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
        BlockPos blockpos = new BlockPos((int) player.getX(), (int) player.getY(), (int) player.getZ());
        ItemStack itemInOtherHand = player.getItemInHand(otherHand);
        ItemStack fruit = new ItemStack(LFItems.LIFE_FRUIT.get()).copy();
        ItemStack vine = new ItemStack(LFItems.VINE.get()).copy();
        if (!itemInOtherHand.is(Items.SHEARS)) {
           {
                return InteractionResultHolder.fail(itemstack);
            }
        } else {
            level.playSound(player, blockpos, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            if (!player.isCreative()) {
                itemstack.shrink(1);
                itemInOtherHand.hurt(1, RandomSource.create(), null);
            }

            Random random = new Random();
            int randomNumber = random.nextInt(4);
            if (randomNumber == 0) {
                ItemHandlerHelper.giveItemToPlayer(player, vine);
            }
            ItemHandlerHelper.giveItemToPlayer(player, fruit);
            return InteractionResultHolder.consume(player.getItemInHand(hand));
        }
    }
}
