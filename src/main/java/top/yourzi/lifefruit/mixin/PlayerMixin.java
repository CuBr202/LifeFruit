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
import top.yourzi.lifefruit.capability.DragonHeart.CurrentDragonHeartCapabilityProvider;
import top.yourzi.lifefruit.capability.DragonHeart.MaxDragonHeartCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.CurrentLifeHealthCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.MaxLifeHeartCapabilityProvider;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity{
    @Shadow public abstract FoodData getFoodData();

    @Shadow @Final private static Logger LOGGER;

    @Shadow public abstract boolean hurt(DamageSource p_36154_, float p_36155_);

    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "aiStep", at = @At(value = "HEAD"))
    public void aiStep(CallbackInfo ci) {
        int exhaustion = (int) this.getFoodData().getExhaustionLevel();

        if (this.level().getGameRules().getBoolean(GameRules.RULE_NATURAL_REGENERATION) && this.getHealth() >= this.getMaxHealth() && !this.getFoodData().needsFood()) {
            if (this.tickCount % 20 == 0) {

                this.getCapability(CurrentLifeHealthCapabilityProvider.CURRENT_LIFE_HEALTH_CAPABILITY).ifPresent((heart) -> {
                    this.getCapability(MaxLifeHeartCapabilityProvider.MAX_LIFE_HEART_CAPABILITY).ifPresent((maxheart) -> {

                        if (heart.getCurrentLifeHeart() < maxheart.getMaxLifeHeart()){
                            heart.increaseCurrentLifeHeart(maxheart.getMaxLifeHeart());
                            this.getFoodData().setExhaustion(exhaustion + 6);
                        } else if (heart.getCurrentLifeHeart() >= maxheart.getMaxLifeHeart()) {
                            this.getCapability(CurrentDragonHeartCapabilityProvider.CURRENT_DRAGON_HEART_CAPABILITY).ifPresent((dragonheart) -> {
                                this.getCapability(MaxDragonHeartCapabilityProvider.MAX_DRAGON_HEART_CAPABILITY).ifPresent((maxdragonheart) -> {

                                    if (dragonheart.getCurrentDragonHeart() < maxdragonheart.getMaxDragonHeart()){
                                        dragonheart.increaseMaxDragonHeart(maxdragonheart.getMaxDragonHeart());

                                        this.getFoodData().setExhaustion(exhaustion + 6);

                                    }
                                });
                            });
                        }
                    });
                });
            }
        }
    }


    @Inject(method = "actuallyHurt", at = @At(value = "HEAD"), cancellable = true)
    protected void actuallyHurt(DamageSource source, float damage, CallbackInfo ci) {
        this.getCapability(CurrentLifeHealthCapabilityProvider.CURRENT_LIFE_HEALTH_CAPABILITY).ifPresent((heart) -> {
            this.getCapability(CurrentDragonHeartCapabilityProvider.CURRENT_DRAGON_HEART_CAPABILITY).ifPresent((dragonheart) -> {

                if (!this.isInvulnerableTo(source) && dragonheart.getCurrentDragonHeart() > 0 && damage > 0) {

                    float hurt = ForgeHooks.onLivingHurt(this, source, damage);
                    hurt = this.getDamageAfterArmorAbsorb(source, hurt);
                    hurt = this.getDamageAfterMagicAbsorb(source, hurt);
                    hurt = ForgeHooks.onLivingDamage(this, source, hurt);

                    if (dragonheart.getCurrentDragonHeart() > hurt) {
                        dragonheart.setCurrentDragonHeart((int) (dragonheart.getCurrentDragonHeart() - hurt));
                    }else if (dragonheart.getCurrentDragonHeart() <= hurt) {
                        hurt = hurt - dragonheart.getCurrentDragonHeart();
                        dragonheart.setCurrentDragonHeart(0);
                        heart.setCurrentLifeHeart((int) (heart.getCurrentLifeHeart() - hurt));
                    } else if (heart.getCurrentLifeHeart() + dragonheart.getCurrentDragonHeart() <= hurt) {
                        dragonheart.setCurrentDragonHeart(0);
                        heart.setCurrentLifeHeart(0);

                        this.getCapability(MaxDragonHeartCapabilityProvider.MAX_DRAGON_HEART_CAPABILITY).ifPresent((maxheart) -> {
                            this.getCapability(MaxLifeHeartCapabilityProvider.MAX_LIFE_HEART_CAPABILITY).ifPresent((maxdragonheart) -> {
                                this.setHealth(this.getHealth() - maxdragonheart.getMaxLifeHeart() - maxheart.getMaxDragonHeart());
                            });
                        });

                    }
                    this.gameEvent(GameEvent.ENTITY_DAMAGE);
                    ci.cancel();
                }

                if (!this.isInvulnerableTo(source) && heart.getCurrentLifeHeart() > 0 && damage > 0 && dragonheart.getCurrentDragonHeart() <= 0) {

                    float hurt = ForgeHooks.onLivingHurt(this, source, damage);
                    hurt = this.getDamageAfterArmorAbsorb(source, hurt);
                    hurt = this.getDamageAfterMagicAbsorb(source, hurt);
                    hurt = ForgeHooks.onLivingDamage(this, source, hurt);

                    if (heart.getCurrentLifeHeart() > hurt) {
                        heart.setCurrentLifeHeart((int) (heart.getCurrentLifeHeart() - hurt));
                    }else{
                        hurt = hurt - heart.getCurrentLifeHeart();
                        heart.setCurrentLifeHeart(0);
                        this.setHealth(this.getHealth() - hurt);
                    }
                    this.gameEvent(GameEvent.ENTITY_DAMAGE);
                    ci.cancel();
                }


            });
            });
    }

}
