package top.yourzi.lifefruit;


import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class CommonEventHandler {
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        double oldLifeHealth = event.getOriginal().getPersistentData().getDouble("life_health");
        double oldDragonHealth = event.getOriginal().getPersistentData().getDouble("dragon_health");
        event.getEntity().getPersistentData().putDouble("life_health",oldLifeHealth);
        event.getEntity().getPersistentData().putDouble("dragon_health",oldDragonHealth);
    }
}
