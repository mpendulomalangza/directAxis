package racing.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import racing.db.DbUtil;
import racing.model.Car;
import racing.model.Race;
import racing.model.RaceResult;
import racing.model.ResultResponse;
import racing.model.Track;

public class RacingGame {

	public static void main(String[] args) throws Exception {
		DbUtil db = new DbUtil();
		Scanner scanner = new Scanner(System.in);
		int trackCount = 1;
		System.out.println("Welcome to direct-axis racing game");
		int numberOfCars = -1;
		int carCount = 1;
		HashMap<Integer, Track> cTrack = new HashMap<Integer, Track>();
		List<Track> selectedTracks = new ArrayList<Track>();
		List<Car> cars = new ArrayList<Car>();
		while (trackCount < 3) {
			String trackQuestion = getStringValidatedInput(
					"Do you want to create a new track for track. Please enter 'yes' or 'no' for track no " + trackCount,
					"yes,no", scanner);
			if (trackQuestion.equalsIgnoreCase("yes")) {
				String trackInput = getTrackInput("Enter track value e.g 1000101010  for track " + trackCount, scanner);
				selectedTracks.add(db.saveTrack(trackInput));
				trackCount++;
			} else {
				System.out.println("Race track options:");
				System.out.println("---------------------");
				List<Track> tracks = db.getTracks();
				ArrayList<String> options = new ArrayList<String>();
				tracks.stream().forEach(track -> {
					System.out.println("Type " + track.getId() + " for track " + track.getTrack());
					options.add(String.valueOf(track.getId()));
					cTrack.put(track.getId(), track);
				});
				String trackInput = getStringValidatedInput("Please select an option for track " + trackCount,
						String.join(",", options), scanner);
				selectedTracks.add(cTrack.get(Integer.parseInt(trackInput)));
				trackCount++;
			}
		}
		numberOfCars = getIntInput("Please enter number of cars not less than 3 ", scanner, 3, 99999) + 1;
		System.out.println("-----------------------");
		System.out.println("Car Details");
		System.out.println("-----------------------");
		while (carCount < numberOfCars) {
			String name = getStringInput("Please enter car " + carCount + " name", scanner);
			int acceleration = getIntInput("Please enter car " + carCount + " Acceleration ", scanner, -1, 999999);
			int braking = getIntInput("Please enter car " + carCount + " Braking ", scanner, -1, 999999);
			int corneringAbility = getIntInput("Please enter car " + carCount + " Cornering Ability ", scanner, -1,
					999999);
			int topSpeed = getIntInput("Please enter car " + carCount + " Top Speed ", scanner, -1, 999999);
			Car car = db.saveCar(new Car(-1, name, acceleration, braking, corneringAbility, topSpeed));
			cars.add(car);
			carCount++;
		}
		HashMap<Integer, List<ResultResponse>> standings=processRace(cars, selectedTracks, db);
		standings.keySet().stream().forEach(trackId->{
			System.out.println();
			System.out.println("Race Results for track ");
			System.out.println();
			System.out.println("---------------------------------------");
			System.out.println("Pos\tScore\tCar");
			System.out.println("---------------------------------------");
			standings.get(trackId).forEach(car->{System.out.println(car.getPosition()+"\t"+car.getScore()+"\t"+car.getName());});
		});
		System.out.println();

	}

	private static int getIntInput(String message, Scanner scanner, int min, int max) {
		String value = "";
		while (!isInteger(value)) {
			System.out.println(message);
			value = scanner.nextLine();
		}
		return Integer.parseInt(value);
	}

	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
		} catch (NumberFormatException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
		return true;
	}

	private static String getTrackInput(String message, Scanner scanner) {
		String value = "";
		while (value.isEmpty()) {
			System.out.println(message);
			value = scanner.nextLine();
			if (!isValidTrack(value)) {
				value = "";
			}
		}
		return value;
	}

	private static boolean isValidTrack(String track) {
		return track.replaceAll("0", "").replaceAll("1", "").length() == 0;
	}

	private static String getStringValidatedInput(String message, String commaSeperatedValues, Scanner scanner) {
		String[] validVals = commaSeperatedValues.split(",");
		String value = "";
		while (!isValidInput(value, validVals)) {
			System.out.println(message);
			value = scanner.nextLine();
		}
		return value;
	}

	private static String getStringInput(String message, Scanner scanner) {
		String value = "";
		while (value.isEmpty()) {
			System.out.println(message);
			value = scanner.nextLine();
		}
		return value;
	}

	private static boolean isValidInput(String input, String[] validValues) {
		boolean found = false;
		for (String string : validValues) {
			if ((input.equalsIgnoreCase(string))) {
				found = true;
				break;
			}
		}
		return found;
	}

	private static HashMap<Integer, List<ResultResponse>> processRace(List<Car> cars, List<Track> tracks, DbUtil db)
			throws Exception {
		HashMap<Integer, List<ResultResponse>> output = new HashMap<Integer, List<ResultResponse>>();
		HashMap<Integer, Race> races = new HashMap<Integer,Race>();
		for (Track track : tracks) {
			if(!races.containsKey(track.getId())) {
				races.put(track.getId(),db.saveRace(new Race(-1, track.getId())));
			}
			int totalTurns = getCharacterCount(track.getTrack(), '0');
			int totalStraight = getCharacterCount(track.getTrack(), '1');
			for (Car car : cars) {
				int score = totalTurns * car.getCorneringAbility() + totalTurns * car.getBraking()
				+ totalStraight * car.getAcceleration() + totalStraight * car.getTopSpeed();
				int raceId=races.get(track.getId()).getId();
				db.saveResult(new RaceResult(-1,raceId, car.getId(), score));
			}
		}
		races.keySet().stream().forEach(trackId->{try {
			int raceId=races.get(trackId).getId();
			output.put(raceId, db.getRaceStandings(raceId));
		} catch (Exception e) {
			e.printStackTrace();
		}});
		return output;
	}

	public static int getCharacterCount(String text, char character) {
		int count = 0;
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == character) {
				count++;
			}
		}
		return count;
	}

}
