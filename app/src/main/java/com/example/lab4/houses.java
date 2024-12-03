package com.example.lab4;

import static com.example.lab4.GameView.screenRatioX;
import static com.example.lab4.GameView.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.util.Random;

public class houses {
    int x,y, widthS, heightS,widthH, heightH;
    int previousX, previousWidth;
    Bitmap start, house, floor;
    int direction=1;

    houses(int screenY, Resources res){
        int[] starters={
                R.drawable.daytime01starter,
                R.drawable.daytimeblue01starter,
                R.drawable.daytimered01starter,
                R.drawable.daytimegreen01starter,
        };
        int random=new Random().nextInt(starters.length);

        start = BitmapFactory.decodeResource(res,starters[random]);



        widthS=start.getWidth();
        heightS=start.getHeight();

        widthS/=4;
        heightS/=4;

        widthS*=(int)screenRatioX;
        heightS*=(int)screenRatioY;



        start = Bitmap.createScaledBitmap(start,widthS,heightS, false);

        y=32*(int)screenRatioX;
        x= screenY/2 ;
        previousX=x;
        previousWidth=widthS;
    }

    Bitmap getStart(){
        return start;
    }

    public void createNewFloor(Resources res, int newX,int newWidth){
        int[] houses={
                R.drawable.daytime01,
                R.drawable.daytimegreen01,
                R.drawable.daytimered01,
                R.drawable.daytimeblue01,
        };

        int random=new Random().nextInt(houses.length);


        floor = BitmapFactory.decodeResource(res,houses[random]);

        widthH=floor.getWidth();
        heightH=floor.getHeight();

        widthH/=4;
        heightH/=4;

        widthH*=(int)screenRatioX;
        heightH*=(int)screenRatioY;

        if (widthH!=newWidth){
            widthH=newWidth;
        }

        if(x!=newX){
            x=newX;
        }

        floor = Bitmap.createScaledBitmap(floor,widthH,heightH, false);

        y-=start.getHeight();

        previousX=x;
        previousWidth=widthH;

    }

    public int getPrevX(){
        return previousX;
    }
    public int getPrevWidth(){
        return previousWidth;
    }

    public void moveFloor(int screenWidth){
        x+=10*direction;

        if(x<0 || x+widthH>=screenWidth){
            direction*=-1;
        }

    }

    Bitmap getHouse(){
        return house;
    }

}
