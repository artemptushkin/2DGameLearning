package entities.battle_mobs;

import entities.fractions.Fraction;
import graphic.assets.Assets;

import java.awt.*;

/**
 * Created by Артем on 17.01.2017.
 */
public class HeroUnit extends BattleUnit {
    public HeroUnit(Fraction fraction, int x, int y) {
        super(fraction,x, y);
        currentImage = Assets.PlayerAssets.watchingBottom;
        speed = 3;

        rotateCurrentImage();
    }

    @Override
    public void tick() {

    }
}
