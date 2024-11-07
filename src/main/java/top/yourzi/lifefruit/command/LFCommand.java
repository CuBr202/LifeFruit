package top.yourzi.lifefruit.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

import java.awt.*;

public class LFCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
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

                                                    return 0;
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


                                                    return 0;
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


                                                    return 0;
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


                                                    return 0;
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


                                                    return 0;
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


                                                    return 0;
                                                }))
                                )
                        )
                        .then(Commands.literal("test")
                                .then(Commands.argument("targets", EntityArgument.players()))
                                .executes((context) ->{
                                    context.getSource().sendSuccess(() -> Component.literal("test"), false);
                                    return 0;
                                })
                        )
        );
    }
}
