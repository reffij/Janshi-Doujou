package engine.builtin.shaders;

import java.util.Set;

import engine.graphics.Shader;

public class ReplaceOutsideSetShader implements Shader {

    Set<Integer> colors;
    int newColor;

    public ReplaceOutsideSetShader(int newColor, Set<Integer> colors) {
        this.colors = colors;
        this.newColor = newColor;
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
        if (colors.contains(color)) return color;
        return this.newColor;
    }

}
