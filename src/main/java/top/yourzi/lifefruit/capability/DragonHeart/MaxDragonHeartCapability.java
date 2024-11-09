package top.yourzi.lifefruit.capability.DragonHeart;

import net.minecraft.nbt.CompoundTag;

public class MaxDragonHeartCapability {
    private int maxDragonHeart;

    public MaxDragonHeartCapability() {
        this.maxDragonHeart = 0;
    }

    public int getMaxDragonHeart() {
        return maxDragonHeart;
    }

    public void setMaxDragonHeart(int maxDragonHeart) {
        this.maxDragonHeart = maxDragonHeart;
    }

    public void increaseMaxDragonHeart(float maxHealth) {
        if (maxHealth > maxDragonHeart) {
            maxDragonHeart+=2;
        }
    }

    public void decreaseMaxLifeHeart() {
        this.maxDragonHeart--;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("maxDragonHeart", maxDragonHeart);
    }

    public void loadNBTData(CompoundTag tag) {
        maxDragonHeart = tag.getInt("maxDragonHeart");
    }
}