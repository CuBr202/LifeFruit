package top.yourzi.lifefruit.event;

import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import top.yourzi.lifefruit.Lifefruit;
import top.yourzi.lifefruit.client.gui.LifeHealthOverlay;
import top.yourzi.lifefruit.network.Channel;

@Mod.EventBusSubscriber(modid = Lifefruit.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEventListener {


    @SubscribeEvent
    public static void registerOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("life_health", LifeHealthOverlay.LIFE_HEALTH_HUD);
        //event.registerAbove("dragon_health","life_health", );
    }

    @SubscribeEvent
    public static void registerChannel(FMLCommonSetupEvent event) {
        Channel.register();
    }
}
