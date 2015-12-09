package com.example.xiezi.screenlock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 蝎子莱莱123 on 2015/12/5.
 */
public class GetureLock extends View {


    private boolean inited=false,isDraw=false;
    private Paint paint,paint_line;
    private Bitmap error,normal,press;
    private Point[][] points=new Point[3][3];
    private int height;
    private int width;
    private int offSet;
    private int offSetX,offSetY,space;
    private int bitmapR;
    private float mouseX,mouseY;
    private ArrayList<Point>pointArrayList=new ArrayList<>();
    private ArrayList<Integer>passList=new ArrayList<>();

    private OnDrawFinishedListener listener;

    public void setOnDrawFinishedListener(OnDrawFinishedListener listener){
        this.listener=listener;
    }
    public GetureLock(Context context) {
        super(context);
    }

    public GetureLock(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GetureLock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){


            case MotionEvent.ACTION_DOWN:
                reset();
                if(getCircle(event.getX(),event.getY())!=null){
                    int i=getCircle(event.getX(),event.getY())[0];
                    int j=getCircle(event.getX(),event.getY())[1];


                    points[i][j].state =Point.PRESSED;
                    pointArrayList.add(points[i][j]);
                    passList.add(i*3+j);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                mouseX=event.getX();
                mouseY=event.getY();
                isDraw=true;
                if(getCircle(mouseX,mouseY)!=null&&!pointArrayList.contains(points[getCircle(mouseX,mouseY)[0]][getCircle(mouseX,mouseY)[1]])){

                    pointArrayList.add(points[getCircle(mouseX,mouseY)[0]][getCircle(mouseX,mouseY)[1]]);

                    points[getCircle(mouseX,mouseY)[0]][getCircle(mouseX, mouseY)[1]].state=Point.PRESSED;
                    passList.add(getCircle(mouseX,mouseY)[0]*3+getCircle(mouseX, mouseY)[1]);


                }
             break;
            case MotionEvent.ACTION_UP:

                boolean valid=false;
                if(listener!=null&&isDraw){
                    valid=listener.OnDrawFinished(passList);
                }
                if(!valid){
                    for(Point p:pointArrayList){
                        p.state=Point.ERROR;
                    }
                }
                isDraw=false;

                break;
        }
        this.postInvalidate();
        return true;
    }

    private int[] getCircle(float x,float y){
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(new Point(x,y).distance(points[i][j])<bitmapR){
                    int a[]=new int[2];
                    a[0]=i;
                    a[1]=j;
                    return a;
                }
            }
        }
        return null;
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!inited){
            init(canvas);
        }
        drawPoints(canvas);

        if(pointArrayList.size()>0){
            Point a=pointArrayList.get(0);
            for (int i = 1;i < pointArrayList.size(); i++)
            {
                Point b = pointArrayList.get(i);
                canvas.drawLine(a.x,a.y, b.x,b.y,paint_line);
                a = b;
            }
            if(isDraw){
                canvas.drawLine(a.x,a.y,mouseX,mouseY,paint_line);
            }
        }



    }

    private void init(Canvas canvas) {
        height=getHeight();
        width=getWidth();
        paint=new Paint(Paint.ANTI_ALIAS_FLAG);
        paint_line=new Paint();
        paint_line.setColor(Color.YELLOW);
        paint_line.setStrokeWidth(5);
        offSet=Math.abs(height-width)/2;
        error= BitmapFactory.decodeResource(getResources(),R.drawable.error);
        normal= BitmapFactory.decodeResource(getResources(),R.drawable.normal);
        press= BitmapFactory.decodeResource(getResources(),R.drawable.press);

        bitmapR=error.getHeight()/2;
        if(height>width){
            space=width/4;
            offSetX=0;
            offSetY=offSet;
        }
        else{
            space=height/4;
            offSetY=0;
            offSetX=offSet;
        }
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                points[i][j]=new Point(offSetX+space*(j+1),offSetY+space*(i+1));
            }
        }
        inited=true;
    }

    private void drawPoints(Canvas canvas) {
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(points[i][j].state==Point.ERROR){
                    canvas.drawBitmap(error,points[i][j].x-bitmapR,points[i][j].y-bitmapR,paint);
                }else if(points[i][j].state==Point.PRESSED){
                    canvas.drawBitmap(press,points[i][j].x-bitmapR,points[i][j].y-bitmapR,paint);

                }else{
                    canvas.drawBitmap(normal,points[i][j].x-bitmapR,points[i][j].y-bitmapR,paint);
                }
            }
        }
    }

    public void reset(){
        passList.clear();
        pointArrayList.clear();
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                points[i][j].state=Point.NORMAL;
            }
        }
        postInvalidate();
    }

    public interface OnDrawFinishedListener{
        boolean OnDrawFinished(List<Integer> passList);
    }


}
