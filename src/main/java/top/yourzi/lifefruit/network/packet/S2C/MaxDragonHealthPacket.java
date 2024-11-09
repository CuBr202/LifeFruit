package top.yourzi.lifefruit.network.packet.S2C;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import top.yourzi.lifefruit.capability.DragonHeart.MaxDragonHeartCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.MaxLifeHeartCapabilityProvider;

import java.util.function.Supplier;

public class MaxDragonHealthPacket {
    private final int maxDragonHealth;

    public MaxDragonHealthPacket(int lifeHealth) {
        this.maxDragonHealth = lifeHealth;
    }

    public MaxDragonHealthPacket(FriendlyByteBuf buf) {
        maxDragonHealth = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(maxDragonHealth);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        var ctx = supplier.get();
        MaxDragonHeartCapabilityProvider.clientMaxDragonHeart = maxDragonHealth;
        ctx.setPacketHandled(true);
        return true;
    }
}

