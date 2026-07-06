# Mass Smith

Mass Smith is a small Slay the Spire 1 mod that changes the campfire Smith option.

Instead of opening the normal single-card upgrade screen, Smith immediately upgrades every upgradable card in the player's master deck.

## Features

- Replaces the campfire Smith action with a mass upgrade.
- Upgrades every card where `card.canUpgrade()` returns true.
- Runs bottled-card upgrade checks after upgrading each card.
- Plays the normal card upgrade sound.
- Shows an upgrade shine effect.
- Briefly previews up to 10 upgraded cards.

## Requirements

This project is intentionally set up with local jar dependencies, because Slay the Spire's game jar is not a public Maven dependency.

Put these files in `lib/` before building:

```text
lib/
  desktop-1.0.jar
  ModTheSpire.jar
  BaseMod.jar
```

Where to get them:

- `desktop-1.0.jar`: from your own Slay the Spire install directory.
- `ModTheSpire.jar`: from the ModTheSpire release page.
- `BaseMod.jar`: from the BaseMod release page or Steam Workshop install.

## Build

This project uses Maven.

On this machine, Maven is available at:

```powershell
D:\apache-maven-3.9.9\bin\mvn.cmd
```

Build the mod:

```powershell
cd D:\MassSmith
D:\apache-maven-3.9.9\bin\mvn.cmd package
```

The built jar will be created at:

```text
target/masssmith-1.0.0.jar
```

## Install

Copy the built jar into your Slay the Spire `mods` directory:

```powershell
Copy-Item D:\MassSmith\target\masssmith-1.0.0.jar "D:\SteamLibrary\steamapps\common\SlayTheSpire\mods\"
```

Then launch the game through ModTheSpire and enable `Mass Smith`.

## How It Works

The mod patches:

```java
com.megacrit.cardcrawl.ui.campfire.SmithOption.useOption()
```

When the player clicks Smith at a campfire, the patch skips the original single-card selection flow and adds a custom `MassSmithEffect`.

The effect loops through:

```java
AbstractDungeon.player.masterDeck.group
```

For every upgradable card, it calls:

```java
card.upgrade();
AbstractDungeon.player.bottledCardUpgradeCheck(card);
```

## Project Structure

```text
MassSmith/
  pom.xml
  lib/
    README.md
  src/
    main/
      java/
        masssmith/
          effects/
            MassSmithEffect.java
          patches/
            SmithOptionPatch.java
      resources/
        ModTheSpire.json
```

## Notes

- The mod does not add a new campfire button.
- The original Smith button is reused.
- If there are no upgradable cards, the Smith action simply finishes without upgrading anything.
- The project will not compile until the three required jar files are placed in `lib/`.
