package top.yourzi.lifefruit.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.ReportedException;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundSetHealthPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public class ServerPlayerMixin extends Player {
    @Shadow private float lastSentHealth;

    @Shadow private int lastSentFood;

    @Shadow public ServerGamePacketListenerImpl connection;

    @Shadow private boolean lastFoodSaturationZero;

    public ServerPlayerMixin(Level p_250508_, BlockPos p_250289_, float p_251702_, GameProfile p_252153_) {
        super(p_250508_, p_250289_, p_251702_, p_252153_);
    }

    @Inject(method = "doTick", at = @At(value = "HEAD"))
    public void doTick(CallbackInfo ci){
        int presentLifeHealth = this.getPersistentData().getInt("present_life_health");
        int presentDragonHealth = this.getPersistentData().getInt("present_dragon_health");
        int lastLifeHealth = 1;
        int lastDragonHealth = 1;
        int lifeHealth = this.getPersistentData().getInt("life_health");
        int dragonHealth = this.getPersistentData().getInt("dragon_health");
        try {
            /*
            if (presentLifeHealth != lastLifeHealth || this.lastSentFood != this.foodData.getFoodLevel() || this.foodData.getSaturationLevel() == 0.0F != this.lastFoodSaturationZero) {
                this.connection.send(new ClientboundSetHealthPacket(presentLifeHealth, this.foodData.getFoodLevel(), this.foodData.getSaturationLevel()));
                lastLifeHealth = presentLifeHealth;
                this.lastSentFood = this.foodData.getFoodLevel();
                this.lastFoodSaturationZero = this.foodData.getSaturationLevel() == 0.0F;
            }
            if (presentDragonHealth != lastDragonHealth || this.lastSentFood != this.foodData.getFoodLevel() || this.foodData.getSaturationLevel() == 0.0F != this.lastFoodSaturationZero) {
                this.connection.send(new ClientboundSetHealthPacket(presentDragonHealth, this.foodData.getFoodLevel(), this.foodData.getSaturationLevel()));
                lastDragonHealth = presentDragonHealth;
                this.lastSentFood = this.foodData.getFoodLevel();
                this.lastFoodSaturationZero = this.foodData.getSaturationLevel() == 0.0F;
            }

             */

        } catch (Throwable var4) {
            Throwable throwable = var4;
            CrashReport crashreport = CrashReport.forThrowable(throwable, "Ticking Mixed ServerPlayer");
            CrashReportCategory crashreportcategory = crashreport.addCategory("Player being ticked");
            this.fillCrashReportCategory(crashreportcategory);
            throw new ReportedException(crashreport);
        }
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    @Override
    public boolean isCreative() {
        return false;
    }
}
