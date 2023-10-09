import java.util.*;
import java.util.concurrent.TimeUnit;


class ClienteRegular extends Cliente {
    private Date horaEntrada;

    public ClienteRegular(String placa, String marca, String color, Date horaEntrada) {
        super(placa, marca, color);
        this.horaEntrada = horaEntrada;
    }

    public double calcularTarifa(Date horaSalida, Date horaSalidaClienteRegular) {
        long tiempoEstacionadoMillis = horaSalida.getTime() - horaEntrada.getTime();
        long tiempoEstacionadoHoras = TimeUnit.MILLISECONDS.toHours(tiempoEstacionadoMillis);
        long tiempoEstacionadoMinutos = TimeUnit.MILLISECONDS.toMinutes(tiempoEstacionadoMillis) % 60;

        double tarifaPorHora = 10.0;

        double costoTotal = (tiempoEstacionadoHoras + (tiempoEstacionadoMinutos / 60.0)) * tarifaPorHora;

        return costoTotal;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }
}