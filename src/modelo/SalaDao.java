package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import controlador.Entrada;
import controlador.Pelicula;
import controlador.Sala;
import utilidades.Utilidades;


public class SalaDao {
	public static Connection conexion;

	public SalaDao(Connection conexion) {
		this.conexion=conexion;
	}
	
	public static ArrayList<Sala> consultarSalas(){
		//Creo el arrayList de departamentos donde los ire guardando 
		ArrayList<Sala> salas = new ArrayList<Sala>();
		try{
			Statement st=conexion.createStatement();
			//ResultSet: tipo de dato donde se recoge lo que venga de la tabla
			ResultSet rs=st.executeQuery("SELECT * FROM salas");
			while(rs.next()){
				int id_sala = rs.getInt("id_sala");
				int filas = rs.getInt("fila");
				int columnas = rs.getInt("columna");
				
				Sala sala = new Sala(id_sala, filas, columnas);
				salas.add(sala);
			}
			return salas;
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	
}



