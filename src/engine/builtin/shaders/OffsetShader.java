package engine.builtin.shaders;

import java.util.List;

import engine.graphics.ColorUtils;
import engine.graphics.Shader;

public class OffsetShader implements Shader{
    private List<int[]> xOffsets;
    private List<int[]> yOffsets;
    private List<int[]> cOffsets;
    private int width;
    private int height;
    private double frameDuration;
    private int totalFrames;
    private double elapsed;
    private boolean loop;

    public OffsetShader(
        List<int[]> xOffsets, 
        List<int[]> yOffsets, 
        List<int[]> cOffsets, 
        int width, 
        int height, 
        double frameDuration, 
        boolean loop
    ) {
        
        if (xOffsets.size() != yOffsets.size()) {
            throw new IllegalArgumentException(
                "xOffsets and yOffsets must have same number of frames."
            );
        }
        if (cOffsets != null && xOffsets.size() != cOffsets.size()) {
            throw new IllegalArgumentException(
                "cOffsets must be null or have the same number of frames as xOffsets and yOffsets"
            );
        }

        this.xOffsets = xOffsets;
        this.yOffsets = yOffsets;
        this.cOffsets = cOffsets;
        this.width = width;
        this.height = height;
        this.frameDuration = frameDuration;
        this.totalFrames = xOffsets.size();
        this.loop = loop;
        this.elapsed = 0;
    }

    @Override
    public void update(double dt) {
        elapsed += dt;
        if (!loop) elapsed = Math.min(elapsed, totalFrames * frameDuration);
    }

    private int getFrameIndex() {
        int f = (int)(elapsed / frameDuration);
        if (loop) f = f % totalFrames;
        else f = Math.min(f, totalFrames - 1);
        return f;
    }

    @Override
    public int sampleX(int x, int y) {
        int f = getFrameIndex();
        return x + xOffsets.get(f)[x + y * width];
    }

    @Override
    public int sampleY(int x, int y) {
        int f = getFrameIndex();
        return y + yOffsets.get(f)[x + y * width];
    }

    @Override
    public int shade(int color, int x, int y) {
        if (cOffsets == null) return color;
        int f = getFrameIndex();
        int offset = cOffsets.get(f)[x + y * width];
        return ColorUtils.brighten(color, offset);
    }
}
