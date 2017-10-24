package sample.helpers;

public class OperationHelper {
    public static byte[] toGrayScale(byte[] image) {

        for (int i = 0; i < image.length; i += 3) {
            int argb;
            int R, G, B;
            B = image[i] & 0xff;
            G = image[i + 1] & 0xff;
            R = image[i + 2] & 0xff;

            argb = (int) (0.299 * R + 0.587 * G + 0.114 * B);
            image[i] = image[i + 1] = image[i + 2] = (byte) argb;
        }
        return image;
    }

    public static byte[] multiply(byte[] image, double value) {

        for (int i = 0; i < image.length; i += 3) {
            int R, G, B;
            B = image[i] & 0xff;
            G = image[i + 1] & 0xff;
            R = image[i + 2] & 0xff;
            R *= value;
            G *= value;
            B *= value;
            R = truncate(R);
            B = truncate(B);
            G = truncate(G);
            image[i] = (byte) R;
            image[i + 1] = (byte) G;
            image[i + 2] = (byte) B;
        }
        return image;
    }

    public static byte[] div(byte[] image, double value) {

        for (int i = 0; i < image.length; i += 3) {
            int R, G, B;
            B = image[i] & 0xff;
            G = image[i + 1] & 0xff;
            R = image[i + 2] & 0xff;
            R /= value;
            G /= value;
            B /= value;
            R = truncate(R);
            B = truncate(B);
            G = truncate(G);
            image[i] = (byte) R;
            image[i + 1] = (byte) G;
            image[i + 2] = (byte) B;
        }
        return image;
    }

    public static byte[] add(byte[] image, double value) {

        for (int i = 0; i < image.length; i += 3) {
            int R, G, B;
            B = image[i] & 0xff;
            G = image[i + 1] & 0xff;
            R = image[i + 2] & 0xff;
            R += value;
            G += value;
            B += value;
            R = truncate(R);
            B = truncate(B);
            G = truncate(G);
            image[i] = (byte) R;
            image[i + 1] = (byte) G;
            image[i + 2] = (byte) B;
        }
        return image;
    }

    public static byte[] substract(byte[] image, double value) {

        for (int i = 0; i < image.length; i += 3) {
            int R, G, B;
            B = image[i] & 0xff;
            G = image[i + 1] & 0xff;
            R = image[i + 2] & 0xff;
            R -= value;
            G -= value;
            B -= value;
            R = truncate(R);
            B = truncate(B);
            G = truncate(G);
            image[i] = (byte) R;
            image[i + 1] = (byte) G;
            image[i + 2] = (byte) B;
        }
        return image;
    }

    private static int truncate(int value) {
        if (value > 255) {
            value = 255;
        }
        if (value < 0) {
            value = 0;
        }
        return value;
    }

    public static byte[] changeContrast(byte[] image, double contrast) {

        double factor = (259 * (contrast + 255)) / (255 * (259 - contrast));
        for (int i = 0; i < image.length; i += 3) {
            int argb;
            int R, G, B;
            R = image[i] & 0xff;
            G = image[i + 1] & 0xff;
            B = image[i + 2] & 0xff;

            R = (int) (factor * (R - 128) + 128);
            G = (int) (factor * (G - 128) + 128);
            B = (int) (factor * (B - 128) + 128);
            R = truncate(R);
            G = truncate(G);
            B = truncate(B);
            image[i] = (byte) B;
            image[i + 1] = (byte) G;
            image[i + 2] = (byte) R;
        }
        return image;
    }
}
