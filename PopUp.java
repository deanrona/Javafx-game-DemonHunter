package DemonHunter;

import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.util.Random;

import javafx.geometry.*;

public class PopUp {

	static String answer = "";
	static int choice1 = 0;
	static int choice2 = 0;
	static Random rnd = new Random();

	public static int catPop() {

		Stage window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Tricky cat");
		Label label = new Label();
		label.setText("Cat: Give me something shinny or I'll shout! Meow...\n" + "What would you do?\n");

		// Create buttons
		Button btn1 = new Button("\t Give a piece of heaven");
		Button btn2 = new Button("\t Tell a story");
		Button btn3 = new Button("\t Ignore it");

		// Clicking will set answer and close window
		btn1.setOnAction(e -> {
			choice1 = 1;
			window.close();
		});
		btn2.setOnAction(e -> {
			choice1 = 2;
			window.close();
		});
		btn3.setOnAction(e -> {
			choice1 = 3;
			window.close();
		});

		VBox layout = new VBox(10);

		// Add buttons
		layout.getChildren().addAll(label, btn1, btn2, btn3);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout, 300, 300);
		window.setScene(scene);
		window.showAndWait();

		return choice1;
	}

	public static void enemyScream() {
		Stage window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.initModality(Modality.APPLICATION_MODAL);
		Label label = new Label(
				"The cat shouted from the top of it's lung. \n The arch demon is alerted and starts looking for intruder!");

		// Create buttons
		Button btn1 = new Button("Close");

		// close window
		btn1.setOnAction(e -> {
			window.close();
		});

		VBox layout = new VBox(10);

		// Add buttons
		layout.getChildren().addAll(label, btn1);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();

	}

	public static void enemyReaction(String mood) {
		Stage window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.initModality(Modality.APPLICATION_MODAL);
		Label label = new Label();

		if (mood == "angry") {
			label.setText("The cat hates your story.");
		}
		if (mood == "happy") {
			label.setText("The cat likes your story." + "\n It lets you go without a toll.");
		}
		if (mood == "moved") {
			label.setText("The cat is moved by your story." + "\n you gained an ally.");
		}

		// Create buttons
		Button btn1 = new Button("Close");

		// close window
		btn1.setOnAction(e -> {
			window.close();
		});

		VBox layout = new VBox(10);

		// Add buttons
		layout.getChildren().addAll(label, btn1);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();

	}

	public static int tellAStory() {

		int rnd2 = rnd.nextInt(100);

		Stage window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Trick-or-treat cat");
		Label label = new Label();
		label.setText("Oh, are you telling me a story?");

		// Create buttons
		Button btn1 = new Button("\t memory of your love");
		Button btn2 = new Button("\t memory of your family");
		Button btn3 = new Button("\t the tradgedy of your village");

		// Clicking will set answer and close window
		btn1.setOnAction(e -> {
			choice2 = 1;
			window.close();
		});
		btn2.setOnAction(e -> {
			choice2 = 2;
			window.close();
		});
		btn3.setOnAction(e -> {
			choice2 = 3;
			window.close();
		});

		VBox layout = new VBox(10);

		// Add buttons
		layout.getChildren().addAll(label, btn1, btn2, btn3);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();

		tellStory2(choice2);

		return rnd2;
	}

	public static void tellStory2(int choice2) {

		Stage window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Trick-or-treat cat");
		Label label2 = new Label();

		// show story
		if (choice2 == 1) {

			label2.setText("...with the smallest voice that noone could hear, she cried alone. "
					+ "\n You sat beside her and said \"If you talk, I'll listen.\"" + "...she called you an idiot. "
					+ "\n You told her this is why you always want her to be around...");
		}

		if (choice2 == 2) {
			Label label1 = new Label();
			label2.setText("...you don't know who your parents are. "
					+ "\n Even though you are an elf, the old couple treated you as their child."
					+ "\n Everyone in the village was friendly toward you...");

		}

		if (choice2 == 3) {

			label2.setText("...a month ago, demons showed up and surrounded your village..."
					+ "\n people that were precious to you lost their lives");

		}

		VBox layout = new VBox(10);

		Button btn5 = new Button("  close  ");
		btn5.setOnAction(e -> {

			window.close();
		});
		layout.getChildren().addAll(label2, btn5);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();

	}

	public static String shootPop(String title, String message) {

		Stage window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		Label label = new Label();
		label.setText(message);

		// Create buttons
		Button btn1 = new Button("\t ^ Shoot North");
		Button btn2 = new Button("\t v Shoot South");
		Button btn3 = new Button("\t > Shoot East   ");
		Button btn4 = new Button("\t < Shoot West ");
		Button btn5 = new Button("          Don't Shoot           ");

		// Clicking will set answer and close window
		btn1.setOnAction(e -> {
			answer = "north";
			window.close();
		});
		btn2.setOnAction(e -> {
			answer = "south";
			window.close();
		});
		btn3.setOnAction(e -> {
			answer = "east";
			window.close();
		});
		btn4.setOnAction(e -> {
			answer = "west";
			window.close();
		});
		btn5.setOnAction(e -> {
			answer = "cancel";
			window.close();
		});

		VBox layout = new VBox(10);

		// Add buttons
		layout.getChildren().addAll(label, btn1, btn2, btn3, btn4, btn5);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();

		return answer;
	}

	public static void detectPop(String text) {

		Stage window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.initModality(Modality.APPLICATION_MODAL);
		Label label = new Label(text);

		// Create buttons
		Button btn1 = new Button("Close");

		// close window
		btn1.setOnAction(e -> {
			window.close();
		});

		VBox layout = new VBox(10);

		// Add buttons
		layout.getChildren().addAll(label, btn1);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();

	}
	
	public static void shotMissedPop() {
		Stage window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.initModality(Modality.APPLICATION_MODAL);
		Label label = new Label("< Shot missed >. \n the arch demon starts roaming");

		// Create buttons
		Button btn1 = new Button("Close");

		// close window
		btn1.setOnAction(e -> {
			window.close();
		});

		VBox layout = new VBox(10);

		// Add buttons
		layout.getChildren().addAll(label, btn1);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
	}

	public static int gameOverPop(String reason) {
		int gameOverChoice = 0;

		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Game Over");
		window.setMinWidth(500);
		window.setMinHeight(400);
		Label label = new Label();

		// Create buttons
		Button btn1 = new Button("          close         ");

		// Clicking will set answer and close window
		btn1.setOnAction(e -> {
			// gameOverChoice = 1;
			window.close();
		});

		VBox layout = new VBox(10);

		// Add buttons
		layout.getChildren().addAll(label, btn1);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);

		if (reason == "boss1") {
			label.setText("The demon found you \n its gaze melt you into a glob of flesh \n Game Over");
		}

		if (reason == "trap") {

			label.setText("You walked into a mob of undeads \n you died \n Game Over");
		}

		window.setScene(scene);
		window.showAndWait();

		return 0;
	}

	public static int victory(String reason) {
		int victoryChoice = 0;

		Stage window = new Stage();

		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("Game Over");
		window.setMinWidth(700);
		window.setMinHeight(700);

		Label label = new Label();

		// Create buttons
		Button btn1 = new Button("        close        ");

		// Clicking will set answer and close window
		btn1.setOnAction(e -> {
			// gameOverChoice = 1;
			window.close();
		});

		VBox layout = new VBox(10);

		// Add buttons
		layout.getChildren().addAll(label, btn1);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);

		if (reason == "boss kill") {
			label.setText("You killed the boss \n VICTORY!");
		}


		window.setScene(scene);

		window.showAndWait();

		return victoryChoice;
	}

	public static void foundAmmo() {
		Stage window = new Stage();
		window.initStyle(StageStyle.UNDECORATED);
		window.initModality(Modality.APPLICATION_MODAL);
		Label label = new Label("You found a piece of heaven");

		// Create buttons
		Button btn1 = new Button("Close");

		// close window
		btn1.setOnAction(e -> {
			window.close();
		});

		VBox layout = new VBox(10);

		// Add buttons
		layout.getChildren().addAll(label, btn1);
		layout.setAlignment(Pos.CENTER);
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
		
	}

}