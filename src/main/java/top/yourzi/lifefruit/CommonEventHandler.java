package top.yourzi.lifefruit;


import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber()
public class CommonEventHandler {
    @SubscribeEvent
    public static void onPlayerCloned(PlayerEvent.Clone event) {
        int oldLifeHealth = event.getOriginal().getPersistentData().getInt("life_health");
        int oldDragonHealth = event.getOriginal().getPersistentData().getInt("dragon_health");
        event.getEntity().getPersistentData().putInt("life_health",oldLifeHealth);
        event.getEntity().getPersistentData().putInt("dragon_health",oldDragonHealth);
    }

    @SubscribeEvent
    public static void registerCommand(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        LiteralCommandNode<CommandSourceStack> cmd = dispatcher.register(
                Commands.literal("lifefruit")
                        .requires((source) -> source.hasPermission(2))
                        .then(Commands.literal("life_heart")
                                .then(Commands.literal("add")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{

                                                    int addHeart = IntegerArgumentType.getInteger(context, "count");
                                                    Player player = EntityArgument.getPlayer(context, "targets");
                                                    int heart =  player.getPersistentData().getInt("life_heart");

                                                    if (heart < (player.getMaxHealth() - addHeart)) {
                                                        heart += addHeart;
                                                        player.getPersistentData().putInt("life_heart", heart);
                                                    }
                                                    player.displayClientMessage(Component.literal(("fuckyou")), true);

                                                    return Command.SINGLE_SUCCESS;
                                                }))
                                )
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{

                                                    int removeHeart = IntegerArgumentType.getInteger(context, "count");
                                                    Player player = EntityArgument.getPlayer(context, "targets");
                                                    int heart =  player.getPersistentData().getInt("life_heart");

                                                    if (heart >= removeHeart) {
                                                        heart -= removeHeart;
                                                        player.getPersistentData().putInt("life_heart", heart);
                                                        int dragonHeart =  player.getPersistentData().getInt("dragon_heart");
                                                        if (dragonHeart >= heart) {
                                                            player.getPersistentData().putInt("dragon_heart", heart);
                                                        }
                                                    }


                                                    return Command.SINGLE_SUCCESS;
                                                }))
                                )
                                .then(Commands.literal("set")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{

                                                    int setHeart = IntegerArgumentType.getInteger(context, "count");
                                                    Player player = EntityArgument.getPlayer(context, "targets");

                                                    if (setHeart <= player.getMaxHealth()) {
                                                        player.getPersistentData().putInt("life_heart", setHeart);
                                                        int dragonHeart =  player.getPersistentData().getInt("dragon_heart");
                                                        if (dragonHeart >= setHeart) {
                                                            player.getPersistentData().putInt("dragon_heart", setHeart);
                                                        }
                                                    }


                                                    return Command.SINGLE_SUCCESS;
                                                }))
                                )
                        )
                        .then(Commands.literal("dragon_heart")
                                .then(Commands.literal("add")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{

                                                    int addHeart = IntegerArgumentType.getInteger(context, "count");
                                                    Player player = EntityArgument.getPlayer(context, "targets");
                                                    int heart =  player.getPersistentData().getInt("dragon_heart");

                                                    if (heart < (player.getPersistentData().getInt("life_heart") - addHeart)) {
                                                        heart += addHeart;
                                                        player.getPersistentData().putInt("dragon_heart", heart);
                                                    }


                                                    return Command.SINGLE_SUCCESS;
                                                }))
                                )
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{

                                                    int removeHeart = IntegerArgumentType.getInteger(context, "count");
                                                    Player player = EntityArgument.getPlayer(context, "targets");
                                                    int heart =  player.getPersistentData().getInt("dragon_heart");

                                                    if (heart >= removeHeart) {
                                                        heart -= removeHeart;
                                                        player.getPersistentData().putInt("dragon_heart", heart);
                                                    }


                                                    return Command.SINGLE_SUCCESS;
                                                }))
                                )
                                .then(Commands.literal("set")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{

                                                    int setHeart = IntegerArgumentType.getInteger(context, "count");
                                                    Player player = EntityArgument.getPlayer(context, "targets");
                                                    int heart =  player.getPersistentData().getInt("dragon_heart");

                                                    if (setHeart <= player.getPersistentData().getInt("life_heart")) {
                                                        player.getPersistentData().putInt("dragon_heart", setHeart);
                                                    }


                                                    return Command.SINGLE_SUCCESS;
                                                }))
                                )
                        )
        );
        dispatcher.register(Commands.literal("lifefruit").redirect(cmd));
    }
}
