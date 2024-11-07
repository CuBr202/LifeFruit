package top.yourzi.lifefruit;


import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.yourzi.lifefruit.command.LFCommand;

@Mod.EventBusSubscriber(modid = Lifefruit.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEventHandler {
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        int oldLifeHealth = event.getOriginal().getPersistentData().getInt("life_health");
        int oldDragonHealth = event.getOriginal().getPersistentData().getInt("dragon_health");
        event.getEntity().getPersistentData().putInt("life_health",oldLifeHealth);
        event.getEntity().getPersistentData().putInt("dragon_health",oldDragonHealth);
    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        LFCommand.register(event.getDispatcher());
    }
}
