package top.yourzi.lifefruit.capability.LifeHeart;

import net.minecraft.nbt.CompoundTag;

public class CurrentLifeHealthCapability {
    private int currentLifeHeart;

    public CurrentLifeHealthCapability() {
        this.currentLifeHeart = 0;
    }

    public int getCurrentLifeHeart() {
        return currentLifeHeart;
    }

    public void setCurrentLifeHeart(int currentLifeHeart) {
        this.currentLifeHeart = currentLifeHeart;
    }

    public void increaseCurrentLifeHeart(int maxLifeHeart) {
        if (maxLifeHeart > currentLifeHeart) {
            currentLifeHeart++;
        }
    }

    public void increaseCurrentLifeHeartByNumbers(int maxLifeHeart, int addNum) {
        if (maxLifeHeart > currentLifeHeart) {
            currentLifeHeart += Math.min(maxLifeHeart - currentLifeHeart, addNum);
        }
    }

    public void decreaseMaxLifeHeart() {
        this.currentLifeHeart--;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("currentLifeHeart", currentLifeHeart);
    }

    public void loadNBTData(CompoundTag tag) {
        currentLifeHeart = tag.getInt("currentLifeHeart");
    }
}
