package controlador;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import utilidades.Utilidades;

public class Sesion {
	private int id_sesion;
	private int id_sala;
	private int numSala;
	private String nombre_pelicula;
	private Date fecha;
	private String hora;
	private int duracion;
	
	public Sesion(int id_sesion, int id_sala, String nombre_pelicula, Date fecha, String hora, int duracion) {
		this.id_sesion = id_sesion;
		this.id_sala = id_sala;
		this.fecha = fecha;
		this.hora = hora;	
		this.nombre_pelicula = nombre_pelicula;
		this.duracion = duracion;
	}
	
	public Sesion() {}

	public void crearSesion(Pelicula pelicula) throws ParseException {
		String fecha = null;
		Scanner pk = new Scanner(System.in);
		Scanner pkNumeros = new Scanner(System.in);
		
		//No hace falta introducir el indice de la sesion porque es auto_increment
		System.out.println("Introduzca en que sala donde quieres que se reproduzca la pelicula: ");
		this.id_sala = pkNumeros.nextInt();
		
		while(this.id_sala < 1 || this.id_sala > 4) {
			System.out.println("NUMERO DE SALA NO VALIDO. Introduzca en que sala quieres que se reproduzca la pelicula: ");
			this.id_sala = pkNumeros.nextInt();
		}
	
		System.out.println("Introduzca la fecha: ");
		System.out.println("Ej. 08/07/2020");
		fecha = pk.nextLine();
		this.fecha = Utilidades.parsearFechaString(fecha);
		
		System.out.println("Introduzca la hora: ");
		this.hora = pk.nextLine();
		
		this.nombre_pelicula = pelicula.getTitulo();
		this.duracion = pelicula.getDuracion();
	}
	
	public String toString() {
		return "Sesion [Id sesion=" + id_sesion + ", Nombre pelicula=" + nombre_pelicula + ", Sala =" + id_sala
				+ ", Fecha=" + fecha + ", Hora=" + hora +  ", Duracion=" + duracion + " ]";
	}

	public int getId_sesion() {
		return id_sesion;
	}

	public void setId_sesion(int id_sesion) {
		this.id_sesion = id_sesion;
	}

	public int getId_sala() {
		return id_sala;
	}

	public void setId_sala(int id_sala) {
		this.id_sala = id_sala;
	}

	public String getNombre_pelicula() {
		return nombre_pelicula;
	}

	public void setNombre_pelicula(String nombre_pelicula) {
		this.nombre_pelicula = nombre_pelicula;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}
}
