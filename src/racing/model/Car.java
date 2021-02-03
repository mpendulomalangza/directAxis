package racing.model;

public class Car {

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAcceleration() {
		return acceleration;
	}
	public void setAcceleration(int acceleration) {
		this.acceleration = acceleration;
	}
	public int getBraking() {
		return braking;
	}
	public void setBraking(int braking) {
		this.braking = braking;
	}
	public int getCorneringAbility() {
		return corneringAbility;
	}
	public void setCorneringAbility(int corneringAbility) {
		this.corneringAbility = corneringAbility;
	}
	public int getTopSpeed() {
		return topSpeed;
	}
	public void setTopSpeed(int topSpeed) {
		this.topSpeed = topSpeed;
	}
	public Car(int id, String name, int acceleration, int braking, int corneringAbility, int topSpeed) {
		super();
		this.id = id;
		this.name = name;
		this.acceleration = acceleration;
		this.braking = braking;
		this.corneringAbility = corneringAbility;
		this.topSpeed = topSpeed;
	}
	private int id;
	private String name;
	private int acceleration;
	private int braking;
	private int corneringAbility;
	private int topSpeed;
}
