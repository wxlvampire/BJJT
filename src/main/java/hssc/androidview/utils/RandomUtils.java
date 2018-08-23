package hssc.androidview.utils;

import java.util.Random;

/**
 * Created by li on 2017/9/5.
 */
public class RandomUtils {
    public static int nextInt(final int min, final int max){
        Random rand= new Random();
        int tmp = Math.abs(rand.nextInt());
        return tmp % (max - min + 1) + min;
    }
}
