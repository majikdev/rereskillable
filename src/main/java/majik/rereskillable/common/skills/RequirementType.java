package majik.rereskillable.common.skills;

import majik.rereskillable.Configuration;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public enum RequirementType {
    USE(Configuration::getRequirements),
    CRAFT(Configuration::getCraftRequirements),
    ATTACK(Configuration::getEntityAttackRequirements);
    private final Function<ResourceLocation, Requirement[]> requirementMap;
    RequirementType(Function<ResourceLocation, Requirement[]> requirementMap){
        this.requirementMap = requirementMap;
    }

    public Requirement[] getRequirements(ResourceLocation resource) {
        return this.requirementMap.apply(resource);
    }
}