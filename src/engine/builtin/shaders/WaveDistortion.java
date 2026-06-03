package engine.builtin.shaders;

import engine.graphics.Shader;

public class WaveDistortion implements Shader {

    private double t;

    public void update(double dt) {
        t = (t + dt) % 256;
    }

    @Override
    public int sampleX(int x, int y) {
        return x + (int)(Math.sin(y * 0.1 + t) * 3);
    }

    @Override
    public int sampleY(int x, int y) {
        return y;
    }

    @Override
    public int shade(int color, int x, int y) {
        return color;
    }
    
}
