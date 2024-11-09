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

public class CurrentDragonHeartCapabilityProvider implements ICapabilityProvider, INBTSerializable {
    private CurrentDragonHeartCapability capability;

    public static final Capability<CurrentDragonHeartCapability> CURRENT_DRAGON_HEART_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });
    private final LazyOptional<CurrentDragonHeartCapability> lazyOptional = LazyOptional.of(() -> capability);

    public CurrentDragonHeartCapabilityProvider() {
        this.capability = new CurrentDragonHeartCapability();
    }

    public static int clientCurrentDragonHeart = 0;

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> capability, @Nullable Direction direction) {
        if (capability == CURRENT_DRAGON_HEART_CAPABILITY) {
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