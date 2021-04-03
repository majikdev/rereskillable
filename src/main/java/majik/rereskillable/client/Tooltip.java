package majik.rereskillable.client;

import majik.rereskillable.Configuration;
import majik.rereskillable.common.capabilities.SkillModel;
import majik.rereskillable.common.skills.Requirement;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class Tooltip
{
    @SubscribeEvent
    public void onTooltipDisplay(ItemTooltipEvent event)
    {
        ItemStack item = event.getItemStack();
        Requirement[] requirements = Configuration.getRequirements(item);
        
        if (requirements != null)
        {
            List<ITextComponent> tooltips = event.getToolTip();
            tooltips.add(StringTextComponent.EMPTY);
            tooltips.add(new TranslationTextComponent("tooltip.requirements").append(":").withStyle(TextFormatting.GRAY));
            
            for (Requirement requirement : requirements)
            {
                int level = SkillModel.get().getSkillLevel(requirement.skill);
                TextFormatting colour = level >= requirement.level ? TextFormatting.GREEN : TextFormatting.RED;
        
                tooltips.add(new StringTextComponent(" ").append(new TranslationTextComponent(requirement.skill.displayName).append(" " + requirement.level).withStyle(colour)));
            }
        }
    }
}