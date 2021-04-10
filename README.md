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

## Planned Features

May add an item blacklist later, currently you can do that by setting it's requirement level above the max level.
