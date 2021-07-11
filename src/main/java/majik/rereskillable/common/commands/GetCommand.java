package majik.rereskillable.common.commands;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import majik.rereskillable.common.capabilities.SkillModel;
import majik.rereskillable.common.skills.Skill;
import net.minecraft.command.CommandSource;
import net.minecraft.command.Commands;
import net.minecraft.command.arguments.EntityArgument;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.server.command.EnumArgument;

public class GetCommand
{
    static ArgumentBuilder<CommandSource, ?> register()
    {
        return Commands.literal("get")
            .then(Commands.argument("player", EntityArgument.player())
            .then(Commands.argument("skill", EnumArgument.enumArgument(Skill.class))
            .executes(GetCommand::execute)));
    }
    
    // Execute Command
    
    private static int execute(CommandContext<CommandSource> context) throws CommandSyntaxException
    {
        ServerPlayerEntity player = EntityArgument.getPlayer(context, "player");
        Skill skill = context.getArgument("skill", Skill.class);
        int level = SkillModel.get(player).getSkillLevel(skill);
        
        context.getSource().sendSuccess(new TranslationTextComponent(skill.displayName).append(" " + level), true);
        
        return level;
    }
}