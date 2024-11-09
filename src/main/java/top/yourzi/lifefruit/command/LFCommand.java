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




                                                    return 0;
                                                }))
                                )
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{





                                                    return 0;
                                                }))
                                )
                                .then(Commands.literal("set")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{



                                                    return 0;
                                                }))
                                )
                        )
                        .then(Commands.literal("dragon_heart")
                                .then(Commands.literal("add")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{



                                                    return 0;
                                                }))
                                )
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{


                                                    return 0;
                                                }))
                                )
                                .then(Commands.literal("set")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{


                                                    return 0;
                                                }))
                                )
                        )

        );
    }
}
