package majik.rereskillable.client;

import com.mojang.blaze3d.matrix.MatrixStack;
import majik.rereskillable.Configuration;
import majik.rereskillable.client.screen.SkillScreen;
import majik.rereskillable.common.capabilities.SkillCapability;
import majik.rereskillable.common.capabilities.SkillModel;
import majik.rereskillable.common.skills.Requirement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.List;

public class Overlay extends AbstractGui
{
    private static List<Requirement> requirements = null;
    private static int showTicks = 0;
    
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event)
    {
        if (event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE && showTicks > 0)
        {
            Minecraft minecraft = Minecraft.getInstance();
            
            if (minecraft.player.getCapability(SkillCapability.INSTANCE).isPresent())
            {
                MatrixStack stack = event.getMatrixStack();
    
                minecraft.textureManager.bind(SkillScreen.RESOURCES);
                GL11.glEnable(GL11.GL_BLEND);
    
                int cx = event.getWindow().getGuiScaledWidth() / 2;
                int cy = event.getWindow().getGuiScaledHeight() / 4;
    
                blit(stack, cx - 71, cy - 4, 0, 194, 142, 40);
    
                String message = new TranslationTextComponent("overlay.message").getString();
                minecraft.font.drawShadow(stack, message, cx - minecraft.font.width(message) / 2, cy, 0xFF5555);
    
                for (int i = 0; i < requirements.size(); i++)
                {
                    Requirement requirement = requirements.get(i);
                    int maxLevel = Configuration.getMaxLevel();
        
                    int x = cx + i * 20 - requirements.size() * 10 + 2;
                    int y = cy + 15;
                    int u = Math.min(requirement.level, maxLevel - 1) / (maxLevel / 4) * 16 + 176;
                    int v = requirement.skill.index * 16 + 128;
        
                    minecraft.textureManager.bind(SkillScreen.RESOURCES);
                    blit(stack, x, y, u, v, 16, 16);
        
                    String level = Integer.toString(requirement.level);
                    boolean met = SkillModel.get().getSkillLevel(requirement.skill) >= requirement.level;
                    minecraft.font.drawShadow(stack, level, x + 17 - minecraft.font.width(level), y + 9, met ? 0x55FF55 : 0xFF5555);
                }
            }
        }
    }
    
    @SubscribeEvent
    public void onClientTick(TickEvent.ClientTickEvent event)
    {
        if (showTicks > 0) showTicks--;
    }
    
    // Show Warning
    
    public static void showWarning(ResourceLocation resource)
    {
        requirements = Arrays.asList(Configuration.getRequirements(resource));
        showTicks = 60;
    }
}