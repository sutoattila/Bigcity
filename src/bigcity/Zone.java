package bigcity;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import model.CursorSignal;

public abstract class Zone {

    protected int topLeftX;
    protected int topLeftY;

    protected int level;
    protected int price;

    protected BufferedImage img;
    protected CursorSignal cursorSignal;

    public CursorSignal getCursorSignal() {
        return cursorSignal;
    }

    public void setCursorSignal(CursorSignal cursorSignal) {
        this.cursorSignal = cursorSignal;
    }

    public BufferedImage getImg() {
        return img;
    }

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public int getTopLeftY() {
        return topLeftY;
    }

    public int getTopLeftX() {
        return topLeftX;
    }

    public int getLevel() {
        return level;
    }

    public boolean isConnectedToRoad() {
        //TODO
        return true;
    }

    public void destroy() {
        //TODO
    }
}
