package engine.graphics;

import engine.graphics.render.Renderer;
import utilities.MathUtils;

public class Animation {

    Sprite[] frames;
    int[] offsetX;
    int[] offsetY;
    Shader shader;
    boolean loop;
    double elapsed;
    double duration;
    boolean finished;

    public Animation(
        Sprite[] frames, 
        Shader shader, 
        int[] offsetX, 
        int[] offsetY, 
        double duration, 
        boolean loop
    ) {
        if (frames.length != offsetX.length || frames.length != offsetY.length) {
            throw new IllegalArgumentException(
                "frames offsetX and offsetY's length must all be equal"
            );
        }
        this.frames = frames;
        this.shader = shader;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.duration = duration;
        this.elapsed = 0;
        this.loop = loop;
        this.finished = false;
    }


    public void update(double dt) {
        if (finished) return;

        elapsed += dt;
        shader.update(dt);

        if (duration >= 0) {
            if (loop) elapsed %= duration;
            else if (elapsed >= duration) {
                elapsed = duration;
                finished = true;
            }
        }
    }

    private int frameIndex() {
        if (duration <= 0 || frames.length == 0) return 0;

        int frameIndex = (int)((elapsed / duration) * frames.length);
        frameIndex = Math.clamp(frameIndex, 0, frames.length - 1);
        return frameIndex;
    }

    public void render(Renderer r, int x, int y) {
        int frameIndex = this.frameIndex();
        Sprite frame = this.frames[frameIndex];
        int ox = this.offsetX[frameIndex];
        int oy = this.offsetY[frameIndex];

        r.drawSprite(
            frame, 
            x + ox, 
            y + oy, 
            shader
        );
    }

    public boolean isFinished() {
        return finished;
    }

    public int getWidth() {
        return frames[frameIndex()].width;
    }

    public int getHeight() {
        return frames[frameIndex()].height;
    }

}
