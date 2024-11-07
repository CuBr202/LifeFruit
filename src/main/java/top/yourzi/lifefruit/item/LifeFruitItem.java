package top.yourzi.lifefruit.item;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

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

        pTooltipComponents.add(Component.translatable("tooltip.lifefruit.when_eat"));
        pTooltipComponents.add(Component.translatable("tooltip.lifefruit.life_fruit"));

        super.appendHoverText(stack,worldIn, pTooltipComponents, flagIn);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (player.getPersistentData().getDouble("life_health") < player.getMaxHealth()) {
                player.getPersistentData().putDouble("life_health", player.getPersistentData().getDouble("life_health") + 1);
            }
            player.displayClientMessage(Component.literal(("life_health" + entity.getPersistentData().getDouble("life_health"))), true);


        }
        return entity.eat(level, stack);
    }

}
