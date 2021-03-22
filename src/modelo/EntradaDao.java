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
import utilidades.Utilidades;


public class EntradaDao {
	public static Connection conexion;

	public EntradaDao(Connection conexion) {
		this.conexion=conexion;
	}

	//Devuelve el numero de filas afectadas por el select
	public static int insertar(Entrada entrada){
		try{
			//1 interrogacion: numero
			//2 interrogacion:nombre
			//3 interrogacion: localidad
			PreparedStatement stmt = conexion.prepareStatement
					("insert into entradas (id_sala, id_sesion, titulo, fila, asiento, hora, fecha) "
							+ "values(?,?,?,?,?,?,?)");

			//Ahora sustituimos las interrogaciones por el contenido del objeto depart
			stmt.setInt(1,entrada.getId_sala());
			stmt.setInt(2,entrada.getId_sesion());
			stmt.setString(3,entrada.getTitulo());
			stmt.setInt(4,entrada.getFila());
			stmt.setInt(5,entrada.getAsiento());
			stmt.setString(6,entrada.getHora());
			stmt.setTimestamp(7, Utilidades.parsearFechaSQL(entrada.getFecha()));

			//ExecuteUpdate cuando quiero realizar una modificacion en la bbdd
			return stmt.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
	
	public static ArrayList<Entrada> consultarEntradas(){
		//Creo el arrayList de departamentos donde los ire guardando 
		ArrayList<Entrada> entradas = new ArrayList<Entrada>();
		try{
			Statement st=conexion.createStatement();
			//ResultSet: tipo de dato donde se recoge lo que venga de la tabla
			ResultSet rs=st.executeQuery("SELECT * FROM entradas");
			//Automaticamente se coloca en la posicion 0
			//rs.next() devuelve true si hay algo en la siguiente posicion
			while(rs.next()){
				int id_ticket = rs.getInt("id_ticket");
				int id_sala = rs.getInt("id_sala");
				int id_sesion = rs.getInt("id_sesion");
				String titulo = rs.getString("titulo");
				int fila = rs.getInt("fila");
				int asiento = rs.getInt("asiento");
				String hora = rs.getString("hora");
				Date fecha = rs.getDate("fecha");
				
				Entrada entrada = new Entrada(id_ticket, id_sala, id_sesion, titulo, fila, asiento, hora, fecha);
				entradas.add(entrada);
			}
			return entradas;
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	
}



