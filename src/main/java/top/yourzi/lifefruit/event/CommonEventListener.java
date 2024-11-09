package top.yourzi.lifefruit.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.yourzi.lifefruit.Lifefruit;
import top.yourzi.lifefruit.capability.LifeHeart.CurrentLifeHealthCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.MaxLifeHeartCapabilityProvider;
import top.yourzi.lifefruit.network.Channel;
import top.yourzi.lifefruit.network.packet.S2C.CurrentLifeHealthPacket;
import top.yourzi.lifefruit.network.packet.S2C.MaxLifeHealthPacket;

@Mod.EventBusSubscriber(modid = Lifefruit.MODID)
public class CommonEventListener {


}
