package racing.model;

public class Track {
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTrack() {
		return track;
	}
	public void setTrack(String track) {
		this.track = track;
	}
	public Track(int id, String track) {
		super();
		this.id = id;
		this.track = track;
	}
	private int id;
	private String track;
}
