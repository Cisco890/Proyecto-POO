import java.time.LocalDateTime;
import java.util.*;

abstract class Movimiento {
    protected String placa;
    protected int hours;
    protected int minutes;
    protected int seconds;
    protected LocalDateTime horaEntrada;
    protected LocalDateTime horaSalida;

    public void ingresoVehiculo(){

    }

    public void salidaVehiculo(){

    }

    public void CalcularTarifa(LocalDateTime horaEntrada, LocalDateTime horaSalida) {
        double tarifaPorHora = 10.0;
        long diferenciaTiempo = horaSalida - horaEntrada;
        double horasRedondeadas = Math.ceil((double) diferenciaTiempo / 3600000);
        double costoTotal = horasRedondeadas * tarifaPorHora;
        System.out.println(costoTotal);
    }
}