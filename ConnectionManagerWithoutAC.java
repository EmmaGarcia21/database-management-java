package series;
import java.sql.*;

public class ConnectionManagerWithoutAC extends ConnectionManager {
	
public ConnectionManagerWithoutAC ( String user , String password ) {
	super ( user , password ); // Para construir ConnectionManager
}

@Override
public String runTask ( DataBaseTask [] tasks , String [] dataArray ) {
	Connection conn = null;
	try {
		conn = DriverManager.getConnection(url(),user,password);
		conn.setAutoCommit(false);
		
		for(int i = 0; i<tasks.length; i++) {
			try {
				tasks[i].run(conn,dataArray[i]);	
			}
			catch(SeriesException e) {
				conn.rollback();
				return e.getMessage() + " " + e.when();
			}
			catch(Exception e) {
				conn.rollback();
				return e.getMessage();
			}
		}
		conn.commit();
		conn.close();
		return "OK";
	}
	catch (Exception e) {
		return e.getMessage();
	}
}

}