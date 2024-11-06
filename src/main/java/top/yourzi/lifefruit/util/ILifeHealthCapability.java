package top.yourzi.lifefruit.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.util.INBTSerializable;

public interface ILifeHealthCapability extends INBTSerializable<CompoundTag> {
    int getLifeHealth();
}
