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

                    
                case 5: 
                    System.out.println("Saliendo del programa.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void ingresoVehiculo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese la placa del vehículo: ");
        String placa = scanner.nextLine();
        for (Residente residente : residentes) {
            if (residente.getPlaca().equals(placa)) {
                System.out.println("Se identifico al usuario como residente!"); 
                System.out.println("Verificando si a pagado...");
                if(residente.tienePagoSolvente() == true){

                    System.out.println("Usuario esta al dia en sus pagos, registrando informacion de movimiento..."); 
                    MovimientoResidente movimiento = new MovimientoResidente(placa, null, null);
                    movimiento.gethoraEntrada();
                    movimientos.add(movimiento);

                } else if(residente.tienePagoSolvente() == false){

                    System.out.println("Usuario no al dia en sus pagos, quiere realizarlo ahorita?"); 
                    int ans1 = scanner.nextInt();
                    System.out.println("1. Si realizara el pago en este instante"); 
                    System.out.println("2. No realizara el pago en este instante"); 

                    if(ans1 == 1){  //Aun esta pendeinte ver caunto tendra que pagar al mes, este valor es ajeno e inventado solo por conveniencia actual

                        System.out.println("Su total son 100 Quetzales"); 
                        System.out.println("Pago registrado! Ingresando al residente..."); 
                        MovimientoResidente movimiento = new MovimientoResidente(placa, null, null);
                        movimiento.gethoraEntrada();
                        movimientos.add(movimiento);

                    } else if(ans1 == 2){

                        System.out.println("Que tenga un buen dia, de media vuelta! (Desgraciado)"); 
                    }
                }
            }
        }
    }
        

    private static void guardarMovimientosGenerales(String placa, LocalDateTime horaEntrada, LocalDateTime horaSalida, double redondear, double total) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Movimientos.csv", true))) {
            for (Movimiento movimiento : movimientos) {
                if (movimiento instanceof MovimientoRegular){
                    writer.println("Cliente Regular" + "," +placa + "," + horaEntrada + "," + horaSalida + "," + redondear + "," + total);
                }
                if (movimiento instanceof MovimientoResidente){
                    writer.println("Residente" + "," +placa + "," + horaEntrada + "," + horaSalida + "," + redondear + "," + total);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void guardarResidentes(String placa, String marca, String color, String modelo, Boolean pagoSolvente) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Residentes.csv", true))) {
            writer.println(placa + "," + marca + "," + color + "," + modelo + "," +  pagoSolvente);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void salidaVehiculo() {
        double tarifa = 10;
        for (Movimiento movimiento : movimientos) {
            if (movimiento instanceof MovimientoRegular) {
                MovimientoRegular regular = (MovimientoRegular) movimiento;
                LocalDateTime horaEntrada = regular.gethoraEntrada();
                LocalDateTime horaSalida = regular.gethoraSalida();
                long diferenciaTiempo = java.time.Duration.between(horaEntrada,horaSalida).toMillis();
                double redondear = Math.ceil((double)diferenciaTiempo/3600000);
                double total = redondear*tarifa;
                System.out.println("El cliente con placa " + regular.getplaca() + " debe pagar" + total + " Quetzales");
            }
            else{
                MovimientoResidente mresidente = (MovimientoResidente) movimiento;
                LocalDateTime horaEntrada = mresidente.gethoraEntrada();
                LocalDateTime horaSalida = mresidente.gethoraSalida();
                long diferenciaTiempo = java.time.Duration.between(horaEntrada, horaSalida).toMillis();
                double redondear = Math.ceil((double)diferenciaTiempo/3600000);
                double total = 0;
                System.out.println("El residente con placa " + mresidente.getplaca() + " Se ha retirado del estacionamiento");
                movimientos.add(new Movimiento(mresidente.getPlaca(), horaEntrada, horaSalida));
            }
        }
    }


    private static void registrarResidente() {
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
        if(pagoSolvente.equalsIgnoreCase("si")){
           residentes.add(new Residente(placa,marca,color,modelo,true));
        }
        else if(pagoSolvente.equalsIgnoreCase("no")){
           residentes.add(new Residente(placa,marca,color,modelo,false));
        }
        
        System.out.println("Se registró al Residente con éxito.");
        guardarResidentes(placa, marca, color, modelo, null);
    }


  
    private static void imprimirInformeMovimiento() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Ingrese la fecha de inicio (formato: dd/MM/yyyy HH:mm):");
        String fechaHoraInicioString = scanner.nextLine();
        System.out.println("Ingrese la fecha de fin (formato: dd/MM/yyyy HH:mm):");
        String fechaHoraFinString = scanner.nextLine();
    
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime fechaHoraInicio = LocalDateTime.parse(fechaHoraInicioString, formatter);
            LocalDateTime fechaHoraFin = LocalDateTime.parse(fechaHoraFinString, formatter);
    
            int contador = 0;
    
            for (Movimiento movimiento : movimientos) {
                LocalDateTime horaEntrada = movimiento.getHoraEntrada();
    
                if (horaEntrada.isAfter(fechaHoraInicio) && horaEntrada.isBefore(fechaHoraFin)) {
                    contador++;
                    long minutosEstacionado = java.time.Duration.between(horaEntrada, fechaHoraFin).toMinutes();

                }
            }
    
            System.out.println("Cantidad de carros que entraron entre " + fechaHoraInicioString + " y " + fechaHoraFinString + ": " + contador);

        } catch (Exception e) {
            System.out.println("Fecha no válida. Formato incorrecto.");
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
    }
}
    


