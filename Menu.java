/** Anthony Lou, Andres Mazariegos, Juan Francisco Martinez, Daniela Ramirez 

  * ClienteRegular
 
  * @param placa,color,marca,horaentrada,parqueoAsignado 
  * @throws  menu del programa, aca se miran todas las opciones que los usuarios vana  poder seleccionar
  */
import java.util.Scanner;
import java.util.Date;
import java.util.Arrays;
public class Menu {

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        ClienteRegular[] ClienteRegular = new ClienteRegular[10]; // Array de ClienteRegular, cantidad máxima de 10
        Residente[] Residente = new Residente[10]; // Array de Residente, cantidad máxima de 10
        int Tipovehiculo;

        int ClienteRegularcount = 0; // llevar control de Clientes Regular
        int Residentecount = 0; // llevar control de cantidad de Residentes

        while (true) { // atributos del menú
            System.out.println("Menú:");
            System.out.println("1. Ingresar datos de un nuevo residente");
            System.out.println("2. Registrar un nuevo cliente");
            System.out.println("3. Realizar un cobro");
            System.out.println("4. Cargar un CSV");
            System.out.println("5. Salir");
            System.out.print("Ingrese su opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); 

            switch (opcion) {// inicio del switch

                case 1:
                boolean terminado = false;
                System.out.print("Nombre del Residente: ");
                String nombre = scanner.nextLine();
                
                if (!terminado){
                System.out.print("Que tipo de vehiculo es? ");
                System.out.print("1. Moto");
                System.out.print("2. Carro Chico");
                System.out.print("3. Camioneta");
                int ans = scanner.nextInt();

                if(ans == 1){
                    Tipovehiculo = 3;
                    terminado = true;
                }
                if(ans == 2){
                    Tipovehiculo = 2;
                    terminado = true;
                }
                if(ans == 3){
                    Tipovehiculo = 3;
                    terminado = true;
                }
                }

                System.out.print("Tipo de vehículo: ");
                Tipovehiculo = scanner.nextInt();
                System.out.print("Placa del vehículo: ");
                String placa = scanner.nextLine();
                System.out.print("Color del vehículo: ");
                String color = scanner.nextLine();
                System.out.print("Marca del vehículo: ");
                String marca = scanner.nextLine();
                scanner.nextLine(); // Fin de los datos de la clase Residente
                // Creación del objeto Residente y meterlo al array 
                Residente[Residentecount] = new Residente(nombre, Tipovehiculo, placa, color, marca);
                Residentecount++; 

                break;

                case 2:
                boolean terminado1 = false;
                if (!terminado1){
                System.out.print("Que tipo de vehiculo es? ");
                System.out.print("1. Moto");
                System.out.print("2. Carro Chico");
                System.out.print("3. Camioneta");
                int ans = scanner.nextInt();

                if(ans == 1){
                    Tipovehiculo = 1;
                    terminado1 = true;
                }
                if(ans == 2){
                    Tipovehiculo = 2;
                    terminado1 = true;
                }
                if(ans == 3){
                    Tipovehiculo = 3;
                    terminado1 = true;
                }
                }
                System.out.print("Placa del vehículo: ");
                placa = scanner.nextLine();
                System.out.print("Color del vehículo: ");
                color = scanner.nextLine();
                System.out.print("Marca del vehículo: ");
                marca = scanner.nextLine();
                System.out.print("Ingrese la hora de entrada: ");
                Date horaEntrada = new Date();;
                System.out.print("Ingrese el parqueo asignado: ");
                String parqueoAsignado = scanner.nextLine();
                scanner.nextLine(); // Fin de los datos de la clase Cliente
                // Creación del objeto Cliente y meterlo al array 
                ClienteRegular[ClienteRegularcount] = new ClienteRegular(Tipovehiculo, placa, color, marca, horaEntrada, parqueoAsignado);
                ClienteRegularcount++; 
                break;

                case 3:
                // codigo para ingresar el CSV

                case 4:
                // codigo para cobrar el parqueo

                case 5:
                System.out.println("Saliendo del programa.");
                System.exit(0); // fin del programa
                default:
                System.out.println("Opción no válida. Por favor, seleccione una opción válida.");
            }


    }

}
}
