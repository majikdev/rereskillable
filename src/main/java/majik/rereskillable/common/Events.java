package majik.rereskillable.common;

import majik.rereskillable.Configuration;
import majik.rereskillable.common.capabilities.SkillModel;
import majik.rereskillable.common.capabilities.SkillProvider;
import majik.rereskillable.common.network.SyncToClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEquipmentChangeEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Events
{
    // Player Interact
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event)
    {
        if (event instanceof PlayerInteractEvent.RightClickEmpty || event instanceof PlayerInteractEvent.LeftClickEmpty) return;
        
        PlayerEntity player = event.getPlayer();
        
        if (!player.isCreative() && !SkillModel.get(player).canUseItem(player, event.getItemStack()))
        {
            event.setCanceled(true);
        }
    }
    
    // Player Attack
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerAttack(AttackEntityEvent event)
    {
        PlayerEntity player = event.getPlayer();
        
        if (!player.isCreative() && !SkillModel.get(player).canUseItem(player, player.getMainHandItem()))
        {
            event.setCanceled(true);
        }
    }
    
    // Player Equip
    
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onPlayerEquip(LivingEquipmentChangeEvent event)
    {
        if (event.getEntity() instanceof PlayerEntity)
        {
            PlayerEntity player = (PlayerEntity) event.getEntity();
            
            if (!player.isCreative() && event.getSlot().getType() == EquipmentSlotType.Group.ARMOR)
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
    
    // Entity Drops
    
    @SubscribeEvent
    public void onEntityDrops(LivingDropsEvent event)
    {
        if (Configuration.getDisableWool() && event.getEntity() instanceof SheepEntity)
        {
            event.getDrops().removeIf(item -> ItemTags.getAllTags().getTag(new ResourceLocation("minecraft", "wool")).contains(item.getItem().getItem()));
        }
    }
    
    // Capabilities
    
    @SubscribeEvent
    public void onAttachCapabilities(AttachCapabilitiesEvent<Entity> event)
    {
        if (event.getObject() instanceof PlayerEntity)
        {
            SkillModel skillModel = new SkillModel();
            SkillProvider provider = new SkillProvider(skillModel);
            
            event.addCapability(new ResourceLocation("rereskillable", "cap_skills"), provider);
            event.addListener(provider::invalidate);
        }
    }
    
    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone event)
    {
        SkillModel.get(event.getPlayer()).skillLevels = SkillModel.get(event.getOriginal()).skillLevels;
    }
    
    @SubscribeEvent
    public void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent e)
    {
        SyncToClient.send(e.getPlayer());
    }
    
    @SubscribeEvent
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent e)
    {
        SyncToClient.send(e.getPlayer());
    }
    
    @SubscribeEvent
    public void onChangeDimension(PlayerEvent.PlayerChangedDimensionEvent e)
    {
        SyncToClient.send(e.getPlayer());
    }
}