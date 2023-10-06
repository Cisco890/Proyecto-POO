/** Anthony Lou, Andres Mazariegos, Juan Francisco Martinez, Daniela Ramirez 

  * ClienteRegular
 
  * @param placa,color,marca,horaentrada,parqueoAsignado 
  * @throws Es la clase que se encarga de registrar todo lo relacionado a los clientes del parqueo, su hora de netrada, cuanto deben, parqueo que deben usar etcetera.

  */
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ClienteRegular extends Cliente {
    private  String color;
    private  String marca;
    private  Date horaEntrada;
    private  String parqueoAsignado;
    private String Tipovehiculo;
//Atributos de la clase ClienteRegular

    public ClienteRegular( String Tipovehiculo,String placa, String color, String marca, Date horaEntrada, String parqueoAsignado) {
        super(Tipovehiculo, placa, color, marca);
        this.horaEntrada = horaEntrada;
        this.parqueoAsignado = parqueoAsignado;
    }// Constructor del objeto ClienteRegular, con los parámetros placa, color, marca, hora de entrada y parqueo asignado

    public double calcularTarifa(Date horaSalida) {
        long tiempoEstacionadoMillis = horaSalida.getTime() - horaEntrada.getTime();
        long tiempoEstacionadoHoras = TimeUnit.MILLISECONDS.toHours(tiempoEstacionadoMillis);
        long tiempoEstacionadoMinutos = TimeUnit.MILLISECONDS.toMinutes(tiempoEstacionadoMillis) % 60;

        double tarifaPorHora = 10.0;
        double tarifaPorMediaHora = 5.0;

        double costoTotal = (tiempoEstacionadoHoras * tarifaPorHora) + (tiempoEstacionadoMinutos / 30 * tarifaPorMediaHora);

        return costoTotal;
    }// se hace el algoritmo que va a permitir saber cuanto se tiene que pagar del parqueo




    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public String getParqueoAsignado() {
        return parqueoAsignado;
    }


    public static void mostrarParqueosDisponibles(List<String> parqueosDisponibles) {
        System.out.println("Parqueos disponibles:");
        for (String parqueo : parqueosDisponibles) {
            System.out.print(parqueo + " ");
        }
        System.out.println();
    }   // se finalizan los getters del objeto jugador
}