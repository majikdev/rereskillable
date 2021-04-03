package majik.rereskillable.client;

import majik.rereskillable.client.screen.SkillScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;

public class Keybind
{
    private final KeyBinding openKey = new KeyBinding("key.skills", KeyConflictContext.IN_GAME, InputMappings.Type.KEYSYM, 71, "Rereskillable");
    
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