package graphic.assets;

import graphic.ImageLoader;
import graphic.SpriteSheet;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

/**
 * Created by Артем on 10.01.2017.
 */
public class Assets {
    private static final int width = 32, height = 32;
    private static String path = "/level.png";

    private Assets(){

    }

    public static BufferedImage grass, stone, dirt, tree, battleGrass, battleGrassToMove;

    public static void init(){
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage(path));

        grass = sheet.crop(width,0, width, height);
        dirt = sheet.crop(width*2,0, width, height);
        stone = sheet.crop(width*3,0, width, height);
        tree = sheet.crop(width*4,0,width,height*2);
        battleGrass = sheet.crop(width*5,0, width,height);
        battleGrassToMove = sheet.crop(width*6,0, width, height);

        PlayerAssets.loadPlayer();
    }

    public static BufferedImage rotateImage(BufferedImage bufferedImage){

        AffineTransform tx = AffineTransform.getScaleInstance(-1, 1);
        tx.translate(-bufferedImage.getWidth(null), 0);
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        bufferedImage = op.filter(bufferedImage, null);

        return bufferedImage;
    }

    public static class PlayerAssets{
        public static BufferedImage watchingBottom, watchingRight, watchingLeft, stepOneLeft, stepTwoLeft, stepTwoRight, watchingTop, stepOneRight,
                stepOneTop, stepTwoTop, stepTwoBottom, stepOneBottom;
        private int sheetWidth = 15; //sprites
        private int sheetHeight = 4;
        private static String playerSheetPath = "/Magican_Player.png";
        private static SpriteSheet sheet;

        private static void loadPlayer(){

            sheet = new SpriteSheet(ImageLoader.loadImage(playerSheetPath));
            watchingBottom = cutPicture(1,1);
            watchingLeft = cutPicture(1, 14);
            watchingRight = cutPicture(1, 4);
            watchingTop = cutPicture(1, 9);

            stepOneLeft = cutPicture(1,8);
            stepTwoLeft = cutPicture(1,7);

            stepOneRight = cutPicture(1,5);
            stepTwoRight = cutPicture(1,6);

            stepOneTop = cutPicture(1, 10);
            stepTwoTop = cutPicture(1, 11);

            stepOneBottom = cutPicture(1, 12);
            stepTwoBottom = cutPicture(1,13);
        }

        //start with 1
        public static BufferedImage cutPicture(int row, int column){
            return sheet.crop( (column-1)*height, (row-1)*width, width, height);
        }
    }
}
