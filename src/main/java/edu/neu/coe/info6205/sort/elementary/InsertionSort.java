/*
  (c) Copyright 2018, 2019 Phasmid Software
 */
package edu.neu.coe.info6205.sort.elementary;


import edu.neu.coe.info6205.sort.BaseHelper;
import edu.neu.coe.info6205.sort.Helper;
import edu.neu.coe.info6205.sort.SortWithHelper;
import edu.neu.coe.info6205.util.Benchmark_Timer;
import edu.neu.coe.info6205.util.Config;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Supplier;

public class InsertionSort<X extends Comparable<X>> extends SortWithHelper<X> {

    /**
     * Constructor for any sub-classes to use.
     *
     * @param description the description.
     * @param N           the number of elements expected.
     * @param config      the configuration.
     */
    protected InsertionSort(String description, int N, Config config) {
        super(description, N, config);
    }

    /**
     * Constructor for InsertionSort
     *
     * @param N      the number elements we expect to sort.
     * @param config the configuration.
     */
    public InsertionSort(int N, Config config) {
        this(DESCRIPTION, N, config);
    }

    public InsertionSort(Config config) {
        this(new BaseHelper<>(DESCRIPTION, config));
    }

    /**
     * Constructor for InsertionSort
     *
     * @param helper an explicit instance of Helper to be used.
     */
    public InsertionSort(Helper<X> helper) {
        super(helper);
    }

    public InsertionSort() {
        this(BaseHelper.getHelper(InsertionSort.class));
    }

    /**
     * Sort the sub-array xs:from:to using insertion sort.
     *
     * @param xs   sort the array xs from "from" to "to".
     * @param from the index of the first element to sort
     * @param to   the index of the first element not to sort
     */
    public void sort(X[] xs, int from, int to) {
        final Helper<X> helper = getHelper();
        if(helper.instrumented())
        {
            for(int i=from+1;i<to;i++)
            {
                for(int j=i;j>0 && helper.swapStableConditional(xs,j);j--);

            }
        }
        else
        {

            for (int i = from; i < to; i++) {
                int j=i;
                while ( j > 0   && (xs[j-1].compareTo(xs[j])) > 0 ) {
                    X x = xs[j-1];
                    xs[j-1] = xs[j];
                    xs[j] = x;
                    j--;
                }
            }
        }




    }


    public static void main(String[] args) {

        StringBuilder output=new StringBuilder();
        output.append("SL.NO,")
                .append("Array Length(n),")
                .append("Ordered Array,")
                .append("Reverse Ordered Array,")
                .append("Partially Ordered Array,")
                .append("Random Array,").append("Mean Time")
                .append("\n");

        for (int t = 1; t < 5 ; t++) {

            final int n= (int) (Math.pow(2,t)*500);


            final Supplier<Integer[]> intsSupplierOrdered = () -> {
                Integer [] result = (Integer[]) Array.newInstance(Integer.class, n);
                for (int i = 0; i < n; i++) result[i] = i*100;
              // for (int i:result) System.out.print(i+" ");
                return result;
            };

            final Supplier<Integer[]> intsSupplierReversed = () -> {
                Integer [] result2 = (Integer[]) Array.newInstance(Integer.class, n);
                for (int i = 0; i < n; i++) result2[i] = (n-i)*100;
                //for (int i:result) System.out.print(i+" ");
                return result2;
            };

            final Supplier<Integer[]> intsSupplierPartial = () -> {
                Integer [] result3 = (Integer[]) Array.newInstance(Integer.class, n);
                for (int i = 0; i < n/2; i++) result3[i]=i*100 ;
                for(int i=(n/2);i<n;i++) result3[i]=n-i*100 ;
               // for (int i:result) System.out.print(i+" ");
                return result3;
            };

            final Supplier<Integer[]> intsSupplierRandom = () -> {
                Integer [] result4 = (Integer[]) Array.newInstance(Integer.class, n);
                for (int i = 0; i < n; i++) result4[i]=(int)(Math.random()*100);
                //for (int i:result4) System.out.print(i+" ");
                return result4;
            };



            final double timeForOrdered = new Benchmark_Timer<Integer[]>(
                    "intArraysorter",
                   null,
                    x->new InsertionSort<Integer>().sort(x,0,x.length-1),
                    null
            ).runFromSupplier(intsSupplierOrdered, 30);

            final double timeForReversed = new Benchmark_Timer<Integer[]>(
                    "intArraysorter",
                    null,
                    x->new InsertionSort<Integer>().sort(x,0,x.length-1),
                    null
            ).runFromSupplier(intsSupplierReversed, 30);

            final double timeForPartial = new Benchmark_Timer<Integer[]>(
                    "intArraysorter",
                    null,
                    x->new InsertionSort<Integer>().sort(x,0,x.length-1),
                    null
            ).runFromSupplier(intsSupplierPartial, 30);

            final double timeForRandom = new Benchmark_Timer<Integer[]>(
                    "intArraysorter",
                    null,
                    x->new InsertionSort<Integer>().sort(x,0,x.length-1),
                    null
            ).runFromSupplier(intsSupplierRandom, 30);

            double meanTime=(timeForOrdered+timeForReversed+timeForPartial+timeForRandom)/4;
// .append("Array Length(n),")
//                .append("Ordered Array,")
//                .append("Reverse Ordered Array,")
//                .append("Partially Ordered Array,")
//                .append("Random Array")
//                .append("\n");
            output.append(t).append(",")
                    .append(n).append(",").
                    append(timeForOrdered).append(",")
                    .append(timeForReversed).append(",")
                    .append(timeForPartial).append(",")
                    .append(timeForRandom).append(",")
                    .append(meanTime).append(",")
                    .append("\n");
            System.out.println("ordered "+timeForOrdered);
            System.out.println("reversed "+timeForReversed);
            System.out.println("partial "+timeForPartial);
            System.out.println("random "+timeForRandom);


            System.out.println("mean time "+ meanTime);


        }

        try {
            PrintWriter writer = new PrintWriter("./src/main/java/edu/neu/coe/info6205/sort/elementary/insertion-sort-benchmark.csv");
            writer.write(output.toString());
            writer.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }



    }







    public static final String DESCRIPTION = "Insertion sort";

    public static <T extends Comparable<T>> void sort(T[] ts) {
        new InsertionSort<T>().mutatingSort(ts);
    }
}
