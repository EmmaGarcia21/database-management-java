package series; 
import java.util.*;
import java.sql.*;

public class ConsultaTemporadas extends ConsultaConResultado<Temporada> {
    @Override
    public void run(Connection connection, String data) throws SeriesException {
        Statement stmt = null;
        ResultSet rs = null;
        resultado = new ArrayList<>();
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery("SELECT * FROM temporada ORDER BY fecha_estreno ASC");
            while (rs.next()) {
                Temporada t = new Temporada(
                    rs.getInt("id_serie"),
                    rs.getInt("n_temporada"),
                    rs.getInt("n_capitulos"),
                    rs.getDate("fecha_estreno"));
                resultado.add(t);
            }
            stmt.close();
            
        } catch (SQLException e) {
            throw new SeriesException(e , "Consulta Temporadas");
        } 
    }
}