import java.util.*;

public class Main {

    public static Map<Character, String> charPrefixHashMap = new HashMap<>();
    static HuffmanNode root;
    static Map<Character, Integer> freq = new HashMap<>();

    public static void main(String[] args) {

        //String test = "Ayssenur ";
        System.out.println("Enter your text : ");
        Scanner scan =  new Scanner(System.in);
        String test =scan.nextLine();
        //System.out.println("Original Text = "+test);

        int len =test.length();

        String s = encode(test);

        System.out.println("Encoded string : " + s );
        System.out.println("Lenght : " + s.length());
        decode(s);


        System.out.println("----------------------");

        CheckFixedLenght(len);
        //System.out.println(freq.size());


    }

    public static void CheckFixedLenght(int len){
        int  k=0;
        for (int i = 0; i <len ; i++) {
            if (Math.pow(2,i) > freq.size()){
                k=i;
                break;
            }
        }
        System.out.println("Fix Lenght Binary Code Lenght : "+ len*k);

    }

    public static HuffmanNode buildTree(Map<Character, Integer> freq) {

        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>();
        Set<Character> keySet = freq.keySet();
        for (Character c : keySet) {

            HuffmanNode huffmanNode = new HuffmanNode();
            huffmanNode.data = c;
            huffmanNode.frequency = freq.get(c);
            huffmanNode.left = null;
            huffmanNode.right = null;
            priorityQueue.offer(huffmanNode);  //adding to the pq
        }
        assert priorityQueue.size() > 0; //stops here if priorityQueue.size() <=0
        //son 1 node kalana kadar devam ettir
        while (priorityQueue.size() > 1) {


            //getting 2 min element
            HuffmanNode x = priorityQueue.peek(); //top element of the pq
            priorityQueue.poll(); //then delete the top

            HuffmanNode y = priorityQueue.peek();
            priorityQueue.poll();


            //then create new node for sum of those elements
            HuffmanNode sum = new HuffmanNode();

            sum.frequency = x.frequency + y.frequency;
            sum.data = '-';

            sum.left = x;

            sum.right = y;
            root = sum;

            priorityQueue.offer(sum); //then add new node to pq
        }

        return priorityQueue.poll(); // en tepedeki kalan bir node u yolla
    }


    public static void setPrefixCodes(HuffmanNode node, StringBuilder prefix) {

        if (node != null) {
            if (node.left == null && node.right == null) {
                charPrefixHashMap.put(node.data, prefix.toString());

            } else { ///assign all left nodes to 0 all right nodes to 1 recursively
                prefix.append('0');
                setPrefixCodes(node.left, prefix);
                prefix.deleteCharAt(prefix.length() - 1);

                prefix.append('1');
                setPrefixCodes(node.right, prefix);
                prefix.deleteCharAt(prefix.length() - 1);
            }
        }

    }

    public static String encode(String test) {

        //creating frequency table
        for (int i = 0; i < test.length(); i++) {
            if (!freq.containsKey(test.charAt(i))) {
                freq.put(test.charAt(i), 0);
            }
            freq.put(test.charAt(i), freq.get(test.charAt(i)) + 1);
        }

        System.out.println("Character frequency :" + freq);
        root = buildTree(freq); //max element of the pq

        setPrefixCodes(root, new StringBuilder());
        System.out.println("Code for each letter : " + charPrefixHashMap);
        StringBuilder s = new StringBuilder();

        for (int i = 0; i < test.length(); i++) {
            char c = test.charAt(i);
            s.append(charPrefixHashMap.get(c));
        }

        return s.toString();
    }

    public static void decode(String s) {

        StringBuilder stringBuilder = new StringBuilder();

        HuffmanNode temp = root;



        for (int i = 0; i < s.length(); i++) {
            int j = Integer.parseInt(String.valueOf(s.charAt(i)));

            if (j == 0) {
                temp = temp.left;
                if (temp.left == null && temp.right == null) { //end of the tree
                    stringBuilder.append(temp.data);
                    temp = root;  //basa geri don
                }
            }
            if (j == 1) {
                temp = temp.right;
                if (temp.left == null && temp.right == null) {
                    stringBuilder.append(temp.data);
                    temp = root;
                }
            }
        }

        System.out.println("Decoded string : " + stringBuilder.toString());

    }
}

