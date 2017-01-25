package graphic.assets;

import graphic.ImageLoader;
import graphic.SpriteSheet;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Created by Артем on 13.01.2017.
 */
public class CuteAngelAssets {
    private static final int width = 64, height = 64;
    private static String path = "/Cute_Angel_Creature.png";
    private static SpriteSheet sheet;

    public static BufferedImage stand, attack, moveLeft, moveRight, moveTop, moveDown, watchDown, watchTop, watchLeft, watchRight;

    private CuteAngelAssets(){

    }

    public static void init(){
        sheet = new SpriteSheet(ImageLoader.loadImage(path));
        stand = cutPicture(1,1);
    }

    private static BufferedImage cutPicture(int row, int column){
        return sheet.crop( (column-1)*height +1, (row-1)*width+1, width, height);
    }

}
