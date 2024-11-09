package top.yourzi.lifefruit.capability.DragonHeart;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.yourzi.lifefruit.capability.LifeHeart.MaxLifeHeartCapability;

public class MaxDragonHeartCapabilityProvider implements ICapabilityProvider, INBTSerializable {
    private MaxDragonHeartCapability capability;

    public static final Capability<MaxDragonHeartCapability> MAX_DRAGON_HEART_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
    private final LazyOptional<MaxDragonHeartCapability> lazyOptional = LazyOptional.of(() -> capability);

    public MaxDragonHeartCapabilityProvider() {
        this.capability = new MaxDragonHeartCapability();
    }

    public static int clientMaxDragonHeart = 0;

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == MAX_DRAGON_HEART_CAPABILITY) {
            return lazyOptional.cast();
        } else {
            return lazyOptional.empty();
        }
    }

    @Override
    public Tag serializeNBT() {
        var tag = new CompoundTag();
        capability.saveNBTData(tag);
        return tag;
    }

    @Override
    public void deserializeNBT(Tag tag) {
        capability.loadNBTData((CompoundTag) tag);
    }
}
