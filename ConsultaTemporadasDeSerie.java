package series; 
import java.sql.*;
import java.util.*;

public class ConsultaTemporadasDeSerie extends ConsultaConResultado<Temporada> {
    @Override
    public void run(Connection connection, String data) throws SeriesException {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        resultado = new ArrayList<>();
        try {
            String sql = """
                SELECT t.*
                FROM temporada t
                JOIN serie s ON t.id_serie = s.id_serie
                WHERE s.sinopsis LIKE ?
                ORDER BY t.fecha_estreno ASC
            	""";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, data);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                Temporada t = new Temporada(
                    rs.getInt("id_serie"),
                    rs.getInt("n_temporada"),
                    rs.getInt("n_capitulos"),
                    rs.getDate("fecha_estreno")
                );
                resultado.add(t);
            }
            pstmt.close();
            
        } catch (SQLException e) {
            throw new SeriesException(e,"Consulta Temporadas De Serie");
        } 
    }
}
