package com.example.admin.lec01;

import android.graphics.Color;

/**
 * Created by admin on 2016/05/26.
 */
public class Pixel extends Vector{
    int x, y, z;
    float r, g, b;

    Pixel(float x, float y, float z, float r, float g, float b){
        this.x = (int)Math.round(x);
        this.y = (int)Math.round(y);
        this.z = (int)Math.round(z);
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * 線形補間
     * @param p1 始点
     * @param p2 終点
     * @param a 割合
     * @return (1-a)*p1 + a*p2
     */
    public static Pixel lerp(Pixel p1, Pixel p2, float a){
        float inva = 1f - a; // (1 - a)のこと

        Pixel ret = new Pixel(
                inva*p1.x + a*p2.x,
                inva*p1.y + a*p2.y,
                inva*p1.z + a*p2.z,
                inva*p1.r + a*p2.r,
                inva*p1.g + a*p2.g,
                inva*p1.b + a*p2.b);
        return ret;
    }

    int getColor(){
        if(255 < r) r = 255; if(r < 0) r = 0;
        if(255 < g) g = 255; if(g < 0) g = 0;
        if(255 < b) b = 255; if(b < 0) b = 0;
        // Androidでプログラミングするとき、色は32bitのint型で表現される。
        if(0 <= r && r <= 255 && 0 <= g && g <= 255 && 0 <= b && b <= 255){
            return Color.rgb((int)r, (int)g, (int)b);

        } else{
            System.out.println("範囲外の色" + r);
            return -1;
        }

//        // r,g,bが0.0~1.0の範囲に収まっていることを確認
//        if(0.0 <= r && r <= 1.0 && 0.0 <= g && g <= 1.0 && 0.0 <= b && b <= 1.0){
//            //return new Color((float)r, (float)g, (float)b);
//            //Color x = new Color(0,0,0);
//            return Color.rgb(255 * r, 0, 0);
//
//        } else{
//            System.out.println("範囲外の色");
//            return -1;
//        }
    }
}
