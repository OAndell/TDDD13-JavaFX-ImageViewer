package ImageViewer;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.File;

/**
 * Created by Oscar on 2016-11-27.
 * @// TODO: 2016-11-28 JAVADOC 
 */
public class ImageViewerController {

    private Image currentImage;
    private int currentRotation = 0;

    private int zoomValue = 20;
    private int rotateDegree = 30;


    //Variables for mouse movement.
    private double mouseDragEnteredX;
    private double mouseDragEnteredY;



    @FXML ImageView imageView;
    @FXML ImageView zoomOutView;
    @FXML BorderPane borderPane;

    @FXML public void handleClickLeftRotate(Event event) {
        currentRotation += -rotateDegree;
        imageView.setRotate(currentRotation);
    }

    @FXML public void handleClickRightRotate(Event event) {
        currentRotation += rotateDegree;
        imageView.setRotate(currentRotation);
    }

    @FXML public void openImage(Event event) {
        File file;
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(null);
        currentRotation = 0; //new image will load in straight.
        imageView.setRotate(currentRotation);
        currentImage = new Image(file.toURI().toString());
        imageView.setImage(currentImage);
        imageView.setViewport(new Rectangle2D(0,0,currentImage.getWidth(), currentImage.getHeight()));
        imageView.setFitHeight(currentImage.getHeight());
        imageView.setFitWidth(currentImage.getWidth());
        Main.getStage().setTitle(file.getName());
    }

    @FXML public void handleZoomIn(Event event) {

        imageView.setFitHeight(imageView.getFitHeight()*1.15);
        imageView.setFitWidth(imageView.getFitWidth()*1.15);
    }

    @FXML public void handleZoomOut(Event event) {
        imageView.setFitHeight(imageView.getFitHeight()*0.85);
        imageView.setFitWidth(imageView.getFitWidth()*0.85);
    }

    @FXML public void handlScrollOnImage(ScrollEvent event) {
        if(event.getDeltaY() > 0){ //scrolling forward
            handleZoomOut(event);
        }
        if(event.getDeltaY() < 0){//scrolling backward
            handleZoomIn(event);
        }
    }

    public void handleMouseDrag(MouseEvent event) {
        double changeX = -(mouseDragEnteredX- event.getSceneX());
        double changeY = -(mouseDragEnteredY- event.getSceneY() +50);
        borderPane.getCenter().setTranslateX(changeX);//event.getSceneX()- mouseDragEnteredX
        borderPane.getCenter().setTranslateY(changeY);//event.getSceneY()- mouseDragEnteredY
    }

    @FXML public void handleDragEntered(MouseEvent event) {
        mouseDragEnteredX = event.getSceneX();
        mouseDragEnteredY = event.getSceneY();
    }
}
