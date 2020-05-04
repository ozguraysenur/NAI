import java.util.List;

public class Point {


    private List<Double> attributes;
    private int flower;

    public Point(List<Double> attributes, int flower) {
        this.attributes = attributes;
        this.flower = flower;
    }

    public List<Double> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Double> attributes) {
        this.attributes = attributes;
    }

    public int getFlower() {
        return flower;
    }

    public void setFlower(int flower) {
        this.flower = flower;
    }

    @Override
    public String toString() {
        return "Point{" +
                "attributes=" + attributes.get(0) +
                ", flower='" + flower + '\'' +
                '}';
    }
}
