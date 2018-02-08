package clockWatch;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class StopWatch extends Application
{

	private double clock_Start_Seconds;
	private double clock_Start_Minutes;
	private double clock_Start_Hours;

	public static void main(String[] args) throws Exception
	{
		launch(args);
	}

	public void start(Stage stage) throws Exception
	{
		Circle clock_Face = new Circle(200, 200, 200);
		clock_Face.setId("clock_Face");

		Line clock_Hour_Hand = new Line(0, 0, 0, -150);
		clock_Hour_Hand.setTranslateX(200);
		clock_Hour_Hand.setTranslateY(200);
		clock_Hour_Hand.setId("clock_Hour_Hand");

		Line clock_Minute_Hand = new Line(0, 0, 0, -175);
		clock_Minute_Hand.setTranslateX(200);
		clock_Minute_Hand.setTranslateY(200);
		clock_Minute_Hand.setId("clock_Minute_Hand");

		Line clock_Second_Hand = new Line(0, 0, 0, -188);
		clock_Second_Hand.setTranslateX(200);
		clock_Second_Hand.setTranslateY(200);
		clock_Second_Hand.setId("clock_Second_Hand");

		Group clock_All_Tick_Marks = new Group();
		Line clock_Tick_Mark;
		for (int i = 0; i < 60; i++)
		{
			if (i % 15 == 0)
			{
				clock_Tick_Mark = new Line(0, -165, 0, -190);
			} else if (i % 5 == 0)
			{
				clock_Tick_Mark = new Line(0, -175, 0, -190);
			} else
			{
				clock_Tick_Mark = new Line(0, -185, 0, -190);
			}
			clock_Tick_Mark.setTranslateX(200);
			clock_Tick_Mark.setTranslateY(200);
			clock_Tick_Mark.getStyleClass().add("clock_Tick_Mark");
			clock_Tick_Mark.getTransforms().add(new Rotate(i * (360 / 60)));
			clock_All_Tick_Marks.getChildren().add(clock_Tick_Mark);
		}

		Group clock_Object = new Group(clock_Face, clock_All_Tick_Marks, clock_Hour_Hand, clock_Minute_Hand,
				clock_Second_Hand);

		Rotate clock_Rotate_Hours = new Rotate(clock_Start_Hours);
		Rotate clock_Rotate_Minutes = new Rotate(clock_Start_Minutes);
		Rotate clock_Rotate_Seconds = new Rotate(clock_Start_Seconds);
		clock_Hour_Hand.getTransforms().add(clock_Rotate_Hours);
		clock_Minute_Hand.getTransforms().add(clock_Rotate_Minutes);
		clock_Second_Hand.getTransforms().add(clock_Rotate_Seconds);

		// one hour every 10 hours (12 hours in one rotation * 10 = 120)
		Timeline clock_Hours_Time_Rate = new Timeline(new KeyFrame(Duration.hours(120),
				new KeyValue(clock_Rotate_Hours.angleProperty(), 360 + clock_Start_Hours)));

		// one minute every 10 minutes (60 minutes in one rotation * 10 = 600)
		Timeline clock_Minutes_Time_Rate = new Timeline(new KeyFrame(Duration.minutes(600),
				new KeyValue(clock_Rotate_Minutes.angleProperty(), 360 + clock_Start_Minutes)));

		// one second every 10 seconds (60 seconds in one rotation * 10 = 600)
		Timeline clock_Seconds_Time_Rate = new Timeline(new KeyFrame(Duration.seconds(600),
				new KeyValue(clock_Rotate_Seconds.angleProperty(), 360 + clock_Start_Seconds)));

		clock_Hours_Time_Rate.setCycleCount(Animation.INDEFINITE);
		clock_Minutes_Time_Rate.setCycleCount(Animation.INDEFINITE);
		clock_Seconds_Time_Rate.setCycleCount(Animation.INDEFINITE);

		stage.initStyle(StageStyle.UNIFIED);

		// start the layout
		VBox clock_GUI_Layout = new VBox();
		HBox clock_Start_Stop_Button_Box = new HBox();

		Button clock_Start_Button = new Button();
		clock_Start_Button.setText("Start");
		clock_Start_Button.setOnAction((event) ->
		{
			play(clock_Seconds_Time_Rate, clock_Minutes_Time_Rate, clock_Hours_Time_Rate);
		});

		Button clock_Stop_Button = new Button();
		clock_Stop_Button.setText("Stop");
		clock_Stop_Button.setOnAction((event) ->
		{
			pause(clock_Seconds_Time_Rate, clock_Minutes_Time_Rate, clock_Hours_Time_Rate);
		});

		clock_Start_Stop_Button_Box.getChildren().addAll(clock_Start_Button, clock_Stop_Button);
		clock_GUI_Layout.getChildren().addAll(clock_Object, clock_Start_Stop_Button_Box);
		clock_GUI_Layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(clock_GUI_Layout, Color.TRANSPARENT);
		scene.getStylesheets().add(getResource("clock.css"));
		stage.setScene(scene);

		stage.show();
	}

	public void play(Timeline clock_Seconds_Time_Rate, Timeline clock_Minutes_Time_Rate, Timeline clock_Hours_Time_Rate)
	{
		clock_Seconds_Time_Rate.play();
		clock_Minutes_Time_Rate.play();
		clock_Hours_Time_Rate.play();
	}

	public void pause(Timeline clock_Seconds_Time_Rate, Timeline clock_Minutes_Time_Rate,
			Timeline clock_Hours_Time_Rate)
	{
		clock_Seconds_Time_Rate.stop();
		clock_Minutes_Time_Rate.stop();
		clock_Hours_Time_Rate.stop();
	}

	static String getResource(String path)
	{
		return Clock.class.getResource(path).toExternalForm();
	}

}
