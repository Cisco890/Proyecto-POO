import java.util.*;
import java.util.concurrent.TimeUnit;


class ClienteRegular extends Cliente {
    private Date horaEntrada;
    private Date horaSalida;

    public ClienteRegular(String placa, String marca, String color, Date horaEntrada, Date horaSalida) {
        super(placa, marca, color);
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    public double calcularTarifa(Date horaSalida, Date horaEntrada) {
        long tiempoEstacionadoMillis = horaSalida.getTime() - horaEntrada.getTime();
        long tiempoEstacionadoHoras = TimeUnit.MILLISECONDS.toHours(tiempoEstacionadoMillis);

        double tarifaPorHora = 10.0;

        double costoTotal = tiempoEstacionadoHoras * tarifaPorHora;

        return costoTotal;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }
}