package majik.rereskillable.common.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import majik.rereskillable.common.capabilities.SkillModel;
import majik.rereskillable.common.skills.Skill;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.server.command.EnumArgument;

public class GetCommand
{
    static ArgumentBuilder<CommandSourceStack, ?> register()
    {
        return Commands.literal("get")
            .then(Commands.argument("player", EntityArgument.player())
            .then(Commands.argument("skill", EnumArgument.enumArgument(Skill.class))
            .executes(GetCommand::execute)));
    }
    
    // Execute Command
    
    private static int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException
    {
        ServerPlayer player = EntityArgument.getPlayer(context, "player");
        Skill skill = context.getArgument("skill", Skill.class);
        int level = SkillModel.get(player).getSkillLevel(skill);
        
        context.getSource().sendSuccess(new TranslatableComponent(skill.displayName).append(" " + level), true);
        
        return level;
    }
}