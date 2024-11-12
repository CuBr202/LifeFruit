package top.yourzi.lifefruit;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.slf4j.Logger;
import top.yourzi.lifefruit.capability.DragonHeart.CurrentDragonHeartCapabilityProvider;
import top.yourzi.lifefruit.capability.DragonHeart.MaxDragonHeartCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.CurrentLifeHealthCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.MaxLifeHeartCapabilityProvider;
import top.yourzi.lifefruit.event.ForgeEventListener;
import top.yourzi.lifefruit.event.ModEventListener;
import top.yourzi.lifefruit.register.LFBlocks;
import top.yourzi.lifefruit.register.LFCreativeTab;
import top.yourzi.lifefruit.register.LFItems;
import top.yourzi.lifefruit.sound.LFSounds;
import top.yourzi.lifefruit.world.LFLootAdditions;



@Mod(Lifefruit.MODID)
public class Lifefruit {


    public static final String MODID = "lifefruit";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, Lifefruit.MODID);

    public Lifefruit() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        modEventBus.addListener(this::commonSetup);

        MinecraftForge.EVENT_BUS.register(this);

        LFItems.register(modEventBus);
        LFBlocks.register(modEventBus);
        LFCreativeTab.register(modEventBus);
        LFSounds.register(modEventBus);
        LOOT_MODIFIERS.register("loot_additions", LFLootAdditions.CODEC);
        LOOT_MODIFIERS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(new ForgeEventListener());
        MinecraftForge.EVENT_BUS.register(new ModEventListener());
        MinecraftForge.EVENT_BUS.addGenericListener(Entity.class, this::attachedCapabilities);

        modEventBus.addListener(this::init);

        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }


    private void commonSetup(final FMLCommonSetupEvent event) {}

    public void init(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            registerCompostables();
        });}

    public static void registerCompostables() {
        ComposterBlock.COMPOSTABLES.put(LFItems.DRAGON_FRUIT.get(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(LFItems.LIFE_FRUIT.get(), 0.5F);
        ComposterBlock.COMPOSTABLES.put(LFItems.VINE.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(LFItems.LIFE_FRUIT_WITH_VINE.get(), 0.6F);
        ComposterBlock.COMPOSTABLES.put(LFBlocks.LIFE_FRUIT_PLANT.get(), 0.6F);
    }



    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {}


    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {}
    }

    public void attachedCapabilities(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player player) {
            if (!player.getCapability(MaxLifeHeartCapabilityProvider.MAX_LIFE_HEART_CAPABILITY).isPresent()){
                    event.addCapability(new ResourceLocation(MODID, "max_life_heart"), new MaxLifeHeartCapabilityProvider());
            }
            if (!player.getCapability(CurrentLifeHealthCapabilityProvider.CURRENT_LIFE_HEALTH_CAPABILITY).isPresent()){
                  event.addCapability(new ResourceLocation(MODID, "current_life_heart"), new CurrentLifeHealthCapabilityProvider());
            }
            if (!player.getCapability(MaxDragonHeartCapabilityProvider.MAX_DRAGON_HEART_CAPABILITY).isPresent()){
                event.addCapability(new ResourceLocation(MODID, "max_dragon_heart"), new MaxDragonHeartCapabilityProvider());
            }
            if (!player.getCapability(CurrentDragonHeartCapabilityProvider.CURRENT_DRAGON_HEART_CAPABILITY).isPresent()){
                event.addCapability(new ResourceLocation(MODID, "current_dragon_heart"), new CurrentDragonHeartCapabilityProvider());
            }
        }
    }


}
