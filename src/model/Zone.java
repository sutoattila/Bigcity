package model;

import java.awt.Image;
import java.awt.image.BufferedImage;

public class Zone {

    private int topLeftX;
    private int topLeftY;
    private int width;
    private int heigth;

    private BufferedImage img;

    private CursorSignal type;

    public CursorSignal getType() {
        return type;
    }

    public void setType(CursorSignal type) {
        this.type = type;
    }

    public Zone(int width, int heigth) {
        this.width = width;
        this.heigth = heigth;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public int getHeigth() {
        return heigth;
    }

    public int getWidth() {
        return width;
    }

    public int getTopLeftY() {
        return topLeftY;
    }

    public int getTopLeftX() {
        return topLeftX;
    }

    public void setTopLeftX(int topLeftX) {
        this.topLeftX = topLeftX;
    }

    public void setTopLeftY(int topLeftY) {
        this.topLeftY = topLeftY;
    }

    public void destroy() {
        //TODO: magic
    }

}
