package engine.ui.elements;

import engine.graphics.render.Renderer;
import engine.ui.renderable.Renderable;
import utilities.MathUtils;

public class UISlider extends UIControl<Float> {
    
    protected Renderable backgroundRable;
    protected Renderable knobRable;

    protected int knobWidth;

    public UISlider(
        int width,
        int height,
        int knobWidth,
        Renderable backgroundRable,
        Renderable knobRable
    ) {
        super(width, height);

        this.knobWidth = knobWidth;

        this.backgroundRable = backgroundRable;
        this.knobRable = knobRable;

        super.value = 0f;
    }

    @Override
    protected void onDrag(int localX, int localY) {
        float percent = (float)localX / (float)(super.getWidth() - knobWidth);
    
        percent = Math.clamp(percent, 0f, 1f);

        super.setValue(percent);
    }

    @Override
    public void render(Renderer r, int globalX, int globalY) {
        int screenX = globalX + super.getX();
        int screenY = globalY + super.getY();

        if (backgroundRable != null) {
            backgroundRable.render(r, screenX, screenY);
        }

        int knobX = (int)((super.getWidth() - knobWidth) * super.value);
        if (knobRable != null) {
            knobRable.render(r, screenX + knobX, screenY);
        }
    }

    @Override
    public void update(double dt) {
        if (backgroundRable != null) {
            backgroundRable.update(dt);
        }

        if (knobRable != null) {
            knobRable.update(dt);
        }
    }
}
