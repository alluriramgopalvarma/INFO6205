/*
 * Copyright (c) 2017. Phasmid Software
 */

package edu.neu.coe.info6205.randomwalk;

import java.util.Random;

public class RandomWalk {

    private int x = 0;
    private int y = 0;

    private final Random random = new Random();

    /**
     * Private method to move the current position, that's to say the drunkard moves
     *
     * @param dx the distance he moves in the x direction
     * @param dy the distance he moves in the y direction
     */
    private void move(int dx, int dy) {
        x+=dx;
        y+=dy;
    }

    /**
     * Perform a random walk of m steps
     *
     * @param m the number of steps the drunkard takes
     */
    private void randomWalk(int m) {
        for(int i=0;i<m;i++)
        {
            this.randomMove();
        }
    }

    /**
     * Private method to generate a random move according to the rules of the situation.
     * That's to say, moves can be (+-1, 0) or (0, +-1).
     */
    private void randomMove() {
        boolean ns = random.nextBoolean();
        int step = random.nextBoolean() ? 1 : -1;
        move(ns ? step : 0, ns ? 0 : step);
    }

    /**
     * Method to compute the distance from the origin (the lamp-post where the drunkard starts) to his current position.
     *
     * @return the (Euclidean) distance from the origin to the current position.
     */
    public double distance() {
        // TO BE IMPLEMENTED
        return (Math.sqrt((x)*(x) + (y)*(y)));

    }

    /**
     * Perform multiple random walk experiments, returning the mean distance.
     *
     * @param m the number of steps for each experiment
     * @param n the number of experiments to run
     * @return the mean distance
     */
    public static double randomWalkMulti(int m, int n) {
        double totalDistance = 0;
        for (int i = 0; i < n; i++) {
            RandomWalk walk = new RandomWalk();
            walk.randomWalk(m);
            totalDistance = totalDistance + walk.distance();
        }
        return totalDistance / n;
    }

    public static void main(String[] args) {
        if (args.length == 0)
            throw new RuntimeException("Syntax: RandomWalk steps [experiments]");
        int n = 30;

        // here the arguments contains a array of steps (n)
        for(int t=0;t<args.length;t++)
        {
            int respositive=0,resnegative=0;
            for(int t2=0;t2<n;t2++)
            {

                int m = Integer.parseInt(args[t]);
                double meanDistance = randomWalkMulti(m, n);
                System.out.println(m + " steps: " + meanDistance + " over " + n + " experiments");
                if(meanDistance<Math.sqrt(m)) respositive+=1;
                else resnegative+=1;
            }
            if(respositive>resnegative) System.out.println("Distacne (d) ≈ Sqrt(m)");
            else System.out.println("No relation");

            System.out.println();
        }

    }

}
