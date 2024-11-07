package top.yourzi.lifefruit.item;


import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import top.yourzi.lifefruit.LFBlocks;

import javax.annotation.Nullable;
import java.util.List;

public class DragonFruitItem extends ItemNameBlockItem {


    public DragonFruitItem(Block block, Properties properties) {
        super(LFBlocks.ENDER_DRAGON_FRUIT_CROP.get(), properties.food(new FoodProperties.Builder()
                .nutrition(10)
                .saturationMod(0.75f)
                .alwaysEat()
                .build()));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> pTooltipComponents, TooltipFlag flagIn) {

        pTooltipComponents.add(Component.translatable("tooltip.lifefruit.when_eat"));
        pTooltipComponents.add(Component.translatable("tooltip.lifefruit.ender_dragon_fruit"));

        super.appendHoverText(stack,worldIn, pTooltipComponents, flagIn);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (player.getPersistentData().getInt("dragon_health") < player.getPersistentData().getDouble("life_health")) {
                player.getPersistentData().putInt("dragon_health", player.getPersistentData().getInt("dragon_health") + 1);
            }
            player.displayClientMessage(Component.literal(("dragon_health" + entity.getPersistentData().getInt("dragon_health"))), true);


        }
        return entity.eat(level, stack);
    }
}
