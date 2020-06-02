import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Cluster {
    int index;
    int att;
    List<Iris> listOfIris;
    List<Double> centroid;
    List<Double> counting;
    List<Double> mindistnaces;


    public Cluster(int index ,int atrribute){
        this.index = index;
        this.att=atrribute;
        listOfIris = new ArrayList<>();
        centroid=new ArrayList<>(4);
        assignRandomCenter();

        counting=new ArrayList<>();
        mindistnaces=new ArrayList<>();
    }
    public Cluster(int index, List<Iris> listOfIris){
        this.index = index;
        this.listOfIris = listOfIris;
    }

    public List<Iris> getListOfIris() {
        return listOfIris;
    }

    public void setListOfIris(List<Iris> listOfIris) {
        this.listOfIris = listOfIris;
    }
    public void setnewList(){
        listOfIris = new ArrayList<>();
    }

    public List<Double> getCentroid() {
        return centroid;
    }

    public void setCentroid(List<Double> centroid) {
        this.centroid = centroid;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void assignRandomCenter(){
        Random random = new Random();
        for (int i = 0; i < att; i++) {
            double a=(random.nextDouble() * (7 - 1) + 1);
            double roundOff = Math.round(a * 100.0) / 100.0;
            centroid.add(roundOff);
        }

    }



    @Override
    public String toString() {
        return "Cluster{" +
                "index=" + index +
                ", listOfIris=" + listOfIris +
                '}' ;
    }
}
