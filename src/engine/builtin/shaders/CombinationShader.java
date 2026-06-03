package engine.builtin.shaders;

import java.util.*;

import engine.graphics.Shader;

public class CombinationShader implements Shader {

    List<Shader> shaders;

    public CombinationShader() {
        this.shaders = new ArrayList<>();
    }

    public CombinationShader add(Shader shader) {
        List<Shader> copy = new ArrayList<>(this.shaders);
        copy.add(shader);
        this.shaders = copy;
        return this;
    }

    @Override
    public void update(double dt) {
        for (Shader shader : this.shaders) {
            shader.update(dt);
        }
    }

    private int[] sample(int x, int y) {
        int currX = x;
        int currY = y;
        for (Shader shader : this.shaders) {
            int oldX = currX;
            int oldY = currY;
            currX = shader.sampleX(oldX, oldY);
            currY = shader.sampleY(oldX, oldY);
        }
        return new int[]{currX, currY};
    }

    @Override
    public int sampleX(int x, int y) {
        return sample(x, y)[0];
    }

    @Override
    public int sampleY(int x, int y) {
        return sample(x, y)[1];
    }

    @Override
    public int shade(int color, int x, int y) {
        int res = color;
        for (Shader shader : this.shaders) {
            res = shader.shade(res, x, y);
        }
        return res;
    }
    
}
