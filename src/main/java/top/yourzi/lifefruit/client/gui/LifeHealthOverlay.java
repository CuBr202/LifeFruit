package top.yourzi.lifefruit.client.gui;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import org.slf4j.Logger;
import top.yourzi.lifefruit.network.Channel;
import top.yourzi.lifefruit.network.packet.S2C.LifeHealthPacket;

@OnlyIn(Dist.CLIENT)
public class LifeHealthOverlay{
    private static final ResourceLocation LIFE_HEALTH = new ResourceLocation("lifefruit:textures/gui/life_health.png");
    private static final ResourceLocation LIFE_HEALTH_HALF = new ResourceLocation("lifefruit:textures/gui/life_health_half.png");
    private static final Logger LOGGER = LogUtils.getLogger();




    public static final IGuiOverlay LIFE_HEALTH_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {

        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null || player.isCreative() || player.isSpectator()) {return;}
        int lifeHearts =  player.getPersistentData().getInt("present_life_health") / 2 ;
        int halfLifeHearts = player.getPersistentData().getInt("present_life_health") % 2;
        ForgeGui Gui = (ForgeGui) mc.gui;
        int x = mc.getWindow().getGuiScaledWidth()/2 - 90;
        int y = mc.getWindow().getGuiScaledHeight() - Gui.rightHeight + 10;
        if (player.getPersistentData().getInt("present_life_health") <= 0) {return;}

        guiGraphics.blit(LIFE_HEALTH_HALF,x + ((lifeHearts + halfLifeHearts - 1) * 8),y,90,0,0,8,8,
                8,8) ;

        for(int i = 0; i < lifeHearts; i++) {
                LOGGER.info("lifeHearts: " + lifeHearts);
                guiGraphics.blit(LIFE_HEALTH,x + (i * 8),y,90,0,0,8,8,
                    8,8) ;

        }



    };


}
