import java.io.*;
import java.util.*;

import static java.lang.Double.parseDouble;

public class Kmeans{



    String trainingfile ="C:\\Users\\aysen\\Desktop\\dersler\\nai\\K-means\\src\\iris_training.txt";

    public  List<Iris> irislist;
    public List<Cluster> clusters;
    int attribute;
    String flower;
    public int k ;
    Scanner scanner =new Scanner(System.in);
    int size;
    double sum=0;
    public Kmeans() {

        irislist=readData(trainingfile);
        size=irislist.size();

        clusters=new ArrayList<>();
        /*
        for (int i = 0; i < irislist.size(); i++) {
            System.out.println(irislist.get(i));
        }*/
        attribute=irislist.get(0).getData().size();

        System.out.println("lets start!");
        System.out.println("please enter k :");

        k=scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < k; i++) {
            clusters.add(new Cluster(i+1,attribute));
        }

        assignRandomtoCluster();
        //calculatedistances();

/*
        for (int i = 0; i < k; i++) {
            System.out.println(clusters.get(i).getCentroid());
        }


        for (int i = 0; i < size; i++) {
            System.out.println(irislist.get(i).getDistance());
            System.out.println();
        }

        for (int i = 0; i < clusters.size(); i++) {
            System.out.println(i);
            System.out.println(clusters.get(i).getListOfIris());
            System.out.println();
        }

*/

        for (int i = 0; i < clusters.size(); i++) {
            average(clusters.get(i).listOfIris,i);
        }
           calculatedistances();

            learn();


        for (int i = 0; i < clusters.size(); i++) {
            System.out.println(i+1);
            System.out.println("-----------------------------------------");
            for (int j = 0; j < clusters.get(i).getListOfIris().size(); j++) {
                System.out.println(clusters.get(i).getListOfIris().get(j));
            }

        }
/*
        for (int i = 0; i < size; i++) {
            System.out.println(irislist.get(i).getDistance());
            System.out.println();
        }
*/      System.out.println("--------------------------------------");
        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("cluster : " +clusters.get(i).index);
            clusters.get(i).counting=count(clusters.get(i));
        }
        System.out.println("--------------------------------------");
        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("Cluster "+clusters.get(i).getIndex() +" entropy : "+ calculateEntropy(clusters.get(i).counting));



        }


    }

    public double log2(double x)
    {
        return (double) (Math.log(x) / Math.log(2));
    }


    public double calculateEntropy(List<Double> p){
        double sum=0.0;
        for (int i = 0; i < p.size(); i++) {
            if(p.get(i)==0.0){
                continue;
            }
            sum+=p.get(i)*log2(p.get(i));
        }
            sum=(-1)*sum;
        //System.out.print("Entropy of the : " + sum);
        return sum;
    }

    public void learn(){

        List<Cluster> tmpCluster = new ArrayList<>();
        for(int i = 0; i < k; i++){
            tmpCluster.add(new Cluster(i+1,attribute));
        }
        for(int i = 0; i < k; i++){
            tmpCluster.get(i).listOfIris.addAll(clusters.get(i).getListOfIris());
        }
        int iteration=0;
        boolean islearning=true;
        while(islearning){

            iteration++;

            System.out.println("Iteration : "+iteration);
            System.out.println( "=----------------------------------");
            for (int i = 0; i < clusters.size(); i++) {
                average(clusters.get(i).listOfIris,i);
            }
/*
            for (int i = 0; i < k; i++) {
                System.out.println(clusters.get(i).getCentroid());
            }
*/


            for (int i = 0; i < clusters.size(); i++) {
                for (int j = 0; j < clusters.get(i).mindistnaces.size(); j++) {


                    sum+=clusters.get(i).mindistnaces.get(j);
                }


                System.out.println("cluster "+clusters.get(i).getIndex()+" : ");
                System.out.println("sum :" +sum);

                //System.out.println( "=----------------------------------");
                sum=0;
            }




/*

            for (int i = 0; i < size; i++) {
                int index=checkmin(irislist.get(i).distancestocentroids);

                sum+=irislist.get(i).getDistance().get(index);
            }

            System.out.println("sum :" + sum);
            sum=0;


*/

            for (int i = 0; i < size; i++) {

               irislist.get(i).distancestocentroids.clear();
            }

            for (int i = 0; i < clusters.size(); i++) {

               clusters.get(i).mindistnaces.clear();;
            }

            for (int i = 0; i < k; i++) {

                clusters.get(i).listOfIris.clear();
            }

            calculatedistances();


            islearning=compareCluster(tmpCluster);


            if(islearning==false){
                break;
            }

            for(int i = 0; i < k; i++){
                tmpCluster.get(i).listOfIris.clear();
            }

            for(int i = 0; i < k; i++){
                tmpCluster.get(i).listOfIris.addAll(clusters.get(i).getListOfIris());
            }


        }

    }
    
    public void average(List<Iris> iris,int index){



        for (int i = 0; i < attribute; i++) {
            double sum=0;


            for (int j = 0; j < iris.size(); j++) {
                sum+=iris.get(j).getData().get(i);
            }
            if(iris.size()==0){
                continue;
            }
            double aver= (double)sum/(double)iris.size();
            clusters.get(index).centroid.set(i,aver);
        }
    }


    public void calculatedistances(){
        for (int i = 0; i < size; i++) {

            for (int j = 0; j < k; j++) {
               double d =euclidean(irislist.get(i),clusters.get(j).centroid);
               irislist.get(i).distancestocentroids.add(d);


            }
            int index=checkmin(irislist.get(i).distancestocentroids);
            clusters.get(index).listOfIris.add(irislist.get(i));
            double value =irislist.get(i).getDistance().get(index);
            clusters.get(index).mindistnaces.add(value);

        }

    }
    public List<Double> count(Cluster cluster){

        List<Double> tmp=new ArrayList<>();
        double irisSetosa = 0;
        double irisverColor = 0;
        double irisVirginica = 0;


        for(Iris iris : cluster.getListOfIris()){
            if(iris.getName().equals("Iris-setosa")){
                irisSetosa++;
            } else if (iris.getName().equals("Iris-versicolor")){
                irisverColor++;
            } else {
                irisVirginica++;
            }
        }

        irisSetosa = irisSetosa / cluster.getListOfIris().size() ;
        irisverColor = irisverColor  / cluster.getListOfIris().size();
        irisVirginica = irisVirginica  / cluster.getListOfIris().size();

        tmp.add(irisSetosa);
        tmp.add(irisverColor);
        tmp.add(irisVirginica);


        System.out.println("Iris Setosa : " + irisSetosa);
        System.out.println("Iris Versicolor : " + irisverColor );
        System.out.println("Iris Virginica : " + irisVirginica );

        return tmp;
    }




    public int checkmin(List<Double> dis){
        List<Double> sortedlist = new ArrayList<>(dis);


        Collections.sort(sortedlist);


        int minIndex = dis.indexOf(sortedlist.get(0));

        return minIndex;
    }
    public List<Iris> readData (String filee){
        List<Iris> tmp = new ArrayList<>();
        File file = new File(filee);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
                List <Double> att= getAttributes(st);
                tmp.add(new Iris(att,flower));
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("io exception");
        }
        return tmp;

    }

    public List<Double> getAttributes(String a){
        String replaceString=a.replace(",",".");

        String[] sp =split(replaceString);
        List<String> removeFirst =new ArrayList<>();
        removeFirst.addAll(Arrays.asList(sp));
        removeFirst.remove(0);

        flower=removeFirst.get(removeFirst.size()-1);

        List<Double> att =new ArrayList<>(removeFirst.size()-1);
        for (int i = 0; i < removeFirst.size()-1; i++) {
            att.add(parseDouble(removeFirst.get(i)));
        }
        return att;
    }
    public String[] split(String a){
        return a.split("\\s+");

    }
    public double euclidean(Iris iris ,List<Double> centroid){
        double sum =0;
        for (int i = 0; i <attribute; i++) {
            double s=centroid.get(i);
            double power =Math.pow((iris.getData().get(i) -s),2);
            sum+=power;

        }
        //return Math.sqrt(sum);
        return sum;
    }


    public boolean compareCluster(List<Cluster> list){
        boolean running = true;
        int count1=0;
        int count2=0;

        //outerloop:

        for(int i = 0; i < clusters.size(); i++){
            //if listofcluster not same with the temporary cluster then change listofcluster to temporary one and keep the loop
            if(!clusters.get(i).listOfIris.equals(list.get(i).listOfIris)) {
                //clusters.get(i).listOfIris.clear();
                //clusters.get(i).listOfIris.addAll(list.get(i).listOfIris);

                count1++;
            }
                //break outerloop;

            //} else{
                //if same then stop loop
                //running = false;
           // }

        }

        if(count1!=0){
            return true;
        }else {
            return false;
        }
    }

    public  void assignRandomtoCluster(){
        Random random = new Random();
        for(Iris iris : irislist){
            int index = random.nextInt(k) +1;
            // int index = (int)( Math.random() * numberOfCluster )+ 1;
            clusters.get(index-1).getListOfIris().add(iris);

        }
    }

    public static void main(String[] args) {
      Kmeans kmeans=new Kmeans();
    }
}
