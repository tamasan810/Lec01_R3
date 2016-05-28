package com.example.admin.lec01;

/**
 * Created by admin on 2016/05/26.
 */
public class Face {
    Vertex A, B, C; // 頂点

    public Face(Vertex a, Vertex b, Vertex c){
        A = a;
        B = b;
        C = c;
    }

    Vector getNormal(){
        Vector AC = new Vector(C.v.x - A.v.x, C.v.y - A.v.y, C.v.z - A.v.z);
        Vector AB = new Vector(B.v.x - A.v.x, B.v.y - A.v.y, B.v.z - A.v.z);

        return Vector.cross(AC, AB);
    }
}
