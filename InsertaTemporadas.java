package series;

import java . io . FileInputStream ;
import java . sql .*;
import java . time . LocalDate ;
import java . util . Scanner ;

public class InsertaTemporadas implements DataBaseTask {
	
	public int insertaUnaTemporada (PreparedStatement pst , String linea) throws SQLException {
		String[] tokens  = linea.split(",");
		
		if(tokens.length != 4)
			throw new SQLException("Tamaño incorrecto");
		
		int idSerie = Integer.parseInt(tokens[0].trim());
        int nTemporada = Integer.parseInt(tokens[1].trim());
        int nCapitulos = Integer.parseInt(tokens[2].trim());

        String[] fechaPartes = tokens[3].trim().split("-");
        if (fechaPartes.length != 3)
            throw new SQLException("Formato incorrecto");
        int dia = Integer.parseInt(fechaPartes[0]);
        int mes = Integer.parseInt(fechaPartes[1]);
        int anho = Integer.parseInt(fechaPartes[2]);
        
        LocalDate fecha = LocalDate.of(anho, mes, dia);

        pst.setInt(1, idSerie);
        pst.setInt(2, nTemporada);
        pst.setInt(3, nCapitulos);
        pst.setDate(4, Date.valueOf(fecha));

        return pst.executeUpdate();
	}

@Override
public void run(Connection conn, String data) throws SeriesException {
    String sql = "INSERT INTO temporada (id_serie, n_temporada, n_capitulos, fecha_estreno) VALUES (?, ?, ?, ?)";
    try (PreparedStatement pst = conn.prepareStatement(sql);
         FileInputStream fis = new FileInputStream(data);
         Scanner scanner = new Scanner(fis)) {

        while (scanner.hasNextLine()) {
            String linea = scanner.nextLine().trim();
            if (!linea.isEmpty()) {
                	insertaUnaTemporada(pst, linea);
            }
        }
    } 
    catch (Exception e) {
        throw new SeriesException(e, "Insertando");
    }
}
}