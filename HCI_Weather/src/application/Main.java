package application;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter.DEFAULT;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
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
	private ArrayList<String[]> data;
	int counter;
	
	
	@Override
	public void start(Stage primaryStage) throws InvalidShapeFileException, IOException {
		BufferedReader brr = new BufferedReader(new FileReader("data.csv"));
		String strrg = brr.readLine();
		counter = 0;
		data = new ArrayList<String[]>();
		while (strrg != null)
		{
			String[] points = strrg.split(",");
			data.add(points);
			strrg = brr.readLine();
		}
		brr.close();
		
		counties = new ArrayList<Polygon>();
		countyName = new ArrayList<String>();
		BufferedReader br = new BufferedReader(new FileReader("countyShapes.csv"));
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
						if(points[0].equalsIgnoreCase("custer              ")) {
							polygon.setFill(Color.GREEN);
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
			Tab day = new Tab();


			FileInputStream input1 = new FileInputStream("moon.jpg");
			FileInputStream input2 = new FileInputStream("raindrop.png");
			FileInputStream input3 = new FileInputStream("sun.png");
			FileInputStream input4 = new FileInputStream("cloudy.png");
			FileInputStream input5 = new FileInputStream("rainy.png");
			FileInputStream input6 = new FileInputStream("daycloudy.png");
			FileInputStream input7 = new FileInputStream("dayraindrop.png");
			FileInputStream input8 = new FileInputStream("dayrainy.png");
			FileInputStream input9 = new FileInputStream("daysun.png");
			FileInputStream input10 = new FileInputStream("daymoon.jpg");

			Image moonImg = new Image(input1, 30, 20, false, false);
			ImageView moon = new ImageView(moonImg);
			Image rainDrop = new Image(input2, 30, 30, false, false);
			Image cloudy = new Image(input4, 30, 30, false, false);
			Image rainy = new Image(input5, 30, 30, false, false);
			Image sunImg = new Image(input3, 30, 20, false, false);
			Image daycloudy = new Image(input6, 30, 30, false, false);
			Image dayraindrop = new Image(input7, 30, 30, false, false);
			Image dayrainy = new Image(input8, 30, 30, false, false);
			Image daysun = new Image(input9, 30, 20, false, false);
			Image daymoon = new Image(input10, 30, 20, false, false);
			ImageView sun = new ImageView(daysun);

			day.setGraphic(sun);
			day.setStyle("-fx-background-color: #FDC16B;");

			GridPane dayData = new GridPane();
			Separator dayseparator1 = new Separator();
			dayseparator1.setStyle("-fx-background-color: gray;");
			Label dayDate = new Label();
			Label daySky = new Label();
			Label dayTemp = new Label();
			Label dayHumid = new Label();
			Label uv = new Label();


			HBox dayBox = new HBox();
			dayBox.getChildren().add(new ImageView(daysun));
			dayBox.getChildren().add(dayTemp);

			HBox dayNightPics1 = new HBox();
			dayNightPics1.getChildren().add(new ImageView(daysun));
			dayNightPics1.getChildren().add(new Label("     "));
			dayNightPics1.getChildren().add(new ImageView(daysun));
			dayNightPics1.getChildren().add(new Label("     "));
			dayNightPics1.getChildren().add(new ImageView(daymoon));
			dayNightPics1.getChildren().add(new Label("     "));
			dayNightPics1.getChildren().add(new ImageView(daymoon));
			dayNightPics1.getChildren().add(new Label("     "));
			dayNightPics1.getChildren().add(new ImageView(daymoon));
			dayNightPics1.getChildren().add(new Label("     "));
			dayNightPics1.getChildren().add(new ImageView(daysun));

			HBox dayNightTime1 = new HBox();
			Label daytime1 = new Label();
			Label daytime2 = new Label();
			Label daytime3 = new Label();
			Label daytime4 = new Label();
			Label daytime5 = new Label();
			Label daytime6 = new Label();
			dayNightTime1.getChildren().add(daytime1);
			dayNightTime1.getChildren().add(daytime2);
			dayNightTime1.getChildren().add(daytime3);
			dayNightTime1.getChildren().add(daytime4);
			dayNightTime1.getChildren().add(daytime5);
			dayNightTime1.getChildren().add(daytime6);


			VBox dayprecipWind = new VBox();
			HBox dayprecipWindLabels = new HBox();
			Label dayprecip = new Label("     Daily Precipitation           ");
			Label daywind = new Label("Wind Speed");
			dayprecipWindLabels.getChildren().add(dayprecip);
			dayprecipWindLabels.getChildren().add(daywind);
			HBox dayprecipWindData = new HBox();
			Label dayprecipData = new Label();
			dayprecipData.setFont(new Font("System Regular", 20));
			Label daywindData = new Label();
			daywindData.setFont(new Font("System Regular", 20));
			dayprecipWindData.getChildren().add(dayprecipData);
			dayprecipWindData.getChildren().add(daywindData);
			dayprecipWind.getChildren().add(dayprecipWindLabels);
			dayprecipWind.getChildren().add(dayprecipWindData);

			HBox dayrainData = new HBox();
			Label dayrain1 = new Label();
			Label dayrain2 = new Label();
			Label dayrain3 = new Label();
			Label dayrain4 = new Label();
			Label dayrain5 = new Label();
			Label dayrain6 = new Label();
			dayrainData.getChildren().add(dayrain1);
			dayrainData.getChildren().add(new ImageView(dayraindrop));
			dayrainData.getChildren().add(dayrain2);
			dayrainData.getChildren().add(new ImageView(dayraindrop));
			dayrainData.getChildren().add(dayrain3);
			dayrainData.getChildren().add(new ImageView(dayraindrop));
			dayrainData.getChildren().add(dayrain4);
			dayrainData.getChildren().add(new ImageView(dayraindrop));
			dayrainData.getChildren().add(dayrain5);
			dayrainData.getChildren().add(new ImageView(dayraindrop));
			dayrainData.getChildren().add(dayrain6);
			dayrainData.getChildren().add(new ImageView(dayraindrop));
			dayrainData.setAlignment(Pos.CENTER);

			VBox dayforcast = new VBox();
			HBox dayforcastRain = new HBox();
			Label dayday1rain = new Label();
			Label dayday2rain = new Label();
			Label dayday3rain = new Label();
			Label dayday4rain = new Label();
			Label dayday5rain = new Label();
			Label dayday6rain = new Label();
			dayforcastRain.getChildren().add(dayday1rain);
			dayforcastRain.getChildren().add(new ImageView(dayraindrop));
			dayforcastRain.getChildren().add(dayday2rain);
			dayforcastRain.getChildren().add(new ImageView(dayraindrop));
			dayforcastRain.getChildren().add(dayday3rain);
			dayforcastRain.getChildren().add(new ImageView(dayraindrop));
			dayforcastRain.getChildren().add(dayday4rain);
			dayforcastRain.getChildren().add(new ImageView(dayraindrop));
			dayforcastRain.getChildren().add(dayday5rain);
			dayforcastRain.getChildren().add(new ImageView(dayraindrop));
			dayforcastRain.getChildren().add(dayday6rain);
			dayforcastRain.getChildren().add(new ImageView(dayraindrop));
			dayforcastRain.setAlignment(Pos.CENTER);

			HBox dayforcastIcons = new HBox();
			dayforcastIcons.getChildren().add(new ImageView(daysun));
			dayforcastIcons.getChildren().add(new Label("     "));
			dayforcastIcons.getChildren().add(new ImageView(daycloudy));
			dayforcastIcons.getChildren().add(new Label("     "));
			dayforcastIcons.getChildren().add(new ImageView(dayrainy));
			dayforcastIcons.getChildren().add(new Label("     "));
			dayforcastIcons.getChildren().add(new ImageView(dayrainy));
			dayforcastIcons.getChildren().add(new Label("     "));
			dayforcastIcons.getChildren().add(new ImageView(daycloudy));
			dayforcastIcons.getChildren().add(new Label("     "));
			dayforcastIcons.getChildren().add(new ImageView(daycloudy));

			HBox dayforcastLbls = new HBox();
			Label dayday1 = new Label("  Today   ");
			Label dayday2 = new Label("Fri       ");
			Label dayday3 = new Label("Sat      ");
			Label dayday4 = new Label("Sun      ");
			Label dayday5 = new Label("Mon      ");
			Label dayday6 = new Label("Tue");
			dayforcastLbls.getChildren().add(dayday1);
			dayforcastLbls.getChildren().add(dayday2);
			dayforcastLbls.getChildren().add(dayday3);
			dayforcastLbls.getChildren().add(dayday4);
			dayforcastLbls.getChildren().add(dayday5);
			dayforcastLbls.getChildren().add(dayday6);

			dayforcast.getChildren().add(dayforcastRain);
			dayforcast.getChildren().add(dayforcastIcons);
			dayforcast.getChildren().add(dayforcastLbls);

			dayData.add(dayDate, 0, 0);
			dayData.add(daySky, 0, 1);
			dayData.add(dayBox, 1, 0);
			dayData.add(dayHumid, 1, 1);
			dayData.add(uv, 0, 2);
			dayData.add(dayseparator1, 0, 3, 2, 1);
			dayData.add(dayrainData, 0, 4, 2, 1);
			dayData.add(dayNightPics1, 0, 5, 2, 1);
			dayData.add(dayNightTime1, 0, 6, 2, 1);
			dayData.add(dayprecipWind, 0, 7, 2, 1);
			Windmill daytesting = new Windmill();
			Pane daywindPane = new Pane();
			daywindPane.getChildren().add(daytesting.createWindmill(4));
			dayData.add(daywindPane, 0, 8);
			dayData.add(dayforcast,0, 9, 2, 1);
			dayData.setStyle("-fx-background-color: #FDC16B;");
			day.setContent(dayData);
			tabPane.getTabs().add(day);



			night.setGraphic(moon);
			night.setStyle("-fx-background-color: #B29AE7;");
			GridPane nightData = new GridPane();
			Separator separator1 = new Separator();
			separator1.setStyle("-fx-background-color: gray;");
			nightData.setStyle("-fx-background-color: #B29AE7;");
			Label nightDate = new Label();
			Label nightSky = new Label();
			Label nightTemp = new Label();
			Label nightHumid = new Label();


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
			Label time1 = new Label();
			Label time2 = new Label();
			Label time3 = new Label();
			Label time4 = new Label();
			Label time5 = new Label();
			Label time6 = new Label();
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
			Label precipData = new Label();
			precipData.setFont(new Font("System Regular", 20));
			Label windData = new Label();
			windData.setFont(new Font("System Regular", 20));
			precipWindData.getChildren().add(precipData);
			precipWindData.getChildren().add(windData);
			precipWind.getChildren().add(precipWindLabels);
			precipWind.getChildren().add(precipWindData);

			HBox rainData = new HBox();
			Label rain1 = new Label();
			Label rain2 = new Label();
			Label rain3 = new Label();
			Label rain4 = new Label();
			Label rain5 = new Label();
			Label rain6 = new Label();
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

			VBox forcast = new VBox();
			HBox forcastRain = new HBox();
			Label day1rain = new Label();
			Label day2rain = new Label();
			Label day3rain = new Label();
			Label day4rain = new Label();
			Label day5rain = new Label();
			Label day6rain = new Label();
			forcastRain.getChildren().add(day1rain);
			forcastRain.getChildren().add(new ImageView(rainDrop));
			forcastRain.getChildren().add(day2rain);
			forcastRain.getChildren().add(new ImageView(rainDrop));
			forcastRain.getChildren().add(day3rain);
			forcastRain.getChildren().add(new ImageView(rainDrop));
			forcastRain.getChildren().add(day4rain);
			forcastRain.getChildren().add(new ImageView(rainDrop));
			forcastRain.getChildren().add(day5rain);
			forcastRain.getChildren().add(new ImageView(rainDrop));
			forcastRain.getChildren().add(day6rain);
			forcastRain.getChildren().add(new ImageView(rainDrop));
			forcastRain.setAlignment(Pos.CENTER);

			HBox forcastIcons = new HBox();
			forcastIcons.getChildren().add(new ImageView(sunImg));
			forcastIcons.getChildren().add(new Label("     "));
			forcastIcons.getChildren().add(new ImageView(cloudy));
			forcastIcons.getChildren().add(new Label("     "));
			forcastIcons.getChildren().add(new ImageView(rainy));
			forcastIcons.getChildren().add(new Label("     "));
			forcastIcons.getChildren().add(new ImageView(rainy));
			forcastIcons.getChildren().add(new Label("     "));
			forcastIcons.getChildren().add(new ImageView(cloudy));
			forcastIcons.getChildren().add(new Label("     "));
			forcastIcons.getChildren().add(new ImageView(cloudy));

			HBox forcastLbls = new HBox();
			Label day1 = new Label("  Today   ");
			Label day2 = new Label("Fri       ");
			Label day3 = new Label("Sat      ");
			Label day4 = new Label("Sun      ");
			Label day5 = new Label("Mon      ");
			Label day6 = new Label("Tue");
			forcastLbls.getChildren().add(day1);
			forcastLbls.getChildren().add(day2);
			forcastLbls.getChildren().add(day3);
			forcastLbls.getChildren().add(day4);
			forcastLbls.getChildren().add(day5);
			forcastLbls.getChildren().add(day6);

			forcast.getChildren().add(forcastRain);
			forcast.getChildren().add(forcastIcons);
			forcast.getChildren().add(forcastLbls);

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
			nightData.add(forcast,0, 8, 2, 1);

			night.setContent(nightData);
			tabPane.getTabs().add(night);
			dayDate.setText(data.get(0)[0]+ "		");
			daySky.setText(data.get(0)[2]);
			dayTemp.setText(data.get(0)[1]+ "\u2109	");
			dayHumid.setText(data.get(0)[3]+ "\u0025");
			uv.setText(data.get(0)[4]);
			dayrain1.setText(data.get(0)[5]+ "\u0025");
			dayrain2.setText(data.get(0)[6]+ "\u0025");
			dayrain3.setText(data.get(0)[7]+ "\u0025");
			dayrain4.setText(data.get(0)[8]+ "\u0025");
			dayrain5.setText(data.get(0)[9]+ "\u0025");
			dayrain6.setText(data.get(0)[10]+ "\u0025");
			daytime1.setText(data.get(0)[11]+ "    ");
			daytime2.setText(data.get(0)[12]+ "    ");
			daytime3.setText(data.get(0)[13]+ "    ");
			daytime4.setText(data.get(0)[14]+ "    ");
			daytime5.setText(data.get(0)[15]+ "    ");
			daytime6.setText(data.get(0)[16]);
			dayprecipData.setText("          "+data.get(0)[17]+"\u0025");
			daywindData.setText("               "+ data.get(0)[18]);
			dayday1rain.setText(data.get(0)[19]+ "\u0025");
			dayday2rain.setText(data.get(0)[20] + "\u0025");
			dayday3rain.setText(data.get(0)[21] + "\u0025");
			dayday4rain.setText(data.get(0)[22] + "\u0025");
			dayday5rain.setText(data.get(0)[23] + "\u0025");
			dayday6rain.setText(data.get(0)[24] + "\u0025");
			day.setOnSelectionChanged(new EventHandler<Event>() {
				@Override
				public void handle(Event arg0) {
					if(day.isSelected()) {
						dayDate.setText(data.get(0)[0]+ "		");
						daySky.setText(data.get(0)[2]);
						dayTemp.setText(data.get(0)[1]+ "\u2109	");
						dayHumid.setText(data.get(0)[3]+ "\u0025");
						uv.setText(data.get(0)[4]);
						dayrain1.setText(data.get(0)[5]+ "\u0025");
						dayrain2.setText(data.get(0)[6]+ "\u0025");
						dayrain3.setText(data.get(0)[7]+ "\u0025");
						dayrain4.setText(data.get(0)[8]+ "\u0025");
						dayrain5.setText(data.get(0)[9]+ "\u0025");
						dayrain6.setText(data.get(0)[10]+ "\u0025");
						daytime1.setText(data.get(0)[11]+ "    ");
						daytime2.setText(data.get(0)[12]+ "    ");
						daytime3.setText(data.get(0)[13]+ "    ");
						daytime4.setText(data.get(0)[14]+ "    ");
						daytime5.setText(data.get(0)[15]+ "    ");
						daytime6.setText(data.get(0)[16]);
						dayprecipData.setText("          "+data.get(0)[17]+"\u0025");
						daywindData.setText("               "+ data.get(0)[18]);
						dayday1rain.setText(data.get(0)[19]+ "\u0025");
						dayday2rain.setText(data.get(0)[20] + "\u0025");
						dayday3rain.setText(data.get(0)[21] + "\u0025");
						dayday4rain.setText(data.get(0)[22] + "\u0025");
						dayday5rain.setText(data.get(0)[23] + "\u0025");
						dayday6rain.setText(data.get(0)[24] + "\u0025");
					}
				}
			});
			
			night.setOnSelectionChanged(new EventHandler<Event>() {
				@Override
				public void handle(Event arg0) {
					if(night.isSelected()) {
						nightDate.setText(data.get(1)[0]+ "		");
						nightSky.setText(data.get(1)[2]);
						nightTemp.setText(data.get(1)[1]+ "\u2109	");
						nightHumid.setText(data.get(1)[3]+ "\u0025");
						rain1.setText(data.get(1)[5]+ "\u0025");
						rain2.setText(data.get(1)[6]+ "\u0025");
						rain3.setText(data.get(1)[7]+ "\u0025");
						rain4.setText(data.get(1)[8]+ "\u0025");
						rain5.setText(data.get(1)[9]+ "\u0025");
						rain6.setText(data.get(1)[10]+ "\u0025");
						time1.setText(data.get(1)[11]+ "    ");
						time2.setText(data.get(1)[12]+ "    ");
						time3.setText(data.get(1)[13]+ "    ");
						time4.setText(data.get(1)[14]+ "    ");
						time5.setText(data.get(1)[15]+ "    ");
						time6.setText(data.get(1)[16]);
						precipData.setText("          "+data.get(1)[17]+"\u0025");
						windData.setText("               "+ data.get(1)[18]);
						day1rain.setText(data.get(1)[19]+ "\u0025");
						day2rain.setText(data.get(1)[20] + "\u0025");
						day3rain.setText(data.get(1)[21] + "\u0025");
						day4rain.setText(data.get(1)[22] + "\u0025");
						day5rain.setText(data.get(1)[23] + "\u0025");
						day6rain.setText(data.get(1)[24] + "\u0025");
					}
				}
			});
			
			GridPane gp = new GridPane();

			Label namenew = new Label("Oklahoma");
			namenew.setFont(Font.font(50));
			Label bird = new Label("State Bird: Scisssor-tailed Flycatcher");
			bird.setFont(Font.font(20));
			Label flower = new Label("State Flower: Mistletoe");
			flower.setFont(Font.font(20));
			Label tree = new Label("State Tree: Eastern Redbud");
			tree.setFont(Font.font(20));
			Label flag = new Label("State Flag");
			flag.setFont(Font.font(20));
			Button back = new Button("Back");

			Image im = new Image("Oklahoma.png");
			ImageView image = new ImageView(im);
			image.setFitWidth(500);
			image.setFitHeight(400);

			GridPane left = new GridPane();
			GridPane right = new GridPane();
			GridPane leftbottom = new GridPane();
			leftbottom.setAlignment(Pos.BOTTOM_LEFT);

			left.add(name, 0, 0);
			left.add(bird, 0, 1);
			left.add(flower, 0, 2);
			left.add(tree, 0, 3);
			leftbottom.add(back, 0,0);
			left.add(leftbottom, 0, 5);
			left.setPadding(new Insets(40.0, 40.0, 40.0, 40.0));
			left.setVgap(75);

			right.add(flag, 0, 0);
			right.add(image, 0, 1);
			right.setPadding(new Insets(30.0, 30.0, 30.0, 30.0));
			right.setVgap(50);


			gp.add(left, 0, 0);
			gp.add(right, 1, 0);

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
