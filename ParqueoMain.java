import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;


public class ParqueoMain {
    private static List<ClienteRegular> clientesRegulares = new ArrayList<>();
    private static List<Residente> residentes = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Ingreso de un vehículo");
            System.out.println("2. Salida de un vehículo");
            System.out.println("4. Registrar Residente");
            System.out.println("5. Imprimir informe");
            System.out.println("6. Salir");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    ingresoVehiculo(scanner);
                    break;
                case 2:
                    salidaVehiculo(scanner);
                    break;
                case 3:
                    registrarResidente(scanner);
                    break;
                case 4:
                    imprimirInforme(scanner);
                case 5: 
                    System.out.println("Saliendo del programa.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void ingresoVehiculo(Scanner scanner) {
        System.out.println("Seleccione el tipo de cliente:");
        System.out.println("1. Cliente Regular");
        System.out.println("2. Residente");
        int tipoCliente = scanner.nextInt();

        scanner.nextLine(); 

        switch (tipoCliente) {
            case 1:
                System.out.println("Ingrese el número de placa del vehículo:");
                String placaClienteRegular = scanner.nextLine();
                LocalDateTime horaEntrada = LocalDateTime.now();
                ClienteRegular clienteRegular = new ClienteRegular(placaClienteRegular, colorClienteRegular, marcaClienteRegular, horaEntrada);
                clientesRegulares.add(clienteRegular);
                System.out.println("Se registró el vehículo del Cliente Regular con éxito.");


                guardarDatosClienteRegularCSV(placaClienteRegular, colorClienteRegular, marcaClienteRegular, horaEntrada);
                break;
                
            case 2:
                System.out.println("Ingrese el número de placa del vehículo del Residente:");
                String placaResidente = scanner.nextLine();
                System.out.println("Ingrese el color del vehículo del Residente:");
                String colorResidente = scanner.nextLine();
                System.out.println("Ingrese la marca del vehículo del Residente:");
                String marcaResidente = scanner.nextLine();
                
                boolean residenteEncontrado = false;
            
                for (Residente residente : residentes) {
                    if (residente.getPlaca().equalsIgnoreCase(placaResidente)) {
                        System.out.println("El residente está verificado");
                        residenteEncontrado = true;
                        break;
                    } else if (residente.isPagoSolvente()) {
                        System.out.println("El residente está solvente de sus pagos");
                        Residente nuevoResidente = new Residente(placaResidente, marcaResidente, colorResidente, true, true);
                        residentes.add(nuevoResidente);
                        System.out.println("Se registró al Residente con éxito.");
                        guardarDatosResidenteCSV(placaResidente, marcaResidente, colorResidente, true, true);
                        residenteEncontrado = true;
                        break;
                    }
                }
                
                if (!residenteEncontrado) {
                    System.out.println("Residente no verificado/Residente con pagos pendientes");
                }
                break;
            default:
                System.out.println("Opción no válida.");
            }
        }

    private static void guardarDatosClienteRegularCSV(String placa, String color, String marca, LocalDateTime horaEntrada) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("DatosClientesRegulares.csv", true))) {
            writer.println(placa + "," + color + "," + marca + "," + horaEntrada);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void guardarDatosResidenteCSV(String placa, String marca, String color, String verificado) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Residentes.csv", true))) {
            writer.println(placa + "," + marca + "," + color + "," + verificado);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void salidaVehiculo(Scanner scanner) {
        System.out.println("Seleccione el tipo de cliente:");
        System.out.println("1. Cliente Regular");
        System.out.println("2. Residente");
        int tipoCliente = scanner.nextInt();
        scanner.nextLine();

        switch (tipoCliente) {
            case 1:
                System.out.println("Ingrese la hora de salida (formato HH:mm):");
                String horaSalidaStr = scanner.nextLine();
                try {
                    Date horaSalidaClienteRegular = dateFormat.parse(horaSalidaStr);
                    System.out.println("Ingrese el número de placa del vehículo del Cliente Regular:");
                    String placaClienteRegular = scanner.nextLine();
                    ClienteRegular clienteRegular = buscarClienteRegularPorPlaca(placaClienteRegular);

                    if (clienteRegular != null) {
                        double tarifa = clienteRegular.calcularTarifa(clienteRegular.getHoraEntrada(), horaSalidaClienteRegular);
                        System.out.println("El costo del estacionamiento es: " + tarifa + " quetzales.");
                    } else {
                        System.out.println("No se encontró al Cliente Regular con la placa especificada.");
                    }
                } catch (Exception e) {
                    System.out.println("Hora de salida inválida.");
                }
                break;
            case 2:
                System.out.println("Ingrese la placa del vehículo del Residente:");
                String placaResidente = scanner.nextLine();
                Residente residente = buscarResidentePorPlaca(placaResidente);

                if (residente != null) {
                    System.out.println("¿Cuántos meses desea pagar?");
                    int meses = scanner.nextInt();
                    double costoTotal = residente.calcularTarifa(meses);
                    System.out.println("El costo del estacionamiento para " + meses + " meses es: " + costoTotal + " quetzales.");
                } else {
                    System.out.println("No se encontró al Residente con la placa especificada.");
                }
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void mostrarEstadisticas() {
        int totalClientesRegulares = clientesRegulares.size();
        double totalGananciasClientesRegulares = 0.0;
        double totalGananciasResidentes = 0.0;

        for (ClienteRegular clienteRegular : clientesRegulares) {
            totalGananciasClientesRegulares += clienteRegular.calcularTarifa(clienteRegular.getHoraEntrada(), new Date());
        }

        for (Residente residente : residentes) {
            if (residente.esResidenteVerificado()) {
                System.out.println("Vehículo con placa " + residente.placa + " pagó " + (residente.calcularTarifa(1) / 500) + " meses del servicio de parqueo.");
                totalGananciasResidentes += residente.calcularTarifa(1);
            }
        }

        System.out.println("Estadísticas del día:");
        System.out.println("Total de clientes regulares: " + totalClientesRegulares);
        System.out.println("Total de ganancias de clientes regulares: " + totalGananciasClientesRegulares + " quetzales.");
        System.out.println("Total de ganancias de residentes: " + totalGananciasResidentes + " quetzales.");
    }

    private static ClienteRegular buscarClienteRegularPorPlaca(String placa) {
        for (ClienteRegular clienteRegular : clientesRegulares) {
            if (clienteRegular.placa.equalsIgnoreCase(placa)) {
                return clienteRegular;
            }
        }
        return null;
    }

    private static Residente buscarResidentePorPlaca(String placa) {
        for (Residente residente : residentes) {
            if (residente.placa.equalsIgnoreCase(placa)) {
                return residente;
            }
        }
        return null;
    }
}