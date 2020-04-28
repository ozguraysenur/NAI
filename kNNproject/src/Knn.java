import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Knn {

    String trainingfile ="C:\\Users\\aysen\\Desktop\\dersler\\nai\\KNNproject\\src\\iris_training.txt";
    String testfile ="C:\\Users\\aysen\\Desktop\\dersler\\nai\\KNNproject\\src\\iris_test.txt";

    List<String> irisTraining;
    List<String> irisTest;
    List<Distance> distances =new ArrayList<>();
    List<String> flowersResult =new ArrayList<>();

    Scanner scanner = new Scanner(System.in);

    private int totaltest;
    private int attribute=0;
    private String flower;
    private int k;
    int count =0 ;


    public Knn(){
        irisTraining =readData(trainingfile);
        irisTest=readData(testfile);
        totaltest= irisTest.size();

        double [] a =getAttributes(irisTest.get(0));
        attribute=a.length;

        System.out.println("lets start!");
        System.out.println("please enter k :");

        k=scanner.nextInt();
        scanner.nextLine();
        distancesForTestSamples();

        double percent=(((double) count/totaltest)*100);
        String percentage ="%"+ percent ;

        System.out.println("Accuracy : " +  percentage);

        flowersResult.clear();
        FROMUSER();

    }


    public List<String> readData (String filee){
        List<String> tmp = new ArrayList<>();
        File file = new File(filee);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
               tmp.add(st);
            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("io exception");
        }
        return tmp;

    }
    public double[] getAttributes(String a){
        String replaceString=a.replace(",",".");

        String[] sp =split(replaceString);
        List<String> removeFirst =new ArrayList<>();
        removeFirst.addAll(Arrays.asList(sp));
        removeFirst.remove(0);
        flower=removeFirst.get(removeFirst.size()-1);

        double[] att =new double[removeFirst.size()-1];
        for (int i = 0; i < att.length; i++) {
            att[i] =Double.parseDouble(removeFirst.get(i));
        }
        return att;
    }

    public void distancesForTestSamples(){
        for (int i = 0; i < totaltest; i++) {
            double[] testattributes =getAttributes(irisTest.get(i));

            calculateDistanceFromTrainings(testattributes);
            accuracy(irisTest.get(i),flowersResult.get(i));
            distances.clear();

        }
    }
    public void calculateDistanceFromTrainings(double[] attributes){
        for (int j = 0; j < irisTraining.size(); j++) {

            double[] trainingattributes =getAttributes(irisTraining.get(j));

            double euclid =euclidean(attributes,trainingattributes);

            distances.add(new Distance(euclid,flower));

        }
        sorting(distances);
    }


    public double euclidean(double[] first ,double[] second){
        double sum =0;
        for (int i = 0; i <attribute; i++) {
            double power =Math.pow(first[i] -second[i],2);
            sum+=power;

        }
        return Math.sqrt(sum);
    }



    public void sorting(List<Distance> list ){

         distances = list.stream()
                .sorted()
                .limit(k)
                .collect(Collectors.toList());

        int countIrVi = 0;
        int countIrVer = 0;
        int countIrSer = 0;
        for(Distance i : distances){
            if(i.getFlower().equals("Iris-virginica") ){
                countIrVi++;
            }
            if(i.getFlower().equals("Iris-versicolor") ){
                countIrVer++;
            }
            if(i.getFlower().equals("Iris-setosa") ){
                countIrSer++;
            }
        }

        if(countIrVi > countIrVer && countIrVi > countIrSer){
            flowersResult.add("Iris-virginica");
        }
        else if(countIrVer > countIrVi && countIrVer > countIrSer){
            flowersResult.add("Iris-versicolor");
        }
        else {
            flowersResult.add("Iris-setosa");

        }


    }

    public void accuracy(String realResult,String ourResult){

        String[] s = split(realResult);
        String testFlower =s[s.length-1];

        if(testFlower.equals(ourResult)){
            count++;
        }

    }

    public void FROMUSER(){

        System.out.println("----------------------------------------");
        System.out.println();

        while(true){
            System.out.println("enter your measurements with one space and use '.' instead of ',' !");
            System.out.println("(write 'stop' for exit)");
            try {
                String input = scanner.nextLine();
                if (input.equals("stop")) {
                    System.out.println("goodbye...");
                    break;
                }
                String[] str = split(input);
                double[] userAttributes = new double[str.length];
                for (int i = 0; i < str.length; i++) {
                    userAttributes[i] = Double.parseDouble(str[i]);
                }

            calculateDistanceFromTrainings(userAttributes);
            distances.clear();
            System.out.println("your flower is : " + flowersResult.get(0));
            flowersResult.clear();
            }catch (Exception e){
                System.err.println("Please enter a valid input!");
            }
        }

    }

    public String[] split(String a){
        return a.split("\\s+");

    }
    public static void main(String[] args) {
        Knn knn = new Knn();
    }

}

