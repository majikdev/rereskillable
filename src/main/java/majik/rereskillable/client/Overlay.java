package majik.rereskillable.client;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import majik.rereskillable.Configuration;
import majik.rereskillable.client.screen.SkillScreen;
import majik.rereskillable.common.capabilities.SkillCapability;
import majik.rereskillable.common.capabilities.SkillModel;
import majik.rereskillable.common.skills.Requirement;
import majik.rereskillable.common.skills.RequirementType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.opengl.GL11;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;

public class Overlay extends GuiComponent
{
    private static List<Requirement> requirements = null;
    private static int showTicks = 0;
    private static String messageKey = "";
    
    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.PreLayer event)
    {
        if (event.getOverlay() == ForgeIngameGui.EXPERIENCE_BAR_ELEMENT && showTicks > 0)
        {
            Minecraft minecraft = Minecraft.getInstance();
            
            if (minecraft.player.getCapability(SkillCapability.INSTANCE).isPresent())
            {
                PoseStack stack = event.getMatrixStack();
    
                RenderSystem.setShaderTexture(0, SkillScreen.RESOURCES);
                GL11.glEnable(GL11.GL_BLEND);
    
                int cx = event.getWindow().getGuiScaledWidth() / 2;
                int cy = event.getWindow().getGuiScaledHeight() / 4;
    
                blit(stack, cx - 71, cy - 4, 0, 194, 142, 40);
    
                String message = new TranslatableComponent(messageKey).getString();
                minecraft.font.drawShadow(stack, message, cx - minecraft.font.width(message) / 2, cy, 0xFF5555);
    
                for (int i = 0; i < requirements.size(); i++)
                {
                    Requirement requirement = requirements.get(i);
                    int maxLevel = Configuration.getMaxLevel();
        
                    int x = cx + i * 20 - requirements.size() * 10 + 2;
                    int y = cy + 15;
                    int u = Math.min(requirement.level, maxLevel - 1) / (maxLevel / 4) * 16 + 176;
                    int v = requirement.skill.index * 16 + 128;
        
                    RenderSystem.setShaderTexture(0, SkillScreen.RESOURCES);
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
    
    public static void showWarning(ResourceLocation resource, RequirementType type)
    {
        requirements = Arrays.asList(type.getRequirements(resource));
        messageKey = "overlay.message." + type.name().toLowerCase(Locale.ROOT);
        showTicks = 60;
    }
}