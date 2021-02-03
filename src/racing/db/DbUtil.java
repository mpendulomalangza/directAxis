package racing.db;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import racing.model.Car;
import racing.model.Race;
import racing.model.RaceResult;
import racing.model.ResultResponse;
import racing.model.Track;

public class DbUtil {

	Properties p;

	public DbUtil() throws IOException {
		FileReader reader = new FileReader("resource//db.properties");
		p = new Properties();
		p.load(reader);
	}

	public Connection getDbConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(p.getProperty("url"), p.getProperty("username"),
					p.getProperty("password"));
		} catch (Exception ex) {
			return null;
		}
	}

	public List<Track> getTracks() throws Exception {
		Connection con = null;
		try {
			List<Track> list = new ArrayList<Track>();
			con = getDbConnection();
			PreparedStatement query = con.prepareStatement("Select * from track");
			ResultSet results = query.executeQuery();
			while (results.next()) {
				list.add(new Track(results.getInt("id"), results.getString("track")));
			}
			return list;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public Track saveTrack(String track) throws Exception {
		Connection con = null;
		try {
			int id = -1;
			con = getDbConnection();
			PreparedStatement update = con.prepareStatement("Insert into Track (track) values(?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			update.setString(1, track);
			update.executeUpdate();
			ResultSet rs = update.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}
			return new Track(id, track);
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
	
	public Race saveRace(Race race) throws Exception {
		Connection con = null;
		try {
			con = getDbConnection();
			PreparedStatement update = con.prepareStatement("Insert into race (track_id) values(?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			update.setInt(1, race.getTrackId());
			update.executeUpdate();
			ResultSet rs = update.getGeneratedKeys();
			if (rs.next()) {
				race.setId(rs.getInt(1));
			}
			return race;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public List<ResultResponse> getRaceStandings(int raceId) throws Exception {
		Connection con = null;
		try {
			List<ResultResponse> cars=new ArrayList<ResultResponse>();
			con = getDbConnection();
			PreparedStatement query = con.prepareStatement("select car.name,race_result.score,race_id,race.track_id from race_result \n" + 
					"join car on car.id=race_result.car_id\n" + 
					"join race on race.id =race_result.race_id\n" + 
					"join\n" + 
					"(SELECT distinct score FROM race.race_result\n" + 
					"join race on race.id =race_result.race_id where race_id=? limit 3 ) t on t.score =race_result.score\n" + 
					"where race_result.race_id=?\n" + 
					"group by car.name,race_result.score,race_id,race.track_id\n" + 
					"order by race_result.score desc");
			query.setInt(1, raceId);
			query.setInt(2, raceId);
			ResultSet rs = query.executeQuery();
			HashMap<Integer, Integer> positions=new HashMap<Integer, Integer>();
			while (rs.next()) {
				if(!positions.containsKey(rs.getInt(2))) {
					positions.put(rs.getInt(2),positions.size()+1);
				}
				cars.add(new ResultResponse(rs.getString(1), rs.getInt(2), positions.get(rs.getInt(2)),rs.getInt(3)));
			}
			return cars;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}	
	
	public Car saveCar(Car car) throws Exception {
		Connection con = null;
		try {
			con = getDbConnection();
			PreparedStatement update = con.prepareStatement(
					"Insert into Car (name,acceleration,top_speed,braking_ability,cornering_ability) values(?,?,?,?,?)",
					PreparedStatement.RETURN_GENERATED_KEYS);
			update.setString(1, car.getName());
			update.setInt(2, car.getAcceleration());
			update.setInt(3, car.getTopSpeed());
			update.setInt(4, car.getBraking());
			update.setInt(5, car.getCorneringAbility());
			update.executeUpdate();
			ResultSet rs = update.getGeneratedKeys();
			if (rs.next()) {
				car.setId(rs.getInt(1));
			}
			return car;
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}

	public void saveResult(RaceResult result) throws Exception {
		Connection con = null;
		try {
			con = getDbConnection();
			PreparedStatement update = con
					.prepareStatement("Insert into race_result (race_id,car_id,score) values(?,?,?)");
			update.setInt(1, result.getRaceId());
			update.setInt(2, result.getCarId());
			update.setInt(3, result.getScore());
			update.executeUpdate();
		} catch (Exception ex) {
			throw ex;
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}
	/** List<RaceResult> trackResults **/

}
