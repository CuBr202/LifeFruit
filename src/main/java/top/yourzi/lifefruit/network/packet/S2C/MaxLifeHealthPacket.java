package top.yourzi.lifefruit.network.packet.S2C;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import top.yourzi.lifefruit.capability.LifeHeart.MaxLifeHeartCapabilityProvider;

import java.util.function.Supplier;

public class MaxLifeHealthPacket {
    private final int maxlifeHealth;

    public MaxLifeHealthPacket(int lifeHealth) {
        this.maxlifeHealth = lifeHealth;
    }

    public MaxLifeHealthPacket(FriendlyByteBuf buf) {
        maxlifeHealth = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(maxlifeHealth);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        var ctx = supplier.get();
        MaxLifeHeartCapabilityProvider.clientMaxLifeHeart = maxlifeHealth;
        ctx.setPacketHandled(true);
        return true;
    }
}

