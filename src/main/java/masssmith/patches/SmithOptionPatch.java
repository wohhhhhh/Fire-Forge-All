package masssmith.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.campfire.SmithOption;
import masssmith.effects.MassSmithEffect;

@SpirePatch(clz = SmithOption.class, method = "useOption")
public class SmithOptionPatch {
    @SpirePrefixPatch
    public static SpireReturn<Void> upgradeEveryCard(SmithOption __instance) {
        AbstractDungeon.effectList.add(new MassSmithEffect());
        return SpireReturn.Return();
    }
}
