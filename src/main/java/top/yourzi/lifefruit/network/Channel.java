package top.yourzi.lifefruit.network;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;
import org.slf4j.Logger;
import top.yourzi.lifefruit.network.packet.S2C.LifeHealthPacket;

public class Channel {

    private static final String PROTOCOL_VERSION = "1";
    private static int id = 0;

    private static final Logger LOGGER = LogUtils.getLogger();

    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("lifefruit", "life_healths"),
            () -> PROTOCOL_VERSION,
            PROTOCOL_VERSION::equals,
            PROTOCOL_VERSION::equals

    );

    public static void register() {
        INSTANCE.messageBuilder(LifeHealthPacket.class, id++, NetworkDirection.PLAY_TO_CLIENT)
                .decoder(LifeHealthPacket::new)
                .encoder(LifeHealthPacket::encode)
                .consumerMainThread(LifeHealthPacket::handle)
                .add();
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player),message);
        LOGGER.info("Sent message: " + message + player.getName());
    }

}
