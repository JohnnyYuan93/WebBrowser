package application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class Main extends Application {
	
	private TextField textField = new TextField();
    private double zoom = 1;
    
    TabPane tabpane = new TabPane();
    TableView<History> table=new TableView<>();
    
    MyTab selectedTab = new MyTab();
    WebView  webView = selectedTab.getWebview();
    WebEngine webEngine = webView.getEngine();
	
	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("J Browser");
        //Create BorderPane
        BorderPane root = new BorderPane();
                
     // Create buttons
        Image img1 = new Image("/img/back.png");
	    ImageView backview = new ImageView(img1);
	    backview.setFitHeight(18);
	    backview.setFitWidth(18);
	    Image img2 = new Image("/img/forward.png");
	    ImageView forview = new ImageView(img2);
	    forview.setFitHeight(18);
	    forview.setFitWidth(18);
	    Image img3 = new Image("/img/reload.png");
	    ImageView reloadview = new ImageView(img3);
	    reloadview.setFitHeight(18);
	    reloadview.setFitWidth(18);
        Image img4 = new Image("/img/bookmark.png");
	    ImageView bookmarkview = new ImageView(img4);
	    bookmarkview.setFitHeight(18);
	    bookmarkview.setFitWidth(18);
	    
	    Image img5 = new Image("/img/zoomin.png");
	    ImageView zoominview = new ImageView(img5);
	    zoominview.setFitHeight(18);
	    zoominview.setFitWidth(18);
	    
	    Image img6 = new Image("/img/zoomout.png");
	    ImageView zoomoutview = new ImageView(img6);
	    zoomoutview.setFitHeight(18);
	    zoomoutview.setFitWidth(18);
	    
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
        
        
        //Add zoom function
        Button zoomin = new Button();
        zoomin.setGraphic(zoominview);
        zoomin.setOnAction(e -> zoomIn());
        Button zoomout = new Button();
        zoomout.setGraphic(zoomoutview);
        zoomout.setOnAction(e -> zoomOut());
        
        //Add Book mark button
        Button bookmarkButton = new Button();
        bookmarkButton.setGraphic(bookmarkview);
        bookmarkButton.setOnAction(e -> webEngine.getHistory().go(-1));
        
        
        //Add History Button
        Button historyButton =new Button("History");
        
        HistoryTable();
        
		historyButton.setOnAction(event -> {
			Stage historyStage = new Stage();
			VBox pane = new VBox();
			historyStage.setTitle("History");
			pane.getChildren().add(table);
			Scene scene = new Scene(pane, 500, 500);
			historyStage.setScene(scene);
			historyStage.show();

		});
        
        //Add New Tab Button
        Button newTabButton = new Button("+");
        newTabButton.setOnAction(e -> createTab());
        

//        Build top bar for button and TextField
        HBox topBar = new HBox();
        textField.setPrefWidth(500); 
        textField.setPrefHeight(30);
        
//       Add an event listener to the textField for typing "Enter" on keyboard.
        textField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                loadWeb(textField.getText());
            }
        });
        
        VBox top = new VBox();
        topBar.getChildren().addAll(backButton, forwardButton, refreshButton, textField ,goButton,newTabButton,zoomin,zoomout,bookmarkButton,historyButton);
        top.getChildren().add(topBar);
        tabpane.getTabs().add(selectedTab);
        top.getChildren().add(tabpane);
        
        tabpane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {	
			@Override
			public void changed(ObservableValue<? extends Tab> observable, Tab oldtab, Tab newtab) {
				System.out.print("tab changed");
				webView = ((MyTab) newtab).getWebview();
				webEngine = webView.getEngine();
				selectedTab = (MyTab) newtab;
			}
		});
        
        
        root.setTop(top);
        webEngine.load("https://www.google.com");
        root.setCenter(webView);
        
        // Create a scene with the BorderPane
        Scene scene = new Scene(root, 800, 600);

        // Set the scene for the stage
        primaryStage.setScene(scene);
        primaryStage.show();


    }

    
    private void createTab() {
    	MyTab tab = new MyTab();
    	tabpane.getTabs().add(tab);
    	System.out.println("New Tab");
    	tab.getWebview().getEngine().load("http://google.com");
//    	tab.setOnSelectionChanged(e -> {
//    		if (tab.isSelected()) {
//    			selectedTab = tab;
//    		}
//    	});
    	tabpane.getSelectionModel().select(tab);
	}


	private void loadWeb(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url= "http://" +url;
        }
        webEngine.load(url);
        History history =new History(url);
        table.getItems().add(history);
    }
    
	
    public void zoomIn() {
    	zoom +=0.2;
    	webView.setZoom(zoom);
    }
    public void zoomOut() {
    	zoom -=0.2;
    	webView.setZoom(zoom);
    }
    
    private void HistoryTable() {
		TableColumn<History, String> urlColumn = new TableColumn<>("URL");
		urlColumn.setCellValueFactory(new PropertyValueFactory<>("Url"));
		table.getColumns().add(urlColumn);
		
	}
    
    
    public static void main(String[] args) {
        launch(args);
    }

}