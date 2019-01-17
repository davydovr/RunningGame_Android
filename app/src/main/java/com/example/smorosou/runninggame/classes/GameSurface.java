package com.example.smorosou.runninggame.classes;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.support.design.widget.Snackbar;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.smorosou.runninggame.activities.R;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.smorosou.runninggame.classes.Utils.showInfoDialog;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback
{
    private GameThread gameThread;
    Canvas canvas;
    private boolean gameOn = true;

    private StickFigureCharacter sticky;
    private BrickWallObject bricky;
    private BrickWallObject brickyDos;

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
        //this makes them run
        this.sticky.update();
        this.bricky.update();

        // checks if crashed
       if(bricky.getX() == sticky.getX())
       {
         surfaceCreated(getHolder());
       //  Toast.makeText(getContext(), "New Game", Toast.LENGTH_SHORT).show();

//FALL            for(int y = 750; y < 1000; y++)
//            {
////                this.sticky.setMovingVector(0, y);
////                this.sticky.update();
////                this.sticky.draw(canvas); // not sure how necessary this line is...
//                this.sticky.setY(y);
//                //this.sticky.update();
//                gameOn = false;
//            }
        }
    }

    @Override
    public void draw(Canvas canvas)
    {
        this.canvas = canvas;
        super.draw(canvas);
        canvas.drawRGB( 225, 225, 255); //white background
        this.sticky.draw(canvas);
        this.bricky.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            sticky.setY(300);

            //loop for 2 seconds
            for (int i = 0; i < 147483647; i++) {
                //another loop because it's 2012 and PCs have gotten considerably faster :)
            }
            sticky.setY(750);
            this.bricky.draw(canvas);

            return true;
      }
        else {
            return false;
        }
    }



    // sticky.setY(750);
    //if(sticky.getX() != bricky.getX()) // Y is always same
    // sticky.setY(300);

    //sticky.setY(750);
//                for(int y = 750; y >=  300; y--)
//                {
//                    //this.sticky.setMovingVector(0, y);
//                    this.sticky.setY(y);
//                    //this.sticky.update();
//                    //this.sticky.draw(canvas); // not sure how necessary this line is...
//                }
//                for(int y = 300; y <= 750; y++)
//                {
//                    //this.sticky.setMovingVector(0, y);
//                    this.sticky.setY(y);
//                    //this.sticky.update();
//                    //this.sticky.draw(canvas); // not sure how necessary this line is
//                }
    // sort of new game...
    //surfaceCreated(getHolder());

    // attempt at creating a second bricky...
//            if(brickyDos == null)
//            {
//                Bitmap brickyBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.brick_wall);
//                this.brickyDos = new BrickWallObject(this, brickyBitmap, 1000, 750);
//                this.bricky = null;
//            }
//            else
//            {
//                Bitmap brickyBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.brick_wall);
//                this.bricky = new BrickWallObject(this, brickyBitmap, 1000, 750);
//                brickyDos = null;
//            }



    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
//        Bitmap stickyBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.stick_figure);
//        this.sticky = new StickFigureCharacter(this,stickyBitmap,0 ,halfY);
//
//        Bitmap brickyBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.brick_wall);
//        this.bricky = new BrickWallObject(this,brickyBitmap,maxX ,halfY);

        Bitmap stickyBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.stick_figure);
        this.sticky = new StickFigureCharacter(this,stickyBitmap,0 ,750);

        Bitmap brickyBitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.brick_wall);
        this.bricky = new BrickWallObject(this,brickyBitmap,1000 ,750);


        this.gameThread = new GameThread(this, holder);
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

