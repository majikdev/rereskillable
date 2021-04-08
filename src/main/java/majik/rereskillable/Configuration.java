package majik.rereskillable;

import majik.rereskillable.common.skills.Requirement;
import majik.rereskillable.common.skills.Skill;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;

public class Configuration
{
    private static final ForgeConfigSpec CONFIG_SPEC;
    private static final ForgeConfigSpec.BooleanValue DISABLE_WOOL;
    private static final ForgeConfigSpec.IntValue STARTING_COST;
    private static final ForgeConfigSpec.IntValue MAXIMUM_LEVEL;
    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> SKILL_LOCKS;
    
    private static boolean disableWool;
    private static int startingCost;
    private static int maximumLevel;
    private static final Map<String, Requirement[]> skillLocks = new HashMap<>();
    
    static
    {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        
        builder.comment("Disable wool drops to force the player to get shears.");
        DISABLE_WOOL = builder.define("disableWoolDrops", true);
        
        builder.comment("Starting cost of upgrading to level 2, in levels.");
        STARTING_COST = builder.defineInRange("startingCost", 2, 1, 10);
        
        builder.comment("Maximum level each skill can be upgraded to.");
        MAXIMUM_LEVEL = builder.defineInRange("maximumLevel", 32, 2, 100);
        
        builder.comment("List of item and block skill requirements.", "Format: mod:id skill:level", "Valid skills: attack, defence, mining, gathering, farming, building, agility, magic");
        SKILL_LOCKS = builder.defineList("skillLocks", Arrays.asList("minecraft:iron_shovel gathering:5", "minecraft:iron_axe gathering:5", "minecraft:iron_sword attack:5", "minecraft:iron_pickaxe mining:5", "minecraft:iron_hoe farming:5", "minecraft:iron_helmet defence:5", "minecraft:iron_chestplate defence:5", "minecraft:iron_leggings defence:5", "minecraft:iron_boots defence:5", "minecraft:golden_shovel gathering:5 magic:5", "minecraft:golden_axe gathering:5 magic:5", "minecraft:golden_sword attack:5 magic:5", "minecraft:golden_pickaxe mining:5 magic:5", "minecraft:golden_hoe farming:5 magic:5", "minecraft:golden_helmet defence:5 magic:5", "minecraft:golden_chestplate defence:5 magic:5", "minecraft:golden_leggings defence:5 magic:5", "minecraft:golden_boots defence:5 magic:5", "minecraft:diamond_shovel gathering:15", "minecraft:diamond_axe gathering:15", "minecraft:diamond_sword attack:15", "minecraft:diamond_pickaxe mining:15", "minecraft:diamond_hoe farming:15", "minecraft:diamond_helmet defence:15", "minecraft:diamond_chestplate defence:15", "minecraft:diamond_leggings defence:15", "minecraft:diamond_boots defence:15", "minecraft:netherite_shovel gathering:25", "minecraft:netherite_axe gathering:25", "minecraft:netherite_sword attack:25", "minecraft:netherite_pickaxe mining:25", "minecraft:netherite_hoe farming:25", "minecraft:netherite_helmet defence:25", "minecraft:netherite_chestplate defence:25", "minecraft:netherite_leggings defence:25", "minecraft:netherite_boots defence:25", "minecraft:shears gathering:5", "minecraft:fishing_rod gathering:8", "minecraft:shield defence:8", "minecraft:bow attack:8", "minecraft:ender_pearl magic:8", "minecraft:ender_eye magic:16", "minecraft:elytra defence:16 agility:24", "minecraft:lead farming:5", "minecraft:end_crystal building:24 magic:32"), obj -> true);
        
        CONFIG_SPEC = builder.build();
    }
    
    // Initialise
    
    public static void load()
    {
        disableWool = DISABLE_WOOL.get();
        startingCost = STARTING_COST.get();
        maximumLevel = MAXIMUM_LEVEL.get();
        
        for (String line : SKILL_LOCKS.get())
        {
            String[] entry = line.split(" ");
            Requirement[] requirements = new Requirement[entry.length - 1];
            
            for (int i = 1; i < entry.length; i++)
            {
                String[] req = entry[i].split(":");
                
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
    
    public static int getStartCost()
    {
        return startingCost;
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