package sample.helpers;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileHelper {
    static final Logger logger = Logger.getLogger(FileHelper.class.getName());
    public static File getFile(GridPane gridPane) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose resource");
        Stage stage = (Stage) gridPane.getScene().getWindow();
        return fileChooser.showOpenDialog(stage);
    }

    public static Image getImageFromFile(File file) throws IOException {
        return SwingFXUtils.toFXImage(getBufferedImage(file), null);
    }

    public static BufferedImage getBufferedImage(File file) throws IOException {
        try {
            return ImageIO.read(file);
        } catch (IOException e) {
            logger.log(Level.INFO, "File not found.");
            throw e;
        }
    }

    public static byte[] extractBytes (Image image) throws IOException {
        BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
        BufferedImage newImage = new BufferedImage(
                bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D g = newImage.createGraphics();
        g.drawImage(bufferedImage, 0, 0, null);
        g.dispose();
        WritableRaster raster = newImage.getRaster();
        DataBufferByte data = (DataBufferByte) raster.getDataBuffer();
        return ( data.getData() );
    }

    public static Image convertBytesIntoBufferImage(byte[] bytes,Image image) throws IOException {
        ByteArrayInputStream  in = new ByteArrayInputStream(bytes);
        BufferedImage bufferedImage =  new BufferedImage((int) image.getWidth(),(int) image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        bufferedImage.getRaster().setDataElements(0, 0, (int) image.getWidth(),(int) image.getHeight(), bytes);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    public static BufferedImage createBufferedImage(int[][] pixels) {
        int width = pixels[0].length;
        int height = pixels.length;
        byte[] data = new byte[width * height * 3];
        int R, G, B;
        int offset = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = pixels[i][j];

                R = pixel >> 16 & 0xff;
                G = pixel >> 8 & 0xff;
                B = pixel & 0xff;
                data[offset++] = (byte) R;
                data[offset++] = (byte) G;
                data[offset++] = (byte) B;
            }
        }
        BufferedImage newImg = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        WritableRaster writableRaster = newImg.getRaster();
        writableRaster.setDataElements(0, 0, width, height, data);
        newImg.setData(writableRaster);
        return newImg;
    }

    public static int[][] get2DIntData(BufferedImage img) {
        byte[] image = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        int width = img.getWidth();
        int height = img.getHeight();
        int result[][] = new int[height][width];
        int R, G, B;
        int offset = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                B = image[offset++] & 0xff;
                G = image[offset++] & 0xff;
                R = image[offset++] & 0xff;
                result[i][j] = 0xff000000 | (R << 16) | (G << 8) | B;
            }
        }
        return result;
    }
}
