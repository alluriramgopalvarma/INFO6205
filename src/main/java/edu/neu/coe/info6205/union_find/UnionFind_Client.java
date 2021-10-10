package edu.neu.coe.info6205.union_find;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

public class UnionFind_Client {


    public static void main(String[] args) {

        Scanner sc=new Scanner(System.in);
        StringBuilder output=new StringBuilder();
        output.append("Initial Nodes,")
                .append("Generated Pairs,")
                .append("1/2 n ln n where n is natural logarithm of n,")
                .append("\n");

        System.out.print("Enter No of test Cases : ");
        int testCases=sc.nextInt(),i=1;
        while (testCases>0)
        {
            System.out.println("Enter No of nodes for testCase "+ i);
            int input=sc.nextInt();
            int generatedPairs=count(input);
            double conclusion= 0.5 * input * Math.log(input);
            output.append(input).append(",").append(generatedPairs).append(",").append(conclusion).append(",").append("\n");
            System.out.println("No of nodes = "+input+"  Avg Generated-Pairs = "+generatedPairs);
            testCases--;
            i++;
            System.out.println();
        }
        try {
            PrintWriter writer = new PrintWriter("./src/main/java/edu/neu/coe/info6205/union_find/conclusion.csv");
            writer.write(output.toString());
            writer.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }


    }

    private static int[] generateRandomPairs(int n, Random r)
    {
        return new int[]{r.nextInt(n), r.nextInt(n)};
    }

    private static int count(int i) {
        // considering average of 200
        int connections=0;

        Random random=new Random();
        for(int t=1;t<200;t++) {
            UF_HWQUPC client = new UF_HWQUPC(i, true);
            {
                int uf=0;
                int c = 0;
                while (client.components() > 1) {
                    int[] pairs = generateRandomPairs(i, random);
                    if (!(client.connected(pairs[0], pairs[1]))) {
                        client.union(pairs[0], pairs[1]);
                    }
                    c++;
                }
                connections += c;
            }
        }

        return connections/200;
    }
}
