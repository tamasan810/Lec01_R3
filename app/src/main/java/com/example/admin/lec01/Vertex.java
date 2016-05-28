package com.example.admin.lec01;

/**
 * Created by admin on 2016/05/26.
 */
public class Vertex { // 頂点

    Vector v;	// 変換後（スクリーン）位置座標
    Vector ov;	// 変換前（モデル）位置座標
    float r, g, b;

    public Vertex(float x, float y, float z){
        ov = new Vector(x,y,z);
    }

    public Vertex(float x, float y, float z, float r, float g, float b){
        ov = new Vector(x,y,z);
        this.r = r;
        this.g = g;
        this.b = b;
    }

    /**
     * 変換後座標(vtx1.v, vtx2.v)について線形補間を行い、結果をPixelで返す
     * @param vtx1 始点
     * @param vtx2 終点
     * @param a 割合
     * @return (1-a)*vtx1 + a*vtx2
     */
    public static Pixel lerp(Vertex vtx1, Vertex vtx2, float a){

        float inva = 1f - a; // (1 - a)のこと

        Pixel ret = new Pixel(
                inva*vtx1.v.x + a*vtx2.v.x,
                inva*vtx1.v.y + a*vtx2.v.y,
                inva*vtx1.v.z + a*vtx2.v.z,
                inva*vtx1.r + a*vtx2.r, // 色の補間
                inva*vtx1.g + a*vtx2.g,
                inva*vtx1.b + a*vtx2.b
        );

        return ret;
    }
}