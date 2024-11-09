package top.yourzi.lifefruit.network.packet.S2C;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import top.yourzi.lifefruit.capability.LifeHeart.CurrentLifeHealthCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.MaxLifeHeartCapabilityProvider;

import java.util.function.Supplier;

public class CurrentLifeHealthPacket {
    private final int currentlifeHealth;

    public CurrentLifeHealthPacket(int lifeHealth) {
        this.currentlifeHealth = lifeHealth;
        CurrentLifeHealthCapabilityProvider.clientCurrentLifeHeart = currentlifeHealth;
    }

    public CurrentLifeHealthPacket(FriendlyByteBuf buf) {
        currentlifeHealth = buf.readInt();
        CurrentLifeHealthCapabilityProvider.clientCurrentLifeHeart = currentlifeHealth;
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(currentlifeHealth);
        CurrentLifeHealthCapabilityProvider.clientCurrentLifeHeart = currentlifeHealth;
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        var ctx = supplier.get();
        CurrentLifeHealthCapabilityProvider.clientCurrentLifeHeart = currentlifeHealth;
        ctx.setPacketHandled(true);
        return true;
    }
}