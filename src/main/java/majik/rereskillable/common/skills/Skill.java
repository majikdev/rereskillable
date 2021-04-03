package majik.rereskillable.common.skills;

public enum Skill
{
    MINING (0, "skill.mining"),
    GATHERING (1, "skill.gathering"),
    ATTACK (2, "skill.attack"),
    DEFENCE(3, "skill.defence"),
    BUILDING (4, "skill.building"),
    FARMING (5, "skill.farming"),
    AGILITY (6, "skill.agility"),
    MAGIC (7, "skill.magic");
    
    public final int index;
    public final String displayName;
    
    Skill(int index, String name)
    {
        this.index = index;
        this.displayName = name;
    }
}