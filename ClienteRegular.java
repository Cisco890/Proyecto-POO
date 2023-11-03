import java.time.LocalDateTime;
import java.util.*;

class ClienteRegular extends Cliente {
    int hours;
    int minutes;
    int seconds;
    LocalDateTime horaEntrada;
    LocalDateTime horaSalida;

    public ClienteRegular(String placa, String marca, String color, LocalDateTime horaEntrada, LocalDateTime horaSalida) {
        super(placa, marca, color);
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    
    }

    public LocalDateTime gethoraEntrada(){
        return horaEntrada;
    }

    public LocalDateTime gethoraSalida(){
        return horaSalida;
    }

    public void CalcularTarifa(Date horaEntrada, Date horaSalida) {
        double tarifaPorHora = 10.0;
        long diferenciaTiempo = horaSalida.getTime() - horaEntrada.getTime();
        double horasRedondeadas = Math.ceil((double) diferenciaTiempo / 3600000); // 3600000 ms = 1 hora
        double costoTotal = horasRedondeadas * tarifaPorHora;
        System.out.println(costoTotal);
    }
}