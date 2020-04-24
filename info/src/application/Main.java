package application;
	
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			GridPane gp = new GridPane();

			Label name = new Label("Oklahoma");
			name.setFont(Font.font(50));
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

			Scene scene = new Scene(gp, 1000, 600);




			//BorderPane root = new BorderPane();
			//Scene scene = new Scene(root,400,400);
			//scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
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
