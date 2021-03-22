package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;


import controlador.Pelicula;
import utilidades.Utilidades;


public class PeliculaDao {
	public static Connection conexion;

	public PeliculaDao(Connection conexion) {
		this.conexion=conexion;
	}

	//Devuelve el numero de filas afectadas por el select
	public static int insertar(Pelicula pelicula){
		try{
			//1 interrogacion: numero
			//2 interrogacion:nombre
			//3 interrogacion: localidad
			PreparedStatement stmt = conexion.prepareStatement
					("insert into peliculas (titulo, duracion, sinopsis, fecha_estreno, fecha_finalizacion) "
							+ "values(?,?,?,?,?)");

			//Ahora sustituimos las interrogaciones por el contenido del objeto depart
			stmt.setString(1,pelicula.getTitulo());
			stmt.setInt(2,pelicula.getDuracion());
			stmt.setString(3,pelicula.getSinopsis());
			stmt.setTimestamp(4, Utilidades.parsearFechaSQL(pelicula.getFecha_estreno()));
			stmt.setTimestamp(5, Utilidades.parsearFechaSQL(pelicula.getFecha_finalizacion()));

			//ExecuteUpdate cuando quiero realizar una modificacion en la bbdd
			return stmt.executeUpdate();
		} catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}

	public static int eliminarPelicula(String pelicula) {
		try{
			//1 interrogacion: numero
			//2 interrogacion:nombre
			//3 interrogacion: localidad
			PreparedStatement stmt = conexion.prepareStatement
					("DELETE FROM peliculas where titulo= ?");

			stmt.setString(1, pelicula);
			//ResultSet: tipo de dato donde se recoge lo que venga de la tabla
			return stmt.executeUpdate();

		} catch(SQLException e){
			e.printStackTrace();
			return -1;
		}
	}
	
	public static ArrayList<Pelicula> consultarPeliculas(){
		//Creo el arrayList de departamentos donde los ire guardando 
		ArrayList<Pelicula> peliculas = new ArrayList<Pelicula>();
		try{
			Statement st=conexion.createStatement();
			//ResultSet: tipo de dato donde se recoge lo que venga de la tabla
			ResultSet rs=st.executeQuery("SELECT * FROM peliculas");
			//Automaticamente se coloca en la posicion 0
			//rs.next() devuelve true si hay algo en la siguiente posicion
			while(rs.next()){
				//Entre comillas va EL NOMBRE DE LA COLUMNA EN LA BBDD
				int id_pelicula=rs.getInt("id_pelicula");
				String titulo=rs.getString("titulo");
				int duracion=rs.getInt("duracion");
				String sinopsis=rs.getString("sinopsis");
				Date fecha_estreno = rs.getDate("fecha_estreno");
				Date fecha_finalizacion = rs.getDate("fecha_finalizacion");
				Pelicula pelicula = new Pelicula(id_pelicula, titulo, duracion, sinopsis, fecha_estreno, fecha_finalizacion);
				//Guardo en el array de departamentos el que acabo de crear
				peliculas.add(pelicula);
			}
			return peliculas;
		}catch (SQLException e){
			e.printStackTrace();
			return null;
		}
	}
}



