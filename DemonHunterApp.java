package DemonHunter;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.css.Styleable;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DemonHunterApp extends Application implements EventHandler<KeyEvent>, TextSaveAndLoad {

	Stage theStage;

	AnimationTimer timer;
	int hunterStep = 0;

	Scene scene1, scene2, scene3;

	// top box
	HBox topBox = new HBox();

	// bottom box
	HBox bottomBox = new HBox();
	// Label stepBox = new Label("move: " + hunterStep);

	// left box
	VBox leftBox;
	Button shootBtn;

	// middle grid
	int rowSize = 21;
	int colSize = 21;
	int tileSize = 40; // it's a square
	int trapNum;
	int enemyNum;
	int ammoExtra;

	GridPane gridPane;
	GridMap gridMap;

	// cave
	Label floor;

	// boss
	Boss theBoss;
	Label boss;
	boolean alert = false;
	boolean bossMeetHunter = false;
	String bossIcon = "fog";

	// Hunter
	Hunter theHunter;
	Label hunter;
	int shotLeft = 3;
	Label ammoCount;

	@Override
	public void start(Stage firstStage) throws Exception {
		theStage = firstStage;

		firstStage.setTitle("Demon Hunter");

		// scene 1 ///////////////
		Label label1 = new Label("scene 1: Start screen");
		Button button1 = new Button("New Game");
		Button button2 = new Button("Load game");
		Button button3 = new Button("Game Manual");
		Button button4 = new Button("Quit");
		Label creator_lb = new Label("by Ponaroth Eab");
		button1.setOnAction(e -> theStage.setScene(scene2));
		button2.setOnAction(e -> loadGame());
		button4.setOnAction(e -> System.exit(0));

		// Layout 1 - children laid out in vertical column
		VBox layout1 = new VBox(20);
		layout1.setAlignment(Pos.CENTER);
		layout1.getChildren().addAll(label1, button1, button2, button3, button4, creator_lb);
		scene1 = new Scene(layout1, 800, 600);

		// scene 2 //////////////
		Label label2 = new Label("scene 2: choose a stage");
		Button stage1_btn = new Button("Bulls Village 5x5");
		Button stage2_btn = new Button("Bronze River Town 7x7");
		Button stage3_btn = new Button("Sun city 10x10");
		Button button5 = new Button("Back to start screen");

		stage1_btn.setOnAction(e -> {
			startStage1();
		});
		stage2_btn.setOnAction(e -> {
			startStage2();
		});
		stage3_btn.setOnAction(e -> {
			startStage3();
		});
		button5.setOnAction(e -> theStage.setScene(scene1));

		// Layout 2
		VBox layout2 = new VBox(20);
		layout2.setAlignment(Pos.CENTER);
		layout2.getChildren().addAll(label2, stage1_btn, stage2_btn, stage3_btn, button5);
		scene2 = new Scene(layout2, 800, 600);

		// Scene 3 ////////////////////
		// top box
		Label title = new Label("Demon Hunter");
		title.getStyleClass().add("title");

		topBox.getStyleClass().add("title");
		topBox.getChildren().add(title);

		// bottom box
		Label instruction = new Label("");
		instruction.getStyleClass().add("instruction");

		bottomBox.getStyleClass().add("instruction");
		bottomBox.getChildren().add(instruction);

		// right box
		VBox rightBox = new VBox();
		rightBox.setAlignment(Pos.CENTER);
		Label soul1 = new Label("Kill the arch demon");
		Label soul2 = new Label("");
		Label soul3 = new Label("");

		Button inquiry = new Button(" Detect ");

		inquiry.setOnAction(e -> {
			detectNearbyRooms();
		});

		// shieldName.getStyleClass().add("shieldName");
		inquiry.getStyleClass().add("inquiry");
		rightBox.getStyleClass().add("shieldName");
		rightBox.getChildren().addAll(soul1, inquiry);

		// left box
		leftBox = new VBox();
		shootBtn = new Button("  Soul Cannon  ");
		ammoCount = new Label("Shot Left: " + shotLeft);

		shootBtn.setOnAction(e -> {
			shooting();
		});

		ammoCount.getStyleClass().add("ammoCount");
		leftBox.getStyleClass().add("leftBox");
		leftBox.getChildren().addAll(shootBtn, ammoCount);

		// scene 3
		BorderPane gameScreen = new BorderPane();
		gameScreen.getStyleClass().add("outerBorder");

		scene3 = new Scene(gameScreen);
		scene3.getStylesheets().add("DemonHunter/style.css");

		// gridPane
		gridPane = new GridPane();

		////// Controls
		////// ////////////////////////////////////////////////////////////////////////
		// add a clickable reset and Debug Mode to bottom box
		Button debugMode = new Button();
		debugMode.setText("Debug Mode");

		Button saveBtn = new Button();
		saveBtn.setText("Save game");

		Button resetBtn = new Button();
		resetBtn.setText("Quit game");

		bottomBox.getChildren().addAll(debugMode, saveBtn, resetBtn);

		saveBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				try {
					// bossIcon = "fog";
					// alert = false;
					// shotLeft = 3;
					saveAsTextFile(gridMap, alert, shotLeft, tileSize);

				} catch (Exception e) {
					e.printStackTrace();

				}

			}
		});

		resetBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				try {
					bossIcon = "fog";
					alert = false;
					shotLeft = 3;
					restart(firstStage);
				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		});

		// debug mode button
		debugMode.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<Event>() {
			@Override
			public void handle(Event event) {
				try {

					for (int x = 0; x < rowSize; x++) {
						for (int y = 0; y < colSize; y++) {

							Coordinate grid = gridMap.getMap()[x][y];

							// fog
							if (grid.getType().equals("fog")) {
								Label table = new Label();
								table.getStyleClass().add("floor");
								table.setMinWidth(tileSize);
								table.setMinHeight(tileSize);
								gridPane.add(table, x, y);
							}

							// trap
							if (grid.getType().equals("trap")) {
								Label trap = new Label();
								// trap.getStyleClass().add("trap");
								trap.getStyleClass().add("trap");
								trap.setMinWidth(tileSize);
								trap.setMinHeight(tileSize);
								gridPane.add(trap, x, y);

							}

							// Small Enemies
							if (grid.getType().equals("enemy")) {
								Label enemy = new Label();
								// enemy.getStyleClass().add("smallEnenmy1");
								enemy.getStyleClass().add("enemy1");
								enemy.setMinWidth(tileSize);
								enemy.setMinHeight(tileSize);
								gridPane.add(enemy, x, y);

							}

							// ammo
							if (grid.getType().equals("ammo")) {
								Label ammo = new Label();

								ammo.getStyleClass().add("ammo");
								ammo.setMinWidth(tileSize);
								ammo.setMinHeight(tileSize);
								gridPane.add(ammo, x, y);

							}

							// boss
							if (grid.getType().equals("boss")) {

								bossIcon = "boss1";
								//boss = new Label();
								
								// testing grid button
								Button boss = new Button();
								boss.setOnAction(e -> {
									bossMeetHunter = bossRoam();
									bossLeavesRemains();
									changeLabel(bossIcon);
									bossCheckThisRoom();});
								
								
								boss.getStyleClass().add(bossIcon);
								boss.setMinWidth(tileSize);
								boss.setMinHeight(tileSize);
								gridPane.add(boss, x, y);

							}

						}
					}

				} catch (Exception e) {

					e.printStackTrace();
				}

			}
		});

		// keyboard input and set up hunter
		scene3.setOnKeyPressed(this);

		// setting up the whole borderPane
		gameScreen.setTop(topBox);
		gameScreen.setBottom(bottomBox);
		gameScreen.setRight(rightBox);
		gameScreen.setLeft(leftBox);
		gameScreen.setCenter(gridPane);

		// set up stage
		firstStage.setScene(scene1);
		firstStage.show();

	}

	public void loadGame() {

		gridMap = loadGridMapFromTextFile();
		rowSize = gridMap.getRowSize();
		colSize = gridMap.getColSize();
		alert = gridMap.isAlert();
		shotLeft = gridMap.getShotLeft();
		tileSize = gridMap.getTileSize();

		// load left box
		leftBox.getChildren().remove(ammoCount);
		ammoCount = new Label("Shot Left: " + shotLeft);
		ammoCount.getStyleClass().add("ammoCount");
		leftBox.getChildren().add(ammoCount);

		// createMap();
		redrawMap();

		theStage.setScene(scene3);

	}

	// Key events
	@Override
	public void handle(KeyEvent keyEvent) {

		// reset = F10
		if (keyEvent.getCode() == KeyCode.F10) {
			try {
				bossIcon = "fog";
				alert = false;
				shotLeft = 3;
				restart(theStage);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		// same as detect button. Check nearby room
		if (keyEvent.getCode() == KeyCode.X) {
			try {
				detectNearbyRooms();
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		// Same as Soul Cannon button. prepare for the shoot out
		if (keyEvent.getCode() == KeyCode.Z) {
			try {
				shooting();
			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		// 1. Go right
		if (keyEvent.getCode() == KeyCode.D || keyEvent.getCode() == KeyCode.RIGHT) {

			Coordinate oldTile = gridMap.getMap()[theHunter.getX()][theHunter.getY()];
			Coordinate newTile = gridMap.getMap()[theHunter.getX() + 1][theHunter.getY()];

			if (newTile.getType().intern() != "floor" && newTile.getType().intern() != "fog") {
				// something in the way: do nothing

			} else if (newTile.getType() == "exit") {

			} else {
				hunterStep++;

				// boss's turn
				if (oldTile.getRow() != newTile.getRow() || oldTile.getCol() != newTile.getCol()) {
					bossMeetHunter = bossRoam();
					bossLeavesRemains();
					changeLabel(bossIcon);
					bossCheckThisRoom();

				}

				try {
					// clear fog in old tile
					oldTile.setType("floor");
					Label floor = new Label();
					floor.getStyleClass().add("floor");
					floor.setMinWidth(tileSize);
					floor.setMinHeight(tileSize);
					gridPane.add(floor, oldTile.getX(), oldTile.getY());

					gridMap.getMap()[theHunter.getX()][theHunter.getY()].setType("floor");
					theHunter.setX(theHunter.getX() + 1);

					changeLabel("hunter");
					hunter.getStyleClass().add("hunterRight");
					gridPane.add(hunter, theHunter.getX(), theHunter.getY());

					// check current room
					hunterCheckThisRoom();

				} catch (Exception e) {
					System.err.println(e);
				}

			}

		}

		// 2. Go left
		if (keyEvent.getCode() == KeyCode.A || keyEvent.getCode() == KeyCode.LEFT) {

			Coordinate oldTile = gridMap.getMap()[theHunter.getX()][theHunter.getY()];
			// check the new tile
			Coordinate newTile = gridMap.getMap()[theHunter.getX() - 1][theHunter.getY()];

			if (newTile.getType().intern() != "floor" && newTile.getType().intern() != "fog") {
				// do nothing

			} else if (newTile.getType() == "exit") {

			} else {
				hunterStep++;

				// boss's turn
				if (oldTile.getRow() != newTile.getRow() || oldTile.getCol() != newTile.getCol()) {
					bossMeetHunter = bossRoam();
					bossLeavesRemains();
					changeLabel(bossIcon);
					bossCheckThisRoom();

				}

				try {
					// clear fog in old tile
					oldTile.setType("floor");
					Label floor = new Label();
					floor.getStyleClass().add("floor");
					floor.setMinWidth(tileSize);
					floor.setMinHeight(tileSize);
					gridPane.add(floor, oldTile.getX(), oldTile.getY());

					gridMap.getMap()[theHunter.getX()][theHunter.getY()].setType("floor");

					theHunter.setX(theHunter.getX() - 1);

					changeLabel("hunter");
					hunter.getStyleClass().add("hunterLeft");
					gridPane.add(hunter, theHunter.getX(), theHunter.getY());
					// check current room
					hunterCheckThisRoom();

				} catch (Exception e) {

				}

			}
		}

		// 3. Go Up
		if (keyEvent.getCode() == KeyCode.W || keyEvent.getCode() == KeyCode.UP) {

			Coordinate oldTile = gridMap.getMap()[theHunter.getX()][theHunter.getY()];
			// check the new tile
			Coordinate newTile = gridMap.getMap()[theHunter.getX()][theHunter.getY() - 1];

			if (newTile.getType().intern() != "floor" && newTile.getType().intern() != "fog") {
				// do nothing

			} else if (newTile.getType() == "exit") {

			} else {
				hunterStep++;

				// boss's turn
				if (oldTile.getRow() != newTile.getRow() || oldTile.getCol() != newTile.getCol()) {
					bossMeetHunter = bossRoam();
					bossLeavesRemains();
					changeLabel(bossIcon);
					bossCheckThisRoom();

				}

				try {
					// clear fog in old tile
					oldTile.setType("floor");
					Label floor = new Label();
					floor.getStyleClass().add("floor");
					floor.setMinWidth(tileSize);
					floor.setMinHeight(tileSize);
					gridPane.add(floor, oldTile.getX(), oldTile.getY());

					gridMap.getMap()[theHunter.getX()][theHunter.getY()].setType("floor");

					theHunter.setY(theHunter.getY() - 1);

					changeLabel("hunter");
					hunter.getStyleClass().add("hunterUp");
					gridPane.add(hunter, theHunter.getX(), theHunter.getY());
					// check current room
					hunterCheckThisRoom();

				} catch (Exception e) {

				}

			}
		}

		// 4. Go Down
		if (keyEvent.getCode() == KeyCode.S || keyEvent.getCode() == KeyCode.DOWN) {

			Coordinate oldTile = gridMap.getMap()[theHunter.getX()][theHunter.getY()];
			// check the new tile
			Coordinate newTile = gridMap.getMap()[theHunter.getX()][theHunter.getY() + 1];

			if (newTile.getType().intern() != "floor" && newTile.getType().intern() != "fog") {
				// do nothing

			} else if (newTile.getType() == "exit") {

			} else {
				hunterStep++;

				// boss's turn
				if (oldTile.getRow() != newTile.getRow() || oldTile.getCol() != newTile.getCol()) {
					bossMeetHunter = bossRoam();
					bossLeavesRemains();
					changeLabel(bossIcon);
					bossCheckThisRoom();

				}

				try {
					// clear fog in old tile
					oldTile.setType("floor");
					Label floor = new Label();
					floor.getStyleClass().add("floor");
					floor.setMinWidth(tileSize);
					floor.setMinHeight(tileSize);
					gridPane.add(floor, oldTile.getX(), oldTile.getY());

					gridMap.getMap()[theHunter.getX()][theHunter.getY()].setType("floor");

					theHunter.setY(theHunter.getY() + 1);

					changeLabel("hunter");
					hunter.getStyleClass().add("hunterDown");
					gridPane.add(hunter, theHunter.getX(), theHunter.getY());
					// check current room
					hunterCheckThisRoom();

				} catch (Exception e) {

				}

			}
		}

	}

	// method to check if a certain type is in nearby area and return type
	public boolean checkNearByRoom(String type1) {

		return true;
	}

	// restart method
	public void restart(Stage stage) throws Exception {

		topBox.getChildren().clear();
		bottomBox.getChildren().clear();
		gridPane.getChildren().clear();
		gridMap = new GridMap(rowSize, colSize, trapNum, enemyNum, ammoExtra);
		hunterStep = 0;

		start(stage);
	}

	public void changeLabel(String text) {

		if (text == "hunter") {
			// erase css from old label
			hunter.getStyleClass().remove("HunterUP");
			hunter.getStyleClass().remove("HunterDown");
			hunter.getStyleClass().remove("HunterLeft");
			hunter.getStyleClass().remove("HunterRight");

			// add css to new hunter label
			hunter = new Label();
			hunter.getStyleClass().add("HunterDown");
			hunter.setMinWidth(tileSize);
			hunter.setMinHeight(tileSize);
			gridMap.getMap()[theHunter.getX()][theHunter.getY()].setType("hunter");
		}

		if (text == bossIcon) {
			// erase css from old label
			boss.getStyleClass().remove(bossIcon);

			// add css to new hunter label
			boss = new Label();
			boss.getStyleClass().add(bossIcon);
			boss.setMinWidth(tileSize);
			boss.setMinHeight(tileSize);
			gridPane.add(boss, theBoss.getX(), theBoss.getY());
		}

		if (text == "boss") {

			boss = new Label();
			boss.getStyleClass().remove(bossIcon);
			boss.getStyleClass().add("boss1");
			boss.setMinWidth(tileSize);
			boss.setMinHeight(tileSize);
			gridPane.add(boss, theBoss.getX(), theBoss.getY());
		}

	}

	//// Extra methods for boss

	public boolean bossRoam() {
		boolean hunterFound2 = false;

		hunterFound2 = theBoss.roam(gridMap, theHunter, alert);

		return hunterFound2;
	}

	// boss leaves remains wherever it goes and it eats everything in the way
	public void bossLeavesRemains() {
		for (int i = 1; i < gridMap.getRowSize(); i++) {
			for (int j = 1; j < gridMap.getColSize(); j++) {

				if (gridMap.getMap()[i][j].getType() == "remainsFog") {
					Label remains = new Label();
					remains.getStyleClass().add("fog");
					remains.setMinWidth(tileSize);
					remains.setMinHeight(tileSize);
					gridPane.add(remains, gridMap.getMap()[i][j].getX(), gridMap.getMap()[i][j].getY());

				}
			}
		}
	}

	//// Extra method for hunter
	// clear fog in the room hunter is in
	public void hunterCheckThisRoom() {

		int hx = theHunter.getX();
		int yx = theHunter.getY();

		if (gridMap.getMap()[hx][yx - 1].getType() == "wall" && gridMap.getMap()[hx][yx + 1].getType() == "wall"
				|| gridMap.getMap()[hx - 1][yx].getType() == "wall"
						&& gridMap.getMap()[hx + 1][yx].getType() == "wall") {
			// fixing left and up bugs
			// no checking

		} else {

			for (int i = ((theHunter.getRow() - 1) * 4); i < (theHunter.getRow() * 4); i++) {
				for (int j = ((theHunter.getCol() - 1) * 4); j < (theHunter.getCol() * 4); j++) {

					boolean trapFound = false;

					if (i > 0 && i < gridMap.getRowSize() && j > 0 && j < gridMap.getColSize()) {
						if (i % 4 != 0 && j % 4 != 0) {

							Coordinate c = gridMap.getMap()[i][j];

							// hunter found remainsFog
							if (c.getType() == "remainsFog") {
								gridMap.getMap()[i][j].setType("remains");
								Label remains = new Label();
								remains.getStyleClass().add("remains");
								remains.setMinWidth(tileSize);
								remains.setMinHeight(tileSize);
								gridPane.add(remains, c.getX(), c.getY());

							}

							// hunter found small enemy
							if (c.getType() == "enemy") {

								Label enemy = new Label();
								enemy.getStyleClass().add("enemy1");
								enemy.setMinWidth(tileSize);
								enemy.setMinHeight(tileSize);
								gridPane.add(enemy, c.getX(), c.getY());

								enemyFound(c);

							}

							// hunter found trap
							if (c.getType() == "trap") {

								if (trapFound == false) {

									Label traplb = new Label();
									traplb.getStyleClass().add("trap");
									traplb.setMinWidth(tileSize);
									traplb.setMinHeight(tileSize);
									gridPane.add(traplb, c.getX(), c.getY());

									defeat("trap");

									trapFound = true;
								}

							}

							// hunter found ammo
							if (c.getType() == "ammo") {

								Label ammolb = new Label();
								ammolb.getStyleClass().add("ammo");
								ammolb.setMinWidth(tileSize);
								ammolb.setMinHeight(tileSize);
								gridPane.add(ammolb, c.getX(), c.getY());
								ammoFound(c);

							}

							// hunter found Boss = death
							if (c.getType() == "boss") {
								changeLabel("boss");

								defeat("boss1");

							}

						}
					}
				}
			}

		}

	}

	public void bossCheckThisRoom() {

		// in case boss and hunter are in the same room
		for (int i = theBoss.getX() - 1; i <= theBoss.getX() + 1; i++) {

			for (int j = theBoss.getY() - 1; j <= theBoss.getY() + 1; j++) {
				Coordinate c = gridMap.getMap()[i][j];

				if (c.getType() == "hunter") {

					defeat("boss1");
				}

			}

		}

	}

	public void ammoFound(Coordinate c) {
		PopUp.foundAmmo();
		gridMap.getMap()[c.getX()][c.getY()].setType("floor");
		shotLeft++;
		leftBox.getChildren().remove(ammoCount);
		ammoCount = new Label("Shot Left: " + shotLeft);
		ammoCount.getStyleClass().add("ammoCount");
		leftBox.getChildren().add(ammoCount);

		Label floor = new Label();
		floor.getStyleClass().add("floor");
		floor.setMinWidth(tileSize);
		floor.setMinHeight(tileSize);
		gridPane.add(floor, c.getX(), c.getY());

	}

	public void enemyFound(Coordinate c) {
		int choice1 = PopUp.catPop();

		if (choice1 == 1) {

			if (shotLeft > 0) {
				// give ammo
				gridMap.getMap()[c.getX()][c.getY()].setType("friend");
				shotLeft--;
				leftBox.getChildren().remove(ammoCount);
				ammoCount = new Label("Shot Left: " + shotLeft);
				ammoCount.getStyleClass().add("ammoCount");
				leftBox.getChildren().add(ammoCount);
			} else {
				PopUp.enemyScream();
				gridMap.getMap()[c.getX()][c.getY()].setType("angryCat");
				alert = true;
			}

		}
		if (choice1 == 2) {
			// tell a story
			int story = PopUp.tellAStory();

			if (story < 29) {
				// cat hates your story
				PopUp.enemyReaction("angry");
				PopUp.enemyScream();
				gridMap.getMap()[c.getX()][c.getY()].setType("angryCat");
				alert = true;

			} else {
				PopUp.enemyReaction("happy");
				gridMap.getMap()[c.getX()][c.getY()].setType("friend");
			}
		}
		if (choice1 == 3) {
			// ignore
			PopUp.enemyScream();
			gridMap.getMap()[c.getX()][c.getY()].setType("angryCat");
			alert = true;

		}

	}

	public void detectNearbyRooms() {

		String text = " ";

		if (theHunter.detect(gridMap, "boss") == true) {
			text += "A chill ran down your spine. An arch demon is closeby\n";

		}

		if (theHunter.detect(gridMap, "trap") == true) {
			text += "You can hear anguish screaming of tortured souls\n";

		}

		if (theHunter.detect(gridMap, "enemy") == true) {

			text += "You feel the present of another living soul nearby\n";

		}

		if (theHunter.detect(gridMap, "ammo") == true) {

			text += "You feel a blessing aura\n";

		}

		if (text == " ") {
			text = "You can not detect anything";

		}
		PopUp.detectPop(text);
	}

	// hunter takes a shot; it leads to victory scene or boss starts roaming
	public void shooting() {

		hunter = new Label();
		hunter.getStyleClass().add("hunterShoot");
		hunter.setMinWidth(tileSize);
		hunter.setMinHeight(tileSize);
		gridPane.add(hunter, theHunter.getX(), theHunter.getY());

		if (shotLeft > 0) {

			// return String: "north", "south", "east", or "west"
			String answer = PopUp.shootPop("Where to shoot?", "choose wisely");
			boolean killConfirm = false;
			int victoryChoice;

			if (answer == "north") {
				killConfirm = theHunter.shoot(gridMap, "north", "boss");
				bossCheckThisRoom();

			}

			if (answer == "south") {
				killConfirm = theHunter.shoot(gridMap, "south", "boss");
				bossCheckThisRoom();

			}

			if (answer == "east") {
				killConfirm = theHunter.shoot(gridMap, "east", "boss");
				bossCheckThisRoom();

			}

			if (answer == "west") {
				killConfirm = theHunter.shoot(gridMap, "west", "boss");
				bossCheckThisRoom();

			}

			// win screen
			if (killConfirm == true) {

				// hunter cheer label

				// boss dead label
				for (int i = theBoss.getX() - 1; i <= theBoss.getX() + 1; i++) {
					for (int j = theBoss.getY() - 1; j <= theBoss.getY() + 1; j++) {
						if (gridMap.getMap()[i][j].getType() != "boss") {
							Label firelb = new Label();
							firelb.getStyleClass().add("blueFire");
							firelb.setMinWidth(tileSize);
							firelb.setMinHeight(tileSize);
							gridPane.add(firelb, i, j);

						}

					}
				}

				changeLabel("boss");

				victoryChoice = PopUp.victory("boss kill");

				bossIcon = "fog";
				alert = false;
				shotLeft = 3;

				try {
					restart(theStage);
				} catch (Exception e) {

					e.printStackTrace();
				}

			} else if (killConfirm == false && answer != "cancel") {
				alert = true;

				// shot in the wrong direction. boss start roaming
				PopUp.shotMissedPop();
				bossMeetHunter = theBoss.roam(gridMap, theHunter, alert);

				bossLeavesRemains();
				changeLabel(bossIcon);
				bossCheckThisRoom();
				hunterCheckThisRoom();

				// update shotLeft
				shotLeft--;
				leftBox.getChildren().remove(ammoCount);
				ammoCount = new Label("Shot Left: " + shotLeft);
				ammoCount.getStyleClass().add("ammoCount");
				leftBox.getChildren().add(ammoCount);

			}
		}
	}

	public void defeat(String reason) {

		int result = PopUp.gameOverPop(reason);

		try {
			bossIcon = "fog";
			alert = false;
			shotLeft = 3;
			restart(theStage);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	///////////////////////////////////////////////////////////////////////////////////////
	// Map and stage setting methods
	private void createMap() {
		gridMap = new GridMap(rowSize, colSize, trapNum, enemyNum, ammoExtra);

	}

	private void redrawMap() {
		gridPane.getChildren().clear();

		for (int x = 0; x < rowSize; x++) {
			for (int y = 0; y < colSize; y++) {

				Coordinate grid = gridMap.getMap()[x][y];

				// fog
				if (grid.getType().equals("fog")) {
					Label table = new Label();
					table.getStyleClass().add("fog");
					table.setMinWidth(tileSize);
					table.setMinHeight(tileSize);
					gridPane.add(table, x, y);

				}

				// cave floor
				if (grid.getType().equals("floor")) {

					Label table = new Label();
					table.getStyleClass().add("floor");
					table.setMinWidth(tileSize);
					table.setMinHeight(tileSize);
					gridPane.add(table, x, y);
				}

				// wall
				if (grid.getType().equals("wall")) {

					Label table = new Label();
					table.getStyleClass().add("wall");
					table.setMinWidth(tileSize);
					table.setMinHeight(tileSize);
					gridPane.add(table, x, y);

				}

				// trap
				if (grid.getType().equals("trap")) {

					Label trap = new Label();
					// trap.getStyleClass().add("trap");
					trap.getStyleClass().add("fog");
					trap.setMinWidth(tileSize);
					trap.setMinHeight(tileSize);
					gridPane.add(trap, x, y);

				}

				// Small Enemies
				if (grid.getType().equals("enemy")) {

					Label enemy = new Label();
					// enemy.getStyleClass().add("smallEnenmy1");
					enemy.getStyleClass().add("fog");
					enemy.setMinWidth(tileSize);
					enemy.setMinHeight(tileSize);
					gridPane.add(enemy, x, y);

				}

				if (grid.getType().equals("angryCat")) {

					Label enemy = new Label();
					// enemy.getStyleClass().add("smallEnenmy1");
					enemy.getStyleClass().add("enemy1");
					enemy.setMinWidth(tileSize);
					enemy.setMinHeight(tileSize);
					gridPane.add(enemy, x, y);

				}

				if (grid.getType().equals("friend")) {

					Label enemy = new Label();
					// enemy.getStyleClass().add("smallEnenmy1");
					enemy.getStyleClass().add("enemy1");
					enemy.setMinWidth(tileSize);
					enemy.setMinHeight(tileSize);
					gridPane.add(enemy, x, y);

				}

				// ammo
				if (grid.getType().equals("ammo")) {

					Label trap = new Label();
					trap.getStyleClass().add("fog");
					trap.setMinWidth(tileSize);
					trap.setMinHeight(tileSize);
					gridPane.add(trap, x, y);

				}

				// remains
				if (grid.getType().equals("remains")) {

					Label trap = new Label();
					trap.getStyleClass().add("remains");
					trap.setMinWidth(tileSize);
					trap.setMinHeight(tileSize);
					gridPane.add(trap, x, y);

				}

				// remainsFog
				if (grid.getType().equals("remainsFog")) {

					Label trap = new Label();
					trap.getStyleClass().add("fog");
					trap.setMinWidth(tileSize);
					trap.setMinHeight(tileSize);
					gridPane.add(trap, x, y);

				}

				// boss
				if (grid.getType().equals("boss")) {

					theBoss = new Boss(grid);
					boss = new Label();
					boss.getStyleClass().add(bossIcon);
					boss.setMinWidth(tileSize);
					boss.setMinHeight(tileSize);
					gridPane.add(boss, theBoss.getX(), theBoss.getY());

				}

				// Hunter
				if (grid.getType().equals("hunter")) {

					theHunter = new Hunter(grid);
					hunter = new Label();
					hunter.getStyleClass().add("hunterDown");
					hunter.setMinWidth(tileSize);
					hunter.setMinHeight(tileSize);
					gridPane.add(hunter, theHunter.getX(), theHunter.getY());

				}

			}

		}

	}

	public void startStage1() {
		rowSize = 21;
		colSize = 21;
		tileSize = 40;
		trapNum = 3;
		enemyNum = 3;
		ammoExtra = 2;
		hunterStep = 0;
		createMap();
		redrawMap();
		theStage.setScene(scene3);
	}

	public void startStage2() {
		rowSize = 29;
		colSize = 29;
		tileSize = 30;
		trapNum = 4;
		enemyNum = 5;
		ammoExtra = 3;
		hunterStep = 0;
		createMap();
		redrawMap();
		theStage.setScene(scene3);
	}

	public void startStage3() {
		rowSize = 41;
		colSize = 41;
		tileSize = 20;
		trapNum = 6;
		enemyNum = 7;
		ammoExtra = 4;
		hunterStep = 0;
		createMap();
		redrawMap();
		theStage.setScene(scene3);
	}

	public static void main(String[] args) {
		launch(args);
	}

}
