package top.yourzi.lifefruit.client.gui;

import com.mojang.logging.LogUtils;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;
import net.minecraftforge.fml.ModList;
import org.slf4j.Logger;
import top.yourzi.lifefruit.capability.DragonHeart.CurrentDragonHeartCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.CurrentLifeHealthCapabilityProvider;



public class ExtraHealthOverlay {
    Minecraft mc = Minecraft.getInstance();
    Player player = mc.player;

    /** Used with to make the heart bar flash. */
    private static long lifeHealthBlinkTime;
    private static long dragonHealthBlinkTime;

    private static int lastLifeHealth;
    private static int lastDragonHealth;

    private static int displayLifeHealth;
    private static int displayDragonHealth;
    /** The last recorded system time */
    private static long lastLifeHealthTime;
    private static long lastDragonHealthTime;

    public static int tickCount;

    public static void startTick(){
        tickCount++;
    }





    private static final Logger LOGGER = LogUtils.getLogger();

    public static final IGuiOverlay EXTRA_HEALTH_HUD = (gui, guiGraphics, partialTick, screenWidth, screenHeight) -> {
        Minecraft mc = Minecraft.getInstance();
        Player player = mc.player;
        if (player == null) {return;}

        boolean lifeHealthBlink = lifeHealthBlinkTime > (long) gui.getGuiTicks() && ( lifeHealthBlinkTime - (long) gui.getGuiTicks() ) / 3L % 2L == 1L;
        boolean dragonHealthBlink = dragonHealthBlinkTime > (long) gui.getGuiTicks() && ( dragonHealthBlinkTime - (long) gui.getGuiTicks() ) / 3L % 2L == 1L;
        long millis = Util.getMillis();

        int lifehealth = CurrentLifeHealthCapabilityProvider.clientCurrentLifeHeart;
        int dragonhealth = CurrentDragonHeartCapabilityProvider.clientCurrentDragonHeart;
        int lifeHearts = lifehealth / 2;
        int halfLifeHearts = lifehealth % 2;
        int dragonHearts = dragonhealth / 2;
        int halfDragonHearts = dragonhealth % 2;

        if ( lifehealth < lastLifeHealth && player.invulnerableTime > 0 ) {
            lastLifeHealthTime = millis;
            lifeHealthBlinkTime = gui.getGuiTicks() + 20;
        }
        else if ( lifehealth > lastLifeHealth) {
            lastLifeHealthTime = millis;
            lifeHealthBlinkTime = gui.getGuiTicks() + 10;
        }

        if ( millis - lastLifeHealthTime > 1000L ) {
            displayLifeHealth = lifehealth;
            lastLifeHealthTime = millis;
        }

        if ( dragonhealth < lastDragonHealth && player.invulnerableTime > 0 ) {
            lastDragonHealthTime = millis;
            dragonHealthBlinkTime = gui.getGuiTicks() + 20;
        }
        else if ( dragonhealth > lastDragonHealth) {
            lastDragonHealthTime = millis;
            dragonHealthBlinkTime = gui.getGuiTicks() + 10;
        }

        if ( millis - lastDragonHealthTime > 1000L ) {
            displayDragonHealth = dragonhealth;
            lastDragonHealthTime = millis;
        }

        lastLifeHealth = lifehealth;
        lastDragonHealth = dragonhealth;


        boolean blink = lifeHealthBlink || dragonHealthBlink;
        int shake = -1;
        int offy = 0;



        if (player.hasEffect(MobEffects.HEALTH_BOOST) && player.getEffect(MobEffects.HEALTH_BOOST).getAmplifier() <= 35){
            offy = player.getEffect(MobEffects.HEALTH_BOOST).getAmplifier() / 5 - 1;
        }else {
            offy = 6;
        }
        if (player.hasEffect(MobEffects.REGENERATION) ) {
            shake = gui.getGuiTicks() % Mth.ceil( player.getMaxHealth() + 5.0F );
        }else {
            shake = -1;
        }



        ResourceLocation LIFE_HEALTH = new ResourceLocation("lifefruit:textures/gui/life_health.png");
        ResourceLocation LIFE_HEALTH_HALF = new ResourceLocation("lifefruit:textures/gui/life_health_half.png");
        ResourceLocation DRAGON_HEALTH = new ResourceLocation("lifefruit:textures/gui/dragon_health.png");
        ResourceLocation DRAGON_HEALTH_HALF = new ResourceLocation("lifefruit:textures/gui/dragon_health_half.png");
        ResourceLocation BLINK_HEALTH = new ResourceLocation("lifefruit:textures/gui/blink_heart.png");
        ResourceLocation OVERLAY_HEART = new ResourceLocation("lifefruit:textures/gui/overlay_heart.png");

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
        } else if (player.isFreezing() && player.getTicksFrozen() >= player.getTicksRequiredToFreeze()) {
            LIFE_HEALTH = new ResourceLocation("lifefruit:textures/gui/life_health_frozen.png");
            LIFE_HEALTH_HALF = new ResourceLocation("lifefruit:textures/gui/life_health_frozen_half.png");
            DRAGON_HEALTH = new ResourceLocation("lifefruit:textures/gui/dragon_health_frozen.png");
            DRAGON_HEALTH_HALF = new ResourceLocation("lifefruit:textures/gui/dragon_health_frozen_half.png");
        }


        ForgeGui Gui = (ForgeGui) mc.gui;
        int x = mc.getWindow().getGuiScaledWidth() / 2 - 90;


        int y = mc.getWindow().getGuiScaledHeight() - 39;


        if (player == null || gui.getMinecraft().options.hideGui || !gui.shouldDrawSurvivalElements()) {
            return;
        }

        if(     !ModList.get().isLoaded("mantle") &&
                !ModList.get().isLoaded("colorfulhearts") &&
                !ModList.get().isLoaded("classicbar") &&
                !ModList.get().isLoaded("overflowingbars")
        ){
            if (blink && player.getHealth() >= player.getMaxHealth()){
                for (int i = 0; i < player.getMaxHealth() / 2; i ++){
                    int dy = y;
                    if ( i == shake ) {
                        dy = y -2;
                    }
                    guiGraphics.blit(BLINK_HEALTH, x + (i % 10 * 8) - 1, dy - (((i) / 10) * (9 - offy)) - 1, 90, 0, 0, 10, 10,
                            10, 10);
                }
            }

            if (lifehealth > 0) {
                for (int i = 0; i < lifeHearts + halfLifeHearts; i++) {
                    int dy = y;
                    if ( i == shake ) {
                        dy = y -2;
                    }
                    guiGraphics.blit(LIFE_HEALTH_HALF, x + (i % 10 * 8), dy - (((i) / 10) * (9 - offy)), 90, 0, 0, 8, 8,
                            8, 8);
                }
                for (int i = 0; i < lifeHearts; i++) {
                    int dy = y;
                    if ( i == shake ) {
                        dy = y -2;
                    }
                    guiGraphics.blit(LIFE_HEALTH, x + (i % 10 * 8), dy - (((i) / 10) * (9 - offy)), 90, 0, 0, 8, 8,
                            8, 8);
                }
            }


            if (dragonhealth > 0) {
                for (int i = 0; i < dragonHearts + halfDragonHearts; i++) {
                    int dy = y;
                    if ( i == shake ) {
                        dy = y -2;
                    }
                    guiGraphics.blit(DRAGON_HEALTH_HALF, x + (i % 10 * 8), dy - (((i) / 10) * (9 - offy)), 90, 0, 0, 8, 8,
                            8, 8);

                }
                for (int i = 0; i < dragonHearts; i++) {
                    int dy = y;
                    if ( i == shake ) {
                        dy = y -2;
                    }
                    guiGraphics.blit(DRAGON_HEALTH, x + (i % 10 * 8), dy - (((i) / 10) * (9 - offy)), 90, 0, 0, 8, 8,
                            8, 8);

                }
            }
        }else {

            if (player.hasEffect(MobEffects.REGENERATION) && ModList.get().isLoaded("overflowingbars")) {
                shake = ((tickCount / 2) % Mth.ceil(Math.min(20.0F, player.getMaxHealth()) + 5.0F));
            } else if (player.hasEffect(MobEffects.REGENERATION)) {
                shake = gui.getGuiTicks() % Mth.ceil(Math.min(20.0F, player.getMaxHealth()) + 5.0F );
            }

            if (blink && player.getHealth() >= player.getMaxHealth()){
                for (int i = 0; i < 10; i ++){
                    int dy = y;
                    if ( i == shake ) {
                        dy = y -2;
                    }
                    guiGraphics.blit(BLINK_HEALTH, x + (i % 10 * 8) - 1, dy - 1, 90, 0, 0, 10, 10,
                            10, 10);
                }
            }

            if (lifehealth > 0) {
                boolean overlay = (lifeHearts + halfLifeHearts) > 10;
                if (overlay){
                    for (int i = 0; i < 10; i++) {
                        int dy = y;
                        if ( i == shake ) {
                            dy = y -2;
                        }
                        guiGraphics.blit(LIFE_HEALTH, x + (i * 8), dy, 90, 0, 0, 8, 8,
                                8, 8);
                        guiGraphics.blit(OVERLAY_HEART, x + (i * 8), dy, 90, 0, 0, 8, 8,
                                8, 8);
                    }
                }

                for (int i = 0; i < ((lifeHearts + halfLifeHearts) % 10 == 0 ? 10 : (lifeHearts + halfLifeHearts) % 10); i++) {
                    int dy = y;
                    if ( i == shake ) {
                        dy = y -2;
                    }
                    guiGraphics.blit(LIFE_HEALTH_HALF, x + (i % 10 * 8), dy, 90, 0, 0, 8, 8,
                            8, 8);
                }
                for (int i = 0; i < ((((lifeHearts + halfLifeHearts) - 1) % 10 == 0) && halfLifeHearts == 1 ? 0 : ((lifeHearts) % 10 == 0 ? 10 : (lifeHearts) % 10)) ; i++) {
                    int dy = y;
                    if ( i == shake ) {
                        dy = y -2;
                    }
                    guiGraphics.blit(LIFE_HEALTH, x + (i % 10 * 8), dy, 90, 0, 0, 8, 8,
                            8, 8);
                }
            }


            if (dragonhealth > 0) {
                boolean overlay = (dragonHearts + halfDragonHearts) > 10;
                if (overlay){
                    for (int i = 0; i < 10; i++) {
                        int dy = y;
                        if ( i == shake ) {
                            dy = y -2;
                        }
                        guiGraphics.blit(DRAGON_HEALTH, x + (i * 8), dy, 90, 0, 0, 8, 8,
                                8, 8);
                        guiGraphics.blit(OVERLAY_HEART, x + (i * 8), dy, 90, 0, 0, 8, 8,
                                8, 8);
                    }
                }

                for (int i = 0; i < ((dragonHearts + halfDragonHearts) % 10 == 0 ? 10 : (dragonHearts + halfDragonHearts) % 10); i++) {
                    int dy = y;
                    if ( i == shake ) {
                        dy = y -2;
                    }
                    guiGraphics.blit(DRAGON_HEALTH_HALF, x + (i % 10 * 8), dy, 90, 0, 0, 8, 8,
                            8, 8);

                }
                for (int i = 0; i <((((dragonHearts + halfDragonHearts) - 1) % 10 == 0) && halfDragonHearts == 1 ? 0 : ((dragonHearts) % 10 == 0 ? 10 : (dragonHearts) % 10)); i++) {
                    int dy = y;
                    if ( i == shake ) {
                        dy = y -2;
                    }
                    guiGraphics.blit(DRAGON_HEALTH, x + (i % 10 * 8), dy, 90, 0, 0, 8, 8,
                            8, 8);

                }
            }
        };
    };
}
