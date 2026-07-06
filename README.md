# Fire-Forge-All

`Fire-Forge-All` 是一个《杀戮尖塔》一代 Mod。

它会修改火堆里的“锻造”功能：玩家点击“锻造”后，不再进入原版的单卡选择界面，而是直接升级牌组里所有可以升级的卡牌。

## 功能

- 复用原版火堆的“锻造”按钮，不新增额外 UI。
- 点击“锻造”后，一次性升级所有 `card.canUpgrade()` 的卡牌。
- 每张牌升级后都会执行瓶装牌升级检查。
- 播放升级音效。
- 显示升级闪光效果。
- 最多短暂展示 10 张升级后的卡牌作为反馈。

## 依赖

这个项目使用本地 jar 依赖，因为《杀戮尖塔》的游戏本体 jar 不是公开 Maven 依赖。

构建前需要把下面三个文件放到 `lib/` 目录：

```text
lib/
  desktop-1.0.jar
  ModTheSpire.jar
  BaseMod.jar
```

来源说明：

- `desktop-1.0.jar`：来自你自己的《杀戮尖塔》游戏安装目录。
- `ModTheSpire.jar`：来自 ModTheSpire 的 Release。
- `BaseMod.jar`：来自 BaseMod 的 Release 或 Steam Workshop 安装目录。

## 构建

项目使用 Maven。

当前机器上的 Maven 路径是：

```powershell
D:\apache-maven-3.9.9\bin\mvn.cmd
```

构建命令：

```powershell
cd D:\MassSmith
D:\apache-maven-3.9.9\bin\mvn.cmd package
```

构建成功后，Mod jar 会生成在：

```text
target/fire-forge-all-1.0.0.jar
```

## 安装

把构建产物复制到《杀戮尖塔》的 `mods` 目录：

```powershell
Copy-Item D:\MassSmith\target\fire-forge-all-1.0.0.jar "D:\SteamLibrary\steamapps\common\SlayTheSpire\mods\"
```

然后通过 ModTheSpire 启动游戏，并启用 `Fire-Forge-All`。

## 实现方式

Mod patch 了原版方法：

```java
com.megacrit.cardcrawl.ui.campfire.SmithOption.useOption()
```

当玩家在火堆点击“锻造”时，patch 会跳过原版单卡选择流程，改为加入一个自定义的 `MassSmithEffect`。

这个 effect 会遍历：

```java
AbstractDungeon.player.masterDeck.group
```

对每张可以升级的牌执行：

```java
card.upgrade();
AbstractDungeon.player.bottledCardUpgradeCheck(card);
```

## 项目结构

```text
Fire-Forge-All/
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

## 注意

- 这个 Mod 不会新增火堆按钮。
- 原版“锻造”按钮会变成“升级全部可升级卡牌”。
- 如果牌组里没有可升级卡牌，锻造操作会直接结束。
- 缺少 `lib/` 里的三个依赖 jar 时，项目无法编译。
