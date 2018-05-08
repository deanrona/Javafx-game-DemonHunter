 package DemonHunter;

import java.util.Random;

public class GridMap {

	private int rowSize;
	private int colSize;
	private Coordinate[][] map;
	public Random rnd = new Random();

	// unused variables
	private boolean alert;
	private int shotLeft;
	private int tileSize;

	public GridMap(int rowSize, int colSize, int trapNum, int enemyNum, int ammoExtra) {
		this.rowSize = rowSize;
		this.colSize = colSize;
		this.map = new Coordinate[rowSize][colSize];

		// set up wall and fog
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {

				if (i % 4 == 0 || j % 4 == 0) {
					map[i][j] = new Coordinate(i, j, "wall");
				} else {
					map[i][j] = new Coordinate(i, j, "fog");
				}

			}
		}

		// entrances in the wall
		for (int i = 2; i < (rowSize - 1); i += 2) {
			for (int j = 2; j < (colSize - 1); j += 2) {

				if (i % 4 != 0 || j % 4 != 0) {
					map[i][j].setType("fog");
				}

			}
		}

		// Set up trap location

		int trapCount = 0;
		while (trapCount < trapNum) {
			for (int i = 0; i < rowSize; i++) {
				if (i != 0 && (i + 2) % 4 == 0) {
					for (int j = 0; j < colSize; j++) {
						if (j != 0 && (j + 2) % 4 == 0) {

							int rnd2 = rnd.nextInt(100);

							if ((rnd2 < 10) && trapCount < trapNum && map[i][j].getType() == "fog") {

								map[i][j].setType("trap");

								// surround trap grid with more traps
								map[i - 1][j - 1].setType("trap");
								map[i + 1][j + 1].setType("trap");
								map[i - 1][j + 1].setType("trap");
								map[i + 1][j - 1].setType("trap");

								trapCount++;
							}

						}

					}
				}
			}
		}

		// set up small enemy location
		int enemyCount = 0;
		while (enemyCount < enemyNum) {
			for (int i = 0; i < rowSize; i++) {
				if (i != 0 && (i + 2) % 4 == 0) {
					for (int j = 0; j < colSize; j++) {
						if (j != 0 && (j + 2) % 4 == 0) {

							int rnd2 = rnd.nextInt(100);
							if (((rnd2 < 10)) && enemyCount < enemyNum && map[i][j].getType() == "fog") {
								map[i][j].setType("enemy");
								enemyCount++;
							}

						}
					}
				}
			}
		}

		// set up boss location
		int bossCount = 0;
		while (bossCount != 1) {
			for (int i = 0; i < rowSize; i++) {
				if (i != 0 && (i + 2) % 4 == 0) {
					for (int j = 0; j < colSize; j++) {
						if (j != 0 && (j + 2) % 4 == 0) {

							if (rnd.nextInt(100) < 10 && bossCount < 1 && map[i][j].getType() == "fog") {

								map[i][j].setType("boss");
								bossCount = 1;
							}

						}

					}
				}
			}
		}

		// set up ammo location
		int ammoCount = 0;
		while (ammoCount < ammoExtra) {
			for (int i = 1; i < rowSize; i++) {
				if (i != 0 && (i + 2) % 4 == 0) {
					for (int j = 1; j < colSize; j++) {
						if (j != 0 && (j + 2) % 4 == 0) {

							int rnd2 = rnd.nextInt(100);
							if (rnd2 < 9 && ammoCount < ammoExtra && map[i][j].getType() == "fog") {
								map[i][j].setType("ammo");
								ammoCount++;
							}

						}
					}
				}
			}
		}

		// set up Hunter location
		int hunterCount = 0;
		while (hunterCount != 1) {
			for (int i = 0; i < rowSize; i++) {
				if (i != 0 && (i + 2) % 4 == 0) {
					for (int j = 0; j < colSize; j++) {
						if (j != 0 && (j + 2) % 4 == 0) {

							if (rnd.nextInt(100) < 10 && hunterCount < 1 && map[i][j].getType() == "fog") {

								map[i][j].setType("hunter");

								// clear all the fog near hunter spawn location
								for (int k = i - 1; k <= i + 1; k++) {
									for (int l = j - 1; l <= j + 1; l++) {
										if (map[k][l].getType() == "fog") {
											map[k][l].setType("floor");
										}
									}

								}

								hunterCount = 1;
							}

						}

					}
				}
			}
		}

	}

	//////////////////////////////////

	public int getRowSize() {
		return rowSize;
	}

	public void setRowSize(int rowSize) {
		this.rowSize = rowSize;
	}

	public int getColSize() {
		return colSize;
	}

	public void setColSize(int colSize) {
		this.colSize = colSize;
	}

	public Coordinate[][] getMap() {
		return map;
	}

	public void setMap(Coordinate[][] map) {
		this.map = map;
	}

	public boolean isAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}

	public int getShotLeft() {
		return shotLeft;
	}

	public void setShotLeft(int shotLeft) {
		this.shotLeft = shotLeft;
	}

	public int getTileSize() {
		return tileSize;
	}

	public void setTileSize(int tileSize) {
		this.tileSize = tileSize;
	}

	public String toString() {

		String[][] map2 = new String[this.rowSize][this.colSize];

		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				map2[i][j] = map[i][j].getType();
			}
		}

		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				System.out.print(map2[j][i] + "\t");
				System.out.print("|");

				if (j == (colSize - 1)) {
					System.out.println();
				}

			}

		}

		return "";
	}

}
