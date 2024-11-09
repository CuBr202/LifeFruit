package top.yourzi.lifefruit.capability.LifeHeart;

import net.minecraft.nbt.CompoundTag;

public class MaxLifeHeartCapability {
    private int maxLifeHeart;

    public MaxLifeHeartCapability() {
        this.maxLifeHeart =0;
    }

    public int getMaxLifeHeart() {
        return maxLifeHeart;
    }

    public void setMaxLifeHeart(int maxLifeHeart) {
        this.maxLifeHeart = maxLifeHeart;
    }

    public void increaseMaxLifeHeart(float maxHealth) {
        if (maxHealth > maxLifeHeart) {
            maxLifeHeart++;
        }
    }

    public void decreaseMaxLifeHeart() {
        this.maxLifeHeart--;
    }

    public void saveNBTData(CompoundTag tag){
        tag.putInt("maxLifeHeart", maxLifeHeart);
    }

    public void loadNBTData(CompoundTag tag){
        maxLifeHeart = tag.getInt("maxLifeHeart");
    }
}
