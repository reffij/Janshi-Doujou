package engine.graphics;

import utilities.MathUtils;

public class ColorUtils {
    public static int multiply(int color, float factor) {
        int a = (color >> 24) & 0xFF;
        int r = (int)(((color >> 16) & 0xFF) * factor);
        int g = (int)(((color >> 8) & 0xFF) * factor);
        int b = (int)((color & 0xFF) * factor);

        r = Math.clamp(r, 0, 255);
        g = Math.clamp(g, 0, 255);
        b = Math.clamp(b, 0, 255);

        return argb(a, r, g, b);
    }

    public static int adjustColor(int color, int brightnessOffset, float alphaFactor) {
        int a = (int)(((color >> 24) & 0xFF) * alphaFactor);
        int r = ((color >> 16) & 0xFF) + brightnessOffset;
        int g = ((color >> 8) & 0xFF) + brightnessOffset;
        int b = (color & 0xFF) + brightnessOffset;

        a = Math.clamp(a, 0, 255);
        r = Math.clamp(r, 0, 255);
        g = Math.clamp(g, 0, 255);
        b = Math.clamp(b, 0, 255);

        return argb(a, r, g, b);
    }

    public static int fade(int color, float alphaFactor) {
        int a = (int)(((color >> 24) & 0xFF) * alphaFactor);
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        return argb(a, r, g, b);
    }

    public static int brighten(int color, int amount) {
        int a = (color >> 24) & 0xFF;
        int r = ((color >> 16) & 0xFF) + amount;
        int g = ((color >> 8) & 0xFF) + amount;
        int b = (color & 0xFF) + amount;

        r = Math.clamp(r, 0, 255);
        g = Math.clamp(g, 0, 255);
        b = Math.clamp(b, 0, 255);

        return argb(a, r, g, b);
    }

    public static int darken(int color, int amount) {
        return brighten(color, -amount);
    }

    public static int argb(int alpha, int red, int green, int blue) {
        alpha = Math.clamp(alpha, 0, 255);
        red = Math.clamp(red, 0, 255);
        green = Math.clamp(green, 0, 255);
        blue = Math.clamp(blue, 0, 255);
        return (alpha << 24) | (red << 16) | (green << 8) | blue;
    }

    public static int rgb(int red, int green, int blue) {
        return argb(255, red, green, blue);
    }

    public static int rgba(int red, int green, int blue, double alpha) {
        int iAlpha = (int) (alpha * 255);
        return argb(iAlpha, red, green, blue);
    }

    public static int alpha(int color) {
        return (color >> 24) & 0xFF;
    }

    public static int blend(int src, int dst) {
        int srcA = (src >> 24) & 0xFF;
        if (srcA == 255) return src;
        if (srcA == 0) return dst;

        int dstA = (dst >> 24) & 0xFF;

        int srcR = (src >> 16) & 0xFF;
        int srcG = (src >> 8) & 0xFF;
        int srcB = src & 0xFF;

        int dstR = (dst >> 16) & 0xFF;
        int dstG = (dst >> 8) & 0xFF;
        int dstB = dst & 0xFF;

        float alpha = srcA / 255.0f;
        float invAlpha = 1.0f - alpha;

        int outR = (int)(srcR * alpha + dstR * invAlpha);
        int outG = (int)(srcG * alpha + dstG * invAlpha);
        int outB = (int)(srcB * alpha + dstB * invAlpha);
        int outA = Math.min(255, srcA + (int)(dstA * invAlpha));

        return (outA << 24) | (outR << 16) | (outG << 8) | outB;
    }

    public static float fA(int color) {
        int a = (color >> 24) & 0xFF;
        return (float) a / 255;
    }

    public static float fR(int color) {
        int r = (color >> 16) & 0xFF;
        return (float) r / 255;
    }

    public static float fG(int color) {
        int g = (color >> 8) & 0xFF;
        return (float) g / 255;
    }

    public static float fB(int color) {
        int b = (color) & 0xFF;
        return (float) b / 255;
    }
}
