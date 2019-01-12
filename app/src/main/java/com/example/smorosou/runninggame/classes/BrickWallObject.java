package com.example.smorosou.runninggame.classes;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class BrickWallObject extends GameObject
{
    private static final int ROW_TOP_TO_BOTTOM = 0;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 2;
    private static final int ROW_BOTTOM_TO_TOP = 3;

    // Row index of Image are being used
    private int rowUsing = ROW_LEFT_TO_RIGHT;

    private int colUsing;

    private Bitmap[] leftToRights;
    private Bitmap[] rightToLefts;
    private Bitmap[] topToBottoms;
    private Bitmap[] bottomToTops;

    // Velocity of game character (pixel/millisecond)
    public static final float VELOCITY = 0.01f; //! was 0.1f

    private int movingVectorX = -25; //! should move left to right
    private int movingVectorY = 0;

    private long lastDrawNanoTime = -1;

    private GameSurface gameSurface;

    public BrickWallObject(GameSurface gameSurface, Bitmap image, int x, int y)
    {
        super(image, 4, 3, x, y);

        this.gameSurface = gameSurface;

        this.topToBottoms = new Bitmap[colCount];
        this.rightToLefts = new Bitmap[colCount];
        this.leftToRights = new Bitmap[colCount];
        this.bottomToTops = new Bitmap[colCount];

        for(int col = 0; col < this.colCount; col++)
        {
            this.topToBottoms[col] = this.createSubImageAt(ROW_TOP_TO_BOTTOM, col);
            this.rightToLefts[col] = this.createSubImageAt(ROW_RIGHT_TO_LEFT, col);
            this.leftToRights[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
            this.bottomToTops[col] = this.createSubImageAt(ROW_BOTTOM_TO_TOP, col);
        }
    }

    public Bitmap[] getMoveBitmaps()
    {
        switch (rowUsing)
        {
            case ROW_BOTTOM_TO_TOP:
                return  this.bottomToTops;
            case ROW_LEFT_TO_RIGHT:
                return this.leftToRights;
            case ROW_RIGHT_TO_LEFT:
                return this.rightToLefts;
            case ROW_TOP_TO_BOTTOM:
                return this.topToBottoms;
            default:
                return null;
        }
    }

    public Bitmap getCurrentMoveBitmap()
    {
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.colUsing];
    }

    public void update()
    {
        this.colUsing++;
        if(colUsing >= this.colCount)
        {
            this.colUsing = 0;
        }

        // Current time in nanoseconds
        long now = System.nanoTime();

        // Never drew before
        if(lastDrawNanoTime == -1)
        {
            lastDrawNanoTime = now;
        }

        // Change nanoseconds to milliseconds
        int deltaTime = (int)((now - lastDrawNanoTime)/ 1000000);

        // Distance moves
        float distance = VELOCITY * deltaTime;

        //! will only move it up...
        double movingVectorLength = Math.sqrt(movingVectorX * movingVectorX + movingVectorY * movingVectorY);

        // Calculate the new position of the game character.
        this.x = x + (int)(distance * movingVectorX/movingVectorLength);
        this.y = y + (int)(distance * movingVectorY / movingVectorLength);

        //! When the game's character touches the brick wall, fall. - fall method - also switches the image
        //! When click, jump. - jump method

        // rowUsing
        if(movingVectorX > 0)
        {
            if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY))
            {
                this.rowUsing = ROW_TOP_TO_BOTTOM;
            }
            else if(movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                this.rowUsing = ROW_BOTTOM_TO_TOP;
            }
            else
            {
                this.rowUsing = ROW_LEFT_TO_RIGHT;
            }
        }
        else
        {
            if(movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY))
            {
                this.rowUsing = ROW_TOP_TO_BOTTOM;
            }
            else if(movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY))
            {
                this.rowUsing = ROW_BOTTOM_TO_TOP;
            }
            else
            {
                this.rowUsing = ROW_RIGHT_TO_LEFT;
            }
        }
    }

    public void draw(Canvas canvas)
    {
        if(lastDrawNanoTime > 900000000) // draw it every few seconds
        {
            Bitmap bitmap = this.getCurrentMoveBitmap();
            canvas.drawBitmap(bitmap, x, y, null);
            // Last draw time
            this.lastDrawNanoTime = System.nanoTime();
        }
    }

    public void setMovingVector(int movingVectorX, int movingVectorY)
    {
        this.movingVectorX = movingVectorX;
        this.movingVectorY = movingVectorY;
    }

}
