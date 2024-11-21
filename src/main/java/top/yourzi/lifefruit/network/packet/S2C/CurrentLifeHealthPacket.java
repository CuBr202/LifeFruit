package top.yourzi.lifefruit.network.packet.S2C;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import top.yourzi.lifefruit.capability.LifeHeart.CurrentLifeHealthCapabilityProvider;

import java.util.function.Supplier;

public class CurrentLifeHealthPacket {
    private final int currentlifeHealth;

    public CurrentLifeHealthPacket(int lifeHealth) {
        this.currentlifeHealth = lifeHealth;
    }

    public CurrentLifeHealthPacket(FriendlyByteBuf buf) {
        currentlifeHealth = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(currentlifeHealth);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        var ctx = supplier.get();
        CurrentLifeHealthCapabilityProvider.clientCurrentLifeHeart = currentlifeHealth;
        ctx.setPacketHandled(true);
        return true;
    }
}