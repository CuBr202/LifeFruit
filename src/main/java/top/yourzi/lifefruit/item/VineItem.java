package top.yourzi.lifefruit.item;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.yourzi.lifefruit.register.LFBlocks;

import javax.annotation.Nullable;
import java.util.List;

public class VineItem extends ItemNameBlockItem {
    public VineItem(Properties properties) {
        super(LFBlocks.LIFE_FRUIT_VINE_CROP.get(), new Item.Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level worldIn, List<Component> pTooltipComponents, TooltipFlag flagIn) {

        if (Screen.hasShiftDown()) {
            pTooltipComponents.add(Component.translatable("tooltip.lifefruit.pressed_shift"));
            pTooltipComponents.add(Component.translatable("tooltip.lifefruit.vine_1"));
            pTooltipComponents.add(Component.translatable("tooltip.lifefruit.vine_2"));
        }else {
            pTooltipComponents.add(Component.translatable("tooltip.lifefruit.press_shift"));
        }



        super.appendHoverText(stack,worldIn, pTooltipComponents, flagIn);
    }
}
