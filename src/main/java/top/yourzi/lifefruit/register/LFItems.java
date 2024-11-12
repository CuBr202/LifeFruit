package top.yourzi.lifefruit.register;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.yourzi.lifefruit.Lifefruit;
import top.yourzi.lifefruit.item.DragonFruitItem;
import top.yourzi.lifefruit.item.LifeFruitItem;
import top.yourzi.lifefruit.item.LifeFruitWithVineItem;
import top.yourzi.lifefruit.item.VineItem;

public class LFItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Lifefruit.MODID);

    public static final RegistryObject<Item> LIFE_FRUIT = ITEMS.register("life_fruit",
            () -> new LifeFruitItem(new Item.Properties()));

    public static final RegistryObject<Item> DRAGON_FRUIT = ITEMS.register("ender_dragon_fruit",
            () -> new DragonFruitItem(LFBlocks.ENDER_DRAGON_FRUIT_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> LIFE_FRUIT_WITH_VINE = ITEMS.register("life_fruit_with_vine",
            () -> new LifeFruitWithVineItem(new Item.Properties()));

    public static final RegistryObject<Item> VINE = ITEMS.register("vine",
            () -> new VineItem(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
