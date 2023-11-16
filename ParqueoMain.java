
/** Juan Francisco Martínez 23617, Andrés Mazariegos 21749,  Daniela Ramirez 23053,  Antony Lou Schanwk 23410

  * ParqueoMain
 
  * @param ingresovehiculos,salidavehiculos,registrarresidente,informe,cargardatosdeloscsv
  * @throws Es el main driver del programa, en este se encuentra el menú de opciones para la persona encargada del parqueo, aparte de toda la lógica y funcionamiento del programa

  */
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.Iterator;
import java.util.Optional;

//se importan todas las librerías necesarias

public class ParqueoMain {
    private static List<Movimiento> movimientos = new ArrayList<>();// array del csv de movimientos
    private static List<Residente> residentes = new ArrayList<>();// array del csv de residentes

    public static void main(String[] args) {// inicio del main driver
        Scanner scanner = new Scanner(System.in);
        cargarMovimientosDesdeCSV();// funcion para cargar los datos del CSV de movimientos siempre que se inicie el
                                    // programa
        while (true) {// inicio del switch para el menú de opciones
            System.out.println("Seleccione una opción:");
            System.out.println("1. Ingreso de un vehículo");
            System.out.println("2. Salida de un vehículo");
            System.out.println("3. Registrar Residente");
            System.out.println("4. Eliminar Residente");
            System.out.println("5. Imprimir informe");
            System.out.println("6. Salir");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    ingresoVehiculo();// opcion para ingresar vehículos al parqueo
                    break;
                case 2:
                    salidaVehiculo();// opción para sacar vehículos del parqueo
                    break;
                case 3:
                    registrarResidente();// opción para registrar nuevos residentes
                    break;
                case 4:
                    eliminarPorID(residentes, "Residentes.csv"); // opción para eliminar residentes existentes
                    break;
                case 5:
                    System.out.println("Informe de Movimiento por rango de hora");// informe de movimientos dentro del
                                                                                  // parqueo
                    imprimirInformeMovimiento();
                    System.out.println("Informe Residentes ");// informe de los residentes del parqueo
                    informeResidentes();
                    break;
                case 6:
                    System.out.println("Saliendo del programa.");// cerrar el programa del parqueo
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");// defensa por si se ingresa otro número
            }
        }
    }

    private static void ingresoVehiculo() {// inicio del método para poder ingresar vehículos
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la placa del vehículo: ");
        String placa = scanner.nextLine().trim().toLowerCase();// Normaliza la placa, es decir vuelve todo lowercase y
                                                               // quita espacios

        String tipoCliente = "Cliente Regular"; // Por defecto, asumimos que es un cliente regular

        for (Residente residente : residentes) {
            if (residente.getPlaca().equals(placa)) {// comparación de placas del csv de residentes con la placa que se
                                                     // está ingresando
                System.out.println("Se identificó al usuario como residente!");
                System.out.println("Verificando si ha pagado...");
                if (residente.tienePagoSolvente()) {// verificación si el residente va al día con sus pagos
                    System.out.println("El usuario está al día en sus pagos, registrando información de movimiento...");

                    // Registra la hora de entrada actual.
                    LocalDateTime horaEntrada = LocalDateTime.now();

                    // Crea un objeto MovimientoResidente con la hora de entrada y agrega a la lista
                    // de movimientos.
                    MovimientoResidente movimiento = new MovimientoResidente(tipoCliente, placa, horaEntrada, null, 0, 0);
                    movimientos.add(movimiento);

                    tipoCliente = "Residente"; // Cambiamos el tipo de cliente a Residente

                    // Llama a la función para guardar el movimiento en el archivo CSV.
                    guardarMovimientosGenerales(placa, horaEntrada, null, 0.0, 0.0, tipoCliente);
                } else {
                    System.out.println("El usuario no está al día en sus pagos, ¿quiere realizarlo ahora?");// si el
                                                                                                            // residente
                                                                                                            // no está
                                                                                                            // en su
                    System.out.println("1. Sí, realizará el pago en este instante");
                    System.out.println("2. No, no realizará el pago en este instante");
                    int ans1 = scanner.nextInt();

                    if (ans1 == 1) {
                        // Aun está pendiente ver cuánto tendrá que pagar al mes.
                        // Este valor es inventado solo por conveniencia actual.
                        System.out.println("Su total son 100 Quetzales");
                        System.out.println("Pago registrado! Ingresando al residente..."); // el residente decide pagar
                                                                                           // su deuda

                        LocalDateTime horaEntrada = LocalDateTime.now();

                        // Crea un objeto MovimientoResidente con la hora de entrada y agrega a la lista
                        // de movimientos.
                        MovimientoResidente movimiento = new MovimientoResidente(tipoCliente, placa, horaEntrada, null, 0, 0);
                        movimientos.add(movimiento);

                        tipoCliente = "Residente"; // Cambiamos el tipo de cliente a Residente

                        // Llama a la función para guardar el movimiento en el archivo CSV.
                        guardarMovimientosGenerales(placa, horaEntrada, null, 0.0, 0.0, tipoCliente);
                    } else if (ans1 == 2) {
                        System.out.println("Que tenga un buen día, ¡de media vuelta! (Desgraciado)");
                        return; // línea para salir de la función si no se realiza el pago
                    } else {
                        System.out.println("Opción no válida. Intente de nuevo.");
                        return; // programación defensiva
                    }
                }
            }
        }

        // Si llegamos a este punto, significa que el cliente no es un residente.
        // Registra la hora de entrada actual.
        LocalDateTime horaEntrada = LocalDateTime.now();

        // Crea un objeto Movimiento con la hora de entrada y agrega a la lista de
        // movimientos.
        Movimiento movimiento = new Movimiento(tipoCliente, placa, horaEntrada, null, 0, 0);
        movimientos.add(movimiento);

        tipoCliente = "Regular"; // Cambia el tipo de cliente a Cliente Regular

        // Llama a la función para guardar el movimiento en el archivo CSV.
        guardarMovimientosGenerales(placa, horaEntrada, null, 0.0, 0.0, tipoCliente);

        System.out.println("Movimiento registrado para el cliente con placa " + placa);// es lo que muestra el panel
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
    }// fin del método para ingresar vehículos

    private static void guardarMovimientosGenerales(String placa, LocalDateTime horaEntrada, LocalDateTime horaSalida,
            double redondear, double total, String tipoCliente) { // inicio del metodo para guardar el movimiento de
                                                                  // clientes regulares/residentes
        try (PrintWriter writer = new PrintWriter(new FileWriter("Movimientos.csv", true))) { // escribir en el csv
                                                                                              // correcto
            Optional<Movimiento> movimientoOpt = obtenerMovimientoPorPlaca(placa);

            if (movimientoOpt.isPresent()) {
                Movimiento movimiento = movimientoOpt.get();

                // DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // formato de la hora
                // String horaEntradaString = horaEntrada.format(formatter);
                // String horaSalidaString = horaSalida != null ? horaSalida.format(formatter) : ""; // Si la hora de
                //                                                                                   // salida es nula,
                //                                                                                   // deja el string
                //                                                                                   // vacio

                movimiento.setHoraEntrada(horaEntrada);
                movimiento.setHoraSalida(horaSalida);
                movimiento.setRedondear(redondear);
                movimiento.setTotal(total);

                reescribirCSV();

                // writer.print(tipoCliente + "," + placa.toLowerCase() + "," + horaEntradaString + ","
                //         + horaSalidaString + "," + redondear + "," + total);
                // writer.println();
            } else {
                System.out.println("No se encontró un movimiento para la placa " + placa);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // fin del método de guardar movimientos de clientes regulares/residentes

    private static void reescribirCSV() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Movimientos.csv"))) {
            // Escribir el encabezado
            writer.println("Tipo de Cliente,Placa,Hora de Entrada,Hora de Salida,Redondear,Total");

            // Escribir cada movimiento
            for (Movimiento movimiento : movimientos) {
                writer.print(movimiento.getTipoCliente() + ",");
                writer.print(movimiento.getPlaca() + ",");
                writer.print(movimiento.getHoraEntrada() + ",");
                writer.print(movimiento.getHoraSalida() + ",");
                writer.print(movimiento.getRedondear() + ",");
                writer.println(movimiento.getTotal());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Optional<Movimiento> obtenerMovimientoPorPlaca(String placa) {
        return movimientos.stream()
                .filter(movimiento -> movimiento.getPlaca().equalsIgnoreCase(placa))
                .findFirst();
    }

    private static void guardarResidentes(String placa, String marca, String color, String modelo,
            Boolean pagoSolvente) {// inicio del metodo para guardar el residentes nuevos en el csv de residentes
        try (PrintWriter writer = new PrintWriter(new FileWriter("Residentes.csv", false))) {
            writer.println(placa + "," + marca + "," + color + "," + modelo + ","
                    + (pagoSolvente != null ? pagoSolvente : ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// fin del metodo para guardar residentes

    private static void salidaVehiculo() {// inicio del metodo para salida de los vehiculos
        System.out.println(
                "----------------------------------------------------------------------------------------------------");

        double tarifa = 10;// tarifa deseada para el parqueo

        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la placa del vehículo que sale: ");
        String placa = scanner.nextLine().trim().toLowerCase(); // Normaliza la placa, es decir vuelve todo lowercase y
                                                                // quita espacios

        for (Movimiento movimiento : movimientos) {
            if (movimiento.getPlaca().toLowerCase().replace(" ", "").equals(placa)) {
                if (movimiento instanceof MovimientoResidente) {
                    // revisasr si el cliente es un residente, para registrar la hora de salida
                    System.out.println("El residente con placa " + placa + " ha salido del estacionamiento.");
                    System.out.println("Registrando la hora de salida...");
                    LocalDateTime horaSalida = LocalDateTime.now();// hora de la compu
                    movimiento.setHoraSalida(horaSalida);

                    // Actualiza el movimiento en el archivo CSV.
                    guardarMovimientosGenerales(movimiento.getPlaca(), movimiento.getHoraEntrada(), horaSalida, 0.0,
                            0.0, "Residente");

                    System.out.println(
                            "----------------------------------------------------------------------------------------------------");
                    return;
                }

                LocalDateTime horaEntrada = movimiento.getHoraEntrada();// obtener la hora de entrada del csv
                LocalDateTime horaSalida = LocalDateTime.now();// hora de salida de la compu

                if (movimiento.getHoraSalida() == null) {
                    // El vehículo está actualmente estacionado, esto se hace para los regulares
                    long diferenciaTiempo = java.time.Duration.between(horaEntrada, horaSalida).toMillis();
                    double horasEstacionado = (double) diferenciaTiempo / 3600000;
                    double total = Math.ceil(horasEstacionado) * tarifa;// lógica para cobrar el parqueo

                    System.out.println("El cliente con placa " + placa + " debe pagar " + total + " Quetzales");
                    System.out.println("Registrando la hora de salida...");
                    System.out.println("Saliendo del estacionamiento...");
                    System.out.println(
                            "----------------------------------------------------------------------------------------------------");

                    // Actualiza la hora de salida en el objeto de movimiento.
                    movimiento.setHoraSalida(horaSalida);

                    // Actualiza el movimiento en el archivo CSV.
                    guardarMovimientosGenerales(movimiento.getPlaca(), horaEntrada, horaSalida, horasEstacionado, total,
                            "Regular");
                } else {
                    // El vehículo ya ha salido si hay una placa que ya salio y no ha vuelto a
                    // ingresar
                    System.out.println("El vehículo con placa " + placa + " ya ha salido del estacionamiento.");
                    System.out.println(
                            "----------------------------------------------------------------------------------------------------");
                }

                return; // Termina la función después de encontrar y procesar el movimiento.
            }
        }

        System.out.println("No se encontró ningún movimiento para la placa " + placa);// si no esta la placa en la base
                                                                                      // de datos
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
    }

    private static void registrarResidente() {// inicio del metodo de registrar residentes nuevos
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la placa del residente: ");
        String placa = scanner.nextLine().trim().toLowerCase(); // Normaliza la placa, es decir vuelve todo lowercase y
                                                                // quita espacios
        System.out.println("Ingrese la marca del vehículo del residente: ");
        String marca = scanner.nextLine();
        System.out.println("Ingrese el color del vehículo del residente: ");
        String color = scanner.nextLine();
        System.out.println("Ingrese el modelo del vehículo del residente: ");
        String modelo = scanner.nextLine();
        System.out.println("¿El residente desea pagar la mensualidad del parqueo del mes actual? Si/No");
        String pagoSolvente = scanner.nextLine();
        // se terminan de pedir los datos generales que se desea saber de los
        // residentes, al igual que si se desea pagar la mensualidad
        if (pagoSolvente.equalsIgnoreCase("si")) {
            residentes.add(new Residente(placa, marca, color, modelo, true));
            guardarResidentes(placa, marca, color, modelo, true);// se guarda como True para indicar que si esta al dia
                                                                 // con suspagos
        } else if (pagoSolvente.equalsIgnoreCase("no")) {
            residentes.add(new Residente(placa, marca, color, modelo, false));
            guardarResidentes(placa, marca, color, modelo, false);// se guarda como false para indicar que no esta al
                                                                  // dia con sus pagos
        }

        System.out.println("Se registró al Residente con éxito.");
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        scanner.nextLine();
    }// final del metodo de registrar residentes nuevos

    public static void eliminarPorID(List<Residente> residenteList, String nombreDocumento) {
        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        System.out.println("Si apacho por accidente, escriba NO en ambas opciones");
        System.out.println("Ingrese la placa del residente que desea borrar del registro: "); // Registramos los datos a
                                                                                              // donde verificaremos su
                                                                                              // eliminacion
        String placa = scanner.nextLine().trim();
        System.out.println("Ingrese la marca del vehículo del residente que desea borrar: ");
        String marca = scanner.nextLine().trim();
        System.out.println(
                "----------------------------------------------------------------------------------------------------");

        // Copiar la lista para evitar ConcurrentModificationException
        List<Residente> copiaResidentes = new ArrayList<>(residenteList); // Se hace una copia de la lista actual de
                                                                          // residentes

        try (BufferedReader brResidentes = new BufferedReader(new FileReader(nombreDocumento)); // Abrimos dos
                                                                                                // diferentes
                                                                                                // documentos, el actual
                                                                                                // y uno temporal para
                                                                                                // los datos
                BufferedWriter bwResidentes = new BufferedWriter(new FileWriter("temp.csv"))) {

            String line;
            while ((line = brResidentes.readLine()) != null) { // Revisamos que hayan datos en el CSV
                String[] residenteData = line.split(",");
                String placaCSV = residenteData[0].trim(); // Identificamos las posiciones de los datos
                String marcaCSV = residenteData[1].trim();

                // Verificar si la fila coincide con los valores ingresados
                if (!placaCSV.equals(placa) || !marcaCSV.equals(marca)) { // Si encuentra ambos datos en sus posiciones
                                                                          // delivaredas, se remplazan con una linea
                                                                          // vacia
                    // Si no coinciden, escribir la línea en el archivo temporal
                    bwResidentes.write(line);
                    bwResidentes.newLine();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        // Renombrar el archivo temporal al nombre original
        File tempFile = new File("temp.csv");
        File originalFile = new File(nombreDocumento);
        if (tempFile.renameTo(originalFile)) {
            System.out.println("Registro eliminado exitosamente.");
        } else {
            // Si el reemplazo directo no funciona, intenta copiar el contenido del temporal
            // al original
            try {
                Files.copy(tempFile.toPath(), originalFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Registro eliminado exitosamente.");
            } catch (IOException e) {
                System.out.println("Error al eliminar el registro.");
                e.printStackTrace();
            }
        }

        // Actualizar la lista original con la copia modificada
        residenteList.clear();
        residenteList.addAll(copiaResidentes);
    }

    private static void imprimirInformeMovimiento() {// inicio del metodo para imprimir el informe de movimientos
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
        System.out.println("Ingrese la hora de inicio (formato: HH:mm y 24 horas):");
        String horaInicioString = scanner.nextLine();
        System.out.println("Ingrese la hora de fin (formato: HH:mm y 24 horas):");
        String horaFinString = scanner.nextLine();

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");// set del formato de la hora
            LocalTime horaInicio = LocalTime.parse(horaInicioString, formatter);// asegurar que la hora de inicio sea
                                                                                // correcta
            LocalTime horaFin = LocalTime.parse(horaFinString, formatter);// asegurar que la hora de fin sea correcta

            int contador = 0;// valor inicial del contador
            double ganancias = 0;// valor inicial de las ganancias

            for (Movimiento movimiento : movimientos) {
                LocalTime horaEntrada = LocalTime.from(movimiento.getHoraEntrada());

                if (horaEntrada.isAfter(horaInicio) && horaEntrada.isBefore(horaFin)) {
                    contador++;// suma al contador para cada auto que ingrese al parqueo en ese lapso de horas
                    long minutosEstacionado = java.time.Duration.between(horaEntrada, horaFin).toMinutes();
                    ganancias += Math.ceil((double) minutosEstacionado / 60) * 10;// logica para calcular las ganancias
                                                                                  // durante ese lapso de horas
                }
            }

            System.out.println("Cantidad de carros que entraron entre " + horaInicioString + " y " + horaFinString
                    + ": " + contador);
            System.out.println("Ganancias entre esas horas: " + ganancias + " quetzales");// lo que se mira en el panel

        } catch (Exception e) {
            System.out.println("Hora no válida. Formato incorrecto.");// programacion defensiva
        }
    }// fin del metodo para imprimir el informe de movimientos

    public static void informeResidentes() {// inicio del metodo para el informe de residentes
        String archivoCSV = "Residentes.csv"; // Reemplaza con el nombre de tu archivo CSV
        int contador = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(archivoCSV))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                contador++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Residentes ingresados: " + contador);// residentes que han ingresado al parqueo
        System.out.println(
                "----------------------------------------------------------------------------------------------------");
    }// fin del metodo para el informe de residentes

    private static void cargarMovimientosDesdeCSV() {// inicio del metodo para cargar archivos del csv de movimientos,
                                                     // para que se pueda usar los datos que ya estaban ingresados
                                                     // dentro del programa si se ciera
        try (BufferedReader brMovimientos = new BufferedReader(new FileReader("Movimientos.csv"))) {
            // Ignora la primera línea (encabezado)
            brMovimientos.readLine();

            String lineaMovimientos;
            while ((lineaMovimientos = brMovimientos.readLine()) != null) {
                String[] datos = lineaMovimientos.split(",");// tomar los datos del archivo

                if (datos.length >= 6) {
                    String tipoCliente = datos[0];
                    String placa = datos[1].toLowerCase().trim().replace(" ", ""); // Normaliza la placa
                    LocalDateTime horaEntrada = LocalDateTime.parse(datos[2],
                            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    LocalDateTime horaSalida = datos[3].isEmpty() ? null
                            : LocalDateTime.parse(datos[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    double redondear = Double.parseDouble(datos[4]);
                    double total = Double.parseDouble(datos[5]);

                    // Crear el objeto de movimiento adecuado (MovimientoRegular o
                    // MovimientoResidente)
                    Movimiento movimiento;
                    if (tipoCliente.equalsIgnoreCase("Residente")) {
                        movimiento = new MovimientoResidente(tipoCliente, placa, horaEntrada, horaSalida, redondear, total);
                    } else {
                        movimiento = new MovimientoRegular(tipoCliente, placa, horaEntrada, horaSalida, redondear, total);
                    }

                    // Agregar el movimiento al array
                    movimientos.add(movimiento);
                } else {
                    System.out.println("Error al leer los datos de movimiento desde el archivo CSV.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        cargarResidentesDesdeCSV();
    }// fin del metodo para cargar movimientos del csv

    private static void cargarResidentesDesdeCSV() {// inicio del metodo para cargar los residentes del archivo csv
        try (BufferedReader brResidentes = new BufferedReader(new FileReader("Residentes.csv"))) {
            String lineaResidentes;// ignorar la primera linea (encabezado)
            while ((lineaResidentes = brResidentes.readLine()) != null) {
                String[] datos = lineaResidentes.split(",");

                if (datos.length == 5) { // tomar los datos del archivo
                    String placa = datos[0].toLowerCase().trim().replace(" ", ""); // Normaliza la placa
                    String marca = datos[1].trim();
                    String color = datos[2].trim();
                    String modelo = datos[3].trim();
                    Boolean pagoSolvente = Boolean.parseBoolean(datos[4].trim());

                    Residente residente = new Residente(placa, marca, color, modelo, pagoSolvente);
                    residentes.add(residente);
                } else {
                    System.out.println("Error al leer los datos de residentes desde el archivo CSV.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }// fin del metodo para cargar los residentes del archivo csv

}// fin del Main