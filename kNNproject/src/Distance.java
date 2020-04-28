public class Distance implements Comparable<Distance> {


    private double dist;
    private String flower;

    public Distance(double dist, String flower) {
        this.dist = dist;
        this.flower = flower;
    }

    public double getDistance() {
        return dist;
    }

    public void setDistance(int dist) {
        this.dist = dist;
    }

    public String getFlower() {
        return flower;
    }

    public void setFlower(String flower) {
        this.flower = flower;
    }

    @Override
    public int compareTo(Distance o) {
        if(getDistance() > o.getDistance() )
            return 1;
        if(getDistance() == o.getDistance())
            return 0;
        else
            return -1;
    }
}
