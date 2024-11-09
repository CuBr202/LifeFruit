package top.yourzi.lifefruit.network;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;
import top.yourzi.lifefruit.network.packet.S2C.CurrentDragonHealthPacket;
import top.yourzi.lifefruit.network.packet.S2C.CurrentLifeHealthPacket;
import top.yourzi.lifefruit.network.packet.S2C.MaxDragonHealthPacket;
import top.yourzi.lifefruit.network.packet.S2C.MaxLifeHealthPacket;

public class Channel {

    private static final String PROTOCOL_VERSION = "1";
    private static int id = 0;

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("lifefruit", "main"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals

    );

    public static void register() {
        INSTANCE.messageBuilder(MaxLifeHealthPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MaxLifeHealthPacket::new)
                .encoder(MaxLifeHealthPacket::encode)
                .consumerMainThread(MaxLifeHealthPacket::handle)
                .add();
        INSTANCE.messageBuilder(CurrentLifeHealthPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CurrentLifeHealthPacket::new)
                .encoder(CurrentLifeHealthPacket::encode)
                .consumerMainThread(CurrentLifeHealthPacket::handle)
                .add();
        INSTANCE.messageBuilder(MaxDragonHealthPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(MaxDragonHealthPacket::new)
                .encoder(MaxDragonHealthPacket::encode)
                .consumerMainThread(MaxDragonHealthPacket::handle)
                .add();
        INSTANCE.messageBuilder(CurrentDragonHealthPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(CurrentDragonHealthPacket::new)
                .encoder(CurrentDragonHealthPacket::encode)
                .consumerMainThread(CurrentDragonHealthPacket::handle)
                .add();
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player),message);
    }

}
