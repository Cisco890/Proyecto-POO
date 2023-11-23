/** Juan Francisco Martínez 23617, Andrés Mazariegos 21749,  Daniela Ramirez 23053,  Antony Lou Schanwk 23410

  * ParqueoMain
 
  * @param ingresovehiculos,salidavehiculos,registrarresidente,informe,cargardatosdeloscsv
  * @throws Es el main driver del programa, en este se encuentra el menú de opciones para la persona encargada del parqueo, aparte de toda la lógica y funcionamiento del programa

  */
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.Iterator;
import java.util.stream.Collectors;
import java.util.Optional;

//se importan todas las librerías necesarias

public class ParqueoMain {
    public static List<Movimiento> movimientos = new ArrayList<>();//array del csv de movimientos
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

    private static void ingresoVehiculo() {
        System.out.println("----------------------------------------------------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la placa del vehículo: ");
        String placa = scanner.nextLine().trim().toLowerCase();
    
        String tipoCliente = "Regular"; // Assume regular customer by default
    
        for (Residente residente : residentes) {
            if (residente.getPlaca().equals(placa)) {
                System.out.println("Se identificó al usuario como residente!");
                System.out.println("Verificando si ha pagado...");
                if (residente.tienePagoSolvente()) {
                    System.out.println("El usuario está al día en sus pagos, registrando información de movimiento...");
    
                    // Registra la hora de entrada actual.
                    LocalDateTime horaEntrada = LocalDateTime.now();
                    tipoCliente = "Residente"; // Change the client type to Residente
    
                    // Llama a la función para guardar el movimiento en el archivo CSV.
                    reescribirCSV(tipoCliente, placa, horaEntrada, null, 0.0, 0.0);
    
                } else {
                    System.out.println("El usuario no está al día en sus pagos, ¿quiere realizarlo ahora?");
                    System.out.println("1. Sí, realizará el pago en este instante");
                    System.out.println("2. No, no realizará el pago en este instante");
                    int ans1 = scanner.nextInt();
    
                    if (ans1 == 1) {
                        System.out.println("Su total son 100 Quetzales");
                        System.out.println("Pago registrado! Ingresando al residente...");
                        modificarSolvencia(true, placa);
                        LocalDateTime horaEntrada = LocalDateTime.now();
    
                        // Crea un objeto MovimientoResidente con la hora de entrada y agrega a la lista de movimientos
                        tipoCliente = "Residente"; // Change the client type to Residente
                        // Llama a la función para guardar el movimiento en el archivo CSV
                        reescribirCSV(tipoCliente, placa, horaEntrada, null, 0.0, 0.0);
    
                    } else if (ans1 == 2) {
                        System.out.println("Que tenga un buen día, ¡de media vuelta!");
                        return; // Exit the function if no payment is made
                    } else {
                        System.out.println("Opción no válida. Intente de nuevo.");
                        return; // Defensive programming
                    }
                }
    
                // If a matching resident is found, exit the loop to prevent duplicate entries.
                return;
            }
        }
    
        // If the loop did not find a matching resident, create a default Movimiento entry.
        LocalDateTime horaEntrada = LocalDateTime.now();
        
        // Llama a la función para guardar el movimiento en el archivo CSV.
        reescribirCSV(tipoCliente, placa, horaEntrada, null, 0.0, 0.0);
        System.out.println("Movimiento registrado para el cliente con placa " + placa);
        System.out.println("----------------------------------------------------------------------------------------------------");
    }
    
    private static void reescribirCSV(String tipoCliente, String placa, LocalDateTime horaEntrada, LocalDateTime horaSalida, double horasEstacionado, double total) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Movimientos.csv", true))) {
            // Escribir el encabezado
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            String horaEntradaFormateada = (horaEntrada != null) ? horaEntrada.format(formatter) : "";
            String horaSalidaFormateada = (horaSalida != null) ? horaSalida.format(formatter) : "";
            if(tipoCliente == "Residente"){
                movimientos.add(new MovimientoResidente(tipoCliente, placa, horaEntrada, horaSalida, horasEstacionado, total));
            } else if (tipoCliente == "Regular"){
                movimientos.add(new MovimientoRegular(tipoCliente, placa, horaEntrada, horaSalida, horasEstacionado, total));
            }
            
            // Escribir cada movimiento
            writer.write(tipoCliente + "," + placa + "," + horaEntradaFormateada + "," + horaSalidaFormateada + "," + horasEstacionado + "," + total + "\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void guardarResidentes(String placa, String marca, String color, String modelo,
        Boolean pagoSolvente, int mesesPagados) {
    try (PrintWriter writer = new PrintWriter(new FileWriter("Residentes.csv", true))) {
        writer.println(placa + "," + marca + "," + color + "," + modelo + ","
                + (pagoSolvente != null ? pagoSolvente : "") + "," + mesesPagados);
    } catch (IOException e) {
        e.printStackTrace();
    }
    }// fin del metodo para guardar residentes

    private static void modificarSolvencia(boolean ValorBool, String placa){
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("Residentes.csv"))) {
            for(Residente res : residentes){
                if(res.getPlaca().equals(placa)){
                    res.setPagoSolvente(ValorBool);
                }
            }
            writer.write("Placa"+","+"Marca"+","+"Color"+","+"Modelo"+","+"Pago Solvente"+","+"Meses pagados"+ "\n");
            for(Residente res: residentes){
                writer.write(res.getPlaca() + "," + res.getMarca() + "," + res.getColor() + "," + res.getModelo() + "," + res.tienePagoSolvente() + "," + res.getMesesPagados() + "\n");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
 
    private static void salidaVehiculo() {//inicio del metodo para salida de los vehiculos
        System.out.println("----------------------------------------------------------------------------------------------------");
    
        double tarifa = 10;//tarifa deseada para el parqueo
    
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la placa del vehículo que sale: ");
        String placa = scanner.nextLine().trim().toLowerCase(); // Normaliza la placa, es decir vuelve todo lowercase y quita espacios
    
        for (Movimiento movimiento : movimientos) {
            if (movimiento.getPlaca().toLowerCase().equals(placa)) {
                LocalDateTime horaEntrada = movimiento.getHoraEntrada();//obtener la hora de entrada del csv
                LocalDateTime horaSalida = LocalDateTime.now();//hora de salida de la compu

                if (movimiento instanceof MovimientoResidente) {
                    // revisasr si el cliente es un residente, para registrar la hora de salida
                    System.out.println("El residente con placa " + placa + " ha salido del estacionamiento.");
                    System.out.println("Registrando la hora de salida...");
                    movimiento.setHoraSalida(horaSalida);
    
                    // Actualiza el movimiento en el archivo CSV.
                    eliminarPorMovimiento(movimientos, placa, "Movimientos.csv", horaEntrada, horaSalida, 0.0, 0.0);
    
                    System.out.println("----------------------------------------------------------------------------------------------------");
                    return;
                }
    
                
    
                if (movimiento.getHoraSalida() == null) {
                    // El vehículo está actualmente estacionado, esto se hace para los regulares
                    long diferenciaTiempo = java.time.Duration.between(horaEntrada, horaSalida).toMillis();
                    double horasEstacionado = (double) diferenciaTiempo / 3600000;
                    double total = Math.ceil(horasEstacionado) * tarifa;//lógica para cobrar el parqueo
    
                    System.out.println("El cliente con placa " + placa + " debe pagar " + total + " Quetzales");
                    System.out.println("Registrando la hora de salida...");
                    System.out.println("Saliendo del estacionamiento...");
                    System.out.println("----------------------------------------------------------------------------------------------------");
        
                    // Actualiza el movimiento en el archivo CSV.
                    eliminarPorMovimiento(movimientos, placa, "Movimientos.csv", horaEntrada, horaSalida, horasEstacionado, total);
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
    
    public static void eliminarPorMovimiento(List<Movimiento> movimientos, String placa,String nombreDocumento, LocalDateTime Entrada, LocalDateTime Salida, double horasEstacionado, double total) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        Entrada.format(formatter);
        Salida.format(formatter);

        // Copiar la lista para evitar ConcurrentModificationException
        List<Movimiento> copiaMovimientos = new ArrayList<>(movimientos);
    
        try (BufferedReader brMovimientos = new BufferedReader(new FileReader(nombreDocumento));
            BufferedWriter bwMovimientos = new BufferedWriter(new FileWriter("temp2.csv"))) {

            String line;
            while ((line = brMovimientos.readLine()) != null) {
                String[] movimientoData = line.split(",");
                String claseCSV = movimientoData[0].trim();
                String placaCSV = movimientoData[1].trim();
                String horaimporante = movimientoData[3].trim();
                // Verificar si la fila coincide con los valores ingresados
                if (placaCSV.equals(placa) && claseCSV.equals("Regular") && horaimporante == "") {
                    // Si no coinciden, escribir la línea en el archivo temporal
                    bwMovimientos.write("Regular" + "," + placa + "," + Entrada.format(formatter) + "," + Salida.format(formatter) + "," + horasEstacionado + "," + total);
                } else if (placaCSV.equals(placa) && claseCSV.equals("Residente") && horaimporante == "") {
                    bwMovimientos.write("Residente" + "," + placa + "," + Entrada.format(formatter) + "," + Salida.format(formatter) + "," + 0.0 + "," + 0.0);
                } else {
                    bwMovimientos.write(line);
                }
                // Agregar una nueva línea después de cada línea escrita
                bwMovimientos.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Renombrar el archivo temporal al nombre original
        File tempFile = new File("temp2.csv");
        File originalFile = new File(nombreDocumento);
        if (tempFile.renameTo(originalFile)) {
            System.out.println("Movimiento eliminado exitosamente.");
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
        movimientos.clear();
        movimientos.addAll(copiaMovimientos);
        // Eliminar el residente de la lista
        Iterator<Movimiento> iterator = movimientos.iterator();
        while (iterator.hasNext()) {
            Movimiento movi = iterator.next();
            if (movi.getPlaca().equals(placa) && movi.getHoraSalida() == null) {
                // Eliminar el elemento utilizando el iterador
                iterator.remove();
                break; // Terminar el bucle después de eliminar el elemento
            }
        }
    }

    public static void eliminarPorID(List<Residente> residenteList, String nombreDocumento) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("Si apacho por accidente, escriba NO en ambas opciones");
        System.out.println("Ingrese la placa del residente que desea borrar del registro: ");
        String placa = scanner.nextLine().trim().toLowerCase();
        System.out.println("Ingrese la marca del vehículo del residente que desea borrar: ");
        String marca = scanner.nextLine().trim();
    
        // Copiar la lista para evitar ConcurrentModificationException
        List<Residente> copiaResidentes = new ArrayList<>(residenteList);
    
        try (BufferedReader brResidentes = new BufferedReader(new FileReader(nombreDocumento));
             BufferedWriter bwResidentes = new BufferedWriter(new FileWriter("temp1.csv"))) {
    
            String line;
            while ((line = brResidentes.readLine()) != null) {
                String[] residenteData = line.split(",");
                String placaCSV = residenteData[0].trim().toLowerCase();
                String marcaCSV = residenteData[1].trim();
    
                // Verificar si la fila coincide con los valores ingresados
                if (!placaCSV.equals(placa) || !marcaCSV.equals(marca)) {
                    // Si no coinciden, escribir la línea en el archivo temporal
                    bwResidentes.write(line);
                    bwResidentes.newLine();
                }
            }
    
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        // Renombrar el archivo temporal al nombre original
        File tempFile = new File("temp1.csv");
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
        // Eliminar el residente de la lista
        Iterator<Residente> iterator = residenteList.iterator();
        while (iterator.hasNext()) {
            Residente residente = iterator.next();
            if (residente.getPlaca().equals(placa) && residente.getMarca().equals(marca)) {
                // Eliminar el elemento utilizando el iterador
                iterator.remove();
                break; // Terminar el bucle después de eliminar el elemento
            }
        }
        
        
    }

    private static void imprimirInformeMovimiento() {// inicio del metodo para imprimir el informe de movimiento de clientes regulares
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la fecha y hora de inicio (formato: yyyy-MM-dd HH:mm:ss.S), por ejemplo 2023-11-03 08:00:00.0 :");
        String fechaHoraInicioString = scanner.nextLine();//ingresar hora y fecha de entrada
        System.out.println("Ingrese la fecha y hora de fin (formato: yyyy-MM-dd HH:mm:ss.S): por ejemplo 2023-11-03 15:00:00.0 ");
        String fechaHoraFinString = scanner.nextLine();//ingresar hora y fecha de salida
    
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            LocalDateTime fechaHoraInicio = LocalDateTime.parse(fechaHoraInicioString, formatter);
            LocalDateTime fechaHoraFin = LocalDateTime.parse(fechaHoraFinString, formatter);//creacion de las variables que utiliza el metodo
    
            // Filtro para obtener los movimientos de entrada y salida de clientes regulares
            List<Movimiento> movimientosRegulares = movimientos.stream()
                    .filter(movimiento -> movimiento instanceof MovimientoRegular)
                    .filter(movimiento -> (movimiento.getHoraEntrada().isAfter(fechaHoraInicio) || (movimiento.getHoraSalida() != null && movimiento.getHoraSalida().isAfter(fechaHoraInicio)))
                            && (movimiento.getHoraEntrada().isBefore(fechaHoraFin) || (movimiento.getHoraSalida() != null && movimiento.getHoraSalida().isBefore(fechaHoraFin))))
                    .collect(Collectors.toList());
    
            // Filtro para obtener los movimientos de entrada de clientes regulares
            List<Movimiento> movimientosEntradaRegular = movimientosRegulares.stream()
                    .filter(movimiento -> movimiento.getHoraEntrada().isAfter(fechaHoraInicio) && movimiento.getHoraEntrada().isBefore(fechaHoraFin))
                    .collect(Collectors.toList());
    
            // Filtro para obtener los movimientos de salida de clientes regulares
            List<Movimiento> movimientosSalidaRegular = movimientosRegulares.stream()
                    .filter(movimiento -> movimiento.getHoraSalida() != null && movimiento.getHoraSalida().isAfter(fechaHoraInicio) && movimiento.getHoraSalida().isBefore(fechaHoraFin))
                    .collect(Collectors.toList());
    
            // Cálculos para la cantidad y el total recaudado de movimientos de entrada de clientes regulares
            long cantidadEntradaRegular = movimientosEntradaRegular.size();
    
            // Cálculos para la cantidad y el total recaudado de movimientos de salida de clientes regulares
            long cantidadSalidaRegular = movimientosSalidaRegular.size();
            double totalRecaudadoSalidaRegular = movimientosSalidaRegular.stream().mapToDouble(Movimiento::getTotal).sum();
    
            System.out.println("Cantidad de clientes REGULARES que ENTRARON entre " + fechaHoraInicioString + " y " + fechaHoraFinString + ": " + cantidadEntradaRegular);
            System.out.println("Cantidad de clientes REGULARES que SALIERON entre " + fechaHoraInicioString + " y " + fechaHoraFinString + ": " + cantidadSalidaRegular);
            System.out.println("Total recaudado por salida de clientes regulares en ese periodo: " + totalRecaudadoSalidaRegular + " Quetzales");
            System.out.println("NOTA: no se toma en cuenta el movimiento de los RESIDENTES" );
        } catch (Exception e) {
            System.out.println("Fecha no válida. Formato incorrecto.");
        }
    }//fin del metodo de imprimir informe de clientes regulares
    
    
   public static void informeResidentes() {//informe de Residentes
    String archivoCSV = "Residentes.csv";
    
    try {
        long cantidad = Files.lines(Paths.get(archivoCSV)).count() - 1; // Restar 1 para excluir la línea de encabezado
        System.out.println("Cantidad de residentes en el sistema: " + cantidad);
        System.out.println("----------------------------------------------------------------------------------------------------");

    } catch (IOException e) {
        e.printStackTrace();
    }
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
                    LocalDateTime horaEntrada = LocalDateTime.parse(datos[2], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
                    LocalDateTime horaSalida = datos[3].isEmpty() ? null : LocalDateTime.parse(datos[3], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S"));
                    double horasEstacionado = Double.parseDouble(datos[4]);
                    double total = Double.parseDouble(datos[5]);
    
                    // Crear el objeto de movimiento adecuado (MovimientoRegular o MovimientoResidente)
                    Movimiento movimiento;
                    if (tipoCliente.equalsIgnoreCase("Residente")) {
                        movimiento = new MovimientoResidente(tipoCliente, placa, horaEntrada, horaSalida, horasEstacionado, total);
                    } else {
                        movimiento = new MovimientoRegular(tipoCliente, placa, horaEntrada, horaSalida, horasEstacionado, total);
                    }
    
                    // Agregar el movimiento al array
                    movimientos.add(movimiento);
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