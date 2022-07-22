package majik.rereskillable.common.commands;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Commands
{
    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event)
    {
        event.getDispatcher().register(
            LiteralArgumentBuilder.<CommandSourceStack>literal("skills")
            .requires(source -> source.hasPermission(2))
            .then(SetCommand.register())
            .then(GetCommand.register()));
    }
}