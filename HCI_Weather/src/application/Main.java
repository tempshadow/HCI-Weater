package application;
	
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;

import org.nocrala.tools.gis.data.esri.shapefile.exception.InvalidShapeFileException;


public class Main extends Application {
	boolean cBlind = false;
	
	private ArrayList<Polygon> counties;
	private ArrayList<String> countyName;
	int counter;
	@Override
	public void start(Stage primaryStage) throws InvalidShapeFileException, IOException {
		counties = new ArrayList<Polygon>();
		countyName = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader("/home/tempshadow/eclipse-workspace/HCI_Weather/countyShapes.csv"));
        String strg = br.readLine();
        counter = 0;
        Label name = new Label();
        while (strg != null)
        {
            String[] points = strg.split(",");
            countyName.add(points[0]);
            double temp[] = new double[points.length -1];
            for(int i = 1; i < points.length; i+=2) {
            	double x = (Double.parseDouble(points[i])*80);
            	double y = (Double.parseDouble(points[i+1])*-80);
            	temp[i-1] = x;
            	temp[i] = y;
            }
            Polygon polygon = new Polygon(temp);
            EventHandler<MouseEvent> mouseHandler = new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent mouseEvent) {
					if(mouseEvent.getEventType() == MouseEvent.MOUSE_ENTERED) {
						name.setText(points[0]);
						if(cBlind == true) {
							polygon.setFill(Color.SANDYBROWN);
						}
						else {
							polygon.setFill(Color.RED);
						}
					}
					
				}
            	
            };
            polygon.setOnMouseClicked(mouseEvent -> name.setText(points[0]));
            polygon.setFill(Color.WHITE);
            polygon.setStroke(Color.BLACK);
            polygon.setOnMouseEntered(mouseHandler);
            polygon.setOnMouseExited(mouseEvent -> polygon.setFill(Color.WHITE));
            
            counties.add(polygon);
            strg = br.readLine();
            counter++;
        }
        br.close();
		try {
			CheckBox colorBlind = new CheckBox("Color Blind");
			colorBlind.selectedProperty().addListener((observable, wasSelected, isSelected) ->  {
				if(isSelected) {
					cBlind = true;
				}
				else {
					cBlind = false;
				}
			});
			
			
			TabPane tabPane = new TabPane();
			Tab night = new Tab();
			
			
			FileInputStream input1 = new FileInputStream("moon.jpg");
			FileInputStream input2 = new FileInputStream("raindrop.png");
			FileInputStream input3 = new FileInputStream("sun.png");
			Image moonImg = new Image(input1, 30, 20, false, false);
			ImageView moon = new ImageView(moonImg);
			Image rainDrop = new Image(input2, 30, 30, false, false);
			Image sunImg = new Image(input3, 30, 20, false, false);
			ImageView sun = new ImageView(sunImg);
			
			
			night.setGraphic(moon);
			night.setStyle("-fx-background-color: #B29AE7;");
			GridPane nightData = new GridPane();
			Separator separator1 = new Separator();
			separator1.setStyle("-fx-background-color: gray;");
			nightData.setStyle("-fx-background-color: #B29AE7;");
			Label nightDate = new Label("Thu, March 13 6:45 PM		");
			Label nightSky = new Label("Clear");
			Label nightTemp = new Label("49"+"\u2109	");
			Label nightHumid = new Label("Humidity 38" + "\u0025");
			
			
			HBox tempBox = new HBox();
			tempBox.getChildren().add(new ImageView(moonImg));
			tempBox.getChildren().add(nightTemp);
			
			HBox dayNightPics = new HBox();
			dayNightPics.getChildren().add(new ImageView(sunImg));
			dayNightPics.getChildren().add(new Label("     "));
			dayNightPics.getChildren().add(new ImageView(sunImg));
			dayNightPics.getChildren().add(new Label("     "));
			dayNightPics.getChildren().add(new ImageView(moonImg));
			dayNightPics.getChildren().add(new Label("     "));
			dayNightPics.getChildren().add(new ImageView(moonImg));
			dayNightPics.getChildren().add(new Label("     "));
			dayNightPics.getChildren().add(new ImageView(moonImg));
			dayNightPics.getChildren().add(new Label("     "));
			dayNightPics.getChildren().add(new ImageView(sunImg));
			
			HBox dayNightTime = new HBox();
			Label time1 = new Label("2 PM    ");
			Label time2 = new Label("6 PM    ");
			Label time3 = new Label("10 PM   ");
			Label time4 = new Label("2 AM    ");
			Label time5 = new Label("6 AM    ");
			Label time6 = new Label("10 AM");
			dayNightTime.getChildren().add(time1);
			dayNightTime.getChildren().add(time2);
			dayNightTime.getChildren().add(time3);
			dayNightTime.getChildren().add(time4);
			dayNightTime.getChildren().add(time5);
			dayNightTime.getChildren().add(time6);
			
			
			VBox precipWind = new VBox();
			HBox precipWindLabels = new HBox();
			Label precip = new Label("     Daily Precipitation           ");
			Label wind = new Label("Wind Speed");
			precipWindLabels.getChildren().add(precip);
			precipWindLabels.getChildren().add(wind);
			HBox precipWindData = new HBox();
			Label precipData = new Label("          0\u0025");
			precipData.setFont(new Font("System Regular", 20));
			Label windData = new Label("               8 mph");
			windData.setFont(new Font("System Regular", 20));
			precipWindData.getChildren().add(precipData);
			precipWindData.getChildren().add(windData);
			precipWind.getChildren().add(precipWindLabels);
			precipWind.getChildren().add(precipWindData);
			
			HBox rainData = new HBox();
			Label rain1 = new Label("0\u0025");
			Label rain2 = new Label("0\u0025");
			Label rain3 = new Label("0\u0025");
			Label rain4 = new Label("0\u0025");
			Label rain5 = new Label("0\u0025");
			Label rain6 = new Label("0\u0025");
			rainData.getChildren().add(rain1);
			rainData.getChildren().add(new ImageView(rainDrop));
			rainData.getChildren().add(rain2);
			rainData.getChildren().add(new ImageView(rainDrop));
			rainData.getChildren().add(rain3);
			rainData.getChildren().add(new ImageView(rainDrop));
			rainData.getChildren().add(rain4);
			rainData.getChildren().add(new ImageView(rainDrop));
			rainData.getChildren().add(rain5);
			rainData.getChildren().add(new ImageView(rainDrop));
			rainData.getChildren().add(rain6);
			rainData.getChildren().add(new ImageView(rainDrop));
			rainData.setAlignment(Pos.CENTER);

			
			
			nightData.add(nightDate, 0, 0);
			nightData.add(nightSky, 0, 1);
			nightData.add(tempBox, 1, 0);
			nightData.add(nightHumid, 1, 1);
			nightData.add(separator1, 0, 2, 2, 1);
			nightData.add(rainData, 0, 3, 2, 1);
			nightData.add(dayNightPics, 0, 4, 2, 1);
			nightData.add(dayNightTime, 0, 5, 2, 1);
			nightData.add(precipWind, 0, 6, 2, 1);
			Windmill testing = new Windmill();
			Pane windPane = new Pane();
			windPane.getChildren().add(testing.createWindmill(1));
			nightData.add(windPane, 0, 7);
			
			night.setContent(nightData);
			tabPane.getTabs().add(night);
			
			
			Group temp = new Group();
			temp.getChildren().addAll(counties);
			GridPane pane = new GridPane();
			pane.add(temp, 0, 0);
			pane.add(name, 0, 1);
			pane.add(tabPane, 1, 0);
			Scene scene = new Scene(pane, 1200, 600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
