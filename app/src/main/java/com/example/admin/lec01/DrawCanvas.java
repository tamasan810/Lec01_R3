package com.example.admin.lec01;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

/**
 * Created by admin on 2016/05/25.
 */
public class DrawCanvas extends SurfaceView implements Callback {
//public class DrawCanvas extends View{

    Vertex[] vtxs;
    Face[] faces;
    private static Paint paint;
    private Thread thread[];
    private int screenW, screenH;

    private GestureDetector mGestureDetector;
    private Context mainA;
    // 特に何もしないコンストラクタ
    // 最初に呼び出される!
    public DrawCanvas(Context context) {
        super(context);
        mainA = context;
//        getHolder().addCallback(this);
        init();
    }

    private void init(){
        // 画面サイズを取得
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display disp = wm.getDefaultDisplay();
        screenW = disp.getWidth();
        screenH = disp.getHeight();

        //setTriangle();
        setCube();

        paint = new Paint();
        paint.setAntiAlias(true); // ジャギり防止

        //mGestureDetector = new GestureDetector(mainA, mOnGestureListener);
    }

    void setTriangle(){
        vtxs = new Vertex[]{
                new Vertex(0, 2, 0, 255, 0, 0),
                new Vertex(1.5f, -1, 0, 0, 255, 0),
                new Vertex(-1.5f, -1, 0, 0, 0, 255)
        };
        faces = new Face[]{
                new Face(vtxs[0], vtxs[1], vtxs[2]),
        };
    }

    public void setCube(){
        vtxs = new Vertex[]{
                new Vertex(-1,  1,  1, 255, 0, 0), // 0
                new Vertex( 1,  1,  1, 255, 255, 0), // 1
                new Vertex( 1, -1,  1, 255, 0, 255), // 2
                new Vertex(-1, -1,  1, 0, 255, 0), // 3
                new Vertex(-1,  1, -1, 0, 255, 255), // 4
                new Vertex( 1,  1, -1, 0, 0, 255), // 5
                new Vertex( 1, -1, -1, 255, 127, 0), // 6
                new Vertex(-1, -1, -1, 0, 255, 127), // 7
        };

        faces = new Face[]{
                new Face(vtxs[0], vtxs[1], vtxs[2]), // 0
                new Face(vtxs[0], vtxs[2], vtxs[3]), // 1
                new Face(vtxs[0], vtxs[4], vtxs[5]), // 2
                new Face(vtxs[0], vtxs[5], vtxs[1]), // 3
                new Face(vtxs[1], vtxs[5], vtxs[6]), // 4
                new Face(vtxs[1], vtxs[6], vtxs[2]), // 5
                new Face(vtxs[2], vtxs[6], vtxs[7]), // 6
                new Face(vtxs[2], vtxs[7], vtxs[3]), // 7
                new Face(vtxs[5], vtxs[4], vtxs[7]), // 8
                new Face(vtxs[5], vtxs[7], vtxs[6]), // 9
                new Face(vtxs[4], vtxs[0], vtxs[3]), // A
                new Face(vtxs[4], vtxs[3], vtxs[7]), // B
        };
    }

    @Override
    public boolean onTouchEvent(MotionEvent e){
        // invalidateは再描画を行うメソッド
        // invalidate()を通じてonDrawメソッドが呼び出される
        this.invalidate();
        return true;
    }

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;

//    private final SimpleOnGestureListener mOnGestureListener = new SimpleOnGestureListener(){
//        @Override
//        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY){
//            // event1: 終了位置, event2: 開始位置
//
//            try{
//                if(Math.abs(event1.getY() - event2.getY()) > SWIPE_MAX_OFF_PATH){
//                    // タテの移動距離が多すぎる場合は無視
//                    return false;
//                }
//
//                if(event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE &&
//                        Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
//                    // 開始位置から終了位置の移動距離が指定値より大きい
//                    // x軸の移動距離が指定値より大きい
//                    Toast.makeText(mainA, "右から左", Toast.LENGTH_SHORT).show();
//                } else if(event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE &&
//                        Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY){
//                    // 終了位置から開始位置の移動距離が指定値より大きい
//                    // x軸の移動速度が指定値より大きい
//                    Toast.makeText(mainA, "左から右", Toast.LENGTH_SHORT).show();
//                }
//            } catch (Exception e){
//                // nothing
//            }
//            return false;
//        }
//    };

    // 描画処理 実行時はinit()の後に自動的に呼ばれる
    @Override
    protected void onDraw(Canvas c){
        int[] zBuff = new int[screenW*screenH];

        // Zバッファ初期化
        // スクリーン座標系においてz軸は画面奥方向を向くので、zバッファは大きい値で初期化する。
        for(int i = 0; i < zBuff.length; i++){
            zBuff[i] = Integer.MAX_VALUE;
        }

        Matrix S = Matrix.Scale(200, 200, 200);
        Matrix SC = Matrix.Screen(screenW, screenH); // スクリーン変換
        Matrix m = SC.mult(S);

        for (int i = 0 ; i < vtxs.length ; i++) {
            vtxs[i].v = vtxs[i].ov.transform(m);
        }

        for(Face f : faces){
            drawFace(f, zBuff, c);
        }

//        // Zバッファの確認用
//        Vertex[] zCheckV = new Vertex[]{
//                new Vertex(0, 200, 50),
//                new Vertex(150, -100, 50),
//                new Vertex(-150, -100, 50)
//        };
//
//        // スクリーン変換のみ実行
//        for(Vertex zvtx : zCheckV){
//            zvtx.v = zvtx.ov.transform(SC);
//        }
//
//        //TODO Zバッファのチェックをする際にコメントアウト解除
//        drawFace(new Face(zCheckV[0], zCheckV[1], zCheckV[2]), zBuff, c);
    }

    void drawFace(Face f, int[] zBuff, Canvas c){
        if(f.getNormal().z >= 0){
            return;
        }

        // 左右バッファを用意
        Pixel[] lBuff = new Pixel[screenH], rBuff = new Pixel[screenH];
        //TODO 左右バッファを初期化
        for (int i = 0; i < screenH; i++){
            int max = Integer.MAX_VALUE;
            int min = Integer.MIN_VALUE;
            lBuff[i] = new Pixel(max, max, max, 0, 0, 0);
            rBuff[i] = new Pixel(min, min, min, 0, 0, 0);
        }

        // 3つの辺をscan(面の左端・右端を取得)
        scan(f.A, f.B, lBuff, rBuff);
        scan(f.B, f.C, lBuff, rBuff);
        scan(f.C, f.A, lBuff, rBuff);

        // span(LBuffからRBuffまで塗りつぶし)
        span(lBuff, rBuff, zBuff, c);
    }

    void scan(Vertex vtx1, Vertex vtx2, Pixel[] lBuff, Pixel[] rBuff){
        // 繰り返し方向確認
        if(vtx1.v.y > vtx2.v.y){
            Vertex vtx3 = vtx1;
            vtx1 = vtx2;
            vtx2 = vtx3;
        }

        // 水平なら描画しない
        if(vtx1.v.y == vtx2.v.y) return;

        // 補間
        for (float i = vtx1.v.y; i <= vtx2.v.y; i++){
            float a = (i - vtx1.v.y) / (vtx2.v.y - vtx1.v.y); // 補間係数
            Pixel p = Vertex.lerp(vtx1, vtx2, a);

            if(p.x < lBuff[p.y].x){
                lBuff[p.y] = p;
            }
            if(p.x > rBuff[p.y].x){
                rBuff[p.y] = p;
            }
        }
    }

    void span(Pixel[] lBuff, Pixel[] rBuff, int[] zBuff, Canvas c){
        float a;
        for(int y = 0; y < screenH; y++){
            if(rBuff[y].x <= lBuff[y].x) continue;

            float v1 = lBuff[y].x, v2 = rBuff[y].x;

            for (int i = lBuff[y].x; i <= rBuff[y].x; i++){
                a = (i - v1) / (v2 - v1); // 補間係数
                Pixel p = Pixel.lerp(lBuff[y], rBuff[y], a);
                paint.setColor(p.getColor());
                c.drawPoint(p.x, y, paint);
            }
        }
    }

    // サーフェイスの生成
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    // SurfaceViewが変化時の処理
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // サーフェイスの破棄
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}