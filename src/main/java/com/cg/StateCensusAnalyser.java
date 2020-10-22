package com.cg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Scanner;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import com.cg.StateCensusAnalyserException.ExceptionType;

public class StateCensusAnalyser {

	private static String CSV_CENSUS_FILE = "./IndianStateCensusData.csv";

	public int readData(String DATA_FILE) throws StateCensusAnalyserException {
		int noOfEntries = 0;
		if(!DATA_FILE.contains(".csv")) {
			throw new StateCensusAnalyserException(ExceptionType.INVALID_TYPE,"Invalid Class Type in the File!! \nInvalidTypeException thrown....");
		}
		try {
			Reader readFile = Files.newBufferedReader(Paths.get(DATA_FILE));
			CsvToBeanBuilder<IndianStateCensus> user = new CsvToBeanBuilder<IndianStateCensus>(readFile);
			user.withType(IndianStateCensus.class);
			CsvToBean user1 = user.withIgnoreLeadingWhiteSpace(true).build();
			BufferedReader br = new BufferedReader(new FileReader(DATA_FILE));
			int count = 0;
			String line = "";
			while ((line = br.readLine()) != null) {
				if (!line.contains(","))
					throw new StateCensusAnalyserException(ExceptionType.INVALID_DELIMITER,
							"Invalid Delimiter in the File!! \nInvalidDelimiterException thrown....");
				if (count == 0) {
					String[] headerArray = line.split(",");
					if (!(headerArray[0].equals("State") && headerArray[1].equals("Population")
							&& headerArray[2].equals("Area") && headerArray[3].equals("Density")))
						throw new StateCensusAnalyserException(ExceptionType.INVALID_HEADER,
								"Invalid headers in File!! \nInvalidHeaderException thrown....");
					count++;

				}

			}
			br.close();
			Iterator<IndianStateCensus> userIterator = user1.iterator();
			while (userIterator.hasNext()) {
				IndianStateCensus csvuser = userIterator.next();
				System.out.println(csvuser);
				System.out.println(
						"===================================================================================================");
				noOfEntries++;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new StateCensusAnalyserException(StateCensusAnalyserException.ExceptionType.INVALID_FILE_PATH,
					"Invalid File Location given!! \nInvalidFilePathException thrown....");

		}
		return noOfEntries;
	}

		public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Welcome to Indian State Census Analyser Program!");
		System.out.println("================================================");
		System.out.println("1.Read Indian State Census Data \n2.Exit");
		System.out.println("Enter your choice:");
		int choice = Integer.parseInt(sc.nextLine());
		StateCensusAnalyser object = new StateCensusAnalyser();
		switch (choice) {
		case 1:
			try {
					object.readData(CSV_CENSUS_FILE);
			} catch (StateCensusAnalyserException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			break;
		case 2:
			System.out.println("Thanks for using application!");
			System.exit(0);
		}
	}
}