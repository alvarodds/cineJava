package vista;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;

import controlador.*;
import modelo.*;
import utilidades.Utilidades;

public class MenuPrincipal {
	//Conexiones
	protected static ConexionDao conDao = new ConexionDao();
	protected static SesionDao seDao = new SesionDao(conDao.getConexion());
	protected static PeliculaDao peDao = new PeliculaDao(conDao.getConexion());
	protected static EntradaDao enDao = new EntradaDao(conDao.getConexion());
	protected static SalaDao saDao = new SalaDao(conDao.getConexion());

	//Arrays
	protected static ArrayList<Sesion> sesiones = SesionDao.consultaSesiones();
	protected static ArrayList<Pelicula> peliculas = PeliculaDao.consultarPeliculas();
	protected static ArrayList<Entrada> entradas = EntradaDao.consultarEntradas();
	protected static ArrayList<Sala> salas = SalaDao.consultarSalas();
	protected static ArrayList<Sala> salasSesiones = new ArrayList<Sala>();


	public static void main(String[] args) throws ParseException {

		//Variables generales
		Scanner sc = new Scanner(System.in);
		int opcion = 0;
		boolean ok = false, salir=false;

		while(!salir) {
			System.out.println("1. Administrador");
			System.out.println("2. Taquilla");
			System.out.println("Introduce la opcion que deseas (0 para salir): ");

			try {
				opcion=sc.nextInt();
				ok = true;
			} catch (InputMismatchException e) {
				System.out.println("OPCION INCORRECTA:");
				sc = new Scanner(System.in);
			}

			switch(opcion) {
			case 0: salir = true;;
			break;
			case 1: menuAdministrador();
			break;
			case 2: menuTaquilla();
			break;
			}
		}


	}
	//SECCION TAQUILLA
	private static void menuTaquilla() throws ParseException {
		//Variables generales
		Scanner sc = new Scanner(System.in);
		Scanner scNumeros = new Scanner(System.in);
		int opcion = 0;
		boolean ok = false, salir=false;

		while(!salir) {
			System.out.println("");
			System.out.println("1. Comprar entradas");
			System.out.println("Introduce la opcion que deseas (0 para salir): ");

			try {
				opcion=scNumeros.nextInt();
				ok = true;
			} catch (InputMismatchException e) {
				System.out.println("OPCION INCORRECTA");
				scNumeros = new Scanner(System.in);
			}

			switch(opcion) {
			case 0: salir = true;;
			break;
			case 1: 
				comprarEntradas();
				break;
			}
		}
	}

	private static void comprarEntradas() throws ParseException {
		//Variables generales
		Scanner sc = new Scanner(System.in);
		Scanner scNumeros = new Scanner(System.in);
		int opcion = 0;
		boolean ok = false, salir=false;

		while(!salir) {
			System.out.println("");
			System.out.println("1. Por titulo");
			System.out.println("2. Por fecha y hora");
			System.out.println("Introduce la opcion que deseas (0 para salir): ");

			try {
				opcion=scNumeros.nextInt();
				ok = true;
			} catch (InputMismatchException e) {
				System.out.println("OPCION INCORRECTA");
				scNumeros = new Scanner(System.in);
			}

			switch(opcion) {


			case 0: salir = true;;
			break;
			case 1:
				boolean asientoOk = false;
				boolean sesionOk = false;
				//ArrayList Para luego mostrar las entradas
				ArrayList <Entrada> entradasCompradas = new ArrayList<Entrada>();

				System.out.println("Dime el titulo de la pelicula que quieras buscar: ");
				String titulo = sc.nextLine();
				System.out.println("");

				ArrayList<Sesion> sesionesTitulo = SesionDao.consultaSesionesPorTitulo(titulo);

				if(mostrarSesiones(sesionesTitulo)) {
					System.out.println();
					System.out.println("Elige la sesion que quieras comprar entradas: ");
					int num = scNumeros.nextInt();


					while(num <= 0 || num > sesionesTitulo.size()) {
						System.out.println("No existe esa sesión. Elige una sesión válida: ");
						num = scNumeros.nextInt();
					}


					pintarSala(entradas, dimensionesSala(num-1), sesiones.get(num-1));

					System.out.println("Dime la fila y el asiento en el que quieras sentarte: ");
					int fila = scNumeros.nextInt();
					int asiento = scNumeros.nextInt();

					while(asientoEncontrado(fila, asiento, sesiones.get(num-1))) {
						System.out.println("El asiento esta ocupado, elija otro:");
						fila = scNumeros.nextInt();
						asiento = scNumeros.nextInt();
					}

					System.out.println("¿Cuantos asientos contiguos desea?: ");
					int contiguos = scNumeros.nextInt();

					if(contiguos >0) {
						while(!asientoOk) {
							if(asiento - contiguos <= 0) {
								System.out.println("No existen esos asientos, seleccione otros:");
								contiguos = scNumeros.nextInt();
							}else if(comprobarContiguos(fila, asiento, contiguos, sesiones.get(num-1).getId_sesion())) {
								System.out.println("Asientos ocupados, seleccione otros:");
								contiguos = scNumeros.nextInt();
							}else {
								asientoOk = true;
							}
						}
						int totalAsientos = asiento - contiguos;
						//los copio porque uso asiento de contador
						int auxAsientos = asiento;
						while(asiento >= totalAsientos) {
							//0 porque el id del ticket es auto increment y nos da igual el que pongamos
							Entrada entrada = new Entrada(0, sesiones.get(num-1).getId_sala(), sesiones.get(num-1).getId_sesion(),
									sesiones.get(num-1).getNombre_pelicula(), fila, asiento, sesiones.get(num-1).getHora(), sesiones.get(num-1).getFecha());
							enDao.insertar(entrada);
							entradasCompradas.add(entrada);
							asiento--;
						}
						asiento = auxAsientos;
					}else {

						while(!asientoOk) {
							if(asiento - contiguos <= 0) {
								System.out.println("No existen esos asientos, seleccione otros:");
								contiguos = scNumeros.nextInt();
							}else {
								asientoOk = true;
							}
						}

						Entrada entrada = new Entrada(0, sesiones.get(num-1).getId_sala(), sesiones.get(num-1).getId_sesion(),
								sesiones.get(num-1).getNombre_pelicula(), fila, asiento, sesiones.get(num-1).getHora(), sesiones.get(num-1).getFecha());
						enDao.insertar(entrada);
						entradasCompradas.add(entrada);


					}
					entradas = EntradaDao.consultarEntradas();
					//Volvemos a pintar la sala
					pintarSala(entradas, dimensionesSala(num-1), sesiones.get(num-1));
					//imprimimos los tickets
					mostrarTickets(entradasCompradas);

					menuTaquilla();
				}else {
					menuTaquilla();
				}

				break;
			case 2: 
				ArrayList<Sesion> haySesiones = new ArrayList<Sesion>();
				//ArrayList Para luego mostrar las entradas
				entradasCompradas = new ArrayList<Entrada>();
				asientoOk = false;
				sesionOk = false;
				Date fech;

				System.out.println("Dime la fecha a la que quieras asistir al cine: ");
				System.out.println("Ej 06/05/2020 ");
				String fecha = sc.nextLine();

				fech = Utilidades.parsearFechaString(fecha);

				System.out.println("Dime la hora a la que quieras la sesión: ");
				String hora = sc.nextLine();
				System.out.println();

				haySesiones = consultarSesionesFechaHora(Utilidades.formatearFechaEuropeo(fech), hora);

				if(haySesiones.size() > 0) {
					System.out.println();
					System.out.println("Elige la sesion que quieras comprar entradas: ");
					int num = scNumeros.nextInt();


					while(num <= 0 || num > haySesiones.size()) {
						System.out.println("No existe esa sesión. Elige una sesión válida: ");
						num = scNumeros.nextInt();
					}


					pintarSala(entradas, dimensionesSala(num-1), haySesiones.get(num-1));

					System.out.println("Dime la fila y el asiento en el que quieras sentarte: ");
					int fila = scNumeros.nextInt();
					int asiento = scNumeros.nextInt();

					while(asientoEncontrado(fila, asiento, haySesiones.get(num-1))) {
						System.out.println("El asiento esta ocupado, elija otro:");
						fila = scNumeros.nextInt();
						asiento = scNumeros.nextInt();
					}

					System.out.println("¿Cuantos asientos contiguos desea?: ");
					int contiguos = scNumeros.nextInt();

					if(contiguos >0) {
						while(!asientoOk) {
							if(asiento - contiguos <= 0) {
								System.out.println("No existen esos asientos, seleccione otros:");
								contiguos = scNumeros.nextInt();
							}else if(comprobarContiguos(fila, asiento, contiguos, haySesiones.get(num-1).getId_sesion())) {
								System.out.println("Asientos ocupados, seleccione otros:");
								contiguos = scNumeros.nextInt();
							}else {
								asientoOk = true;
							}
						}
						int totalAsientos = asiento - contiguos;
						//los copio porque uso asiento de contador
						int auxAsientos = asiento;
						while(asiento >= totalAsientos) {
							//0 porque el id del ticket es auto increment y nos da igual el que pongamos
							Entrada entrada = new Entrada(0, haySesiones.get(num-1).getId_sala(), haySesiones.get(num-1).getId_sesion(),
									haySesiones.get(num-1).getNombre_pelicula(),fila, asiento, haySesiones.get(num-1).getHora(), haySesiones.get(num-1).getFecha());
							enDao.insertar(entrada);
							entradasCompradas.add(entrada);
							asiento--;
						}
						asiento = auxAsientos;
					}else {
						while(!asientoOk) {
							if(asiento - contiguos <= 0) {
								System.out.println("No existen esos asientos, seleccione otros:");
								contiguos = scNumeros.nextInt();
							}else {
								asientoOk = true;
							}
						}

						Entrada entrada = new Entrada(0, haySesiones.get(num-1).getId_sala(), haySesiones.get(num-1).getId_sesion(),
								haySesiones.get(num-1).getNombre_pelicula(), fila, asiento, haySesiones.get(num-1).getHora(), haySesiones.get(num-1).getFecha());
						enDao.insertar(entrada);
						entradasCompradas.add(entrada);


					}
					entradas = EntradaDao.consultarEntradas();
					//Volvemos a pintar la sala
					pintarSala(entradas, dimensionesSala(num-1), haySesiones.get(num-1));

					//imprimimos los tickets
					mostrarTickets(entradasCompradas);
					menuTaquilla();
				}else {
					menuTaquilla();
				}
				break;
			}
		}
	}

	private static ArrayList<Sesion> consultarSesionesFechaHora(String fecha, String hora) {
		ArrayList<Sesion> haySesiones = new ArrayList<Sesion>();
		//contador para solo mostrar 5 pelis
		int contador = 0;

		if(sesiones.size() > 0) {
			for (int i = 0; i < sesiones.size(); i++) {
				if(Utilidades.formatearFechaEuropeo(sesiones.get(i).getFecha()).equals(fecha) && 
						sesiones.get(i).getHora().equals(hora) && contador < 5) {
					haySesiones.add(sesiones.get(i));
					System.out.println(i+1 + ": " + sesiones.get(i).toString());
					contador++;
				}
			}
			System.out.println();
		}
		return haySesiones;
	}

	private static void mostrarTickets(ArrayList<Entrada> entradasCompradas) {
		System.out.println("-------------------------------------------------------------");

		for (int i = 0; i < entradasCompradas.size(); i++) {
			System.out.println("PELICULA: " + entradasCompradas.get(i).getTitulo());
			System.out.println("FILA: " + entradasCompradas.get(i).getFila());
			System.out.println("ASIENTO: " + entradasCompradas.get(i).getAsiento());
			System.out.println("FECHA: " + entradasCompradas.get(i).getFecha());
			System.out.println("HORA: " + entradasCompradas.get(i).getHora());
			System.out.println("SALA: " + entradasCompradas.get(i).getId_sala());
			System.out.println("-------------------------------------------------------------");

		}



	}

	private static boolean comprobarContiguos(int fila, int asiento, int contiguos, int id_sesion) {

		if(entradas.size() > 0) {
			for (int j = asiento-1; j <= contiguos; j--) {
				for (int i = 0; i < entradas.size(); i++) {
					if(entradas.get(i).getFila() == fila &&
							entradas.get(i).getAsiento() == entradas.get(j).getAsiento() &&
							entradas.get(i).getId_sesion() == entradas.get(j).getId_sesion()) {
						return true;
					}
				}
			}
		}

		return false;
	}

	private static int columnasSala(int id_sala) {
		for (int j = 0; j < salas.size(); j++) {
			if(salas.get(j).getId_sala() == id_sala) {
				return salas.get(j).getColumna();
			}
		}

		return -1;
	}

	private static Sala dimensionesSala(int num) {
		return salas.get(num);
	}

	private static void pintarSala(ArrayList<Entrada> entradas, Sala sala, Sesion sesion) {
		boolean existeEntradasSesion = existeEntradaSesion(entradas,sesion);

		System.out.print("       ");
		for (int i = sala.getColumna(); i >= 1 ; i--) {
			if(i > 9) {
				System.out.print(" -"+i+"-");
			}else {
				System.out.print(" -"+i+"- ");
			}
		}
		System.out.println("       ");

		System.out.println();

		for (int i = sala.getFila(); i >= 1; i--) {
			System.out.print("FILA " + i);
			if(i > 9) {
				System.out.print(" ");
			}else {
				System.out.print("  ");
			}
			if(existeEntradasSesion) {
				for (int j = sala.getColumna(); j >= 1; j--) {
					if(asientoEncontrado(i, j, sesion)) {
						System.out.print("*** ");
					}else {
						System.out.print("    ");
					}
					System.out.print("|");

				}
			}else {
				for (int j = 0; j < sala.getColumna(); j++) {
					System.out.print("    ");
					System.out.print("|");
				}
			}

			System.out.println("       ");
			System.out.print("       ");
			for (int j = sala.getColumna(); j >= 1 ; j--) {
				System.out.print("_ _ _");
			}
			System.out.println("       ");
			System.out.println();
		}

	}

	private static boolean existeEntradaSesion(ArrayList<Entrada> entradas, Sesion sesion) {

		if(entradas.size() > 0) {
			for (int i = 0; i < entradas.size(); i++) {
				if(entradas.get(i).getId_sesion() == sesion.getId_sesion()) {
					return true;
				}
			}
		}

		return false;

	}

	private static boolean asientoEncontrado(int fila, int asiento, Sesion sesion) {
		if(entradas.size() > 0) {
			for (int i = 0; i < entradas.size(); i++) {
				if(entradas.get(i).getFila() == fila &&
						entradas.get(i).getAsiento() == asiento &&
						entradas.get(i).getId_sesion() == sesion.getId_sesion()) {
					return true;
				}
			}
		}
		return false;
	}

	private static boolean mostrarSesiones(ArrayList<Sesion> sesionesTitulo) {
		if(sesionesTitulo.size() > 0) {
			if(sesionesTitulo.size() < 5) {
				for (int i = 0; i < sesionesTitulo.size(); i++) {
					System.out.println(i+1 + ": " + sesionesTitulo.get(i).toString());
				}
			}else {
				for (int i = 0; i < 5; i++) {
					System.out.println(i+1 + ": " + sesionesTitulo.get(i).toString());
				}
			}
		}else {
			System.out.println("No hay informacion disponible");
			return false;
		}
		return true;
	}

	//SECCION ADMINISTRADOR
	private static void menuAdministrador() throws ParseException {
		//Variables generales
		Scanner sc = new Scanner(System.in);
		Scanner scNumeros = new Scanner(System.in);
		int opcion = 0;
		boolean ok = false, salir=false;

		while(!salir) {
			System.out.println("");
			System.out.println("1. Añadir película");
			System.out.println("2. Visualizar peliculas");
			System.out.println("3. Eliminar película");
			System.out.println("4. Crear sesión");
			System.out.println("5. Visualizar sesiones");
			System.out.println("6. Eliminar sesión");
			System.out.println("Introduce la opcion que deseas (0 para salir): ");

			try {
				opcion=scNumeros.nextInt();
				ok = true;
			} catch (InputMismatchException e) {
				System.out.println("OPCION INCORRECTA");
				scNumeros = new Scanner(System.in);
			}

			//Cada case del seitch nos lleva a un metodo distinto
			switch(opcion) {

			case 0: salir = true;
			break;
			case 1: 
				Pelicula pelicula =  new Pelicula();
				pelicula.crearPelicula();

				//si devuelve true, ya existe el titulo de la pelicula
				if(comprobarPelicula(pelicula.getTitulo())) {
					System.out.println("Ya existe una pelicula con ese titulo");
				}else {
					int filasAfectadas= peDao.insertar(pelicula);
					if(filasAfectadas==1) {
						System.out.println("Pelicula añadida con exito");
						peliculas = PeliculaDao.consultarPeliculas();
					}else {
						System.out.println("Error al añadir la película.");
					}
				}

				break;
			case 2: 
				visualizarPeliculas();
				break;
			case 3:
				visualizarPeliculas();

				System.out.println("Introduce el titulo de la pelicula que quieras eliminar: ");
				String titulo_pelicula= sc.nextLine();

				//si devuelve true, ya existe el titulo de la pelicula y la podemos eliminar
				if(comprobarPelicula(titulo_pelicula)) {
					int filasAfectadas= peDao.eliminarPelicula(titulo_pelicula);
					if(filasAfectadas==1) {
						System.out.println("Pelicula eliminada con exito");
						eliminarPelicula(titulo_pelicula);
					}else {
						System.out.println("Error al eliminar la película.");
					}
				}else {
					System.out.println("No existe esa película");
				}
				break;
			case 4:
				Sesion sesion = new Sesion();

				System.out.println("¿Que pelicula quieres añadir para la sesión?(Selecciona el número de la lista)");
				for (int i = 0; i < peliculas.size(); i++) {
					System.out.print(i+1 + ": ");
					System.out.println(peliculas.get(i).toString());
				}
				int num_pelicula= sc.nextInt();
				num_pelicula = num_pelicula-1;


				while(num_pelicula < 0 || num_pelicula >= peliculas.size()) {
					System.out.println("SELECCION ERRONEA");
					System.out.println("¿Que pelicula quieres añadir para la sesión?");
					num_pelicula= sc.nextInt();
					num_pelicula = num_pelicula-1;
				}

				sesion.crearSesion(cogerPelicula(num_pelicula));

				
				if(existeSesion(sesion)) {
					System.out.println("Ya existe una sesión en esta fecha y hora o se va a reproducir una pelicula a esas horas");
				}else {
					int filasAfectadas= seDao.insertar(sesion);
					if(filasAfectadas==1) {
						System.out.println("Sesion añadida con exito");
						sesiones = SesionDao.consultaSesiones();
					}else {
						System.out.println("Error al añadir la sesión.");
					}

				}
				 
				break;
			case 5:
				visualizarSesiones();
				break;
			case 6:
				visualizarSesiones();

				System.out.println("Dime el id de la sesión quieres eliminar");
				for (int i = 0; i < sesiones.size(); i++) {
					sesiones.get(i).toString();
				}
				int num= sc.nextInt();

				if(comprobarSesion(num)) {
					int filasAfectadas= seDao.eliminarSesion(num);
					if(filasAfectadas==1) {
						System.out.println("Sesión eliminada con exito");
						eliminarSesion(num);
					}else {
						System.out.println("Error al eliminar la sesión.");

					}


					break;
				}
			}
		}
	}

	private static boolean comprobarHoraSesion(Sesion sesion) {
		String horaSesion = sesion.getHora();
		int nuevaSesion = 0;

		int hora = Integer.parseInt(horaSesion.substring(0,1));
		int minutos = Integer.parseInt(horaSesion.substring(3,4));


		nuevaSesion = (hora * 60) + minutos + sesion.getDuracion();


		return false;
	}

	private static boolean comprobarPelicula(String pelicula) {
		for (int i = 0; i < peliculas.size(); i++) {
			if(peliculas.get(i).getTitulo().equals(pelicula)) {
				return true;
			}
		}
		return false;
	}

	private static void eliminarPelicula(String pelicula) {
		for (int i = 0; i < peliculas.size(); i++) {
			if(peliculas.get(i).getTitulo().equals(pelicula)) {
				peliculas.remove(i);
			}
		}
	}

	private static Pelicula cogerPelicula(int numPelicula) {
		return peliculas.get(numPelicula);
	}

	private static void visualizarPeliculas() {
		for (int i = 0; i < peliculas.size(); i++) {
			System.out.println(peliculas.get(i).toString());
		}
	}

	private static void eliminarSesion(int id_sesion) {
		for (int i = 0; i < sesiones.size(); i++) {
			if(sesiones.get(i).getId_sesion() == id_sesion) {
				sesiones.remove(i);
			}
		}
	}

	private static boolean comprobarSesion(int id_sesion) {
		for (int i = 0; i < sesiones.size(); i++) {
			if(sesiones.get(i).getId_sesion() == id_sesion) {
				return true;
			}
		}
		return false;
	}

	private static void visualizarSesiones() {
		for (int i = 0; i < sesiones.size(); i++) {
			System.out.println(sesiones.get(i).toString());
		}
	}

	private static boolean existeSesion(Sesion sesion) {
		int nuevaSesion = 0;
		boolean ok = false;

		for (int i = 0; i < sesiones.size(); i++) {
			if(sesiones.get(i).getId_sala() == sesion.getId_sala()) {
				if(sesiones.get(i).getFecha().equals(sesion.getFecha())) {
					if(sesiones.get(i).getHora().equals(sesion.getHora())) {
						ok =  true;

					}else {
						//sesiones
						int hora = Integer.parseInt(sesiones.get(i).getHora().substring(0,2));
						int minutos = Integer.parseInt(sesiones.get(i).getHora().substring(4,5));
						int actualSesionMayor = (hora * 60) + minutos + sesiones.get(i).getDuracion();


						//nueva sesion
						hora = Integer.parseInt(sesion.getHora().substring(0,2));
						minutos = Integer.parseInt(sesion.getHora().substring(3,5));
						nuevaSesion = (hora * 60) + minutos;

						if(actualSesionMayor <=  nuevaSesion && sesiones.get(i).getId_sala() == sesion.getId_sala() && !ok) {
							ok =  false;
						}else {
							ok = true;
						}
					}
				}
			}
		}
		return ok;
	}

}
