package majik.rereskillable.common;

import majik.rereskillable.common.capabilities.SkillModel;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import top.theillusivec4.curios.api.event.CurioChangeEvent;

public class Curios
{
    // Player Equip Curio
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerEquipCurio(CurioChangeEvent event)
    {
        if (event.getEntity() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            
            if (!player.isCreative())
            {
                ItemStack item = event.getTo();
                
                if (!SkillModel.get(player).canUseItem(player, item))
                {
                    player.drop(item.copy(), false);
                    item.setCount(0);
                    
                    /*if (!player.addItem(item))
                    {
                        player.drop(item.copy(), false);
                        item.setCount(0);
                    }*/
                }
            }
        }
    }
}