package top.yourzi.lifefruit.register;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import top.yourzi.lifefruit.Lifefruit;

public class LFCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Lifefruit.MODID);

    public static final RegistryObject<CreativeModeTab> BASE_TAB = CREATIVE_MODE_TABS.register("base",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(LFItems.LIFE_FRUIT.get()))
                    .title(Component.translatable("lifefruit.creativetab.base"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(LFBlocks.LIFE_FRUIT_PLANT.get());
                        pOutput.accept(LFItems.LIFE_FRUIT_WITH_VINE.get());
                        pOutput.accept(LFItems.VINE.get());
                        pOutput.accept(LFItems.LIFE_FRUIT.get());
                        pOutput.accept(LFItems.DRAGON_FRUIT.get());

                    })
                    .build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
};
