package majik.rereskillable.common.capabilities;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class SkillStorage implements Capability.IStorage<SkillModel>
{
    @Nullable
    @Override
    public INBT writeNBT(Capability<SkillModel> capability, SkillModel instance, Direction side)
    {
        return instance.serializeNBT();
    }
    
    @Override
    public void readNBT(Capability<SkillModel> capability, SkillModel instance, Direction side, INBT nbt)
    {
        instance.deserializeNBT((CompoundNBT) nbt);
    }
}