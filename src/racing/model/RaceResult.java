package racing.model;

public class RaceResult {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRaceId() {
		return raceId;
	}
	public void setRaceId(int raceId) {
		this.raceId = raceId;
	}
	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public RaceResult(int id, int raceId, int carId, int score) {
		super();
		this.id = id;
		this.raceId = raceId;
		this.carId = carId;
		this.score = score;
	}
	private int id;
	private int raceId;
	private int carId;
	private int score;
}
