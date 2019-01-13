package com.example.smorosou.runninggame.classes;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.smorosou.runninggame.activities.R;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback
{
    private GameThread gameThread;
    Canvas canvas;
    private boolean gameOn = true;

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


    public void update()
    {
        this.sticky.update();
        this.bricky.update();

        // checks if crashed
        if(sticky.getX() == bricky.getX())
        {
            // fall
            for(int y = 0; y > -30; y++)
            {
                this.sticky.setMovingVector(0, y);
                this.sticky.update();
                this.sticky.draw(canvas); // not sure how necessary this line is...
                gameOn = false;

            }

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

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            if(sticky.getX() != bricky.getX()) // Y is always same
            {
                for(int y = 0; y <=  30; y++)
                {
                    this.sticky.setMovingVector(0, y);
                    this.sticky.update();
                    this.sticky.draw(canvas); // not sure how necessary this line is...
                }
                for(int y = 30; y >= 0; y--)
                {
                    this.sticky.setMovingVector(0, y);
                    this.sticky.update();
                    this.sticky.draw(canvas); // not sure how necessary this line is
                }
            }

            // create new bricky
            this.bricky.draw(canvas);

            return true;
        } else {
            return false;
        }
    }





    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {

        int maxX = Resources.getSystem().getDisplayMetrics().widthPixels;
        int halfY = Resources.getSystem().getDisplayMetrics().heightPixels;
//
//        WindowManager wm = (WindowManager)Context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//
//        DisplayMetrics metrics = new DisplayMetrics();
//        getActivityContext().getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        int width1 = metrics.widthPixels;
//        int height1 = metrics.heightPixels;
//
//        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(metrics);
//
//
//
//        //WindowManager window = (WindowManager) getSystemService(WINDOW_SERVICE);
//        Display display = getWindowManager().getDefaultDisplay();
//        Point mdispSize = new Point();
//        display.getSize(mdispSize);
//        int maxX = mdispSize.x;         //the right most of the screen width-wise.
//        int halfY = mdispSize.y/2;      //middle of screen length-wise

//        Bitmap stickyBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.stick_figure);
//        this.sticky = new StickFigureCharacter(this,stickyBitmap,0 ,halfY);
//
//        Bitmap brickyBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.brick_wall);
//        this.bricky = new BrickWallObject(this,brickyBitmap,maxX ,halfY);

        Bitmap stickyBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.stick_figure);
        this.sticky = new StickFigureCharacter(this,stickyBitmap,0 ,600);

        Bitmap brickyBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.brick_wall);
        this.bricky = new BrickWallObject(this,brickyBitmap,1000 ,600);


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

