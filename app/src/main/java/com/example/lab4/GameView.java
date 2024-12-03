package com.example.lab4;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements Runnable {

    private Thread thread;
    private boolean isPlaying;
    private int screenX,screenY;
    private Paint paint;
    private houses house;
    public static float screenRatioX,screenRatioY;
    private Background background1, background2;

    public GameView(Context context, int screenX, int screenY) {
        super(context);

        this.screenX=screenX;
        this.screenY=screenY;

        screenRatioX=1920f /screenX;
        screenRatioY=1080f /screenY;

        house=new houses(screenY,getResources());

        background1=new Background(screenX,screenY,getResources());
        background2=new Background(screenX,screenY,getResources());
        background2.y= screenY;

        paint=new Paint();
    }

    @Override
    public void run() {
        while (isPlaying){
            update();
            draw();
            sleep();
        }
    }

    private void update(){
        background1.y -= 10 * (int)screenRatioX;
        background2.y -= 10 * (int)screenRatioY;

        if (background1.y+background1.background.getHeight()<0){
            background1.y = screenY;
        }
        if (background2.y+background2.background.getHeight()<0){
            background2.y = screenY;
        }

        house.moveFloor(screenX);

    }
    private void draw(){

        if(getHolder().getSurface().isValid()){
            Canvas canvas = getHolder().lockCanvas();
            canvas.drawBitmap(background1.background,background1.x,background1.y,paint);
            canvas.drawBitmap(background2.background,background2.x,background2.y,paint);

            canvas.drawBitmap(house.getStart(),house.x,house.y,paint);

            getHolder().unlockCanvasAndPost(canvas);
        }
        
    }
    private void sleep(){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public void resume(){

        isPlaying=true;
        thread = new Thread(this);
        thread.start();

    }

    public void pause(){
        try {
            isPlaying=false;
            thread.join();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            stopMovingFloor();
            return true;
        }
        return false;
    }

    public void stopMovingFloor(){
        house.getHouse();
        int overlapStart=Math.max(house.x,house.getPrevX());
        int overlapEnd=Math.min(house.widthH,house.getPrevWidth());

        if (overlapStart<overlapEnd){
            int newWidth=overlapEnd-overlapStart;
            house.createNewFloor(getResources(),overlapStart,newWidth);
        }else {
            gameOver();
        }
    }

    public void gameOver(){
        System.out.println("Game Over");
    }
}
