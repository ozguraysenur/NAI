import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    Random rand = new Random();
    int capacity=40;
    int lenght=26;
    int[] sizes;
    int[] values ;

    List<Item> items  =new ArrayList<>();

    public Main(){
     int randomNum = rand.nextInt((15 - 1) + 1) + 1;


       Data data =new Data();
       int[][] s =data.sizes;
       int[][] v =data.values;

       sizes= s[randomNum-1];
       values=v[randomNum-1];

        System.out.println("random number: "+randomNum);
/*        for (int i = 0; i < lenght; i++) {
            System.out.print(sizes[i] + ", ");


        }

        System.out.println();
        for (int i = 0; i < lenght; i++) {
            System.out.print(values[i] + ", ");
        }

*/
        for (int i = 1; i <= lenght; i++) {
            Item item =new Item(i,sizes[i-1],values[i-1]);
            items.add(item);
        }
/*
        for (int i = 0; i < lenght; i++) {
            System.out.println(items.get(i));
        }

*/

        long start1 = System.nanoTime();
        long start = System.currentTimeMillis();
        bruteForce();
        long end = System.currentTimeMillis();
        long end1 = System.nanoTime();

        long elapsedTime = end - start;
        long elapsedTime1 = end1 - start1;
        System.out.println("Time in millisecond : "+elapsedTime);
        System.out.println("Time nanosecond : " +elapsedTime1);

    }

    public void bruteForce(){
        int temp,sumvalue=0,sumweight=0,maxvalue=0,maxweight=0;
        int[] vector,lastVector=null;
        for(int i=0;i<Math.pow(2,lenght);i++){
            vector = new int[lenght];
            temp=i;

            //generate characteristic vector
            for(int j=lenght-1;j>=0;j--){  //binary
                vector[j] = temp%2;
                temp = temp/2;
            }

            for(int j=0;j<lenght;j++){
                if(vector[j]==1){
                    sumvalue+=values[j];
                    sumweight+=sizes[j];
                }
            }

            if(sumweight<=capacity && sumvalue>maxvalue){
                maxvalue=sumvalue;
                maxweight=sumweight;
                lastVector=vector;
            }
            sumweight=0;
            sumvalue=0;
        }
        //System.out.println();
        System.out.println("Total Value: "+maxvalue+" Capacity used: "+maxweight);
        /*
        for(int element: lastVector)
            System.out.print(element+" ");
        */
        //System.out.println();
        for (int i = 0; i < items.size(); i++) {
            if(lastVector[i]==1){
                System.out.println( items.get(i));
            }
        }
    }

    public static void main(String[] args) {

        Main main= new Main();
    }
}
