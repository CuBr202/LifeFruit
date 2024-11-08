package top.yourzi.lifefruit.event;


import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import top.yourzi.lifefruit.Lifefruit;
import top.yourzi.lifefruit.command.LFCommand;
import top.yourzi.lifefruit.network.Channel;
import top.yourzi.lifefruit.network.packet.S2C.LifeHealthPacket;

import java.util.Objects;

@Mod.EventBusSubscriber(modid = Lifefruit.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventListener {

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        LFCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {

                Channel.sendToPlayer(new LifeHealthPacket(player.getPersistentData().getInt("max_life_health")), player);

               int playerMaxLifeHeart = event.getEntity().getPersistentData().getInt("max_life_health");
                event.getEntity().getPersistentData().putInt("max_life_health", playerMaxLifeHeart);
                int playerMaxDragonHeart = event.getEntity().getPersistentData().getInt("max_dragon_health");
                event.getEntity().getPersistentData().putInt("max_dragon_health", playerMaxDragonHeart);



            }
        }
    }

}
