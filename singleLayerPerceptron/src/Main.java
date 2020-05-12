import java.io.*;
import java.util.*;

public class Main {
    Map<Character,Double> charCounter=new HashMap<Character,Double>();
    int numberoflang;
    List<Perceptron> perceptrons=new ArrayList<>();
    List<Double> inputs  ;
    int size  ;

    int count=0;
    int correct =0 ;

    public Main() {

        File Files = new File("C:\\Users\\aysen\\Desktop\\dersler\\nai\\singleLayerPerceptron\\train");
        numberoflang = Files.list().length;
        File[] listOfFiles = Files.listFiles();


        char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();


        for (int i = 0; i < 26; i++) {
            charCounter.put(alphabet[i],0.0);

        }
        //System.out.println(charCounter);

        for (int i = 0; i < numberoflang; i++) {
            //System.out.println(listOfFiles[i].getName());
            perceptrons.add(new Perceptron(listOfFiles[i].getName()));
        }
        for (int m = 0; m < 15 ; m++) {  //epoch


            for (int i = 0; i < listOfFiles.length; i++) {

                //System.out.println("File " + listOfFiles[i].getName());
                String path = listOfFiles[i].getPath();
                File file = new File(path);
                File[] listOftxt = file.listFiles();
                for (int j = 0; j < listOftxt.length; j++) {
                    String txtpath = listOftxt[j].getPath();
                    inputs = readData(txtpath);

                    for (int k = 0; k < numberoflang; k++) {

                        perceptrons.get(k).train(inputs, listOfFiles[i].getName());
                    }

                    count = 0;
                    for (char key : charCounter.keySet()) {
                        charCounter.put(key, 0.0);
                    }
                    inputs.clear();
                }


            }
        }
/*
        for (int i = 0; i < 26; i++) {
            System.out.println(perceptrons.get(0).weights.get(i));
        }
*/
        //String aaa ="abbc";
        //frequency(aaa);
        //System.out.println(charCounter);

        test();

        System.out.println("The number of correctly classified samples :" + correct);

        double percent=(((double) correct/size)*100);
        String percentage ="%"+ percent ;

        System.out.println("Accuracy : " +  percentage);

        FROMUSER();

    }


    public List<Double> readData(String path){
        List<Double> tmp= new ArrayList<>();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            String st;
            while ((st = br.readLine()) != null) {
                st = st.replaceAll("[^a-zA-Z]", "");
                st = st.toLowerCase();
                frequency(st);

            }
        } catch (FileNotFoundException e) {
            System.out.println("file not found");
        } catch (IOException e) {
            System.out.println("io exception");
        }

        for ( char key : charCounter.keySet() ) {
            charCounter.put(key, charCounter.get(key)/count);
        }

        for ( char key : charCounter.keySet() ) {
            tmp.add(charCounter.get(key));
        }
        
        
       return tmp;
    }

    public void frequency(String line){

        char[] strArray = line.toCharArray();
        count +=strArray.length;

        for (char c : strArray) {
            if (charCounter.containsKey(c)) {

                // If char is present in charCountMap,
                // incrementing it's count by 1
                charCounter.put(c, charCounter.get(c) + 1);
            }


        }
       
    }

    public String max_selector(List<Perceptron> perc){
        int max = perceptrons.get(0).out;
        String lang =perceptrons.get(0).getName();
        for(Perceptron p : perceptrons){
            if (p.out > max) {
                max = p.out;
                lang = p.getName();

            }
        }
        return lang;
    }

    public void test(){

        File file = new File("C:\\Users\\aysen\\Desktop\\dersler\\nai\\singleLayerPerceptron\\test");
        List<Double>  testinputs;
        File[] listOftest = file.listFiles();
        size=listOftest.length;

        for (int i = 0; i < listOftest.length; i++) {

            String testpath = listOftest[i].getPath();
            testinputs = readData(testpath);

            for (int j = 0; j < numberoflang; j++) {
                int result = perceptrons.get(j).guess(testinputs);

            }
            count = 0;
            for (char key : charCounter.keySet()) {
                charCounter.put(key, 0.0);
            }
            inputs.clear();

            String res =max_selector(perceptrons);

            if(listOftest[i].getName().contains(res)){
                correct++;
            }
        }

    }

    public void FROMUSER() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("----------------------------------------");
        System.out.println();
        String res="";

       while (true) {
            try {

                System.out.println("enter path  : " );
                String path = scanner.nextLine();
                if (path.equals("stop")) {
                    System.out.println("goodbye...");
                    break;
                 }

                //List<Double> h = new ArrayList<>();
                //List<Double> h=readData("C:\\Users\\aysen\\Desktop\\dersler\\nai\\singleLayerPerceptron\\test\\english3.txt");
                List<Double> h=readData(path);
/*
            input = input.replaceAll("[^a-zA-Z]", "");
            input = input.toLowerCase();
            frequency(input);

            for ( char key : charCounter.keySet() ) {
                charCounter.put(key, charCounter.get(key)/count);
            }

            for ( char key : charCounter.keySet() ) {
                h.add(charCounter.get(key));
            }
 */
                for (int i = 0; i < numberoflang; i++) {
                    int sonuc = perceptrons.get(i).guess(h);

                }

                count = 0;
                for (char key : charCounter.keySet()) {
                    charCounter.put(key, 0.0);
                }
                //inputs.clear();

                h.clear();

                 res = max_selector(perceptrons);
                System.out.println("LANGUAGE :  " + res);

            } catch (Exception e) {
                System.err.println("Please enter a valid input!");
            }

        }
    }

    public static void main(String[] args) {
        Main main =new Main();
    }
}
