package modelo;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//DAO data access object
public class ConexionDao {
	public static Connection conexion;
	
	//Constructor
	public ConexionDao(){
		ConexionDao.conexion=conexion();
	}
	
	public static Connection getConexion() {
		return conexion;
	}
	public static void setConexion(Connection conexion) {
		ConexionDao.conexion = conexion;
	}

public static Connection conexion(){
		try {
			//Cargamos el driver para conectar con mysql
			Class.forName("com.mysql.jdbc.Driver");
			//Creamos la conexion utilizando la base de datos
			//Seguido de el usuario y la contrase�a para acceder
	        Connection 
	        		con=DriverManager.getConnection
	        		("jdbc:mysql://localhost/cine","root","qwerty123");
	        System.out.println("Conexion con base de datos realizada.");
			return con;
			
		} catch (ClassNotFoundException e) {
			System.out.println("Clase mysql no encontrada.");
		} catch (SQLException e) {
			System.out.println("Error en la conexion.");
		}
		return null;
	}
	
	/**
	 * CONEXION PARA VERSION 8
	 * @return
	 */
	/**
	public static Connection conexion() {
		try {
			//Busca la clase correcpondiente en las librerias importadas
			Class.forName("com.mysql.cj.jdbc.Driver");
			//Localhost : La base de datos es local, sino pondriamos una ip
			//3306: es el puerto de mysql
			//proyecto_empleados : nombre del proyecto
			//serverTimezone=UTC : uso horario de la bbdd
			//1: usuario
			//2: contrase�a
			Connection con=DriverManager.getConnection
					("jdbc:mysql://localhost:3306/proyecto_empleados?serverTimezone=UTC","root","1234");
			System.out.println("Conexion con base de datos realizada");
			return con;
			
		} catch (ClassNotFoundException e) {
			//Entra a este catch si no se encuentra la clase
			System.err.println("Clase mysql no encontrada");
		} catch (SQLException e) {
			System.err.println("Error en la conexion");
		}
		return null;
	}
	**/
	
}







