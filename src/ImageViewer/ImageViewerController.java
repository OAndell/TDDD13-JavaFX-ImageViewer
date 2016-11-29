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
 * Controller for the ImageViewer.fxml.
 * Handles the components specified in the fmxl document.
 *@author Oscar Andell
 */
public class ImageViewerController {

    private Image currentImage;
    private int currentRotation = 0;

    //Zoom out/in by this value
    private int rotateDegree = 30; //Degrees to rotate;


    //Variables for mouse movement.
    private double mouseDragEnteredX;
    private double mouseDragEnteredY;

    private double zoomInMultiplier = 1.15;
    private double zoomOutMultiplier = 0.85;



    @FXML ImageView imageView;
    @FXML ImageView zoomOutView;
    @FXML BorderPane borderPane;

    /**
     * Called when rotate left button is clicked. Rotates The Imageview containing the current Image.
     */
    @FXML public void handleClickLeftRotate(Event event) {
        currentRotation += -rotateDegree;
        imageView.setRotate(currentRotation);
    }

    /**
     * Called when rotate right button is clicked. Rotates The Imageview containing the current Image.
     */
    @FXML public void handleClickRightRotate(Event event) {
        currentRotation += rotateDegree;
        imageView.setRotate(currentRotation);
    }

    /**
     * Called when pressing the open image button. The users chooses a file using the explorer.
     * The file is then displayed in the ImageView component.
     */
    @FXML public void openImage(Event event) {
        File file;
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(null); //User selects file from explorer.
        currentRotation = 0; //new image will load in straight.
        imageView.setRotate(currentRotation);
        currentImage = new Image(file.toURI().toString());
        imageView.setImage(currentImage);
        imageView.setViewport(new Rectangle2D(0,0,currentImage.getWidth(), currentImage.getHeight()));
        imageView.setFitHeight(currentImage.getHeight());
        imageView.setFitWidth(currentImage.getWidth());
        Main.getStage().setTitle(file.getName()); //sets filename as window title.
    }

    /**
     * Called when the "Zoom in" button is pressed. Make the ImageView larger
     */
    @FXML public void handleZoomIn(Event event) {

        imageView.setFitHeight(imageView.getFitHeight()*zoomInMultiplier);
        imageView.setFitWidth(imageView.getFitWidth()*zoomInMultiplier);
    }

    /**
     * Called when the "Zoom out" button is pressed. Make the ImageView smaller
     */
    @FXML public void handleZoomOut(Event event) {
        imageView.setFitHeight(imageView.getFitHeight()*zoomOutMultiplier);
        imageView.setFitWidth(imageView.getFitWidth()*zoomOutMultiplier);
    }

    /**
     * Handles mouseScrolling on the image.
     * Zoom out if scrolling forward. Zoom in if scrolling backwards
     */
    @FXML public void handleScrollOnImage(ScrollEvent event) {
        if(event.getDeltaY() > 0){ //scrolling forward
            handleZoomOut(event);
        }
        if(event.getDeltaY() < 0){//scrolling backward
            handleZoomIn(event);
        }
    }

    /**
     *Sets the image position to the mouse position while dragging.
     */
    public void handleMouseDrag(MouseEvent event) {
        double changeX = -(mouseDragEnteredX- event.getSceneX());
        double changeY = -(mouseDragEnteredY- event.getSceneY());
        borderPane.getCenter().setTranslateX(changeX);
        borderPane.getCenter().setTranslateY(changeY);
    }

    /**
     *Saves the position of the mouse then pressed on the image.
     *This is done so the change in position can be calculated when dragging the Image
     */
    @FXML public void handleDragEntered(MouseEvent event) {
        mouseDragEnteredX = event.getSceneX();
        mouseDragEnteredY = event.getSceneY();
    }
}
