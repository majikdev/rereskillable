package majik.rereskillable.common.skills;

public class Requirement
{
    public final Skill skill;
    public final int level;
    
    public Requirement(Skill skill, int level)
    {
        this.skill = skill;
        this.level = level;
    }
}