package majik.rereskillable;

import majik.rereskillable.common.skills.Requirement;
import majik.rereskillable.common.skills.Skill;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class Configuration
{
    private static final ForgeConfigSpec CONFIG_SPEC;
    private static final ForgeConfigSpec.BooleanValue DISABLE_WOOL;
    private static final ForgeConfigSpec.BooleanValue DEATH_RESET;
    private static final ForgeConfigSpec.IntValue STARTING_COST;
    private static final ForgeConfigSpec.IntValue COST_INCREASE;
    private static final ForgeConfigSpec.IntValue MAXIMUM_LEVEL;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> SKILL_LOCKS;
    
    private static boolean disableWool;
    private static boolean deathReset;
    private static int startingCost;
    private static int costIncrease;
    private static int maximumLevel;
    private static final Map<String, Requirement[]> skillLocks = new HashMap<>();
    
    static
    {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        
        builder.comment("Disable wool drops to force the player to get shears.");
        DISABLE_WOOL = builder.define("disableWoolDrops", true);
    
        builder.comment("Reset all skills to 1 when a player dies.");
        DEATH_RESET = builder.define("deathSkillReset", false);
        
        builder.comment("Starting cost of upgrading to level 2, in levels.");
        STARTING_COST = builder.defineInRange("startingCost", 2, 0, 10);
        
        builder.comment("Amount of levels added to the cost with each upgrade (use 0 for constant cost).");
        COST_INCREASE = builder.defineInRange("costIncrease", 1, 0, 10);
        
        builder.comment("Maximum level each skill can be upgraded to.");
        MAXIMUM_LEVEL = builder.defineInRange("maximumLevel", 32, 2, 100);
        
        builder.comment("List of item and block skill requirements.", "Format: mod:id skill:level", "Valid skills: attack, defence/defense, mining, gathering, farming, building, agility, magic");
        SKILL_LOCKS = builder.defineList("skillLocks", Arrays.asList("minecraft:iron_sword attack:5", "minecraft:iron_shovel gathering:5", "minecraft:iron_pickaxe mining:5", "minecraft:iron_axe gathering:5", "minecraft:iron_hoe farming:5", "minecraft:iron_helmet defence:5", "minecraft:iron_chestplate defence:5", "minecraft:iron_leggings defence:5", "minecraft:iron_boots defence:5", "minecraft:diamond_sword attack:15", "minecraft:diamond_shovel gathering:15", "minecraft:diamond_pickaxe mining:15", "minecraft:diamond_axe gathering:15", "minecraft:diamond_hoe farming:15", "minecraft:diamond_helmet defence:15", "minecraft:diamond_chestplate defence:15", "minecraft:diamond_leggings defence:15", "minecraft:diamond_boots defence:15", "minecraft:netherite_sword attack:30", "minecraft:netherite_shovel gathering:30", "minecraft:netherite_pickaxe mining:30", "minecraft:netherite_axe gathering:30", "minecraft:netherite_hoe farming:30", "minecraft:netherite_helmet defence:30", "minecraft:netherite_chestplate defence:30", "minecraft:netherite_leggings defence:30", "minecraft:netherite_boots defence:30", "minecraft:fishing_rod gathering:5", "minecraft:shears gathering:5", "minecraft:lead farming:5", "minecraft:bow attack:5 agility:3", "minecraft:turtle_helmet defence:10", "minecraft:shield defence:5", "minecraft:crossbow attack:5 agility:5", "minecraft:trident attack:15 agility:10", "minecraft:golden_apple magic:5", "minecraft:enchanted_golden_apple magic:10", "minecraft:ender_pearl magic:5", "minecraft:ender_eye magic:10", "minecraft:piston building:5", "minecraft:sticky_piston building:10", "minecraft:tnt building:5", "minecraft:ender_chest magic:15", "minecraft:enchanting_table magic:10", "minecraft:anvil building:5", "minecraft:chipped_anvil building:5", "minecraft:damaged_anvil building:5", "minecraft:smithing_table building:10", "minecraft:end_crystal magic:20", "minecraft:boat agility:5", "minecraft:minecart agility:10", "minecraft:elytra agility:20", "minecraft:horse agility:10", "minecraft:donkey agility:10", "minecraft:mule agility:10", "minecraft:strider agility:15"), obj -> true);
        
        CONFIG_SPEC = builder.build();
    }
    
    // Initialise
    
    public static void load()
    {
        disableWool = DISABLE_WOOL.get();
        deathReset = DEATH_RESET.get();
        startingCost = STARTING_COST.get();
        costIncrease = COST_INCREASE.get();
        maximumLevel = MAXIMUM_LEVEL.get();
        
        for (String line : SKILL_LOCKS.get())
        {
            String[] entry = line.split(" ");
            Requirement[] requirements = new Requirement[entry.length - 1];
            
            for (int i = 1; i < entry.length; i++)
            {
                String[] req = entry[i].split(":");
                
                if (req[0].equalsIgnoreCase("defense"))
                {
                    req[0] = "defence";
                }
                
                requirements[i - 1] = new Requirement(Skill.valueOf(req[0].toUpperCase()), Integer.parseInt(req[1]));
            }
            
            skillLocks.put(entry[0], requirements);
        }
    }
    
    // Get Properties
    
    public static boolean getDisableWool()
    {
        return disableWool;
    }
    
    public static boolean getDeathReset()
    {
        return deathReset;
    }
    
    public static int getStartCost()
    {
        return startingCost;
    }
    
    public static int getCostIncrease()
    {
        return costIncrease;
    }
    
    public static int getMaxLevel()
    {
        return maximumLevel;
    }
    
    public static Requirement[] getRequirements(ResourceLocation key)
    {
        return skillLocks.get(key.toString());
    }
    
    public static ForgeConfigSpec getConfig()
    {
        return CONFIG_SPEC;
    }
}