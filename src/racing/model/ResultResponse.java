package racing.model;

public class ResultResponse {
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public int getTrackId() {
		return trackId;
	}
	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}
	public ResultResponse(String name, int score, int position,int trackId) {
		super();
		this.name = name;
		this.score = score;
		this.position = position;
		this.trackId=trackId;
		
	}
	private String name;
	private int score;
	private int position;
	private int trackId;

}
