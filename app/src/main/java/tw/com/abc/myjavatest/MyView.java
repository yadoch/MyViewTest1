package tw.com.abc.myjavatest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.sql.Time;
import java.util.Timer;

public class MyView extends View {
    private Resources res;
    private Bitmap ballBMP;
    private int ViewW,ViewH;
    private float ballW,ballH,dx,dy;
    private Boolean isInit;
    private Matrix matrix;
    private Timer timer;

    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        res= context.getResources();
        ballBMP=BitmapFactory.decodeResource(res,R.drawable.ball);
        matrix=new Matrix();
        timer=new Timer();
    }
    public void init(){
        ViewH=getHeight();ViewW=getWidth();
        ballW=ViewW /8f; ballH=ballW;
        matrix.reset();
        matrix.postScale(ballW / ballBMP.getWidth(),ballH/ballBMP.getHeight());

        ballBMP=Bitmap.createBitmap(ballBMP,0,0,ballBMP.getWidth(),ballBMP.getHeight(),matrix,false);

            
        dx=dy=0;

}
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isInit) init();

        //Log.i("geoff","Vx:"+Vx+"Vy"+Vy);
        canvas.drawBitmap(ballBMP,0,0,null);
    }
}
