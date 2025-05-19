package top.yourzi.lifefruit.utils;

import java.util.Random;
import java.lang.Math;

public class MixinUtils {
    public static float randomHurt(float hurt, Random random) {
        // Random random = new Random();
        double originalHurt = (double) hurt;
        double baseHurt = Math.floor(originalHurt);
        float realHurt = (float) baseHurt;

        double f = random.nextFloat();
        if (f < originalHurt - baseHurt) {
            realHurt++;
        }
        return realHurt;

    }
}
