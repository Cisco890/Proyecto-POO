import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class ParqueoMain {
    private static List<Movimiento> movimientos = new ArrayList<>();
    private static List<Residente> residentes = new ArrayList<>();
    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        cargarMovimientosDesdeCSV();
        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Ingreso de un vehículo");
            System.out.println("2. Salida de un vehículo");
            System.out.println("3. Registrar Residente");
            System.out.println("4. Imprimir informe");
            System.out.println("5. Salir");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    ingresoVehiculo();
                    break;
                case 2:
                    salidaVehiculo();
                    break;
                case 3:
                    registrarResidente();
                    break;
                case 4:
                    System.out.println("Informe de Movimiento por rango de hora");
                    imprimirInformeMovimiento();
                    System.out.println("Informe Residentes ");
                    informeResidentes();
                    break;

                    
                case 5: 
                    System.out.println("Saliendo del programa.");
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void ingresoVehiculo() {
        System.out.println("----------------------------------------------------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la placa del vehículo: ");
        String placa = scanner.nextLine();
        
        String tipoCliente = "Cliente Regular"; // Por defecto, asumimos que es un cliente regular
        
        for (Residente residente : residentes) {
            if (residente.getPlaca().equals(placa)) {
                System.out.println("Se identificó al usuario como residente!"); 
                System.out.println("Verificando si ha pagado...");
                if (residente.tienePagoSolvente()) {
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
                    System.out.println("El usuario no está al día en sus pagos, ¿quiere realizarlo ahora?");
                    System.out.println("1. Sí, realizará el pago en este instante");
                    System.out.println("2. No, no realizará el pago en este instante");
                    int ans1 = scanner.nextInt();
                    
                    
                    if(ans1 == 1) {
                        // Aun está pendiente ver cuánto tendrá que pagar al mes.
                        // Este valor es ajeno e inventado solo por conveniencia actual.
                        System.out.println("Su total son 100 Quetzales"); 
                        System.out.println("Pago registrado! Ingresando al residente..."); 
                   
                        LocalDateTime horaEntrada = LocalDateTime.now();
                    
                        // Crea un objeto MovimientoResidente con la hora de entrada y agrega a la lista de movimientos.
                        MovimientoResidente movimiento = new MovimientoResidente(placa, horaEntrada, null);
                        movimientos.add(movimiento);
                    
                        tipoCliente = "Residente"; // Cambiamos el tipo de cliente a Residente
                    
                        // Llama a la función para guardar el movimiento en el archivo CSV.
                        guardarMovimientosGenerales(placa, horaEntrada, null, 0.0, 0.0, tipoCliente);
                    } else if (ans1 == 2) {
                        System.out.println("Que tenga un buen día, ¡de media vuelta! (Desgraciado)");
                        return; // Agregué esta línea para salir de la función si no se realiza el pago
                    } else {
                        System.out.println("Opción no válida. Intente de nuevo.");
                        return; // Agregué esta línea para salir de la función si la opción no es "1" ni "2"
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
        
        System.out.println("Movimiento registrado para el cliente con placa " + placa);
        System.out.println("----------------------------------------------------------------------------------------------------");
    }
    
    
        
    private static void guardarMovimientosGenerales(String placa, LocalDateTime horaEntrada, LocalDateTime horaSalida, double redondear, double total, String tipoCliente) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Movimientos.csv", true))) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String horaEntradaString = horaEntrada.format(formatter);
            String horaSalidaString = horaSalida != null ? horaSalida.format(formatter) : ""; // Si la hora de salida es nula, deja el string vacío
            writer.print(tipoCliente + "," + placa.toLowerCase() + "," + horaEntradaString + "," + horaSalidaString + "," + redondear + "," + total);
            writer.println(); // Agrega una nueva línea al final
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    private static void guardarResidentes(String placa, String marca, String color, String modelo, Boolean pagoSolvente) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Residentes.csv", true))) {
            writer.println(placa + "," + marca + "," + color + "," + modelo + "," + (pagoSolvente != null ? pagoSolvente : ""));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void salidaVehiculo() {
        System.out.println("----------------------------------------------------------------------------------------------------");
    
        double tarifa = 10;
    
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la placa del vehículo que sale: ");
        String placa = scanner.nextLine().trim().toLowerCase(); // Normaliza la placa
    
        for (Movimiento movimiento : movimientos) {
            if (movimiento.getPlaca().toLowerCase().replace(" ", "").equals(placa)) {
                if (movimiento instanceof MovimientoResidente) {
                    // El cliente es un residente, se registra la hora de salida
                    System.out.println("El residente con placa " + placa + " ha salido del estacionamiento.");
                    System.out.println("Registrando la hora de salida...");
                    LocalDateTime horaSalida = LocalDateTime.now();
                    movimiento.setHoraSalida(horaSalida);
    
                    // Actualiza el movimiento en el archivo CSV.
                    guardarMovimientosGenerales(movimiento.getPlaca(), movimiento.getHoraEntrada(), horaSalida, 0.0, 0.0, "Residente");
    
                    System.out.println("----------------------------------------------------------------------------------------------------");
                    return;
                }
    
                LocalDateTime horaEntrada = movimiento.getHoraEntrada();
                LocalDateTime horaSalida = LocalDateTime.now();
    
                if (movimiento.getHoraSalida() == null) {
                    // El vehículo está actualmente estacionado
                    long diferenciaTiempo = java.time.Duration.between(horaEntrada, horaSalida).toMillis();
                    double horasEstacionado = (double) diferenciaTiempo / 3600000;
                    double total = Math.ceil(horasEstacionado) * tarifa;
    
                    System.out.println("El cliente con placa " + placa + " debe pagar " + total + " Quetzales");
                    System.out.println("Registrando la hora de salida...");
                    System.out.println("Saliendo del estacionamiento...");
                    System.out.println("----------------------------------------------------------------------------------------------------");
    
                    // Actualiza la hora de salida en el objeto de movimiento.
                    movimiento.setHoraSalida(horaSalida);
    
                    // Actualiza el movimiento en el archivo CSV.
                    guardarMovimientosGenerales(movimiento.getPlaca(), horaEntrada, horaSalida, horasEstacionado, total, "Regular");
                } else {
                    // El vehículo ya ha salido
                    System.out.println("El vehículo con placa " + placa + " ya ha salido del estacionamiento.");
                    System.out.println("----------------------------------------------------------------------------------------------------");
                }
    
                // No elimina el movimiento de la lista, solo actualiza la hora de salida.
                return; // Termina la función después de encontrar y procesar el movimiento.
            }
        }
    
        System.out.println("No se encontró ningún movimiento para la placa " + placa);
        System.out.println("----------------------------------------------------------------------------------------------------");
    }
    
    

    


    private static void registrarResidente() {
        System.out.println("----------------------------------------------------------------------------------------------------");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la placa del residente: ");
        String placa = scanner.nextLine();
        System.out.println("Ingrese la marca del vehículo del residente: ");
        String marca = scanner.nextLine();
        System.out.println("Ingrese el color del vehículo del residente: ");
        String color = scanner.nextLine();
        System.out.println("Ingrese el modelo del vehículo del residente: ");
        String modelo = scanner.nextLine();
        System.out.println("¿El residente desea pagar la mensualidad del parqueo del mes actual? Si/No");
        String pagoSolvente = scanner.nextLine();
    
        if (pagoSolvente.equalsIgnoreCase("si")) {
            residentes.add(new Residente(placa, marca, color, modelo, true));
            guardarResidentes(placa, marca, color, modelo, true);
        } else if (pagoSolvente.equalsIgnoreCase("no")) {
            residentes.add(new Residente(placa, marca, color, modelo, false));
            guardarResidentes(placa, marca, color, modelo, false);
        }
    
        System.out.println("Se registró al Residente con éxito.");
        System.out.println("----------------------------------------------------------------------------------------------------");
        scanner.nextLine(); 
    }
    
    


  
    private static void imprimirInformeMovimiento() {
        Scanner scanner = new Scanner(System.in);
    System.out.println("----------------------------------------------------------------------------------------------------");
        System.out.println("Ingrese la hora de inicio (formato: HH:mm y 24 horas):");
        String horaInicioString = scanner.nextLine();
        System.out.println("Ingrese la hora de fin (formato: HH:mm y 24 horas):");
        String horaFinString = scanner.nextLine();
    
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime horaInicio = LocalTime.parse(horaInicioString, formatter);
            LocalTime horaFin = LocalTime.parse(horaFinString, formatter);
    
            int contador = 0;
            double ganancias = 0;
    
            for (Movimiento movimiento : movimientos) {
                LocalTime horaEntrada = LocalTime.from(movimiento.getHoraEntrada());
    
                if (horaEntrada.isAfter(horaInicio) && horaEntrada.isBefore(horaFin)) {
                    contador++;
                    long minutosEstacionado = java.time.Duration.between(horaEntrada, horaFin).toMinutes();
                    ganancias += Math.ceil((double)minutosEstacionado / 60) * 10;
                }
            }
    
            System.out.println("Cantidad de carros que entraron entre " + horaInicioString + " y " + horaFinString + ": " + contador);
            System.out.println("Ganancias entre esas horas: " + ganancias + " quetzales");
           
    
        } catch (Exception e) {
            System.out.println("Hora no válida. Formato incorrecto.");
        }
    }
    public static void informeResidentes() {
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

        System.out.println("Residentes ingresados: " + contador);
        System.out.println("----------------------------------------------------------------------------------------------------");
    }
    private static void cargarMovimientosDesdeCSV() {
        try (BufferedReader brMovimientos = new BufferedReader(new FileReader("Movimientos.csv"))) {
            // Ignora la primera línea (encabezado)
            brMovimientos.readLine();
    
            String lineaMovimientos;
            while ((lineaMovimientos = brMovimientos.readLine()) != null) {
                String[] datos = lineaMovimientos.split(",");
    
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
    
                    // Agregar el movimiento a la lista
                    movimientos.add(movimiento);
                } else {
                    System.out.println("Error al leer los datos de movimiento desde el archivo CSV.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    
        cargarResidentesDesdeCSV();
    }
    
    private static void cargarResidentesDesdeCSV() {
        try (BufferedReader brResidentes = new BufferedReader(new FileReader("Residentes.csv"))) {
            String lineaResidentes;
            while ((lineaResidentes = brResidentes.readLine()) != null) {
                String[] datos = lineaResidentes.split(",");
    
                if (datos.length == 5) { 
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
    }
    
    }