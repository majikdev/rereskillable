package majik.rereskillable.client.screen;

import majik.rereskillable.client.screen.buttons.TabButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class InventoryTabs
{
    // Initialise GUI
    
    @SubscribeEvent
    public void onInitGui(ScreenEvent.InitScreenEvent.Post event)
    {
        Screen screen = event.getScreen();
        
        if (screen instanceof InventoryScreen || screen instanceof CreativeModeInventoryScreen || screen instanceof SkillScreen)
        {
            boolean creativeOpen = screen instanceof CreativeModeInventoryScreen;
            boolean skillsOpen = screen instanceof SkillScreen;
            int x = (screen.width - (creativeOpen ? 195 : 176)) / 2 - 28;
            int y = (screen.height - (creativeOpen ? 136 : 166)) / 2;
            
            event.addListener(new TabButton(x, y + 7, TabButton.TabType.INVENTORY, !skillsOpen));
            event.addListener(new TabButton(x, y + 36, TabButton.TabType.SKILLS, skillsOpen));
        }
    }
}