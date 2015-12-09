package com.example.xiezi.screenlock;

/**
 * Created by 蝎子莱莱123 on 2015/12/5.
 */
public class Point {
  public static int PRESSED=0;
  public static int NORMAL=1;
  public static int ERROR=2;
     int state=NORMAL;
     float x;
     float y;



    public Point(float x,float y){
        this.x=x;
        this.y=y;


    }

    public float distance(Point a){
        return (float)Math.sqrt((a.x-this.x)*(a.x-this.x)+(a.y-this.y)*(a.y-this.y));
    }

}
