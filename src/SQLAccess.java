import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Date;

public class SQLAccess 
{
        
  private Connection con = null;
  private Statement state = null;
  private PreparedStatement prepState = null;
  private ResultSet resSet = null;
  
  ArrayList<Business> businesses = new ArrayList<Business>();

  final private String host = "138.197.28.159:3306";
  final private String user = "user";
  final private String pass = "";
  
  public void readData() throws Exception 
  {
    try 
    {
     
      Class.forName("com.mysql.jdbc.Driver");
      
     
      con = DriverManager.getConnection("jdbc:mysql://" + host + "/data?useSSL=false&"
               +"&user=" + user + "&password=" + pass );
      
    } 
    catch (Exception e) 
    {
      throw e;
    } 

  }

public ArrayList<Business> createBusiness(){
	
	try {
		readData();
		state = con.createStatement();
		String statement = "SELECT id, name, email FROM data.users";
		resSet = state.executeQuery(statement);
		
		while (resSet.next()) {
			String name = resSet.getString("name");
			String email = resSet.getString("email");
			int id = resSet.getInt("id");
			Business bus = new Business(name, email, id);
			businesses.add(bus);
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return businesses;
}

	public void addRooms() {
		String statement = "Select id, addressline, city, state, user_id from data.addresses where addresses.user_id = ?";
		for(Business b : businesses) {
		int busId = b.getId();
		
		try {
			prepState = con.prepareStatement(statement);
			prepState.setInt(1, busId);
			resSet = prepState.executeQuery();
			
			while (resSet.next()) {
				int id = resSet.getInt("id");
				String address = resSet.getString("addressline") + ", " + resSet.getString("city") + ", " + resSet.getString("state");
				Room r = new Room(address, id);
				b.getRooms().add(r);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
	}
	
	public void addAlarms() {
		String statement = "Select order_product.id, order_product.product_id, order_product.order_id, orders.address_id from order_product inner join orders on order_product.order_id = orders.id where orders.address_id = ?";
		for(Business b : businesses) {
			for(Room r : b.getRooms()) {
				int roomId = r.getId();
				try {
					prepState = con.prepareStatement(statement);
					prepState.setInt(1, roomId);
					resSet = prepState.executeQuery();
					
					while(resSet.next()) {
						Alarm alarm;
						int productId = resSet.getInt("order_product.product_id");
						int id = resSet.getInt("order_product.id");
						switch (productId) {
						case 1:
							alarm = new FireAlarm("Fire Alarm", 2, id);
							r.getAlarms().add(alarm);
							break;
						case 2:
							alarm = new ChemicalAlarm("Chemical Alarm", 2, id);
							r.getAlarms().add(alarm);
							break;
						case 3:
							alarm = new FloodAlarm("Flood Alarm", 2, id);
							r.getAlarms().add(alarm);
							break;
						case 4:
							alarm = new DoorAlarm("Invasion Alarm", 2, id);
							r.getAlarms().add(alarm);
							break;
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	public void logAlarm(Alarm alarm, Business business) {
		String statement = "Insert into data.Log (alarm_id, user_id) values (?, ?)";
		try {
			prepState = con.prepareStatement(statement);
			prepState.setInt(1, alarm.getId());
			prepState.setInt(2, business.getId());
			prepState.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

  private void close() 
  {
    try 
    {
      if (resSet != null) 
      {
        resSet.close();
      }

      if (state != null) 
      {
        state.close();
      }

      if (con != null) 
      {
        con.close();
      }
    } 
    catch (Exception e) 
    {

    }
  }
}