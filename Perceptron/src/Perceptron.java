import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static java.lang.Double.parseDouble;

public class Perceptron {

    String trainingfile ="C:\\Users\\aysen\\Desktop\\dersler\\nai\\Perceptron\\src\\iris_training.txt";
    String testfile ="C:\\Users\\aysen\\Desktop\\dersler\\nai\\Perceptron\\src\\iris_test.txt";

    List<Point> irisTraining;
    List<Point> irisTest;
    List<Double> weight;
    private int totaltest;
    int flower;
    private int attributeN=0;
    double threshold = 0.1;
    double learningRate=0.1;
    int count=0;

    public Perceptron(){
        irisTraining=readData(trainingfile);
        irisTest=readData(testfile);
        totaltest= irisTest.size();
        attributeN=irisTraining.get(0).getAttributes().size();

        weight=randomWeights();

/*      for (int i = 0; i <weight.size(); i++) {
           // System.out.println(irisTraining.get(i));
            System.out.println(weight.get(i));
        }
           int topla=0;
        for (int i = 0; i < irisTraining.size(); i++) {
            int guess= guess(irisTraining.get(i).getAttributes());
            topla+=guess;

            System.out.println(irisTraining.get(i).getAttributes().get(0) + " "  + irisTraining.get(i).getFlower());

        }
       // System.out.println(topla);
*/
        for (int i = 0; i <10; i++) {  //epoch

            for (Point p : irisTraining) {
                train(p.getAttributes(), p.getFlower());
            }
        }
        System.out.println("New weights: ");
        for (int i = 0; i < weight.size(); i++) {
            System.out.println(weight.get(i));
        }

        test();

        System.out.println("The number of correctly classified samples :" + count);

        double percent=(((double) count/totaltest)*100);
        String percentage ="%"+ percent ;

        System.out.println("Accuracy : " +  percentage);


        FROMUSER();
    }

    public List<Point> readData (String filee){
        List<Point> tmp = new ArrayList<>();
        File file = new File(filee);



        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
            String st;
            while ((st = br.readLine()) != null) {
               List <Double> att= getAttributes(st);
                tmp.add(new Point(att,flower));

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
        if(removeFirst.get(removeFirst.size()-1).equals("Iris-setosa")){
        flower=1;
        }else{
            flower=0;
        }

        List<Double> att =new ArrayList<>(removeFirst.size()-1);
        for (int i = 0; i < removeFirst.size()-1; i++) {
            att.add(parseDouble(removeFirst.get(i)));
        }
        return att;
    }
    public String[] split(String a){
        return a.split("\\s+");

    }

    public List<Double> randomWeights(){
        List<Double> weights = new ArrayList<>();
        for(int i = 0; i < attributeN; i++){
            weights.add((Math.random()));
        }
        return weights;
    }

    public int guess(List<Double> inputs){
        double sum=0;
        for (int i = 0; i < inputs.size(); i++) {
            sum+=inputs.get(i)*weight.get(i);
        }
        int output =sign(sum);
        return output;
        
    }

    //activation function
    public int sign(double sum) {

        if(sum>= threshold){
            return 1;
        }else{
            return 0;
        }
    }


    void train(List<Double> inputs,int target){
        int guess =guess(inputs);
        int error =target-guess;


            //adjust all the weights
        for (int i = 0; i < weight.size(); i++) {
            double changedWeight=weight.get(i)+error*inputs.get(i)*learningRate;
            weight.set(i,changedWeight);

        }
    }


    public void test(){

        for (int i = 0; i < irisTest.size(); i++) {

           int test= guess(irisTest.get(i).getAttributes());
           if(test==irisTest.get(i).getFlower()){
               count++;
           }


        }

    }

    public void FROMUSER(){
        Scanner scanner= new Scanner(System.in);
        System.out.println("----------------------------------------");
        System.out.println();

        while(true){
            System.out.println("enter your inputs with one space and use '.' instead of ',' !");
            System.out.println("(write 'stop' for exit)");
            try {
                String input = scanner.nextLine();
                if (input.equals("stop")) {
                    System.out.println("goodbye...");
                    break;
                }
                String[] str = split(input);
                List<Double> userAttributes =new ArrayList<>(str.length);

                for (int i = 0; i < str.length; i++) {
                    userAttributes.add(Double.parseDouble(str[i]));
                }

                int userOutput= guess(userAttributes);
                if(userOutput ==1 ){
                    System.err.println("It's Iris-setosa !");
                }else{
                    System.err.println("It's NOT Iris-setosa !");
                }


            }catch (Exception e){
                System.err.println("Please enter a valid input!");
            }
        }

    }
    public static void main(String[] args) {
        Perceptron perceptron =new Perceptron();
    }
}


