package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.sql.*;

import controlador.Sesion;
import utilidades.Utilidades;


public class SesionDao {
	public static Connection conexion;

	public SesionDao(Connection conexion) {
		this.conexion=conexion;
	}

	//Devuelve el numero de filas afectadas por el select
	public static int insertar(Sesion sesion){
		try{
			//1 interrogacion: numero
			//2 interrogacion:nombre
			//3 interrogacion: localidad
			PreparedStatement stmt = conexion.prepareStatement
					("insert into sesiones (id_sala, nombre_pelicula, fecha, hora, duracion) values(?,?,?,?,?)");

			//No id_sesion porque es autoincrement
			stmt.setInt(1, sesion.getId_sala());
			stmt.setString(2,sesion.getNombre_pelicula());
			stmt.setTimestamp(3, Utilidades.parsearFechaSQL(sesion.getFecha()));
			stmt.setString(4, sesion.getHora());
			stmt.setInt(5, sesion.getDuracion());

			//ExecuteUpdate cuando quiero realizar una modificacion en la bbdd
			return stmt.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}

	public static int eliminarSesion(int id_sesion) {
		try{
			PreparedStatement stmt = conexion.prepareStatement
					("DELETE FROM sesiones where id_sesion= ?");

			stmt.setInt(1, id_sesion);
			return stmt.executeUpdate();

		} catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
	
	public static ArrayList<Sesion> consultaSesiones(){
		//Creo el arrayList de departamentos donde los ire guardando 
		ArrayList<Sesion> sesiones = new ArrayList<Sesion>();
		try{
			Statement st=conexion.createStatement();
			//ResultSet: tipo de dato donde se recoge lo que venga de la tabla
			ResultSet rs=st.executeQuery("SELECT * FROM sesiones");
			//Automaticamente se coloca en la posicion 0
			//rs.next() devuelve true si hay algo en la siguiente posicion
			while(rs.next()){
				//Entre comillas va EL NOMBRE DE LA COLUMNA EN LA BBDD
				int id_sesion=rs.getInt("id_sesion");
				int id_sala=rs.getInt("id_sala");
				String nombre_pelicula=rs.getString("nombre_pelicula");
				Date fecha = rs.getDate("fecha");
				String hora = rs.getString("hora");
				int duracion=rs.getInt("duracion");
				Sesion sesion = new Sesion(id_sesion, id_sala, nombre_pelicula, fecha, hora, duracion);
				//Guardo en el array de departamentos el que acabo de crear
				sesiones.add(sesion);
			}
			return sesiones;
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	static public ArrayList<Sesion> consultaSesionesPorTitulo(String titulo_pelicula){
		//Creo el arrayList de departamentos donde los ire guardando 
		ArrayList<Sesion> sesiones = new ArrayList<Sesion>();
		try{
			PreparedStatement st = conexion.prepareStatement("SELECT * FROM sesiones where nombre_pelicula = ?");
			st.setString(1, titulo_pelicula);
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()){
				int id_sesion=rs.getInt("id_sesion");
				int id_sala=rs.getInt("id_sala");
				String nombre_pelicula=rs.getString("nombre_pelicula");
				Date fecha = rs.getDate("fecha");
				String hora = rs.getString("hora");
				int duracion=rs.getInt("duracion");
				Sesion sesion = new Sesion(id_sesion, id_sala, nombre_pelicula, fecha, hora, duracion);
				//Guardo en el array de departamentos el que acabo de crear
				sesiones.add(sesion);
			}
			return sesiones;
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}

	static public ArrayList<Sesion> consultaSesionesPorFechaHora(String fecha, String hora) throws ParseException{
		//Creo el arrayList de departamentos donde los ire guardando 
		ArrayList<Sesion> sesiones = new ArrayList<Sesion>();
		try{
			PreparedStatement st = conexion.prepareStatement("SELECT * FROM sesiones "
					+ "where fecha = ? and hora = ?");
			st.setTimestamp(3, Utilidades.parsearFechaSQL(fecha));
			st.setString(2, hora);
			
			ResultSet rs=st.executeQuery();
			
			while(rs.next()){
				int id_sesion=rs.getInt("id_sesion");
				int id_sala=rs.getInt("id_sala");
				String nombre_pelicula=rs.getString("nombre_pelicula");
				Date fecha_sesion = rs.getDate("fecha");
				String hora_sesion = rs.getString("hora");
				int duracion=rs.getInt("duracion");
				Sesion sesion = new Sesion(id_sesion, id_sala, nombre_pelicula, fecha_sesion , hora_sesion , duracion);
				//Guardo en el array de departamentos el que acabo de crear
				sesiones.add(sesion);
			}
			return sesiones;
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}
}



