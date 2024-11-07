package top.yourzi.lifefruit.util;

import net.minecraft.nbt.CompoundTag;

public class LifeHealthCapability implements ILifeHealthCapability {
    private int health;

    @Override
    public int getLifeHealth() {
        return this.health;
    }

    public void addLifeHealth(int health) {
        this.health += health;
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag CompoundTag = new CompoundTag();
        CompoundTag.putInt("life_health", this.health);
        return CompoundTag;
    }

    @Override
    public void deserializeNBT(CompoundTag compoundTag) {
        this.health = compoundTag.getInt("life_health");
    }
}
