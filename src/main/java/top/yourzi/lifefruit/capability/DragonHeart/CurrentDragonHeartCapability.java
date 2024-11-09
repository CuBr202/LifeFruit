package top.yourzi.lifefruit.capability.DragonHeart;

import net.minecraft.nbt.CompoundTag;

public class CurrentDragonHeartCapability {
    private int currentDragonHeart;

    public CurrentDragonHeartCapability() {
        this.currentDragonHeart = 0;
    }

    public int getCurrentDragonHeart() {
        return currentDragonHeart;
    }

    public void setCurrentDragonHeart(int currentDragonHeart) {
        this.currentDragonHeart = currentDragonHeart;
    }

    public void increaseMaxDragonHeart(float maxHealth) {
        if (maxHealth > currentDragonHeart) {
            currentDragonHeart++;
        }
    }

    public void decreaseMaxDragonHeart() {
        this.currentDragonHeart--;
    }

    public void saveNBTData(CompoundTag tag) {
        tag.putInt("currentDragonHeart", currentDragonHeart);
    }

    public void loadNBTData(CompoundTag tag) {
        currentDragonHeart = tag.getInt("currentDragonHeart");
    }
}