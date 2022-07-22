package majik.rereskillable.common.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class SkillCapability
{
    public static Capability<SkillModel> INSTANCE = CapabilityManager.get(new CapabilityToken<>(){});
}