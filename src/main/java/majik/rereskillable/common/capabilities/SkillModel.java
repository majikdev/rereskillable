package majik.rereskillable.common.capabilities;

import majik.rereskillable.Configuration;
import majik.rereskillable.common.network.NotifyWarning;
import majik.rereskillable.common.skills.Requirement;
import majik.rereskillable.common.skills.Skill;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class SkillModel implements INBTSerializable<CompoundNBT>
{
    public int[] skillLevels = new int[]{1, 1, 1, 1, 1, 1, 1, 1};
    
    // Get Level for Skill
    
    public int getSkillLevel(Skill skill)
    {
        return skillLevels[skill.index];
    }
    
    // Set Level for Skill
    
    public void increaseSkillLevel(Skill skill)
    {
        skillLevels[skill.index]++;
    }
    
    // Can Player Use Item
    
    public boolean canUseItem(PlayerEntity player, ItemStack item)
    {
        Requirement[] requirements = Configuration.getRequirements(item);
        
        if (requirements != null)
        {
            for (Requirement requirement : requirements)
            {
                if (getSkillLevel(requirement.skill) < requirement.level)
                {
                    if (player instanceof ServerPlayerEntity)
                    {
                        NotifyWarning.send(player, item);
                    }
                    
                    return false;
                }
            }
        }
        
        return true;
    }
    
    // Get Player Skills
    
    public static SkillModel get(PlayerEntity player)
    {
        return player.getCapability(SkillCapability.INSTANCE).orElseThrow(() ->
                new IllegalArgumentException("Player " + player.getName().getContents() + " does not have a Skill Model!")
        );
    }
    
    // Get Local Player Skills
    
    public static SkillModel get()
    {
        return Minecraft.getInstance().player.getCapability(SkillCapability.INSTANCE).orElseThrow(() ->
                new IllegalArgumentException("Player does not have a Skill Model!")
        );
    }
    
    // Serialise and Deserialise
    
    @Override
    public CompoundNBT serializeNBT()
    {
        CompoundNBT compound = new CompoundNBT();
        compound.putInt("mining", skillLevels[0]);
        compound.putInt("gathering", skillLevels[1]);
        compound.putInt("attack", skillLevels[2]);
        compound.putInt("defense", skillLevels[3]);
        compound.putInt("building", skillLevels[4]);
        compound.putInt("farming", skillLevels[5]);
        compound.putInt("agility", skillLevels[6]);
        compound.putInt("magic", skillLevels[7]);
        
        return compound;
    }
    
    @Override
    public void deserializeNBT(CompoundNBT nbt)
    {
        skillLevels[0] = nbt.getInt("mining");
        skillLevels[1] = nbt.getInt("gathering");
        skillLevels[2] = nbt.getInt("attack");
        skillLevels[3] = nbt.getInt("defense");
        skillLevels[4] = nbt.getInt("building");
        skillLevels[5] = nbt.getInt("farming");
        skillLevels[6] = nbt.getInt("agility");
        skillLevels[7] = nbt.getInt("magic");
    }
}