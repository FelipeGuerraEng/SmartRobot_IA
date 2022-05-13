package control;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Input {

	static String nombreLectura = "in";
	static String nombreEscritura = "out";
	static int rows = 10;
	static int columns = 10;

	public Input () {

	}

	public static int [][] input() throws FileNotFoundException {


		int[][] matriz = null;

		File file = new File(nombreLectura + ".txt");

		try {

			Scanner sizeScanner = new Scanner(file);
			String[] temp = sizeScanner.nextLine().split(" ");
			sizeScanner.close();
			int nMatrix = temp.length;

			Scanner scanner = new Scanner(file);
			matriz = new int[nMatrix][nMatrix];

			for (int i = 0; i < nMatrix; i++) {

				String[] numbers = scanner.nextLine().split(" ");

				for (int j = 0; j < nMatrix; j++) {

					matriz[i][j] = Integer.parseInt(numbers[j]);
				}
			}

			scanner.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		int[][] a = new int[rows+2][columns+2];

		
		for(int i = 0; i < rows; ++i)
		{
			for(int j = 0; j < columns; ++j)
			{
				System.out.print(matriz[i][j] + " ");
			}
			System.out.print("\n");
		}


		return matriz;

	}

}




