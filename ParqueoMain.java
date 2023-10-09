/** Anthony Lou, Andres Mazariegos, Juan Francisco Martinez, Daniela Ramirez 

  * ParqueoMain
 
  * @param placa,color,marca,horaentrada,parqueoAsignado 
  * @throws  menu del programa, aca se miran todas las opciones que los usuarios vana  poder seleccionar
  */
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
//importar las librerias

public class ParqueoMain {
    private static List<ClienteRegular> clientesRegulares = new ArrayList<>();
    private static List<Residente> residentes = new ArrayList<>();
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Registrar un vehículo");
            System.out.println("2. Calcular cobro");
            System.out.println("3. Estadísticas");
            System.out.println("4. Salir");
            int opcion = scanner.nextInt();
// inicio del switch
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
                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }
        }
    }
//registro de tipos de clientes del programa
    private static void registrarVehiculo(Scanner scanner) {
        System.out.println("Seleccione el tipo de cliente:");
        System.out.println("1. Cliente Regular");
        System.out.println("2. Residente");
        int tipoCliente = scanner.nextInt();

        scanner.nextLine(); 

        switch (tipoCliente) {
            case 1://residentes
                System.out.println("Ingrese el número de placa del vehículo:");
                String placaClienteRegular = scanner.nextLine();
                System.out.println("Ingrese el color del vehículo:");
                String colorClienteRegular = scanner.nextLine();
                System.out.println("Ingrese la marca del vehículo:");
                String marcaClienteRegular = scanner.nextLine();
                Date horaEntradaClienteRegular = new Date(); // Hora actual
                Date horaSalidaClienteRegular = new Date();
                ClienteRegular clienteRegular = new ClienteRegular(placaClienteRegular, marcaClienteRegular, colorClienteRegular, horaEntradaClienteRegular, horaSalidaClienteRegular);
                clientesRegulares.add(clienteRegular);
                System.out.println("Se registró el vehículo del Cliente Regular con éxito.");

                guardarDatosClienteRegularCSV(placaClienteRegular, colorClienteRegular, marcaClienteRegular, dateFormat.format(horaEntradaClienteRegular), dateFormat.format(horaSalidaClienteRegular));
                break;

            case 2://clientes
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
//escribir en el csv
    private static void guardarDatosClienteRegularCSV(String placa, String color, String marca, String horaEntrada, String horaSalida) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("DatosClientesRegulares.csv", true))) {
            writer.println(placa + "," + color + "," + marca + "," + horaEntrada + "," + horaSalida);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//escribir en el csv
    private static void guardarDatosResidenteCSV(String placa, String marca, String color, String verificado) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Residentes.csv", true))) {
            writer.println(placa + "," + marca + "," + color + "," + verificado);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//calcular el cobro 
    private static void calcularCobro(Scanner scanner) {
        System.out.println("Seleccione el tipo de cliente:");
        System.out.println("1. Cliente Regular");
        System.out.println("2. Residente");
        int tipoCliente = scanner.nextInt();
        scanner.nextLine();

         switch (tipoCliente) {
        case 1://clientes
            System.out.println("Ingrese la hora de entrada (formato HH:mm y formato 24 horas):");
            String horaEntradaStr = scanner.nextLine();
            System.out.println("Ingrese la hora de salida (formato HH:mm y formato 24 horas):");
            String horaSalidaStr = scanner.nextLine();
            
            try {
                Date horaEntradaClienteRegular = dateFormat.parse(horaEntradaStr);
                Date horaSalidaClienteRegular = dateFormat.parse(horaSalidaStr);
                // Resto del código para calcular el cobro
                System.out.println("Ingrese el número de placa del vehículo del Cliente Regular:");
                String placaClienteRegular = scanner.nextLine();
                ClienteRegular clienteRegular = buscarClienteRegularPorPlaca(placaClienteRegular);

                if (clienteRegular != null) {
                    double tarifa = clienteRegular.calcularTarifa(horaEntradaClienteRegular, horaSalidaClienteRegular);
                    System.out.println("El costo del estacionamiento es: " + tarifa + " quetzales.");
                } else {
                    System.out.println("No se encontró al Cliente Regular con la placa especificada.");
                }
            } catch (ParseException e) {
                System.out.println("Error al analizar la hora de salida. Asegúrate de que esté en el formato HH:mm.");
            }
            break;
            case 2://residentes
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
//obtener estadisticas
    private static void mostrarEstadisticas() {
        int totalClientesRegulares = clientesRegulares.size();
        //double totalGananciasClientesRegulares = 0.0;
        //double totalGananciasResidentes = 0.0;

        //for (ClienteRegular clienteRegular : clientesRegulares) {
       //    totalGananciasClientesRegulares += clienteRegular.calcularTarifa(clienteRegular.getHoraEntrada(), new Date());
       // }

      //  for (Residente residente : residentes) {
      //      if (residente.esResidenteVerificado()) {
      //          System.out.println("Vehículo con placa " + residente.placa + " pagó " + (residente.calcularTarifa(1) / 500) + " meses del servicio de parqueo.");
      //          totalGananciasResidentes += residente.calcularTarifa(1);
      //      }
      //  }

        System.out.println("Estadísticas del día:");
        System.out.println("Total de clientes regulares: " + totalClientesRegulares);
      //  System.out.println("Total de ganancias de clientes regulares: " + totalGananciasClientesRegulares + " quetzales.");
      //  System.out.println("Total de ganancias de residentes: " + totalGananciasResidentes + " quetzales."); todo lo comentado acá se debe arreglar para la siguiente entrega
    }
//buscar cliente regular cliente regular por la placa ingresada
    private static ClienteRegular buscarClienteRegularPorPlaca(String placa) {
        for (ClienteRegular clienteRegular : clientesRegulares) {
            if (clienteRegular.placa.equalsIgnoreCase(placa)) {
                return clienteRegular;
            }
        }
        return null;
    }
//buscar Residente regular por la placa ingresada

    private static Residente buscarResidentePorPlaca(String placa) {
        for (Residente residente : residentes) {
            if (residente.placa.equalsIgnoreCase(placa)) {
                return residente;
            }
        }
        return null;
    }
}