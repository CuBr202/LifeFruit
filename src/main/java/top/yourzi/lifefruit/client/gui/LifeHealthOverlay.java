package top.yourzi.lifefruit.client.gui;

import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraft.client.gui.Gui;


import java.util.Random;

@OnlyIn(Dist.CLIENT)
public class LifeHealthOverlay implements IGuiOverlay {
    private static final ResourceLocation LIFE_HEALTH = new ResourceLocation("textures/gui/life_health.png");
    private static final ResourceLocation LIFE_HEALTH_HALF = new ResourceLocation("textures/gui/life_health_half.png");

    private Player getCameraPlayer() {
        return !(Minecraft.getInstance().getCameraEntity() instanceof Player)
                ? null
                : (Player) Minecraft.getInstance().getCameraEntity();
    }


    @Override
    public void render(ForgeGui forgeGui, GuiGraphics guiGraphics, float v, int i, int i1) {
        final Player player = getCameraPlayer();
        int lifeHealth = player.getPersistentData().getInt("life_health");

        if ( player == null || player.isCreative() || player.isSpectator() || lifeHealth <= 0) return;

        if (lifeHealth % 2 == 0) {

        }else {

        }


    }
}
