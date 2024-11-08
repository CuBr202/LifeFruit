package top.yourzi.lifefruit.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.ForgeHooks;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity{
    @Shadow public abstract FoodData getFoodData();

    @Shadow @Final private static Logger LOGGER;

    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "aiStep", at = @At(value = "HEAD"))
    public void aiStep(CallbackInfo ci) {
        int lifeHealth = this.getPersistentData().getInt("present_life_health");
        int maxlifeHealth = this.getPersistentData().getInt("max_life_health");
        int dragonHealth = this.getPersistentData().getInt("present_dragon_health");
        int maxdragonHealth = this.getPersistentData().getInt("max_dragon_health");
        int exhaustion = (int) this.getFoodData().getExhaustionLevel();

        if (this.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION) && this.getHealth() >= this.getMaxHealth()) {
            if (lifeHealth < maxlifeHealth && this.tickCount % 20 == 0) {
                this.getPersistentData().putInt("present_life_health", lifeHealth + 1);
                this.getFoodData().setExhaustion(exhaustion + 6);
            }
            if (dragonHealth < maxdragonHealth && this.tickCount % 20 == 0 && lifeHealth >= maxlifeHealth) {
                this.getPersistentData().putInt("present_dragon_health", dragonHealth + 1);
                this.getFoodData().setExhaustion(exhaustion + 6);
            }
        }
    }


    @Inject(method = "actuallyHurt", at = @At(value = "HEAD"), cancellable = true)
    protected void actuallyHurt(DamageSource source, float damage, CallbackInfo ci){
        int lifeHealth = this.getPersistentData().getInt("present_life_health");
        int dragonHealth = this.getPersistentData().getInt("present_dragon_health");
        damage = this.getDamageAfterArmorAbsorb(source, damage);
        damage = this.getDamageAfterMagicAbsorb(source, damage);
        damage = ForgeHooks.onLivingHurt(this, source, damage);
        float f1 = Math.max(damage - this.getAbsorptionAmount(), 0.0F);
        this.setAbsorptionAmount(this.getAbsorptionAmount() - (damage - f1));
        this.getCombatTracker().recordDamage(source, f1);

        if (dragonHealth > 0 && lifeHealth > 0 && !this.isInvulnerableTo(source)){
            dragonHealth = (int) (dragonHealth - f1);
            if (dragonHealth >= 0) {
                this.getPersistentData().putInt("present_dragon_health", dragonHealth);
            }else {
                lifeHealth = lifeHealth - dragonHealth;
                if (lifeHealth > 0) {
                    this.getPersistentData().putInt("present_life_health", lifeHealth);
                } else if (lifeHealth <= 0) {
                    this.getPersistentData().putInt("present_life_health", 0);
                    f1 = f1 - dragonHealth - lifeHealth;
                    this.setHealth(this.getHealth() - f1);
                    LOGGER.info("present_life_health" + this.getPersistentData().getInt("present_life_health"));
                }
                this.getPersistentData().putInt("present_life_health", 0);
            }
            this.getCombatTracker().recordDamage(source, f1);
            ci.cancel();
        }else if (lifeHealth > 0 )
        {
            lifeHealth = (int) (lifeHealth - f1);
            if (lifeHealth >= 0) {
                this.getPersistentData().putInt("present_life_health", lifeHealth);
            }else {
                this.setHealth(this.getHealth() - f1);
                this.getPersistentData().putInt("present_life_health", 0);
            }
            this.getCombatTracker().recordDamage(source, f1);
            this.gameEvent(GameEvent.ENTITY_DAMAGE);
            ci.cancel();
        }
    }

}
