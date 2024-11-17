package top.yourzi.lifefruit.item;


import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import top.yourzi.lifefruit.register.LFBlocks;

import javax.annotation.Nullable;
import java.util.List;

import static top.yourzi.lifefruit.capability.DragonHeart.MaxDragonHeartCapabilityProvider.clientMaxDragonHeart;
import static top.yourzi.lifefruit.capability.LifeHeart.MaxLifeHeartCapabilityProvider.clientMaxLifeHeart;
import static top.yourzi.lifefruit.item.LifeFruitItem.maxHealth;

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


        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.lifefruit.pressed_shift"));
            pTooltipComponents.add(Component.translatable("tooltip.lifefruit.ender_dragon_fruit_1"));
            pTooltipComponents.add(Component.translatable("tooltip.lifefruit.ender_dragon_fruit_2"));
            pTooltipComponents.add(Component.literal(""));
            pTooltipComponents.add(Component.translatable("tooltip.lifefruit.when_eat"));
            pTooltipComponents.add(Component.translatable("tooltip.lifefruit.ender_dragon_fruit"));
            pTooltipComponents.add(Component.literal(Component.translatable("tooltip.lifefruit.had_eat").getString() + "§l§5" + Math.min(clientMaxDragonHeart, Math.min(maxHealth,clientMaxLifeHeart))/2));
            if( Math.min(clientMaxDragonHeart, Math.min(maxHealth,clientMaxLifeHeart)) != clientMaxDragonHeart){
                pTooltipComponents.add(Component.literal(Component.translatable("tooltip.lifefruit.had_eat_max").getString() + "§l§5" + clientMaxDragonHeart/2));
            }
        }else {
            pTooltipComponents.add(Component.translatable("tooltip.lifefruit.press_shift"));
        }

        super.appendHoverText(stack,worldIn, pTooltipComponents, flagIn);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        return entity.eat(level, stack);
    }
}
