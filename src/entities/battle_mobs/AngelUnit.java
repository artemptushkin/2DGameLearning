package entities.battle_mobs;

import entities.fractions.Fraction;
import graphic.assets.Assets;
import graphic.assets.CuteAngelAssets;

import java.awt.*;

/**
 * Created by Артем on 17.01.2017.
 */
public class AngelUnit extends BattleUnit {
    public AngelUnit(Fraction fraction, int x, int y) {
        super(fraction, x, y);

        currentImage = CuteAngelAssets.stand;
        speed = 2;

        rotateCurrentImage();
    }

    @Override
    public void tick() {

    }
}
