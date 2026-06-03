package engine.graphics;

public interface Shader {
    void update(double dt);
    int sampleX(int x, int y);
    int sampleY(int x, int y);
    int shade(int color, int x, int y);
}
