import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
//Currently the program records obj.letterCount and the array item
//lengths incorrectly. Could try subtracting a value equal to the
//number of padded characters that were tacked on that aren't
//visible.

//replace word counters with more descriptive variables.
//Neurological Soft Signs Tester

//may want to move the answerWords array assignment to be
//outside the menu loop unless the user gets to choose the
//text they're tested on.

//Add documentation
//move variables outside of the main function, and access
//them through an object.
//Debug test cases for the menu.
//Remove extraneous print statements.
public class NSSTester{
	//Set to 1 because # of words = # of spaces + 1
int letterCount = 0;
	public static void main (String[] args) throws IOException {
		int wordArrIndex = 0;
		int answerKeyWordCounter = 1;
		int userInputWordCounter = 1;
		//int index = 0;
		
		int letterCount = -4;
		double accuracyResult = -1.0;
		int wordListSize = 1000;

		double accuracyResultArr[] = new double[wordListSize];
		double resultAccumulator = 0.0;
		String[] answerWords = new String[wordListSize];
		String[] userWords = new String[wordListSize];
		int wordCount = 0;
		String testBank = "test1 test2 test3";
		testBank = "dd dd dd";
		Scanner menuOptionScanner = new Scanner(System.in);
		Scanner dataScanner = new Scanner(System.in);
		String menuOption;
		String dataItem = "no data";
		long startTime;
		long endTime;
		long timeElapsed = 0;
		System.out.print("T)ake test, W)rite to file, q)uit: ");
		menuOption = menuOptionScanner.next();

		while (!((menuOption.equals("q")) || (menuOption.equals("Q")))) {
			NSSTester obj = new NSSTester();
			obj.padStrArray(answerWords, wordListSize);
			obj.padStrArray(userWords, wordListSize);
			//These counters must be reassigned to 1
			//(# of spaces = # of words - 1), because the arrays
			//are filled again.
			answerKeyWordCounter = 1;
			userInputWordCounter = 1;
			//startTime must remain outside the if block.
			//Otherwise, startTime and endTime have the same value.
			startTime = System.currentTimeMillis();

			if (menuOption.equalsIgnoreCase("T")) {
				
				System.out.println("Type the following: " + testBank);
				dataItem = dataScanner.nextLine();
				System.out.println(startTime);
				//Splitting the string into an array of words;
					
				wordArrIndex = 0;
						for (int i = 0; i < testBank.length(); ++i) {

							if (testBank.charAt(i) == (' ')) {
								answerKeyWordCounter += 1;

								++wordArrIndex;
							}
							else {
								answerWords[wordArrIndex] += testBank.charAt(i);
							}
							
						}
				wordArrIndex = 0;
		//Splitting the string into an array of words;

					for (int j = 0; j < dataItem.length(); ++j) {
						if (dataItem.charAt(j) == (' ')) {
							userInputWordCounter += 1;
							++wordArrIndex;
						}
						else {
							userWords[wordArrIndex] += dataItem.charAt(j);
						}
						
					}
				if ((answerKeyWordCounter != userInputWordCounter)) {
					System.out.println("Invalid Input");
					continue;
				}
				while ((obj.inputChecker(userWords, wordCount))) {
					System.out.print("Invalid Input. Enter Again: ");
					dataItem = dataScanner.nextLine();
					for (int j = 0; j < dataItem.length(); ++j) {	
						if (dataItem.charAt(j) == (' ')) {
							userInputWordCounter += 1;
							++wordArrIndex;
						}
						else {
							userWords[wordArrIndex] += dataItem.charAt(j);
						}
						
					}
				}
				wordCount = answerKeyWordCounter;
				obj.letterCount = 0;
				resultAccumulator = 0.0;
				for (int i = 0; i < wordCount; ++i) {
					for (int index = 0; index < userWords[i].length(); ++index) {
		//Must use answerWords as the first input and userWords as the
		//second with linearSearch() to ensure that each userWords value only gets searched
		//for once.

						if (obj.linearSearch(answerWords[i], userWords[i].charAt(index))) {
							++obj.letterCount;
						}
					}
					resultAccumulator += (double)obj.letterCount/(answerWords[i].length());
					obj.letterCount = 0;
				}
				accuracyResult = resultAccumulator/wordCount;
				endTime = System.currentTimeMillis();
				System.out.println(endTime);
				timeElapsed = endTime - startTime;
				System.out.println(timeElapsed);
			}
			if (menuOption.equalsIgnoreCase("W")) {
				obj.write2File(dataItem, accuracyResult, timeElapsed);
				accuracyResult = -1.0;
			}
			System.out.print("T)est, W)rite to file, q)uit: ");
			menuOption = menuOptionScanner.next();
		}				
	}
//Searches an array and returns true if the target was found.
//Else, it returns false.
	boolean linearSearch(String arrayVal, char targetVal) {
		for (int j = 0; j < arrayVal.length(); ++j) {
			if (arrayVal.charAt(j) == targetVal) {

				return true;
			}
		}
		
		return false;
	}
//Writes input and a data point to a file.
	void write2File (String data, double dataPoint, long dataPoint2) throws IOException {

		BufferedWriter fileO = new BufferedWriter(new FileWriter("data.csv", true));
		fileO.write(data + "," + dataPoint + "," + dataPoint2 + "\n");
		fileO.close();
		
		
	}
	//Check the word arrays. Ex. don't allow blank spaces as word
	//values. ie. no blank entries.
	boolean inputChecker (String[] array, int dataLen) {
		for (int index = 0; index < dataLen; ++index) {
			if (array[index].equals("")) {
				return true;
			}
		}
		return false;
	}
	//Fills a String array with empty strings.
	void padStrArray(String[] array, int arraySize) {
		for (int i = 0; i < arraySize; ++i) {
			array[i] = "";
		}
	}
}
