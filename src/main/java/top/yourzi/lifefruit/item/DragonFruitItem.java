package top.yourzi.lifefruit.item;


import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;

public class LiftFruitItem extends Item {
    public LiftFruitItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder()
                .nutrition(10)
                .saturationMod(0.75f)
                .alwaysEat()
                .build()));
    }


}
