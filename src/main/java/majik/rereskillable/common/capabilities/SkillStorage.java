package majik.rereskillable.common.capabilities;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class SkillStorage implements Capability.IStorage<SkillModel>
{
    @Nullable
    @Override
    public Tag writeNBT(Capability<SkillModel> capability, SkillModel instance, Direction side)
    {
        return instance.serializeNBT();
    }
    
    @Override
    public void readNBT(Capability<SkillModel> capability, SkillModel instance, Direction side, Tag nbt)
    {
        instance.deserializeNBT((CompoundTag) nbt);
    }
}