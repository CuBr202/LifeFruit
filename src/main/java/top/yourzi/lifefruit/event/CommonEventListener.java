package top.yourzi.lifefruit.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.yourzi.lifefruit.Lifefruit;
import top.yourzi.lifefruit.network.Channel;
import top.yourzi.lifefruit.network.packet.S2C.LifeHealthPacket;

@Mod.EventBusSubscriber(modid = Lifefruit.MODID)
public class CommonEventListener {

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        Channel.sendToPlayer(new LifeHealthPacket(event.getEntity().getPersistentData().getInt("max_life_health")), (ServerPlayer) event.getEntity());
        int oldLifeHealth = event.getOriginal().getPersistentData().getInt("max_life_health");
        int oldDragonHealth = event.getOriginal().getPersistentData().getInt("max_dragon_health");
        event.getEntity().getPersistentData().putInt("max_life_health",oldLifeHealth);
        event.getEntity().getPersistentData().putInt("present_life_health",oldLifeHealth);
        event.getEntity().getPersistentData().putInt("max_dragon_health",oldDragonHealth);
        event.getEntity().getPersistentData().putInt("present_dragon_health",oldLifeHealth);
    }
}
