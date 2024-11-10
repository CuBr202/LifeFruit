package top.yourzi.lifefruit.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import top.yourzi.lifefruit.capability.DragonHeart.MaxDragonHeartCapabilityProvider;
import top.yourzi.lifefruit.capability.LifeHeart.MaxLifeHeartCapabilityProvider;

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
                                                    EntityArgument.getPlayers(context, "targets").forEach(player -> {
                                                        player.getCapability(MaxLifeHeartCapabilityProvider.MAX_LIFE_HEART_CAPABILITY).ifPresent((heart) ->
                                                                {
                                                                    int addHeart = IntegerArgumentType.getInteger(context, "count");
                                                                    int Heart = heart.getMaxLifeHeart();
                                                                    heart.setMaxLifeHeart(Heart + addHeart);
                                                                }
                                                                );});return 1;}))
                                )
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{
                                                    EntityArgument.getPlayers(context, "targets").forEach(player -> {
                                                        player.getCapability(MaxLifeHeartCapabilityProvider.MAX_LIFE_HEART_CAPABILITY).ifPresent((heart) ->
                                                                {
                                                                    int removeHeart = IntegerArgumentType.getInteger(context, "count");
                                                                    int Heart = heart.getMaxLifeHeart();
                                                                    int currentHeart = Heart - removeHeart;
                                                                    if (currentHeart > 0){
                                                                        heart.setMaxLifeHeart(currentHeart);
                                                                    }else {
                                                                        heart.setMaxLifeHeart(0);
                                                                    }
                                                                }
                                                        );});return 1;}))
                                )
                                .then(Commands.literal("set")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{
                                                    EntityArgument.getPlayers(context, "targets").forEach(player -> {
                                                        player.getCapability(MaxLifeHeartCapabilityProvider.MAX_LIFE_HEART_CAPABILITY).ifPresent((heart) ->
                                                                {
                                                                    int setHeart = IntegerArgumentType.getInteger(context, "count");
                                                                    int maxHealth = (int) player.getMaxHealth();
                                                                    if (maxHealth > setHeart){
                                                                        heart.setMaxLifeHeart(setHeart);
                                                                    }else {
                                                                        heart.setMaxLifeHeart(maxHealth);
                                                                    }
                                                                }
                                                        );});return 1;}))
                                )
                        )
                        .then(Commands.literal("dragon_heart")
                                .then(Commands.literal("add")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{
                                                    EntityArgument.getPlayers(context, "targets").forEach(player -> {
                                                        player.getCapability(MaxDragonHeartCapabilityProvider.MAX_DRAGON_HEART_CAPABILITY).ifPresent((heart) ->
                                                                {
                                                                    int addHeart = IntegerArgumentType.getInteger(context, "count");
                                                                    int Heart = heart.getMaxDragonHeart();
                                                                    heart.setMaxDragonHeart(Heart + addHeart);
                                                                }
                                                        );});return 1;}))
                                )
                                .then(Commands.literal("remove")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{
                                                    EntityArgument.getPlayers(context, "targets").forEach(player -> {
                                                        player.getCapability(MaxDragonHeartCapabilityProvider.MAX_DRAGON_HEART_CAPABILITY).ifPresent((heart) ->
                                                                {
                                                                    int removeHeart = IntegerArgumentType.getInteger(context, "count");
                                                                    int Heart = heart.getMaxDragonHeart();
                                                                    int currentHeart = Heart - removeHeart;
                                                                    if (currentHeart > 0){
                                                                        heart.setMaxDragonHeart(currentHeart);
                                                                    }else {
                                                                        heart.setMaxDragonHeart(0);
                                                                    }
                                                                }
                                                        );});return 1;}))
                                )
                                .then(Commands.literal("set")
                                        .then(Commands.argument("targets", EntityArgument.players())
                                                .then(Commands.argument("count", IntegerArgumentType.integer(0)))
                                                .executes((context) ->{
                                                    EntityArgument.getPlayers(context, "targets").forEach(player -> {
                                                        player.getCapability(MaxDragonHeartCapabilityProvider.MAX_DRAGON_HEART_CAPABILITY).ifPresent((heart) ->
                                                                {
                                                                    int setHeart = IntegerArgumentType.getInteger(context, "count");
                                                                    int maxHealth = (int) player.getMaxHealth();
                                                                    if (maxHealth > setHeart){
                                                                        heart.setMaxDragonHeart(setHeart);
                                                                    }else {
                                                                        heart.setMaxDragonHeart(maxHealth);
                                                                    }
                                                                }
                                                        );});return 1;}))
                                )
                        )

        );
    }
}
