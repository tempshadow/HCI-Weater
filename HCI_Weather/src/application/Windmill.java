package application;

import javafx.animation.Interpolator;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Scale;
import javafx.util.Duration;

public class Windmill {
	public Windmill() {
		
	}
	public Group createWindmill(int speed) {
		Ellipse bottom = new Ellipse(); 
		bottom.setCenterX(15.0f);
		bottom.setCenterY(70.0f);
		bottom.setRadiusX(15.0f);
		bottom.setRadiusY(70.0f);
		bottom.setTranslateX(185);
		bottom.setTranslateY(205);

		Ellipse right = new Ellipse(); 
		right.setCenterX(70.0f);
		right.setCenterY(15.0f);
		right.setRadiusX(70.0f);
		right.setRadiusY(15.0f);
		right.setTranslateX(200);
		right.setTranslateY(190);
		
		Ellipse top = new Ellipse(); 
		top.setCenterX(15.0f);
		top.setCenterY(70.0f);
		top.setRadiusX(15.0f);
		top.setRadiusY(70.0f);
		top.setTranslateX(185);
		top.setTranslateY(65);
		
		Ellipse left = new Ellipse(); 
		left.setCenterX(70.0f);
		left.setCenterY(15.0f);
		left.setRadiusX(70.0f);
		left.setRadiusY(15.0f);
		left.setTranslateX(60);
		left.setTranslateY(190);
		
		Circle circle = new Circle();
		circle.setCenterX(100.0f);
		circle.setCenterY(100.0f);
		circle.setRadius(20.0f);
		circle.setTranslateX(130);
		circle.setTranslateY(55);
		circle.setFill(Color.BLACK);
		
		Rectangle rect = new Rectangle(148,78,5,60);
		rect.setFill(Color.GREENYELLOW);
		
		Shape shape = Shape.union(left, right);
		shape = Shape.union(shape, top);
		shape = Shape.union(shape, bottom);
		shape.setFill(Color.BLUE);
				
		Scale scale = new Scale();
		scale.setX(0.2);
		scale.setY(0.2);
		shape.getTransforms().add(scale);
		shape.setTranslateX(110);
		shape.setTranslateY(33);
		circle.getTransforms().add(scale);
		Group windmillGrp = new Group();
		try {
			
			Group blades = new Group();
			blades.getChildren().add(shape);
			Group base = new Group();
			base.getChildren().add(rect);
			windmillGrp.getChildren().add(base);
			windmillGrp.getChildren().add(blades);
			windmillGrp.getChildren().add(circle);
			RotateTransition rotate = new RotateTransition(Duration.millis(2000), blades);
			rotate.setFromAngle(0);
			rotate.setToAngle(360);
			rotate.stop();
			rotate.setRate(speed);
			rotate.setInterpolator(Interpolator.LINEAR);
			rotate.setCycleCount(Timeline.INDEFINITE);
			rotate.play();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
		return windmillGrp;
	}
}
