package ca.mogkolpon.pbrurun;

/**
 * Created by lap324-04 on 6/13/16 AD.
 */
public class MyData {

    //Explicit
    private int[] avataInts = new int[] {R.drawable.bird48,
            R.drawable.doremon48,R.drawable.kon48,R.drawable.nobita48,R.drawable.rat48};
    private double latADouble = 13.069937,lngADouble = 99.978201;

    public int[] getAvataInts(){
        return avataInts;
    }

    public double getLatADouble() {
        return latADouble;
    }

    public double getLngADouble() {
        return lngADouble;
    }


} // Main Class