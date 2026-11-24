package series;

import java.io.*;
import java.sql.*;

public class InsertaUsuarioConFoto implements DataBaseTask {

    public void run(Connection conn, String data) throws SeriesException {
        String[] campos = data.split(",");
        if (campos.length != 5)
            throw new SeriesException(new IllegalArgumentException("Numero de campos incorrecto"),"insertando usuario");

        String sql = "INSERT INTO usuario (id_usuario, nombre, apellido1, apellido2, fotografia) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql);
            InputStream fotoStream = new FileInputStream(campos[4])) {
            stmt.setString(1, campos[0]);
            stmt.setString(2, campos[1]);
            stmt.setString(3, campos[2]);
            stmt.setString(4, campos[3]);
            stmt.setBinaryStream(5, fotoStream);

            stmt.executeUpdate();
            
            fotoStream.close();
            stmt.close();

        } catch (Exception e) {
            throw new SeriesException(e,"insertando usuario");
        }
    }
}