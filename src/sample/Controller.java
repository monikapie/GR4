package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import sample.filters.GaussianBlur;
import sample.helpers.FileHelper;
import sample.helpers.OperationHelper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Controller {
    @FXML
    GridPane gridPane;
    @FXML
    ImageView imgView;
    @FXML
    ImageView invisibleImage;
    @FXML
    Button openJPGButton;
    @FXML
    Button addButton;
    @FXML
    Button substractButton;
    @FXML
    Button divideButton;
    @FXML
    Button multipleButton;
    @FXML
    Slider slider;
    @FXML
    Slider sliderSize;
    @FXML
    Slider sliderBlur;

    private File file;

    @FXML
    public void setOpenJPG() throws IOException {
        file = FileHelper.getFile(gridPane);
        if (file != null && file.getName().endsWith(".jpg")) {
            Image image = FileHelper.getImageFromFile(file);
            imgView.setImage(image);
            invisibleImage.setImage(image);
        }
    }

    @FXML
    public void changeContrast() throws IOException {
        Image image = imgView.getImage();
        if(image != null){
            byte[] bytes = OperationHelper.changeContrast(FileHelper.extractBytes(image),slider.getValue());
            imgView.setImage(FileHelper.convertBytesIntoBufferImage(bytes,image));
        }

    }

    @FXML
    public void grey() throws IOException {
        Image image = imgView.getImage();
        if(image != null){
            byte[] bytes = OperationHelper.toGrayScale(FileHelper.extractBytes(image));
            imgView.setImage(FileHelper.convertBytesIntoBufferImage(bytes,image));
        }

    }

    @FXML
    public void add() throws IOException {
        Image image = imgView.getImage();
        if(image != null){
            byte[] bytes = OperationHelper.add(FileHelper.extractBytes(image), slider.getValue());
            imgView.setImage(FileHelper.convertBytesIntoBufferImage(bytes,image));
        }

    }

    @FXML
    public void substract() throws IOException {
        Image image = imgView.getImage();
        if(image != null){
            byte[] bytes = OperationHelper.substract(FileHelper.extractBytes(image), slider.getValue());
            imgView.setImage(FileHelper.convertBytesIntoBufferImage(bytes,image));
        }
    }

    @FXML
    public void divide() throws IOException {
        Image image = imgView.getImage();
        if(image != null){
            byte[] bytes = OperationHelper.div(FileHelper.extractBytes(image), slider.getValue());
            imgView.setImage(FileHelper.convertBytesIntoBufferImage(bytes,image));
        }
    }

    @FXML
    public void multiply() throws IOException {
        Image image = imgView.getImage();
        if(image != null){
            byte[] bytes = OperationHelper.multiply(FileHelper.extractBytes(image), slider.getValue());
            imgView.setImage(FileHelper.convertBytesIntoBufferImage(bytes,image));
        }
    }

    @FXML
    public void returnPrevious() throws IOException {
        Image image = invisibleImage.getImage();
        if(image != null){
            imgView.setImage(image);
        }
    }

    @FXML
    public void createGaussianBlur() throws IOException {
        Image image = imgView.getImage();
        BufferedImage bufferedImage =  new BufferedImage((int) image.getWidth(),(int) image.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        bufferedImage.getRaster().setDataElements(0, 0, (int) image.getWidth(),(int) image.getHeight(), FileHelper.extractBytes(image));
        int[][] imageInt = GaussianBlur.blur(FileHelper.get2DIntData(bufferedImage),(int)sliderSize.getValue(), (int)sliderBlur.getValue());
        BufferedImage bufferedImage1 = FileHelper.createBufferedImage(imageInt);
        imgView.setImage(SwingFXUtils.toFXImage(bufferedImage1, null));
    }
}
