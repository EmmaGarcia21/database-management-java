package series;

import java.sql.*;
import java.util.*;

public class ConsultaTemporadasIncompletas extends ConsultaConResultado<String> {

    public void run(Connection conn, String ignored) throws SeriesException {
        String sql =
            "SELECT s.titulo, t.n_temporada " +
            "FROM serie s " +
            "JOIN temporada t ON s.id_serie = t.id_serie " +
            "WHERE (SELECT COUNT(*) FROM capitulo c WHERE c.id_serie = t.id_serie AND c.n_temporada = t.n_temporada) <> t.n_capitulos";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery())
        {
            resultado = new ArrayList<>();
            while (rs.next()) {
                String titulo = rs.getString("titulo");
                String temporada = rs.getString("n_temporada");
                resultado.add(titulo + ":" + temporada);
            }
            
            rs.close();
            stmt.close();
            
        } catch (Exception e) {
            throw new SeriesException(e,"consultando temporadas incompletas");
        }
    }
}
