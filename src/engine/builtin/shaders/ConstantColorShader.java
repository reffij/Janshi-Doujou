package engine.builtin.shaders;

import engine.graphics.Shader;

public class ConstantColorShader implements Shader{

    private int color;

    public ConstantColorShader(int color) {
        this.color = color;
    }

    @Override
    public void update(double dt) {
        return;
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
        return this.color;
    }
    
}
