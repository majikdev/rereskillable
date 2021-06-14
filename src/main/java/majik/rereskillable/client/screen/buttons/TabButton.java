package majik.rereskillable.client.screen.buttons;

import com.mojang.blaze3d.matrix.MatrixStack;
import majik.rereskillable.client.screen.SkillScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.util.text.StringTextComponent;

public class TabButton extends AbstractButton
{
    private final boolean selected;
    private final TabType type;
    
    public TabButton(int x, int y, TabType type, boolean selected)
    {
        super(x, y, 31, 28, StringTextComponent.EMPTY);
        
        this.type = type;
        this.selected = selected;
    }
    
    // Render
    
    @Override
    public void render(MatrixStack stack, int mouseX, int mouseY, float partialTicks)
    {
        Minecraft minecraft = Minecraft.getInstance();
        active = !(minecraft.screen instanceof InventoryScreen) || !((InventoryScreen) minecraft.screen).getRecipeBookComponent().isVisible();
        
        if (active)
        {
            minecraft.textureManager.bind(SkillScreen.RESOURCES);
    
            blit(stack, x, y, selected ? 31 : 0, 166, width, height);
            blit(stack, x + (selected ? 8 : 10), y + 6, 240, 128 + type.iconIndex * 16, 16, 16);
        }
    }
    
    // Press
    
    @Override
    public void onPress()
    {
        Minecraft minecraft = Minecraft.getInstance();
        
        switch (type)
        {
            case INVENTORY:
                minecraft.setScreen(new InventoryScreen(minecraft.player));
                break;
                
            case SKILLS:
                minecraft.setScreen(new SkillScreen());
                break;
        }
    }
    
    public enum TabType
    {
        INVENTORY (0),
        SKILLS (1);
        
        public final int iconIndex;
        
        TabType(int index)
        {
            iconIndex = index;
        }
    }
}