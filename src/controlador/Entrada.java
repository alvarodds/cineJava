package controlador;

import java.util.Date;

public class Entrada {
	private int id_ticket;
	private int id_sala;
	private int id_sesion;
	private String titulo;
	private int fila;
	private int asiento;
	private String hora;
	private Date fecha;
	
	public Entrada(int id_ticket, int id_sala, int id_sesion, String titulo, int fila, int asiento, String hora, Date fecha) {
		this.id_ticket = id_ticket;
		this.id_sala = id_sala;
		this.id_sesion = id_sesion;
		this.titulo = titulo;
		this.fila = fila;
		this.asiento = asiento;
		this.hora = hora;
		this.fecha = fecha;
	}
	
	public Entrada() {}
	

	@Override
	public String toString() {
		return "Entrada [id_ticket=" + id_ticket + ", id_sala=" + id_sala + ", id_sesion=" + id_sesion + ", fila="
				+ fila + ", asiento=" + asiento + ", hora=" + hora + ", fecha=" + fecha + "]";
	}

	public int getId_ticket() {
		return id_ticket;
	}

	public void setId_ticket(int id_ticket) {
		this.id_ticket = id_ticket;
	}

	public int getId_sala() {
		return id_sala;
	}

	public void setId_sala(int id_sala) {
		this.id_sala = id_sala;
	}

	public int getId_sesion() {
		return id_sesion;
	}

	public void setId_sesion(int id_sesion) {
		this.id_sesion = id_sesion;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getAsiento() {
		return asiento;
	}

	public void setAsiento(int asiento) {
		this.asiento = asiento;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	
	
	
}
