package wememe.ca.Activities;

/**
 * Created by Jacob on 2017-05-08.
 */

public class MyReport {

    private int id,R_1,R_2,R_3,R_4;


    public MyReport(int id, int r_1, int r_2, int r_3, int r_4) {
        this.id = id;
        R_1 = r_1;
        R_2 = r_2;
        R_3 = r_3;
        R_4 = r_4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getR_1() {
        return R_1;
    }

    public void setR_1(int r_1) {
        R_1 = r_1;
    }

    public int getR_2() {
        return R_2;
    }

    public void setR_2(int r_2) {
        R_2 = r_2;
    }

    public int getR_3() {
        return R_3;
    }

    public void setR_3(int r_3) {
        R_3 = r_3;
    }

    public int getR_4() {
        return R_4;
    }

    public void setR_4(int r_4) {
        R_4 = r_4;
    }
}
