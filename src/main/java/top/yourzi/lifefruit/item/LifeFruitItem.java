package top.yourzi.lifefruit.item;


import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.yourzi.lifefruit.capability.LifeHeart.CurrentLifeHealthCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.MaxLifeHeartCapabilityProvider;

import javax.annotation.Nullable;
import java.util.List;

public class LifeFruitItem extends Item {
    public LifeFruitItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder()
                .nutrition(10)
                .saturationMod(0.75f)
                .alwaysEat()
                .build()));
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> pTooltipComponents, TooltipFlag flagIn) {

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.lifefruit.pressed_shift"));
            pTooltipComponents.add(Component.translatable("tooltip.lifefruit.when_eat"));
            pTooltipComponents.add(Component.translatable("tooltip.lifefruit.life_fruit"));
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
