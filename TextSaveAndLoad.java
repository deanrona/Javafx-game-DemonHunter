package DemonHunter;

import java.util.Scanner;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import javafx.collections.ObservableList;
import javafx.stage.FileChooser;

public interface TextSaveAndLoad {

	Scanner input = new Scanner(System.in);

	public default void saveAsTextFile(GridMap gridMap, boolean alert, int shotLeft, int tileSize) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose a location to save to");
		fileChooser.setInitialFileName("DemonHunterSav.txt");

		File selectedFile = null;
		selectedFile = fileChooser.showSaveDialog(null);

		if (selectedFile != null) {

			File file = new File(selectedFile + "");

			try {
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				PrintWriter pw = new PrintWriter(bw);

				// alert status, shotLeft, rowSize, colSize
				pw.println(alert);
				pw.println(shotLeft);
				pw.println(tileSize);
				pw.println(gridMap.getRowSize());
				pw.println(gridMap.getColSize());

				// gridMap
				for (int i = 0; i < gridMap.getRowSize(); i++) {
					for (int j = 0; j < gridMap.getColSize(); j++) {
						pw.print(gridMap.getMap()[i][j].getType() + ",");

					}

				}

				pw.close();

			} catch (IOException e) {
				System.err.println(e);

			}
		}

	}

	// File tab: load from text file
	// return gridMap and change the variables in the parameter
	@SuppressWarnings("null")
	public default GridMap loadGridMapFromTextFile() {
		GridMap gridMap = null;

		FileChooser fileChooser = new FileChooser();

		File file = fileChooser.showOpenDialog(null);
		fileChooser.setTitle("Choose the text file");

		if (file != null) {

			try {
				Scanner fileReader = new Scanner(file);

				// set boolean alert and int shotLeft values
				boolean alert2 = Boolean.parseBoolean(fileReader.nextLine());
				int shotLeft2 = Integer.parseInt(fileReader.nextLine());
				int tileSize2 = Integer.parseInt(fileReader.nextLine());
			
				
				String line;
				String[] part;
				String type;

				// read int rowSize and int colSize
				int rowSize = Integer.parseInt(fileReader.nextLine());
				int colSize = Integer.parseInt(fileReader.nextLine());
				int count = 0;

				// read gridmap
				while (fileReader.hasNextLine()) {
					line = fileReader.nextLine();
					part = line.split(",");

					gridMap = new GridMap(rowSize, colSize, 3, 3, 3); // fix this later

					for (int i = 0; i < gridMap.getRowSize(); i++) {
						for (int j = 0; j < gridMap.getColSize(); j++) {
							String s = part[count];

							Coordinate c = new Coordinate(i, j, part[count]);
							gridMap.getMap()[i][j] = c;
							count++;
						}

					}
					
					gridMap.setAlert(alert2);
					gridMap.setShotLeft(shotLeft2);
					gridMap.setTileSize(tileSize2);

				}

				fileReader.close(); // Close to unlock.
				input.close();

			} catch (IOException e) {
				System.err.println(e);

			}
		}
		return gridMap;
	}

}
