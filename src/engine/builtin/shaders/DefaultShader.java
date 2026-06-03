package engine.builtin.shaders;

import engine.graphics.Shader;

public class DefaultShader implements Shader{

    @Override
    public void update(double dt) {}

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
        return color;
    }
}
