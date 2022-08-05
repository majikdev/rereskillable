# Rereskillable

Rereskillable is a mod for Minecraft 1.18.2 that adds upgradeable skills and allows pack developers to lock different features behind those skills. Rereskillable is also compatible with Curios API - items that are put into curios slots will behave the same as armour. Heavily inspired by Reskillable and Skillable.

This mod allows you to lock:
- Items (using, wearing)
- Blocks (breaking, placing)
- Entities (mounting)

Separate lists for locking crafting certain items and attacking certain entities are available.

## Configuration

Config options include:
- Disabling sheep wool drops
- Changing the starting upgrade cost in XP levels
- Changing the maximum skill level
- Specifying a list of skill locks

## Skill Locks

A new lock can be added by adding a string to the skillLocks list:
```
"mod:id skill:level"
```
For example:
```
"minecraft:ender_pearl magic:5"
"minecraft:bow attack:3 agility:2"
"minecraft:horse agility:10"
```

The craftSkillLocks list is in the same format but restricts which items you can craft.  

The attackSkillLocks list is in the same format but restricts which entity types you can damage.  

Remember that the config file is only parsed when you fully restart the game. Client and server must have the same config file, just distribute it as part of your mod pack's default files. 

## Commands

To set a skill level:
```
/skills set [player] [skill] [level]
```
To get a skill level:
```
/skills get [player] [skill]
```

## Changing Skill Names

You can use a resource pack to change how the skills appear in game. 
Their names are controlled by the [lang file](src/main/resources/assets/rereskillable/lang) and the icons 
are on the [skills.png texture](src/main/resources/assets/rereskillable/textures/gui/skills.png) (each skill has 4 icons used as you level up, they're all 16x16).  

You can use the `skillAliases` list in the config to replace the skill names used to write your configuration to match the displayed ones so it's easier to think about. 
Format: key=value. Valid values: attack, defence, mining, gathering, farming, building, agility, magic

## Known Issues

- You can't lock the Totem of Undying from being used.
- You can block items from being used regardless of skill only by setting the requirement to a level above max skill level (example: "minecraft:shield magic:69")

## Modpacks

Feel free to use this mod in any modpacks, but please do add a credit to the modpack's page.

## Credits

Reskillable and Skillable
