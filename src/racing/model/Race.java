package racing.model;

public class Race {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTrackId() {
		return trackId;
	}
	public void setTrackId(int trackId) {
		this.trackId = trackId;
	}
	public Race(int id, int trackId) {
		super();
		this.id = id;
		this.trackId = trackId;
	}
	private int id;
	private int trackId;
	public Race() {
		
	}
}
