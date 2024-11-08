package top.yourzi.lifefruit.network.packet.S2C;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.Objects;
import java.util.function.Supplier;

public class LifeHealthPacket {
    private final int lifeHealth;

    public LifeHealthPacket(int lifeHealth) {
        this.lifeHealth = lifeHealth;
    }

    public LifeHealthPacket(FriendlyByteBuf buf) {
        lifeHealth = buf.readInt();
    }

    public void encode(FriendlyByteBuf buf) {
        buf.writeInt(lifeHealth);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        var ctx = supplier.get();
        CompoundTag tag = new CompoundTag();
        tag.putInt("life_health", lifeHealth);
        ctx.setPacketHandled(true);
        return true;
    }
}

