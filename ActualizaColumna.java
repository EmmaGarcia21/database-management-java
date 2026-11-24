package series; 
import java . sql .*;
public class ActualizaColumna implements DataBaseTask{
		@Override
	    public void run(Connection connection, String data) throws SeriesException {
	        Statement stmt = null;
	        try {
	            stmt = connection.createStatement();
	            stmt.executeUpdate("ALTER TABLE capitulo MODIFY titulo VARCHAR(100)");
	            stmt.close();
	        } catch (SQLException e) {
	            throw new SeriesException(e, "Actualizar Columna");
	        } 
	    }
}
