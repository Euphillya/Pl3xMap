package net.pl3x.map.core.image.io;

import java.awt.image.BufferedImage;

public class Bmp extends IO.Type {
    @Override
    public String extension() {
        return "bmp";
    }

    @Override
    public BufferedImage createBuffer() {
        return new BufferedImage(512, 512, BufferedImage.TYPE_INT_RGB);
    }

    @Override
    public int color(int argb) {
        return argb & 0xFFFFFF;
    }
}