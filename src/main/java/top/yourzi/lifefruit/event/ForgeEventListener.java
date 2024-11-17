package top.yourzi.lifefruit.event;


import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import top.yourzi.lifefruit.Lifefruit;
import top.yourzi.lifefruit.capability.DragonHeart.CurrentDragonHeartCapabilityProvider;
import top.yourzi.lifefruit.capability.DragonHeart.MaxDragonHeartCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.CurrentLifeHealthCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.MaxLifeHeartCapability;
import top.yourzi.lifefruit.capability.LifeHeart.MaxLifeHeartCapabilityProvider;
import top.yourzi.lifefruit.client.gui.ExtraHealthOverlay;
import top.yourzi.lifefruit.command.LFCommand;
import top.yourzi.lifefruit.network.Channel;
import top.yourzi.lifefruit.network.packet.S2C.CurrentDragonHealthPacket;
import top.yourzi.lifefruit.network.packet.S2C.CurrentLifeHealthPacket;
import top.yourzi.lifefruit.network.packet.S2C.MaxDragonHealthPacket;
import top.yourzi.lifefruit.network.packet.S2C.MaxLifeHealthPacket;
import top.yourzi.lifefruit.register.LFItems;

@Mod.EventBusSubscriber(modid = Lifefruit.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ForgeEventListener {

    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        ExtraHealthOverlay.startTick();
    }

    @SubscribeEvent
    public static void onPlayerHeal(LivingHealEvent event) {
    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        LFCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void onPlayerTick(LivingEvent.LivingTickEvent event) {

        if (event.getEntity() instanceof ServerPlayer player){

            player.getCapability(CurrentLifeHealthCapabilityProvider.CURRENT_LIFE_HEALTH_CAPABILITY).ifPresent((heart) ->
                    Channel.sendToPlayer(new CurrentLifeHealthPacket(heart.getCurrentLifeHeart()), player)
            );
            player.getCapability(CurrentDragonHeartCapabilityProvider.CURRENT_DRAGON_HEART_CAPABILITY).ifPresent((heart) ->
                    Channel.sendToPlayer(new CurrentDragonHealthPacket(heart.getCurrentDragonHeart()), player)
            );

            player.getCapability(MaxLifeHeartCapabilityProvider.MAX_LIFE_HEART_CAPABILITY).ifPresent((heart) ->
                    {
                        player.getCapability(CurrentLifeHealthCapabilityProvider.CURRENT_LIFE_HEALTH_CAPABILITY).ifPresent((cheart) ->
                                {
                                    if (player.getMaxHealth() < heart.getMaxLifeHeart() && cheart.getCurrentLifeHeart() > player.getMaxHealth()) {
                                        cheart.setCurrentLifeHeart((int) player.getMaxHealth());
                                    }

                                    Channel.sendToPlayer(new CurrentLifeHealthPacket(cheart.getCurrentLifeHeart()), player);
                                }
                        );
                    }
            );
            player.getCapability(MaxDragonHeartCapabilityProvider.MAX_DRAGON_HEART_CAPABILITY).ifPresent((heart) ->
                    {
                        player.getCapability(CurrentDragonHeartCapabilityProvider.CURRENT_DRAGON_HEART_CAPABILITY).ifPresent((cdheart) ->
                                {
                                    player.getCapability(CurrentLifeHealthCapabilityProvider.CURRENT_LIFE_HEALTH_CAPABILITY).ifPresent((cheart) ->
                                    {
                                        if (cdheart.getCurrentDragonHeart() > cheart.getCurrentLifeHeart()) {
                                            cdheart.setCurrentDragonHeart(cheart.getCurrentLifeHeart());
                                        }
                                    });

                                    Channel.sendToPlayer(new CurrentDragonHealthPacket(cdheart.getCurrentDragonHeart()), player);
                                }
                        );
                    }
            );

        }
    }

    @SubscribeEvent
    public static void onPlayerJoinWorld(EntityJoinLevelEvent event) {
        if(!event.getLevel().isClientSide()) {
            if(event.getEntity() instanceof ServerPlayer player) {
                event.getEntity().getCapability(MaxLifeHeartCapabilityProvider.MAX_LIFE_HEART_CAPABILITY).ifPresent((heart) ->
                        Channel.sendToPlayer(new MaxLifeHealthPacket(heart.getMaxLifeHeart()), (ServerPlayer) event.getEntity())
                );
                event.getEntity().getCapability(CurrentLifeHealthCapabilityProvider.CURRENT_LIFE_HEALTH_CAPABILITY).ifPresent((heart) ->
                        Channel.sendToPlayer(new CurrentLifeHealthPacket(heart.getCurrentLifeHeart()), (ServerPlayer) event.getEntity())
                );
                event.getEntity().getCapability(MaxDragonHeartCapabilityProvider.MAX_DRAGON_HEART_CAPABILITY).ifPresent((heart) ->
                        Channel.sendToPlayer(new MaxDragonHealthPacket(heart.getMaxDragonHeart()), (ServerPlayer) event.getEntity())
                );
                event.getEntity().getCapability(CurrentDragonHeartCapabilityProvider.CURRENT_DRAGON_HEART_CAPABILITY).ifPresent((heart) ->
                        Channel.sendToPlayer(new CurrentDragonHealthPacket(heart.getCurrentDragonHeart()), (ServerPlayer) event.getEntity())
                );
            }
        }
    }

    @SubscribeEvent
    public static void onUseItem(LivingEntityUseItemEvent.Finish event) {
        if (event.getEntity() instanceof ServerPlayer player) {
            if (event.getItem().is(LFItems.LIFE_FRUIT.get())){

                float maxHealth = player.getMaxHealth();
                player.getCapability(MaxLifeHeartCapabilityProvider.MAX_LIFE_HEART_CAPABILITY).ifPresent((heart) -> {
                    heart.increaseMaxLifeHeart(maxHealth);
                });
            }
            if (event.getItem().is(LFItems.DRAGON_FRUIT.get())){

                player.getCapability(MaxDragonHeartCapabilityProvider.MAX_DRAGON_HEART_CAPABILITY).ifPresent((heart) -> {
                    player.getCapability(MaxLifeHeartCapabilityProvider.MAX_LIFE_HEART_CAPABILITY).ifPresent((maxlifehealth) -> {
                        heart.increaseMaxDragonHeart(maxlifehealth.getMaxLifeHeart());
                    });
                });
            }
            if (event.getItem().is(LFItems.DRAGON_FRUIT.get()) || event.getItem().is(LFItems.LIFE_FRUIT.get())){

                player.getCapability(MaxDragonHeartCapabilityProvider.MAX_DRAGON_HEART_CAPABILITY).ifPresent((heart) -> {
                    Channel.sendToPlayer(new MaxDragonHealthPacket(heart.getMaxDragonHeart()), player);
                });
                player.getCapability(MaxLifeHeartCapabilityProvider.MAX_LIFE_HEART_CAPABILITY).ifPresent((heart) -> {
                    Channel.sendToPlayer(new MaxLifeHealthPacket(heart.getMaxLifeHeart()), player);
                });

            }
        }
    }

    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        event.getOriginal().reviveCaps();
        event.getOriginal().getCapability(MaxLifeHeartCapabilityProvider.MAX_LIFE_HEART_CAPABILITY).ifPresent((old) ->
                event.getEntity().getCapability(MaxLifeHeartCapabilityProvider.MAX_LIFE_HEART_CAPABILITY).ifPresent((heart) -> {

                            heart.setMaxLifeHeart(old.getMaxLifeHeart());
                        }
                )
        );
        event.getOriginal().getCapability(MaxLifeHeartCapabilityProvider.MAX_LIFE_HEART_CAPABILITY).ifPresent((old) ->
                event.getEntity().getCapability(CurrentLifeHealthCapabilityProvider.CURRENT_LIFE_HEALTH_CAPABILITY).ifPresent((heart) -> {

                            heart.setCurrentLifeHeart(old.getMaxLifeHeart());
                        }
                )
        );
        event.getOriginal().getCapability(MaxDragonHeartCapabilityProvider.MAX_DRAGON_HEART_CAPABILITY).ifPresent((old) ->
                event.getEntity().getCapability(MaxDragonHeartCapabilityProvider.MAX_DRAGON_HEART_CAPABILITY).ifPresent((heart) -> {

                            heart.setMaxDragonHeart(old.getMaxDragonHeart());
                        }
                )
        );
        event.getOriginal().getCapability(MaxDragonHeartCapabilityProvider.MAX_DRAGON_HEART_CAPABILITY).ifPresent((old) ->
                event.getEntity().getCapability(CurrentDragonHeartCapabilityProvider.CURRENT_DRAGON_HEART_CAPABILITY).ifPresent((heart) -> {

                            heart.setCurrentDragonHeart(old.getMaxDragonHeart());
                        }
                )
        );
    }

}
