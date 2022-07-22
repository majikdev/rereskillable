package majik.rereskillable.client;

import majik.rereskillable.Configuration;
import majik.rereskillable.common.capabilities.SkillModel;
import majik.rereskillable.common.skills.Requirement;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class Tooltip
{
    @SubscribeEvent
    public void onTooltipDisplay(ItemTooltipEvent event)
    {
        if (Minecraft.getInstance().player != null)
        {
            Requirement[] requirements = Configuration.getRequirements(event.getItemStack().getItem().getRegistryName());
    
            if (requirements != null)
            {
                List<Component> tooltips = event.getToolTip();
                tooltips.add(TextComponent.EMPTY);
                tooltips.add(new TranslatableComponent("tooltip.requirements").append(":").withStyle(ChatFormatting.GRAY));
        
                for (Requirement requirement : requirements)
                {
                    ChatFormatting colour = SkillModel.get().getSkillLevel(requirement.skill) >= requirement.level ? ChatFormatting.GREEN : ChatFormatting.RED;
                    tooltips.add(new TranslatableComponent(requirement.skill.displayName).append(" " + requirement.level).withStyle(colour));
                }
            }
        }
    }
}