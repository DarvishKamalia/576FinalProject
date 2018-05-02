public class ColorTuple {
    public double h;
    public double s;
    public double v;

    public ColorTuple(){
        h = 0;
        s = 0;
        v = 0;
    }

    public ColorTuple (float[] hsvValues){
        h = hsvValues[0];
        s = hsvValues[1];
        v = hsvValues[2];
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof ColorTuple)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        ColorTuple c = (ColorTuple) o;

        // Compare the data members and return accordingly
        return Double.compare(h, c.h) == 0
                && Double.compare(s, c.s) == 0
                && Double.compare(v, c.v) == 0;
    }
}
