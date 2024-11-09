package top.yourzi.lifefruit.client.gui;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
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
    private static final ResourceLocation LIFE_HEALTH = new ResourceLocation("lifefruit:textures/gui/life_health.png");
    private static final ResourceLocation LIFE_HEALTH_HALF = new ResourceLocation("lifefruit:textures/gui/life_health_half.png");
    private static final ResourceLocation DRAGON_HEALTH = new ResourceLocation("lifefruit:textures/gui/dragon_health.png");
    private static final ResourceLocation DRAGON_HEALTH_HALF = new ResourceLocation("lifefruit:textures/gui/dragon_health_half.png");
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final IGuiOverlay EXTRA_HEALTH_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        ForgeGui Gui = (ForgeGui) mc.gui;
        int x = mc.getWindow().getGuiScaledWidth()/2 - 90;
        int y = mc.getWindow().getGuiScaledHeight() - Gui.rightHeight + 10;
        if (player == null || player.isCreative() || player.isSpectator() || player.getHealth() < player.getMaxHealth()) {return;}

            int lifeHearts = CurrentLifeHealthCapabilityProvider.clientCurrentLifeHeart / 2 ;
            int halfLifeHearts = CurrentLifeHealthCapabilityProvider.clientCurrentLifeHeart % 2;
            int dragonHearts = CurrentDragonHeartCapabilityProvider.clientCurrentDragonHeart / 2 ;
            int halfDragonHearts = CurrentDragonHeartCapabilityProvider.clientCurrentDragonHeart  % 2;

            if (CurrentLifeHealthCapabilityProvider.clientCurrentLifeHeart != 0){
                guiGraphics.blit(LIFE_HEALTH_HALF,x + ((lifeHearts + halfLifeHearts - 1) * 8),y,90,0,0,8,8,
                        8,8) ;

                for(int i = 0; i < lifeHearts; i++) {
                    guiGraphics.blit(LIFE_HEALTH,x + (i * 8),y,90,0,0,8,8,
                            8,8) ;

                }
            }


            if (CurrentDragonHeartCapabilityProvider.clientCurrentDragonHeart != 0){
            guiGraphics.blit(DRAGON_HEALTH_HALF,x + ((dragonHearts + halfDragonHearts - 1) * 8),y,90,0,0,8,8,
                    8,8) ;

                 for(int i = 0; i < dragonHearts; i++) {
                    guiGraphics.blit(DRAGON_HEALTH,x + (i * 8),y,90,0,0,8,8,
                            8,8) ;

                }
            }


    };

}
