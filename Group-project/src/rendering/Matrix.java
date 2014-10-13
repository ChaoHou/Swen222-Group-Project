package rendering;

/**
 * Created by innocence on 9/9/2013.
 * row majored order
 */

public class Matrix {
    public static float[] multMatrix(float[] one,float[] two) {
        float[] result;

        //1
        result = new float[]{
                one[0]*two[0]+one[1]*two[4]+one[2]*two[8]+one[3]*two[12],
                //2
                one[0]*two[1]+one[1]*two[5]+one[2]*two[9]+one[3]*two[13],
                //3
                one[0]*two[2]+one[1]*two[6]+one[2]*two[10]+one[3]*two[14],
                //4
                one[0]*two[3]+one[1]*two[7]+one[2]*two[11]+one[3]*two[15],
                //5
                one[4]*two[0]+one[5]*two[4]+one[6]*two[8]+one[7]*two[12],
                //6
                one[4]*two[1]+one[5]*two[5]+one[6]*two[9]+one[7]*two[13],
                //7
                one[4]*two[2]+one[5]*two[6]+one[6]*two[10]+one[7]*two[14],
                //8
                one[4]*two[3]+one[5]*two[7]+one[6]*two[11]+one[7]*two[15],
                //9
                one[8]*two[0]+one[9]*two[4]+one[10]*two[8]+one[11]*two[12],
                //10
                one[8]*two[1]+one[9]*two[5]+one[10]*two[9]+one[11]*two[13],
                //11
                one[8]*two[2]+one[9]*two[6]+one[10]*two[10]+one[11]*two[14],
                //12
                one[8]*two[3]+one[9]*two[7]+one[10]*two[11]+one[11]*two[15],
                //13
                one[12]*two[0]+one[13]*two[4]+one[14]*two[8]+one[15]*two[12],
                //14
                one[12]*two[1]+one[13]*two[5]+one[14]*two[9]+one[15]*two[13],
                //15
                one[12]*two[2]+one[13]*two[6]+one[14]*two[10]+one[15]*two[14],
                //16
                one[12]*two[3]+one[13]*two[7]+one[14]*two[11]+one[15]*two[15]};
        return result;
    }
}
