package top.yourzi.lifefruit.client.gui;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.slf4j.Logger;
import top.yourzi.lifefruit.capability.DragonHeart.CurrentDragonHeartCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.CurrentLifeHealthCapabilityProvider;

@OnlyIn(Dist.CLIENT)
public class ExtraHealthOverlay {
    Minecraft mc = Minecraft.getInstance();
    Player player = mc.player;





    private static final Logger LOGGER = LogUtils.getLogger();

    public static final IGuiOverlay EXTRA_HEALTH_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;

        ResourceLocation LIFE_HEALTH = new ResourceLocation("lifefruit:textures/gui/life_health.png");
        ResourceLocation LIFE_HEALTH_HALF = new ResourceLocation("lifefruit:textures/gui/life_health_half.png");
        ResourceLocation DRAGON_HEALTH = new ResourceLocation("lifefruit:textures/gui/dragon_health.png");
        ResourceLocation DRAGON_HEALTH_HALF = new ResourceLocation("lifefruit:textures/gui/dragon_health_half.png");

        if (player.hasEffect(MobEffects.POISON)) {
            LIFE_HEALTH = new ResourceLocation("lifefruit:textures/gui/life_health_poison.png");
            LIFE_HEALTH_HALF = new ResourceLocation("lifefruit:textures/gui/life_health_poison_half.png");
            DRAGON_HEALTH = new ResourceLocation("lifefruit:textures/gui/dragon_health_poison.png");
            DRAGON_HEALTH_HALF = new ResourceLocation("lifefruit:textures/gui/dragon_health_poison_half.png");
        } else if (player.hasEffect(MobEffects.WITHER)) {
            LIFE_HEALTH = new ResourceLocation("lifefruit:textures/gui/life_health_withered.png");
            LIFE_HEALTH_HALF = new ResourceLocation("lifefruit:textures/gui/life_health_withered_half.png");
            DRAGON_HEALTH = new ResourceLocation("lifefruit:textures/gui/dragon_health_withered.png");
            DRAGON_HEALTH_HALF = new ResourceLocation("lifefruit:textures/gui/dragon_health_withered_half.png");
        }else if (player.isFreezing()) {
            LIFE_HEALTH = new ResourceLocation("lifefruit:textures/gui/life_health_frozen.png");
            LIFE_HEALTH_HALF = new ResourceLocation("lifefruit:textures/gui/life_health_frozen_half.png");
            DRAGON_HEALTH = new ResourceLocation("lifefruit:textures/gui/dragon_health_frozen.png");
            DRAGON_HEALTH_HALF = new ResourceLocation("lifefruit:textures/gui/dragon_health_frozen_half.png");
        }


        ForgeGui Gui = (ForgeGui) mc.gui;
        int x = mc.getWindow().getGuiScaledWidth()/2 - 90;
        int y = mc.getWindow().getGuiScaledHeight() - Gui.rightHeight + 10;
        if (player == null || gui.getMinecraft().options.hideGui || !gui.shouldDrawSurvivalElements()) {return;}


            int lifeHearts = CurrentLifeHealthCapabilityProvider.clientCurrentLifeHeart / 2 ;
            int halfLifeHearts = CurrentLifeHealthCapabilityProvider.clientCurrentLifeHeart % 2;
            int dragonHearts = CurrentDragonHeartCapabilityProvider.clientCurrentDragonHeart / 2 ;
            int halfDragonHearts = CurrentDragonHeartCapabilityProvider.clientCurrentDragonHeart  % 2;

            if (CurrentLifeHealthCapabilityProvider.clientCurrentLifeHeart > 0){


                if (CurrentLifeHealthCapabilityProvider.clientCurrentLifeHeart <= 20){
                    guiGraphics.blit(LIFE_HEALTH_HALF,x + ((lifeHearts + halfLifeHearts - 1) * 8),y,90,0,0,8,8,
                            8,8) ;
                    for(int i = 0; i < lifeHearts; i++) {
                        guiGraphics.blit(LIFE_HEALTH,x + (i * 8),y,90,0,0,8,8,
                                8,8) ;

                    }

                }else{
                    guiGraphics.blit(LIFE_HEALTH_HALF,x + ((lifeHearts + halfLifeHearts - 11) * 8),y - 9,90,0,0,8,8,
                            8,8) ;
                    for(int i = 0; i < 10; i++) {
                        guiGraphics.blit(LIFE_HEALTH,x + (i * 8),y,90,0,0,8,8,
                                8,8) ;

                    }
                    for(int i = 0; i < lifeHearts - 10; i++) {
                        guiGraphics.blit(LIFE_HEALTH,x + (i * 8),y - 9,90,0,0,8,8,
                                8,8) ;

                    }

                }

            }


            if (CurrentDragonHeartCapabilityProvider.clientCurrentDragonHeart > 0){

                if (CurrentDragonHeartCapabilityProvider.clientCurrentDragonHeart <= 20){
                    guiGraphics.blit(DRAGON_HEALTH_HALF,x + ((dragonHearts + halfDragonHearts - 1) * 8),y,90,0,0,8,8,
                            8,8) ;
                    for(int i = 0; i < dragonHearts; i++) {
                        guiGraphics.blit(DRAGON_HEALTH,x + (i * 8),y,90,0,0,8,8,
                                8,8) ;

                    }


                }else{
                    guiGraphics.blit(DRAGON_HEALTH_HALF,x + ((dragonHearts + halfDragonHearts - 11) * 8),y - 9,90,0,0,8,8,
                            8,8) ;
                    for(int i = 0; i < 10; i++) {
                        guiGraphics.blit(DRAGON_HEALTH,x + (i * 8),y,90,0,0,8,8,
                                8,8) ;

                    }
                    for(int i = 0; i < dragonHearts - 10; i++) {
                        guiGraphics.blit(DRAGON_HEALTH,x + (i * 8),y - 9,90,0,0,8,8,
                                8,8) ;

                    }


                }

            }


    };

}
