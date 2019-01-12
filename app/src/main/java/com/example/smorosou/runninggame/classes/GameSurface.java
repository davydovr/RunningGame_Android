package com.example.smorosou.runninggame.classes;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.smorosou.runninggame.R;

// to jump: call method to set sticky.y = a number. then call update. then set sticky.y = -number. then call update. then set sticky.y back to 0.
// clarify: for y < whatever number we decide, y++, call update. Then while y > 0, y--, call update.

// to fall: while y > a negative number, y--, then call update.

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback
{
    private GameThread gameThread;
    Canvas canvas;

    private StickFigureCharacter sticky;
    private BrickWallObject bricky;

    public GameSurface(Context context)
    {
        super(context);

        // Make Game Surface focusable so it can handle events.
        this.setFocusable(true);

        // Set callback
        this.getHolder().addCallback(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(sticky.getX() != bricky.getX()) // Y is always zero
            {
                // jump
            }

            // create new bricky
            this.bricky.draw(canvas);

            return true;
        } else {
            return false;
        }
    }

    public void update()
    {
        this.sticky.update();
        this.bricky.update();

        // checks if crashed
        if(sticky.getX() == bricky.getX())
        {
            // fall

        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        this.canvas = canvas;

        super.draw(canvas);

        this.sticky.draw(canvas);
        this.bricky.draw(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        Bitmap stickyBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.stick_figure);
        this.sticky = new StickFigureCharacter(this,stickyBitmap,100,50);

        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {

    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry= true;
        while(retry)
        {
            try
            {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            retry= true;
        }
    }
}
