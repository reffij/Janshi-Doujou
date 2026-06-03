package engine.graphics;

import java.util.*;

import engine.builtin.shaders.*;

public class AnimationFactory {
    public static OffsetShader createDisintegrationShader(int width, int height, int totalFrames, double frameDuration) {
        List<int[]> xOffsets = new ArrayList<>();
        List<int[]> yOffsets = new ArrayList<>();
        List<int[]> cOffsets = new ArrayList<>();
        Random rng = new Random();

        for (int f = 0; f < totalFrames; f++) {
            int[] xFrame = new int[width * height];
            int[] yFrame = new int[width * height];
            int[] cFrame = new int[width * height];

            for (int i = 0; i < width * height; i++) {
                xFrame[i] = rng.nextInt(f + 1);
                yFrame[i] = rng.nextInt(f + 1) - f / 2;
                cFrame[i] = -f * 10;
            }

            xOffsets.add(xFrame);
            yOffsets.add(yFrame);
            cOffsets.add(cFrame);
        }

        return new OffsetShader(xOffsets, yOffsets, cOffsets, width, height, frameDuration, false);
    }

    public static OffsetShader createFloatingShader(int width, int height, int totalFrames, double frameDuration, int amplitude) {
        List<int[]> xOffsets = new ArrayList<>();
        List<int[]> yOffsets = new ArrayList<>();

        for (int f = 0; f < totalFrames; f++) {
            int[] xFrame = new int[width * height];
            int[] yFrame = new int[width * height];

            double angle = 2 * Math.PI * f / totalFrames;
            int dy = (int)(Math.sin(angle) * amplitude);

            Arrays.fill(xFrame, 0);
            Arrays.fill(yFrame, dy);

            xOffsets.add(xFrame);
            yOffsets.add(yFrame);
        }

        return new OffsetShader(xOffsets, yOffsets, null, width, height, frameDuration, true);
    }
}
