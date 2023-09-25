package application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.print.PrinterJob;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Main extends Application {
	
	private TextField textField = new TextField();
	private WebView webView = new WebView();
    private  WebEngine webEngine = webView.getEngine();
	

	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("J Browser");
        //Create BorderPane
        BorderPane root = new BorderPane();
                
     // Create navigation buttons
        Image img1 = new Image("/img/back.png");
	    ImageView backview = new ImageView(img1);
	    backview.setFitHeight(20);
	    backview.setFitWidth(20);
	    Image img2 = new Image("/img/forward.png");
	    ImageView forview = new ImageView(img2);
	    forview.setFitHeight(20);
	    forview.setFitWidth(20);
	    Image img3 = new Image("/img/reload.png");
	    ImageView reloadview = new ImageView(img3);
	    reloadview.setFitHeight(20);
	    reloadview.setFitWidth(20);
	    
        Button backButton = new Button();
        backButton.setGraphic(backview);
		backButton.setOnAction(e -> webEngine.getHistory().go(-1));
        Button forwardButton = new Button();
        forwardButton.setGraphic(forview);
        forwardButton.setOnAction(e -> webEngine.getHistory().go(1));
        Button refreshButton = new Button();
        refreshButton.setGraphic(reloadview);
        refreshButton.setOnAction(e -> webEngine.reload());
        Button goButton = new Button("Go");
        goButton.setOnAction(e -> loadWeb(textField.getText()));

     // Build top bar for navigation
        HBox topBar = new HBox();
        topBar.getChildren().addAll(backButton, forwardButton, refreshButton, textField, goButton);
        
        // Create a BorderPane to organize the layout
        root.setTop(topBar);
        webEngine.load("https://www.google.com");
        root.setCenter(webView);
        
     // Add a listener to update the URL in the TextField when the WebView navigates
        webEngine.locationProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	textField.setText(newValue);
            }
        });

        // Create a scene with the BorderPane
        Scene scene = new Scene(root, 800, 600);

        // Set the scene for the stage
        primaryStage.setScene(scene);
        // Show the stage
        primaryStage.show();


    }

    
    private void loadWeb(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url= "http://" +url;
        }
        webEngine.load(url);
    }
    
    public static void main(String[] args) {
        launch(args);
    }

}