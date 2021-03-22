package controlador;

public class Sala {
	private int id_sala;
	private int fila;
	private int columna;
	
	public Sala(int id_sala, int fila, int columna) {
		this.id_sala = id_sala;
		this.fila = fila;
		this.columna = columna;
	}
	
	public Sala() {}

	public String toString() {
		return "Sala [Id sala=" + id_sala + ", Fila=" + fila + ", Columna=" + columna + "]";
	}

	public int getId_sala() {
		return id_sala;
	}

	public void setId_sala(int id_sala) {
		this.id_sala = id_sala;
	}

	public int getFila() {
		return fila;
	}

	public void setFila(int fila) {
		this.fila = fila;
	}

	public int getColumna() {
		return columna;
	}

	public void setColumna(int columna) {
		this.columna = columna;
	}
	
}
