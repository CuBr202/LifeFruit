package top.yourzi.lifefruit.event;

import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import top.yourzi.lifefruit.Lifefruit;
import top.yourzi.lifefruit.capability.DragonHeart.CurrentDragonHeartCapabilityProvider;
import top.yourzi.lifefruit.capability.DragonHeart.MaxDragonHeartCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.CurrentLifeHealthCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.MaxLifeHeartCapabilityProvider;
import top.yourzi.lifefruit.client.gui.ExtraHealthOverlay;
import top.yourzi.lifefruit.network.Channel;

@Mod.EventBusSubscriber(modid = Lifefruit.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventListener {


    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("extra_health", ExtraHealthOverlay.EXTRA_HEALTH_HUD);
    }


    @SubscribeEvent
    public static void registerChannel(FMLCommonSetupEvent event) {
        Channel.register();
    }

    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(MaxLifeHeartCapabilityProvider.class);
        event.register(CurrentLifeHealthCapabilityProvider.class);
        event.register(MaxDragonHeartCapabilityProvider.class);
        event.register(CurrentDragonHeartCapabilityProvider.class);
    }
}
