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
import java.util.Iterator;
//se importan todas las librerías necesarias

public class ParqueoMain {
    private static List<Movimiento> movimientos = new ArrayList<>();//array del csv de movimientos
    private static List<Residente> residentes = new ArrayList<>();//array del csv de residentes
    

    public static void main(String[] args) {//inicio del main driver
        Scanner scanner = new Scanner(System.in);
        cargarMovimientosDesdeCSV();//funcion para cargar los datos del CSV de movimientos siempre que se inicie el programa
        while (true) {//inicio del switch para el menú de opciones
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
                    ingresoVehiculo();//opcion para ingresar vehículos al parqueo
                    break;
                case 2:
                    salidaVehiculo();//opción para sacar vehículos del parqueo
                    break;
                case 3:
                    registrarResidente();//opción para registrar nuevos residentes
                    break;
                case 4:
                    eliminarPorID(residentes, "Residentes.csv"); // opción para eliminar residentes existentes
                    break;
                case 5: 
                    System.out.println("Informe de Movimiento por rango de fecha ");//informe de movimientos dentro del parqueo
                    imprimirInformeMovimiento();
                    System.out.println();
                    System.out.println("Informe Residentes ");//informe de los residentes del parqueo
                    informeResidentes();
                    break;      
                case 6:
                    System.out.println("Saliendo del programa.");//cerrar el programa del parqueo
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");//defensa por si se ingresa otro número
            }
        }
    }

    private static void ingresoVehiculo() {//inicio del método para poder ingresar vehículos
        System.out.println("----------------------------------------------------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la placa del vehículo: ");
         String placa = scanner.nextLine().trim().toLowerCase();// Normaliza la placa, es decir vuelve todo lowercase y quita espacios
        
        String tipoCliente = "Cliente Regular"; // Por defecto, asumimos que es un cliente regular
        
        for (Residente residente : residentes) {
            if (residente.getPlaca().equals(placa)) {//comparación de placas del csv de residentes con la placa que se está ingresando
                System.out.println("Se identificó al usuario como residente!"); 
                System.out.println("Verificando si ha pagado...");
                if (residente.tienePagoSolvente()) {//verificación si el residente va al día con sus pagos
                    System.out.println("El usuario está al día en sus pagos, registrando información de movimiento...");
                    
                    // Registra la hora de entrada actual.
                    LocalDateTime horaEntrada = LocalDateTime.now();
                
                    // Crea un objeto MovimientoResidente con la hora de entrada y agrega a la lista de movimientos.
                    MovimientoResidente movimiento = new MovimientoResidente(placa, horaEntrada, null);
                    movimientos.add(movimiento);
                    
                    tipoCliente = "Residente"; // Cambiamos el tipo de cliente a Residente
                    
                    // Llama a la función para guardar el movimiento en el archivo CSV.
                    guardarMovimientosGenerales(placa, horaEntrada, null, 0.0, 0.0, tipoCliente);
                } else {
                    System.out.println("El usuario no está al día en sus pagos, ¿quiere realizarlo ahora?");//si el residente no está en su
                    System.out.println("1. Sí, realizará el pago en este instante");
                    System.out.println("2. No, no realizará el pago en este instante");
                    int ans1 = scanner.nextInt();
                    
                    
                    if(ans1 == 1) {
                        // Aun está pendiente ver cuánto tendrá que pagar al mes.
                        // Este valor es inventado solo por conveniencia actual.
                        System.out.println("Su total son 100 Quetzales"); 
                        System.out.println("Pago registrado! Ingresando al residente..."); //el residente decide pagar su deuda
                   
                        LocalDateTime horaEntrada = LocalDateTime.now();
                        
                        // Crea un objeto MovimientoResidente con la hora de entrada y agrega a la lista de movimientos.
                        MovimientoResidente movimiento = new MovimientoResidente(placa, horaEntrada, null);
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
    
        // Crea un objeto Movimiento con la hora de entrada y agrega a la lista de movimientos.
        Movimiento movimiento = new Movimiento(placa, horaEntrada, null);
        movimientos.add(movimiento);
    
        tipoCliente = "Regular"; // Cambia el tipo de cliente a Cliente Regular
        
        // Llama a la función para guardar el movimiento en el archivo CSV.
        guardarMovimientosGenerales(placa, horaEntrada, null, 0.0, 0.0, tipoCliente);
        
        System.out.println("Movimiento registrado para el cliente con placa " + placa);//es lo que muestra el panel
        System.out.println("----------------------------------------------------------------------------------------------------");
    }//fin del método para ingresar vehículos 
    
    private static void guardarMovimientosGenerales(String placa, LocalDateTime horaEntrada, LocalDateTime horaSalida, double redondear, double total, String tipoCliente) {//inicio del metodo para guardar el movimiento de clientes regulares/residentes
        try (PrintWriter writer = new PrintWriter(new FileWriter("Movimientos.csv", true))) {//escribir en el csv correcto
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");//formato de la hora
            String horaEntradaString = horaEntrada.format(formatter);
            String horaSalidaString = horaSalida != null ? horaSalida.format(formatter) : ""; // Si la hora de salida es nula, deja el string vacío
            writer.print(tipoCliente + "," + placa.toLowerCase() + "," + horaEntradaString + "," + horaSalidaString + "," + redondear + "," + total);
            writer.println(); // Agrega una nueva línea al final
        } catch (IOException e) {
            e.printStackTrace();
        }
    }//fin del método de guardar movimientos de clientes regulares/residentes
    
    private static void guardarResidentes(String placa, String marca, String color, String modelo, Boolean pagoSolvente, int mesesPagados) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Residentes.csv", true))) {
            writer.println(placa + "," + marca + "," + color + "," + modelo + "," + (pagoSolvente != null ? pagoSolvente : false) + "," + mesesPagados);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al guardar el Residente en el archivo CSV.");
        }
    }
 
    private static void salidaVehiculo() {//inicio del metodo para salida de los vehiculos
        System.out.println("----------------------------------------------------------------------------------------------------");
    
        double tarifa = 10;//tarifa deseada para el parqueo
    
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la placa del vehículo que sale: ");
        String placa = scanner.nextLine().trim().toLowerCase(); // Normaliza la placa, es decir vuelve todo lowercase y quita espacios
    
        for (Movimiento movimiento : movimientos) {
            if (movimiento.getPlaca().toLowerCase().replace(" ", "").equals(placa)) {
                if (movimiento instanceof MovimientoResidente) {
                    // revisasr si el cliente es un residente, para registrar la hora de salida
                    System.out.println("El residente con placa " + placa + " ha salido del estacionamiento.");
                    System.out.println("Registrando la hora de salida...");
                    LocalDateTime horaSalida = LocalDateTime.now();// hora de la compu
                    movimiento.setHoraSalida(horaSalida);
    
                    // Actualiza el movimiento en el archivo CSV.
                    guardarMovimientosGenerales(movimiento.getPlaca(), movimiento.getHoraEntrada(), horaSalida, 0.0, 0.0, "Residente");
    
                    System.out.println("----------------------------------------------------------------------------------------------------");
                    return;
                }
    
                LocalDateTime horaEntrada = movimiento.getHoraEntrada();//obtener la hora de entrada del csv
                LocalDateTime horaSalida = LocalDateTime.now();//hora de salida de la compu
    
                if (movimiento.getHoraSalida() == null) {
                    // El vehículo está actualmente estacionado, esto se hace para los regulares
                    long diferenciaTiempo = java.time.Duration.between(horaEntrada, horaSalida).toMillis();
                    double horasEstacionado = (double) diferenciaTiempo / 3600000;
                    double total = Math.ceil(horasEstacionado) * tarifa;//lógica para cobrar el parqueo
    
                    System.out.println("El cliente con placa " + placa + " debe pagar " + total + " Quetzales");
                    System.out.println("Registrando la hora de salida...");
                    System.out.println("Saliendo del estacionamiento...");
                    System.out.println("----------------------------------------------------------------------------------------------------");
    
                    // Actualiza la hora de salida en el objeto de movimiento.
                    movimiento.setHoraSalida(horaSalida);
    
                    // Actualiza el movimiento en el archivo CSV.
                    guardarMovimientosGenerales(movimiento.getPlaca(), horaEntrada, horaSalida, horasEstacionado, total, "Regular");
                } else {
                    // El vehículo ya ha salido si hay una placa que ya salio y no ha vuelto a ingresar
                    System.out.println("El vehículo con placa " + placa + " ya ha salido del estacionamiento.");
                    System.out.println("----------------------------------------------------------------------------------------------------");
                }
    
                return; // Termina la función después de encontrar y procesar el movimiento.
            }
        }
    
        System.out.println("No se encontró ningún movimiento para la placa " + placa);//si no esta la placa en la base de datos
        System.out.println("----------------------------------------------------------------------------------------------------");
    }
    
    private static void registrarResidente() {//inicio del registro de residente
        System.out.println("----------------------------------------------------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
    
        try {
            System.out.println("Ingrese la placa del residente: ");
            String placa = scanner.nextLine().trim().toLowerCase();
            System.out.println("Ingrese la marca del vehículo del residente: ");
            String marca = scanner.nextLine();
            System.out.println("Ingrese el color del vehículo del residente: ");
            String color = scanner.nextLine();
            System.out.println("Ingrese el modelo del vehículo del residente: ");
            String modelo = scanner.nextLine();
            System.out.println("¿El residente desea pagar la mensualidad del parqueo del mes actual? Si/No");
            String pagoSolvente = scanner.nextLine();
    
            //  Agregar la cantidad de meses adicionales a pagar
            int mesesPagados = 0;
            if (pagoSolvente.equalsIgnoreCase("si")) {
                System.out.println("¿Cuántos meses desea pagar por adelantado?");
                mesesPagados = scanner.nextInt();
                residentes.add(new Residente(placa, marca, color, modelo, true, mesesPagados));
            guardarResidentes(placa, marca, color, modelo, true, mesesPagados); // Guardar residente en el CSV
            }
            else if (pagoSolvente.equalsIgnoreCase("no")) {
                residentes.add(new Residente(placa, marca, color, modelo, false, mesesPagados));
                guardarResidentes(placa, marca, color, modelo, false, mesesPagados);// se guarda como false para indicar que no esta al dia con sus pagos

            System.out.println("Se registró al Residente con éxito.");
            System.out.println("----------------------------------------------------------------------------------------------------");
            scanner.nextLine(); // Consumir la nueva línea pendiente en el buffer
    
        } }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error al registrar al Residente. Verifique los datos e inténtelo de nuevo.");
        }
    }// fin del registro de nuevo residente
    
    public static void eliminarPorID(List<Residente> residenteList, String nombreDocumento) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("Si apacho por accidente, escriba NO en ambas opciones");
        System.out.println("Ingrese la placa del residente que desea borrar del registro: "); //Registramos los datos a donde verificaremos su eliminacion
        String placa = scanner.nextLine().trim();
        System.out.println("Ingrese la marca del vehículo del residente que desea borrar: ");
        String marca = scanner.nextLine().trim();
        
        // Copiar la lista para evitar ConcurrentModificationException
        List<Residente> copiaResidentes = new ArrayList<>(residenteList); //Se hace una copia de la lista actual de residentes

        try (BufferedReader brResidentes = new BufferedReader(new FileReader(nombreDocumento)); //Abrimos dos diferentes documentos, el actual y uno temporal para los datos
             BufferedWriter bwResidentes = new BufferedWriter(new FileWriter("temp.csv"))) {

            String line;
            while ((line = brResidentes.readLine()) != null) { //Revisamos que hayan datos en el CSV
                String[] residenteData = line.split(",");
                String placaCSV = residenteData[0].trim(); //Identificamos las posiciones de los datos
                String marcaCSV = residenteData[1].trim();

                // Verificar si la fila coincide con los valores ingresados
                if (!placaCSV.equals(placa) || !marcaCSV.equals(marca)) { //Si encuentra ambos datos en sus posiciones delivaredas, se remplazan con una linea vacia
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
            System.out.println("Residente eliminado exitosamente.");
            System.out.println("----------------------------------------------------------------------------------------------------");

        } else {
            // Si el reemplazo directo no funciona, intenta copiar el contenido del temporal al original
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
    
    private static void imprimirInformeMovimiento() {
        //Creamos un scanner 
        Scanner scanner = new Scanner(System.in);
        //Pedimos al usuario un rango de fechas 
    
        System.out.println("Ingrese la fecha de inicio (formato: yyyy-MM-dd HH:mm:ss):");
        String fechaHoraInicioString = scanner.nextLine();
        System.out.println("Ingrese la fecha de fin (formato: yyyy-MM-dd HH:mm:ss):");
        String fechaHoraFinString = scanner.nextLine();
    
        try {
            //Ponemos los limites del rango de dia y fecha 
            DateTimeFormatter entradaFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            DateTimeFormatter salidaFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            //Parseamos lo que obtuvimos para ponerlo a tipo LocalDateTime
    
            LocalDateTime fechaHoraInicio = LocalDateTime.parse(fechaHoraInicioString, entradaFormatter);
            LocalDateTime fechaHoraFin = LocalDateTime.parse(fechaHoraFinString, entradaFormatter);
    //Inicializamos un contador 
            int contador = 0;
    
    //For para registrar cuantos ingresos hubo entre el rango que se estableció 
            for (Movimiento movimiento : movimientos) {
                LocalDateTime horaEntrada = movimiento.getHoraEntrada();
    
                if (horaEntrada.isAfter(fechaHoraInicio) && horaEntrada.isBefore(fechaHoraFin)) {
                    contador++;
                }
            }
    
    //Imprimimos 
            System.out.println("Cantidad de carros que entraron entre " + fechaHoraInicioString + " y " + fechaHoraFinString + ": " + contador);

    //Validacion de formato para errores amigables 
        } catch (Exception e) {
            System.out.println("Fecha no válida. Formato incorrecto.");
        }
    }
    
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

        System.out.println("Residentes ingresados: " + contador);//  residentes que han ingresado al parqueo
        System.out.println("----------------------------------------------------------------------------------------------------");
    }// fin del metodo para el informe de residentes

    private static void cargarMovimientosDesdeCSV() {// inicio del metodo para cargar archivos del csv de movimientos, para que se pueda usar los datos que ya estaban ingresados dentro del programa si se ciera
        try (BufferedReader brMovimientos = new BufferedReader(new FileReader("Movimientos.csv"))) {
            // Ignora la primera línea (encabezado)
            brMovimientos.readLine();
    
            String lineaMovimientos;
            while ((lineaMovimientos = brMovimientos.readLine()) != null) {
                String[] datos = lineaMovimientos.split(",");//  tomar los datos del archivo 
    
                if (datos.length >= 6) { 
                    String tipoCliente = datos[0];
                    String placa = datos[1].toLowerCase().trim().replace(" ", ""); // Normaliza la placa
                    LocalDateTime horaEntrada = LocalDateTime.parse(datos[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    LocalDateTime horaSalida = datos[3].isEmpty() ? null : LocalDateTime.parse(datos[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                    double redondear = Double.parseDouble(datos[4]);
                    double total = Double.parseDouble(datos[5]);
    
                    // Crear el objeto de movimiento adecuado (MovimientoRegular o MovimientoResidente)
                    Movimiento movimiento;
                    if (tipoCliente.equalsIgnoreCase("Residente")) {
                        movimiento = new MovimientoResidente(placa, horaEntrada, horaSalida);
                    } else {
                        movimiento = new MovimientoRegular(placa, horaEntrada, horaSalida);
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

    private static void cargarResidentesDesdeCSV() {
        try (BufferedReader brResidentes = new BufferedReader(new FileReader("Residentes.csv"))) {
            String lineaResidentes;
    
            // Lee la primera línea (encabezados) y descártala
            brResidentes.readLine();
    
            while ((lineaResidentes = brResidentes.readLine()) != null) {
                String[] datos = lineaResidentes.split(",");
    
                if (datos.length == 6) {
                    try {
                        String placa = datos[0].toLowerCase().trim();
                        String marca = datos[1].trim();
                        String color = datos[2].trim();
                        String modelo = datos[3].trim();
                        boolean pagoSolvente = Boolean.parseBoolean(datos[4].trim());
                        int mesesPagados = Integer.parseInt(datos[5].trim());
    
                        Residente residente = new Residente(placa, marca, color, modelo, pagoSolvente, mesesPagados);
                        residentes.add(residente);
                    } catch (NumberFormatException e) {
                        System.out.println("Error al leer los datos de residentes. Meses pagados no es un número válido.");
                    }
                } else {
                    System.out.println("Error al leer los datos de residentes desde el archivo CSV.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }  // fin del Main