package masssmith.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import java.util.ArrayList;
import java.util.List;

public class MassSmithEffect extends AbstractGameEffect {
    private boolean upgradedCards;

    public MassSmithEffect() {
        this.duration = Settings.ACTION_DUR_MED;
        this.color = Color.WHITE.cpy();
    }

    @Override
    public void update() {
        if (!this.upgradedCards) {
            this.upgradedCards = true;
            upgradeAllCards();
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration <= 0.0F) {
            AbstractRoom.waitTimer = 0.0F;
            this.isDone = true;
        }
    }

    private void upgradeAllCards() {
        List<AbstractCard> upgraded = new ArrayList<AbstractCard>();

        for (AbstractCard card : AbstractDungeon.player.masterDeck.group) {
            if (card.canUpgrade()) {
                card.upgrade();
                AbstractDungeon.player.bottledCardUpgradeCheck(card);
                upgraded.add(card);
            }
        }

        if (!upgraded.isEmpty()) {
            CardCrawlGame.sound.play("CARD_UPGRADE");
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
            showBriefCards(upgraded);
        }
    }

    private void showBriefCards(List<AbstractCard> upgraded) {
        int cardsToShow = Math.min(upgraded.size(), 10);
        float centerX = Settings.WIDTH / 2.0F;
        float y = Settings.HEIGHT * 0.55F;
        float spacing = 90.0F * Settings.scale;
        float startX = centerX - spacing * (cardsToShow - 1) / 2.0F;

        for (int i = 0; i < cardsToShow; i++) {
            AbstractCard preview = upgraded.get(i).makeStatEquivalentCopy();
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(preview, startX + spacing * i, y));
        }
    }

    @Override
    public void render(SpriteBatch sb) {
    }

    @Override
    public void dispose() {
    }
}
