package top.yourzi.lifefruit.network.packet.S2C;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import top.yourzi.lifefruit.capability.DragonHeart.CurrentDragonHeartCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.CurrentLifeHealthCapabilityProvider;

import java.util.function.Supplier;

public class CurrentDragonHealthPacket {
    private final int currentdragonHealth;

    public CurrentDragonHealthPacket(int lifeHealth) {
        this.currentdragonHealth = lifeHealth;
    }

    public CurrentDragonHealthPacket(FriendlyByteBuf buf) {
        currentdragonHealth = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(currentdragonHealth);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        var ctx = supplier.get();
        CurrentDragonHeartCapabilityProvider.clientCurrentDragonHeart = currentdragonHealth;
        ctx.setPacketHandled(true);
        return true;
    }
}
