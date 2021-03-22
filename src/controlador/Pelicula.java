package controlador;

import java.sql.Time;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

import utilidades.Utilidades;

public class Pelicula {
	private int id_pelicula;
	private String titulo;
	private int duracion;
	private String sinopsis;
	private Date fecha_estreno;
	private Date fecha_finalizacion;

	public Pelicula(int id_pelicula, String titulo, int duracion, String sinopsis, Date fecha_estreno, Date fecha_finalizacion) {
		this.id_pelicula = id_pelicula;
		this.titulo = titulo;
		this.duracion = duracion;	
		this.sinopsis = sinopsis;
		this.fecha_estreno = fecha_estreno;
		this.fecha_finalizacion = fecha_finalizacion;
	}

	public Pelicula() {}

	public void crearPelicula() throws ParseException {
		String fecha = null;
		Scanner pk = new Scanner(System.in);
		Scanner pkNumeros = new Scanner(System.in);

		//id pelicula es auto_increment

		System.out.println("Introduzca el titulo de la pelicula: ");
		this.titulo = pk.nextLine();
		
		System.out.println("Introduzca la duracion de la pelicula: ");
		this.duracion = pkNumeros.nextInt();

		while(this.duracion <= 0 || this.duracion > 300) {
			System.out.println("Duración incorrecta. Introduzca de nuevo la duracion de la pelicula: ");
			this.duracion = pkNumeros.nextInt();
		}

		System.out.println("Introduzca la sinopsis de la pelicula: ");
		this.sinopsis = pk.nextLine();
		

		System.out.println("Introduzca la fecha de estreno: ");
		System.out.println("Ej. 06/05/2020");
		fecha = pk.nextLine();
		this.fecha_estreno = Utilidades.parsearFechaString(fecha);
		
		System.out.println("Introduzca la fecha de finalizacion: ");
		System.out.println("Ej. 14/05/2020");
		fecha = pk.nextLine();
		this.fecha_finalizacion = Utilidades.parsearFechaString(fecha);
	}

	@Override
	public String toString() {
		return "Pelicula [titulo=" + titulo + ", duracion=" + duracion + ", sinopsis=" + sinopsis + ", fecha estreno="
				+ fecha_estreno + ", fecha finalizacion=" + fecha_finalizacion + "]";
	}
	
	public int getId_pelicula() {
		return id_pelicula;
	}

	public void setId_pelicula(int id_pelicula) {
		this.id_pelicula = id_pelicula;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getDuracion() {
		return duracion;
	}

	public void setDuracion(int duracion) {
		this.duracion = duracion;
	}

	public String getSinopsis() {
		return sinopsis;
	}

	public void setSinopsis(String sinopsis) {
		this.sinopsis = sinopsis;
	}

	public Date getFecha_estreno() {
		return fecha_estreno;
	}

	public void setFecha_estreno(Date fecha_estreno) {
		this.fecha_estreno = fecha_estreno;
	}

	public Date getFecha_finalizacion() {
		return fecha_finalizacion;
	}

	public void setFecha_finalizacion(Date fecha_finalizacion) {
		this.fecha_finalizacion = fecha_finalizacion;
	}


}
