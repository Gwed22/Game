package pingpong_dohhuynhanhvu_ce171446;

import java.util.Random;

/**
 *
 * @author Do Huynh Anh Vu - CE171446
 */
public class Randomizer {

    private static Random rand = new Random();

    public static int random(int min, int max) {
        return rand.nextInt((max - min) + 1) + min;
    }

}
