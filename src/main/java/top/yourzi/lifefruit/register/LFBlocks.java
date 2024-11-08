package top.yourzi.lifefruit.register;

import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import top.yourzi.lifefruit.Lifefruit;
import top.yourzi.lifefruit.block.EnderDragonFruitCropBlock;
import top.yourzi.lifefruit.block.LifeFruitVineCropBlock;

import java.util.function.Supplier;

public class LFBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Lifefruit.MODID);



    public static final RegistryObject<Block> LIFE_FRUIT_VINE_CROP = BLOCKS.register("life_fruit_vine_crop",
            () -> new LifeFruitVineCropBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> ENDER_DRAGON_FRUIT_CROP = BLOCKS.register("ender_dragon_fruit_crop",
            () -> new EnderDragonFruitCropBlock(BlockBehaviour.Properties.of()));

    public static final RegistryObject<Block> LIFE_FRUIT_PLANT = registerBlock("life_fruit_plant",
            () -> new FlowerBlock(() -> MobEffects.HEALTH_BOOST, 200, BlockBehaviour.Properties.of()
                    .lightLevel(s -> 13)
                    .mapColor(MapColor.PLANT)
                    .sound(SoundType.GRASS)
                    .noCollission()
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY))
    );


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return LFItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
