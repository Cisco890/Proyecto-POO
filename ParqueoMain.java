import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class ParqueoMain {
    private static List<ClienteRegular> clientesRegulares = new ArrayList<>();
    private static List<Residente> residentes = new ArrayList<>();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
    private static SimpleDateFormat dateFormatResidente = new SimpleDateFormat("dd/MM/yyyy");
    private static double totalGananciasClientesRegulares = 0.0; // Agregamos una variable para rastrear las ganancias.
    public static double meses;

    public static void main(String[] args) {
        //Lector de Clientes regulares 
        Scanner scanner = new Scanner(System.in);
        ArrayList<Cliente> clientes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("DatosClientesRegulares.csv"))) {
        String linea;
        while ((linea = reader.readLine()) != null) {
            String[] datos = linea.split(",");
            String placa = datos[0];
            String marca = datos[2];
            String color = datos[1];
            SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
            try {
                Date entry = formatoHora.parse(datos[3]);
                Date sali = formatoHora.parse(datos[4]);

                ClienteRegular cliente = new ClienteRegular(placa, marca, color, entry, sali);
                clientes.add(cliente);
            } catch (ParseException e) {
            // Manejar la excepción aquí, por ejemplo, imprimir un mensaje de error
                System.out.println("Error al parsear la hora: " + e.getMessage());
            }
        }
        } catch (IOException e) {
            e.printStackTrace();
        }



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
                    registrarVehiculo(scanner);
                    break;
                case 2:
                    calcularCobro(scanner);
                    break;
                case 3:
                    mostrarEstadisticas();
                    break;
                case 4:
                    System.out.println("Saliendo del programa.");
                    scanner.close();
                    System.exit(0);
                case 5 :

                    break;
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void registrarVehiculo(Scanner scanner) {
        System.out.println("Seleccione el tipo de cliente:");
        System.out.println("1. Cliente Regular");
        System.out.println("2. Residente");
        int tipoCliente = scanner.nextInt();

        scanner.nextLine(); 

        switch (tipoCliente) {
            case 1:
                System.out.println("Ingrese el número de placa del vehículo:");
                String placaClienteRegular = scanner.nextLine();
                System.out.println("Ingrese el color del vehículo:");
                String colorClienteRegular = scanner.nextLine();
                System.out.println("Ingrese la marca del vehículo:");
                String marcaClienteRegular = scanner.nextLine();
                Date horaEntradaClienteRegular = new Date(); // Hora actual
                ClienteRegular clienteRegular = new ClienteRegular(placaClienteRegular, marcaClienteRegular, colorClienteRegular, horaEntradaClienteRegular);
                clientesRegulares.add(clienteRegular);
                System.out.println("Se registró el vehículo del Cliente Regular con éxito.");


                guardarDatosClienteRegularCSV(placaClienteRegular, colorClienteRegular, marcaClienteRegular, dateFormat.format(horaEntradaClienteRegular));
                break;
            case 2:
                System.out.println("Ingrese el número de placa del vehículo del Residente:");
                String placaResidente = scanner.nextLine();
                System.out.println("Ingrese el color del vehículo del Residente:");
                String colorResidente = scanner.nextLine();
                System.out.println("Ingrese la marca del vehículo del Residente:");
                String marcaResidente = scanner.nextLine();
                System.out.println("¿El residente está verificado? (Sí/No):");
                String verificadoResidente = scanner.nextLine();
                boolean residenteVerificado = verificadoResidente.equalsIgnoreCase("Sí");
                Residente residente = new Residente(placaResidente, marcaResidente, colorResidente, residenteVerificado);
                residentes.add(residente);
                System.out.println("Se registró al Residente con éxito.");

                guardarDatosResidenteCSV(placaResidente, marcaResidente, colorResidente, verificadoResidente);
                break;
            default:
                System.out.println("Opción no válida.");
        }
    }

    private static void guardarDatosClienteRegularCSV(String placa, String color, String marca, String horaEntrada) {
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

    private static void calcularCobro(Scanner scanner) {
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