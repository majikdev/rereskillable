# Rereskillable

Rereskillable is a mod for Minecraft 1.16.5 that adds upgradeable skills and allows pack developers to lock different features behind those skills. Rereskillable is also compatible with Curios API - items that are put into curios slots will behave the same as armour. Heavily inspired by Reskillable and Skillable.

This mod allows you to lock:
- Items (using, wearing)
- Blocks (breaking, placing)
- Entities (mounting)

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

## Commands

To set a skill level:
```
/skills set [player] [skill] [level]
```
To get a skill level:
```
/skills get [player] [skill]
```

## Known Issues

- You can't lock the Totem of Undying from being used.
- You can block items from being used regardless of skill only by setting the requirement to a level above max skill level (example: "minecraft:shield magic:69")

## Support

I stopped working on this mod and there will not be a Fabric port from me.

But if anyone wants to fork this mod or port it to Fabric, feel free to do it, you can do anything with this code.

## Modpacks

Feel free to use this mod in any modpacks, but please do add a credit to the modpack's page.

## Credits

Reskillable and Skillable

**NOTE: THIS REPOSITORY IS ARCHIVED AND WILL NOT BE UPDATED!**
