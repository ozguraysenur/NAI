import java.util.ArrayList;
import java.util.List;

public class Perceptron {
    private String name;
    List<Double> weights;
    List<Double> inputs;
    double threshold =0.1;
    double learningRate=0.1;
    int out;

    public Perceptron(String name) {
        this.name = name;
        this.weights = randomWeights();

    }

    public List<Double> randomWeights(){
        List<Double> weights = new ArrayList<>();
        for(int i = 0; i < 26; i++){
            weights.add((Math.random()));
        }
        return weights;
    }



    public int guess(List<Double> inputs){
        double sum=0;
        for (int i = 0; i < inputs.size(); i++) {
            sum+=inputs.get(i)*weights.get(i);
        }
        int output =sign(sum);
        out=output;
        return output;

    }

    public int sign(double sum) {

        if(sum>= threshold){
            return 1;
        }else{
            return 0;
        }
    }

    void train(List<Double> inputs,String language){
        int guess =guess(inputs);
        int correct;
        if(language.equals(name)){
            correct =1;
        }else {
            correct=0;
        }
        int error =correct-guess;


        //adjust all the weights
        for (int i = 0; i < weights.size(); i++) {
            double changedWeight=weights.get(i)+error*inputs.get(i)*learningRate;
            weights.set(i,changedWeight);

        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Double> getWeights() {
        return weights;
    }

    public void setWeights(List<Double> weights) {
        this.weights = weights;
    }
}
