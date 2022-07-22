package majik.rereskillable.client;

import majik.rereskillable.client.screen.SkillScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.KeyMapping;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Keybind
{
    private final KeyMapping openKey = new KeyMapping("key.skills", KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, 71, "Rereskillable");
    
    public Keybind()
    {
        ClientRegistry.registerKeyBinding(openKey);
    }
    
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event)
    {
        Minecraft minecraft = Minecraft.getInstance();
        
        if (openKey.consumeClick() && minecraft.screen == null)
        {
            minecraft.setScreen(new SkillScreen());
        }
    }
}