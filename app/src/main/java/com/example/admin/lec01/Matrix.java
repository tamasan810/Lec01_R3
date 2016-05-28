package com.example.admin.lec01;

/**
 * Created by admin on 2016/05/25.
 */
public class Matrix {

    static final private int D = 4;
    float e[][] = new float[D][D]; // 4行4列

    /**
     * 全要素0で初期化
     */
    public Matrix(){
        for(int r = 0 ; r < D ; r++){
            for(int c = 0 ; c < D ; c++){
                e[r][c] = 0;
            }
        }
    }

    /**
     * 配列で初期化
     * @param array
     */
    public Matrix(float[][] array){
        e = array;
    }

    /**
     * 拡大縮小変換
     * @param sx x軸方向倍率
     * @param sy y軸方向倍率
     * @param sz z軸方向倍率
     */
    static Matrix Scale(float sx, float sy, float sz){
        return new Matrix(new float[][]{
                {sx,  0,  0, 0},
                {0,  sy,  0, 0},
                {0,   0, sz, 0},
                {0,   0,  0, 1}});
    }

    /**
     * X軸回転行列
     * @param r 回転角（ラジアン）
     */
    static Matrix RotX(double r){

        float sin = (float)Math.sin(r);
        float cos = (float)Math.cos(r);

        //TODO 埋める
        return new Matrix(new float[][]{
                {1,  0,   0,  0},
                {0, cos,-sin, 0},
                {0, sin, cos, 0},
                {0,  0,   0,  1}
        });

//		return new Matrix();	// ダミー
    }

    /**
     * Y軸回転行列
     * @param r 回転角（ラジアン）
     */
    static Matrix RotY(float r){
        float sin = (float)Math.sin(r);
        float cos = (float)Math.cos(r);

        //TODO 埋める
        return new Matrix(new float[][]{
                { cos, 0, sin, 0},
                {  0,  1,  0,  0},
                {-sin, 0, cos, 0},
                {  0,  0,  0,  1}
        });

//		return new Matrix();	// ダミー
    }

    /**
     * Z軸回転
     * @param r 回転角（ラジアン）
     */
    static Matrix RotZ(float r){
        float sin = (float)Math.sin(r);
        float cos = (float)Math.cos(r);

        //TODO 埋める
        return new Matrix(new float[][]{
                {cos, -sin, 0, 0},
                {sin,  cos, 0, 0},
                { 0,    0,  1, 0},
                { 0,    0,  0, 1}
        });

//		return new Matrix();	// ダミー
    }

    /**
     * 平行移動行列
     * @param dx X移動量
     * @param dy Y移動量
     * @param dz Z移動量
     */
    static Matrix Translate(float dx, float dy, float dz){
        //TODO 埋める
        return new Matrix(new float[][]{
                {1, 0, 0, dx},
                {0, 1, 0, dy},
                {0, 0, 1, dz},
                {0, 0, 0,  1},
        });
//		return new Matrix();	// ダミー
    }

    /**
     * スクリーン変換
     * @param scW スクリーン幅
     * @param scH スクリーン高さ
     */
    static Matrix Screen(int scW, int scH){
        //TODO 埋める
        // y軸が下向きなのでx軸180度回転
        return new Matrix(new float[][]{
                {1,  0,  0, scW/2},
                {0, -1,  0, scH/2},
                {0,  0, -1,   0  },
                {0,  0,  0,   1  },
        });
    }

    /**
     * 行列と行列の掛け算
     * @param m 掛ける行列
     * @return this×m
     */
    public Matrix mult(Matrix m){
        Matrix ret = new Matrix();
        //TODO 埋める
        for (int i = 0; i < D; i++) {
            for (int j = 0; j < D; j++) {
                float sum = 0;
                for (int k = 0; k < D; k++){
                    sum += this.e[i][k] * m.e[k][j];
                }
                ret.e[i][j] = sum;
            }
        }
        return ret;
    }
}