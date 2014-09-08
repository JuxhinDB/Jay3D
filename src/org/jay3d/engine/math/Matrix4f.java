package org.jay3d.engine.math;

/**
 * Created by Juxhin
 * Do not distribute code without permission!
 */
public class Matrix4f {
    private float[][] m;

    public Matrix4f(){
        m = new float[4][4];
    }

    public Matrix4f initIdentity(){
        for(int x = 0; x < 4; x++){
            for(int y = 0; y < 4; y++){
                m[x][y] = 0;
            }
        }

        return this;
    }

    public Matrix4f initTranslation(float x, float y, float z){
        m[0][0] = 1; m[0][1] = 0; m[0][2] = 0; m[0][3] = x;
        m[1][0] = 0; m[1][1] = 1; m[1][2] = 0; m[1][3] = y;
        m[2][0] = 0; m[2][1] = 0; m[2][2] = 1; m[2][3] = z;
        m[3][0] = 0; m[3][1] = 0; m[3][2] = 0; m[3][3] = 1;

        return this;
    }

    public Matrix4f mul(Matrix4f v){
        Matrix4f res = new Matrix4f();

        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 4; j++){
                res.set(i, j, m[i][0] * res.get(0, j)
                            + m[i][1] * res.get(1, j)
                            + m[i][2] * res.get(2, j)
                            + m[i][3] * res.get(3, j));
            }
        }

        return res;
    }

    public float get(int x, int y){
        return m[x][y];
    }

    public void set(int x, int y, float value){
        this.m[x][y] = value;
    }

    public float[][] getM() {
        return m;
    }

    public void setM(float[][] m) {
        this.m = m;
    }
}
