package engine.builtin.shaders;

import engine.graphics.Shader;

public class TintShader implements Shader {

    int factor;

    @Override
    public void update(double dt) {

    }


    public TintShader(double percent) {
        this.factor = (int) (255 * percent);
    }

    @Override
    public int sampleX(int x, int y) {
        return x;
    }

    @Override
    public int sampleY(int x, int y) {
        return y;
    }

    @Override
    public int shade(int color, int x, int y) {
        return color + (((0xFFFFFFFF - color) * factor) >> 8);
    }
    
}
