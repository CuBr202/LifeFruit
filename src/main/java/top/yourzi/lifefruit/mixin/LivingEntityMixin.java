package top.yourzi.lifefruit.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Attackable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.extensions.IForgeLivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class LivingEntityMixin extends Entity implements Attackable, IForgeLivingEntity {
    public LivingEntityMixin(EntityType<?> type, Level level) {
        super(type, level);
    }

    @Nullable
    @Override
    public LivingEntity getLastAttacker() {
        return null;
    }

    @Override
    protected void defineSynchedData() {

    }

    @Override
    protected void readAdditionalSaveData(CompoundTag compoundTag) {

    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compoundTag) {

    }
}
