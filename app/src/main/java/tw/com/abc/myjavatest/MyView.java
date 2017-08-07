package tw.com.abc.myjavatest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

// import java.sql.Time;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class MyView extends View {
    private Resources res;
    private Bitmap[] ballBMP;
    private int[] ballRes={R.drawable.ball0,R.drawable.ball1,R.drawable.ball2,R.drawable.ball3};
    private int viewW,viewH;
    private float ballW,ballH;
    public boolean isInit;
    private Matrix matrix;
    private Timer timer;
    private LinkedList<BallTask> balls;


    public MyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        setBackgroundResource(R.drawable.mybg);

        res = context.getResources();

        //原本為Bitmap 變成Bitmap[] 的處理
        ballBMP=new Bitmap[ballRes.length];
        for (int i = 0; i < ballRes.length; i++){
            ballBMP[i] = BitmapFactory.decodeResource(res, ballRes[i]);
        }
        matrix=new Matrix();

        balls=new LinkedList<>();
        //封裝關係移到MainActivity
        //timer=new Timer();
        //timer.schedule(new BallTask(),500,30);
    }

    //
    public void setTimer(Timer timer){
        this.timer = timer;
      //  timer.schedule(new BallTask(),1000,60);
    }

    //傳出timer 給Activity 控制結束--> getTimer() 會自動帶出
    //封裝關係移到MainActivity
    //public Timer getTimer() {return timer;}

    public void init(){
        isInit=true;
        viewH=getHeight();viewW=getWidth();
        // 控制球的大小
        ballW=viewW /8f; ballH=ballW;
        // 變形前先reset();必免之前的值帶入

        matrix.reset();
        matrix.postScale(ballW / ballBMP[0].getWidth(),ballH/ballBMP[0].getHeight());
        for (int i=0;i<ballBMP.length;i++) {
            ballBMP[i] = Bitmap.createBitmap(
                    ballBMP[i], 0, 0, ballBMP[i].getWidth(), ballBMP[i].getHeight(),
                    matrix, false);
        }
       // dx=dy=8;

    }
    private class BallTask extends TimerTask {
        private float ballX,ballY,dx,dy;
        private int intBall;
        // 建構式 33:45 ,Class 中的 方法,讓外部程式呼叫
        // 用途:放置每個球的資訊框架,搭配LinkedSet 和迴圈用來存取每個球的資訊
        BallTask(int intBall,float ballX,float ballY,float dx,float dy){
            this.intBall=intBall;
            this.ballX= ballX;
            this.ballY= ballY;
            this.dx=dx;
            this.dy=dy;
        }


        @Override
        public void run() {
            if(ballX<0 || ballX + ballW > viewW){
                dx *= -1;
            }
            if(ballY<0 || ballY + ballH > viewH){
                dy *= -1;
            }
            ballX+=dx;
            ballY+=dy;
            postInvalidate();
        }
    }
//BallTask Class END ===

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float ex=event.getX();float ey=event.getY();
        BallTask ball = new BallTask((int)(Math.random()*4),ex,ey,16,16);
        balls.add(ball);
        timer.schedule(ball,0,30);
        return false;
        //return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(!isInit) init();

        //Log.i("geoff","Vx:"+Vx+"Vy"+Vy);

        // 透過foreach 把 balls 的資訊放入 ball 中,在此ball 代表每一個球的資訊
        for(BallTask ball : balls) {
            // 用 ball 的屬性帶入值
            canvas.drawBitmap(ballBMP[ball.intBall], ball.ballX, ball.ballY, null);
        }
        // 原本一個球的程式,多個球註解掉
        //canvas.drawBitmap(ballBMP, ballX, ballY, null);

    }
}
