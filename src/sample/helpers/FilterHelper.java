package sample.helpers;

public class FilterHelper {
    public static int[][] convolve(int[][] image, double[][] kernel) {
        int[][] out = new int[image.length][image[0].length];
        int indx, indy;
        int width, height, kernelV, kernelH;
        double kernelSum = 0;
        for (int n = 0; n < kernel.length; n++) {
            for (int m = 0; m < kernel[0].length; m++) {
                kernelSum += kernel[n][m];
            }
        }
        if (kernelSum < 0.0001) {
            kernelSum = 1; // ze względu na utratę dokładności przy reprezentacji liczb nie porównujemy z zerem
        }
        width = image[0].length;
        height = image.length;
        kernelV = kernel[0].length;
        kernelH = kernel.length;

        for (int i = 0; i < image.length; i++) {
            for (int j = 0; j < image[0].length; j++) {
                double accumR = 0, accumG = 0, accumB = 0;
                int r, g, b;
                for (int n = 0; n < kernel.length; n++) {
                    for (int m = 0; m < kernel[0].length; m++) {
                        indx = j + kernelV / 2 - m;
                        indy = i + kernelH / 2 - n;
                        // zabiezpieczenia przed przekroczeniem obrazu
                        if (indx < 0) {
                            indx = 0;
                        }
                        if (indy < 0) {
                            indy = 0;
                        }
                        if (indx >= width) {
                            indx = width - 1;
                        }
                        if (indy >= height) {
                            indy = height - 1;
                        }

                        accumR += kernel[n][m] * (image[indy][indx] >> 16 & 0xff);
                        accumG += kernel[n][m] * (image[indy][indx] >> 8 & 0xff);
                        accumB += kernel[n][m] * (image[indy][indx] & 0xff);
                    }
                }

                accumR /= kernelSum;
                accumG /= kernelSum;
                accumB /= kernelSum;
                accumR = truncate(accumR);
                accumG = truncate(accumG);
                accumB = truncate(accumB);

                out[i][j] = 0xff000000 | ((int) accumR << 16) | ((int) accumG << 8) | (int) accumB;
            }
        }

        return out;
    }

    public static double truncate(double value) {
        if (value > 255) {
            value = 255;
        }
        if (value < 0) {
            value = 0;
        }
        return value;
    }
}
