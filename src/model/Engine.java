package model;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import res.Assets;

public class Engine {

    private Zone[][] grid;

    private int width;
    private int height;

    private static CursorSignal cursorSignal = CursorSignal.SELECT;

    private BufferedImage img;

    public void setImg(BufferedImage img) {
        this.img = img;
    }

    public Engine(int width, int height) {
        this.width = width;
        this.height = height;
        grid = new Zone[height][width];
        for (int column = 0; column < width; column++) {
            for (int row = 0; row < height; row++) {
                grid[row][column] = null;
            }
        }
    }

    public Zone getCell(int row, int column) {
        return grid[row][column];
    }

    public static void setCursorSignal(CursorSignal signal) {
        cursorSignal = signal;
    }

    //Returns true if successfully built a zone.
    public boolean build(int rowStart, int columnStart, int fieldSize) {
        int rowEnd = rowStart + cursorSignal.getHeight() - 1;
        int columnEnd = columnStart + cursorSignal.getWidth() - 1;
        if (false == areaInsideGridAndFree(rowStart, rowEnd, columnStart, columnEnd)) {
            return false;
        }
        Zone zone = new Zone(cursorSignal.getWidth(), cursorSignal.getHeight());
        zone.setTopLeftX(columnStart * fieldSize);
        zone.setTopLeftY(rowStart * fieldSize);
        zone.setType(cursorSignal);
        zone.setImg(img);

        for (int row = rowStart; row <= rowEnd; row++) {
            for (int column = columnStart; column <= columnEnd; column++) {
                grid[row][column] = zone;
            }
        }
        
        //TODO: If cursorSignal is road, check neighboring fields. If necessary
        //change the img of the new road and the neighboring roads.
        if (cursorSignal == CursorSignal.ROAD) {
            //New road
            refreshImgOfRoad(zone, rowStart, columnStart);

            //Up
            refreshImgOfRoad(grid[rowStart - 1][columnStart], rowStart - 1,
                    columnStart);
            //Down
            refreshImgOfRoad(grid[rowStart + 1][columnStart], rowStart + 1,
                    columnStart);
            //Right
            refreshImgOfRoad(grid[rowStart][columnStart + 1], rowStart,
                    columnStart + 1);
            //Left
            refreshImgOfRoad(grid[rowStart][columnStart - 1], rowStart,
                    columnStart - 1);

        }

        
        return true;
    }

    public CursorSignal getCursorSignal() {
        return cursorSignal;
    }

    //Returns true if the specified area if free and isn't outside of the grid.
    public boolean areaInsideGridAndFree(int rowStart, int rowEnd,
            int columnStart, int columnEnd) {
        for (int row = rowStart; row <= rowEnd; row++) {
            for (int column = columnStart; column <= columnEnd; column++) {
                if (row >= height || column >= width || row < 0 || column < 0) {
                    //Out of grid.
                    return false;
                }
                if (null != grid[row][column]) {
                    //At least one field isn't free.
                    return false;
                }
            }
        }
        return true;
    }

    //Returns true if successfully destroyed a zone.
    public boolean destroyZone(int argRow, int argColumn, int fieldSize) {
        Zone target = grid[argRow][argColumn];
        if (null == target) {
            return false;
        }

        int rowStart = target.getTopLeftY() / fieldSize;
        int rowEnd = target.getTopLeftY() / fieldSize + target.getHeigth() - 1;
        int columnStart = target.getTopLeftX() / fieldSize;
        int columnEnd = target.getTopLeftX() / fieldSize + target.getWidth() - 1;
        for (int row = rowStart; row <= rowEnd; row++) {
            for (int column = columnStart; column <= columnEnd; column++) {
                grid[row][column] = null;
            }
        }

        target.destroy();

        return true;
    }

    public void upgradeZone(/*TODO*/) {
        //TODO: Select mode (signal). -> Zone selected. In the class Gird check 
        //whether it can be upgraded or not. Call this method if it can be 
        //upgraded. -> Change the zone's img and level fields.
    }

    private void refreshImgOfRoad(Zone zone, int rowStart, int columnStart) {
        if (rowStart < 0 || height <= rowStart || columnStart < 0
                || width <= columnStart || null == zone || zone.getType() != CursorSignal.ROAD) {
            return;
        }
        
        
        //Build road at the edge of the grid: 
        //java.lang.ArrayIndexOutOfBoundsException
        boolean roadAbove
                = null != grid[rowStart - 1][columnStart]
                        ? (CursorSignal.ROAD == grid[rowStart - 1][columnStart].getType()
                        ? true : false) : false;
        boolean roadUnder
                = null != grid[rowStart + 1][columnStart]
                        ? (CursorSignal.ROAD == grid[rowStart + 1][columnStart].getType()
                        ? true : false) : false;
        boolean roadOnRight
                = null != grid[rowStart][columnStart + 1]
                        ? (CursorSignal.ROAD == grid[rowStart][columnStart + 1].getType()
                        ? true : false) : false;
        boolean roadOnLeft
                = null != grid[rowStart][columnStart - 1]
                        ? (CursorSignal.ROAD == grid[rowStart][columnStart - 1].getType()
                        ? true : false) : false;
        int roadsAroundCount = 0;
        roadsAroundCount += roadAbove ? 1 : 0;
        roadsAroundCount += roadUnder ? 1 : 0;
        roadsAroundCount += roadOnRight ? 1 : 0;
        roadsAroundCount += roadOnLeft ? 1 : 0;
        if (0 == roadsAroundCount) {
            //Default road image stays. (NS)
            return;
        }
        if (4 == roadsAroundCount) {
            zone.setImg(Assets.roadNESW);
            return;
        }
        if (1 == roadsAroundCount) {
            if (roadAbove || roadUnder) {
                //Default road image stays. (NS)
                return;
            } else {
                zone.setImg(Assets.roadEW);
                return;
            }
        }
        if (3 == roadsAroundCount) {
            if (false == roadAbove) {
                zone.setImg(Assets.roadESW);
                return;
            }
            if (false == roadUnder) {
                zone.setImg(Assets.roadWNE);
                return;
            }
            if (false == roadOnLeft) {
                zone.setImg(Assets.roadNES);
                return;
            } else {
                //if (false == roadOnRight) {
                zone.setImg(Assets.roadSWN);
                return;
            }
        } else {
            //if (2 == roadsAroundCount) {
            if (roadAbove && roadUnder) {
                //Default road image stays. (NS)
                return;
            }
            if (roadOnRight && roadOnLeft) {
                zone.setImg(Assets.roadEW);
                return;
            }
            if (roadAbove && roadOnRight) {
                zone.setImg(Assets.roadNE);
                return;
            }
            if (roadAbove && roadOnLeft) {
                zone.setImg(Assets.roadWN);
                return;
            }
            if (roadUnder && roadOnLeft) {
                zone.setImg(Assets.roadSW);
                return;
            }
            if (roadUnder && roadOnRight) {
                zone.setImg(Assets.roadES);
                return;
            }

        }
    }
}
