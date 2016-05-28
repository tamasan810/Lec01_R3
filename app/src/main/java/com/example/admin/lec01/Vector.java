package com.example.admin.lec01;

/**
 * Created by admin on 2016/05/25.
 */
public class Vector {

    // メンバ変数
    /** x,y,z座標 */
    float x, y, z, w;

    /**
     * デフォルトコンストラクタ
     * @return (0,0,0,1)
     */
    public Vector(){
        x = 0;
        y = 0;
        z = 0;
        w = 1;
    }

    /**
     * x,y,z指定コンストラクタ
     * @param x x座標
     * @param y y座標
     * @param z z座標
     * @return (x,y,z,1)
     */
    public Vector(float x, float y, float z){
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = 1;
    }

    /**
     * ベクトルのノルム(長さ)を計算
     * @return ノルム(スカラー値)
     */
    public double getLength(){
        return Math.sqrt(x*x + y*y + z*z);
    }

    /**
     * ベクトルの正規化(ノルムを1にする)
     * @return 正規化されたベクトル
     */
    public void normalize(){
        double length = this.getLength();
        this.x /= length; // this.x = this.x / length; の省略記法
        this.y /= length;
        this.z /= length;
    }

    /**
     * 内積
     * @param vA
     * @param vB
     * @return 内積(スカラー値)
     */
    public static double dot(Vector vA, Vector vB){
        //TODO 埋める
        return vA.x * vB.x + vA.y * vB.y + vA.z * vB.z;
    }

    /**
     * 外積を計算
     * @param vA
     * @param vB
     * @return 外積(ベクトル)
     */
    public static Vector cross(Vector vA, Vector vB){
        //TODO 埋める
        Vector c = new Vector();
        c.x = vA.y * vB.z - vA.z * vB.y;
        c.y = vA.z * vB.x - vA.x * vB.z;
        c.z = vA.x * vB.y - vA.y * vB.x;
        return c;
    }

    /**
     * 線形補間(Linear intERPolation)
     * @param vS 始点
     * @param vE 終点
     * @param a 割合
     * @return (1-a)*vS + a*vE
     */
    public static Vector lerp(Vector vS, Vector vE, float a){
        //TODO 埋める
        Vector r = new Vector();
        r.x = (1-a) * vS.x + a * vE.x;
        r.y = (1-a) * vS.y + a * vE.y;
        r.z = (1-a) * vS.z + a * vE.z;
        return r;
    }

    /**
     * 座標変換
     * @param M 座標変換行列
     * @return 座標変換されたベクトル
     */
    Vector transform(Matrix M){
        Vector t = new Vector();
        //右手座標系変換
        t.x = M.e[0][0]*x + M.e[0][1]*y + M.e[0][2]*z + M.e[0][3]*w;
        t.y = M.e[1][0]*x + M.e[1][1]*y + M.e[1][2]*z + M.e[1][3]*w;
        t.z = M.e[2][0]*x + M.e[2][1]*y + M.e[2][2]*z + M.e[2][3]*w;
        t.w = M.e[3][0]*x + M.e[3][1]*y + M.e[3][2]*z + M.e[3][3]*w;
        return t;
    }
}

