package majik.rereskillable.common.commands;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import majik.rereskillable.Configuration;
import majik.rereskillable.common.capabilities.SkillModel;
import majik.rereskillable.common.network.SyncToClient;
import majik.rereskillable.common.skills.Skill;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.command.EnumArgument;

public class SetCommand
{
    static ArgumentBuilder<CommandSourceStack, ?> register()
    {
        return Commands.literal("set")
            .then(Commands.argument("player", EntityArgument.player())
            .then(Commands.argument("skill", EnumArgument.enumArgument(Skill.class))
            .then(Commands.argument("level", IntegerArgumentType.integer(1, Configuration.getMaxLevel()))
            .executes(SetCommand::execute))));
    }
    
    // Execute Command
    
    private static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        Skill skill = context.getArgument("skill", Skill.class);
        int level = IntegerArgumentType.getInteger(context, "level");
        
        SkillModel.get(player).setSkillLevel(skill, level);
        SyncToClient.send(player);
        
        return 1;
    }
}