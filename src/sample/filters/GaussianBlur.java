package sample.filters;

import sample.helpers.FilterHelper;

public class GaussianBlur {
    static double[][] kernel;

    public static int[][] blur(int[][] image, int size, int weight){
        calculateKernel(size, weight);
        return FilterHelper.convolve(image, kernel);
    }


    public static void calculateKernel(int length, double weight) {
        kernel = new double[length][length];
        double sumTotal = 0;

        int kernelRadius = length / 2;
        double distance = 0;

        double calculatedEuler = 1.0 / (2.0 * Math.PI * Math.pow(weight, 2));

        for (int filterY = -kernelRadius;
             filterY <= kernelRadius; filterY++) {
            for (int filterX = -kernelRadius; filterX <= kernelRadius; filterX++) {
                distance = ((filterX * filterX) + (filterY * filterY)) / (2 * (weight * weight));
                kernel[filterY + kernelRadius][filterX + kernelRadius] = calculatedEuler * Math.exp(-distance);
                sumTotal += kernel[filterY + kernelRadius][filterX + kernelRadius];
            }
        }

        for (int y = 0; y < length; y++) {
            for (int x = 0; x < length; x++) {
                kernel[y][x] = kernel[y][x]
                        * (1.0 / sumTotal);
            }
        }
    }
}
