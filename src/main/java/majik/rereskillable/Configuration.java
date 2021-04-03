package majik.rereskillable;

import majik.rereskillable.common.skills.Requirement;
import majik.rereskillable.common.skills.Skill;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        
        builder.comment("List of item skill requirements.", "Format: mod:item_id skill:level", "Valid skills: attack, defense, mining, gathering, farming, building, agility, magic");
        SKILL_LOCKS = builder.defineList("skillLocks", Arrays.asList("minecraft:iron_sword attack:8", "minecraft:iron_axe gathering:8", "minecraft:iron_pickaxe mining:8", "minecraft:iron_shovel mining:8",
            "minecraft:iron_hoe farming:8", "minecraft:iron_helmet defence:8", "minecraft:iron_chestplate defence:8", "minecraft:iron_leggings defence:8", "minecraft:iron_boots defence:8",
            "minecraft:diamond_sword attack:16", "minecraft:diamond_axe gathering:16", "minecraft:diamond_pickaxe mining:16", "minecraft:diamond_shovel mining:16", "minecraft:diamond_hoe farming:16",
            "minecraft:diamond_helmet defence:16", "minecraft:diamond_chestplate defence:16", "minecraft:diamond_leggings defence:16", "minecraft:diamond_boots defence:16", "minecraft:netherite_sword attack:24",
            "minecraft:netherite_axe gathering:24", "minecraft:netherite_pickaxe mining:24", "minecraft:netherite_shovel mining:24", "minecraft:netherite_hoe farming:24", "minecraft:netherite_helmet defence:24",
            "minecraft:netherite_chestplate defence:24", "minecraft:netherite_leggings defence:24", "minecraft:netherite_boots defence:24", "minecraft:totem_of_undying magic:16", "minecraft:elytra agility:24"), obj -> true);
        
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
    
    public static Requirement[] getRequirements(ItemStack item)
    {
        return skillLocks.get(item.getItem().getRegistryName().toString());
    }
    
    // Get Configuration
    
    public static ForgeConfigSpec getConfig()
    {
        return CONFIG_SPEC;
    }
}