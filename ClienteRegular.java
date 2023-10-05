import java.util.*;
import java.util.concurrent.TimeUnit;

class ClienteRegular extends Cliente {
    private String color;
    private String marca;
    private Date horaEntrada;
    private String parqueoAsignado;

    public ClienteRegular(String placa, String color, String marca, Date horaEntrada, String parqueoAsignado) {
        super(placa);
        this.color = color;
        this.marca = marca;
        this.horaEntrada = horaEntrada;
        this.parqueoAsignado = parqueoAsignado;
    }

    public double calcularTarifa(Date horaSalida) {
        long tiempoEstacionadoMillis = horaSalida.getTime() - horaEntrada.getTime();
        long tiempoEstacionadoHoras = TimeUnit.MILLISECONDS.toHours(tiempoEstacionadoMillis);
        long tiempoEstacionadoMinutos = TimeUnit.MILLISECONDS.toMinutes(tiempoEstacionadoMillis) % 60;

        double tarifaPorHora = 10.0;
        double tarifaPorMediaHora = 5.0;

        double costoTotal = (tiempoEstacionadoHoras * tarifaPorHora) + (tiempoEstacionadoMinutos / 30 * tarifaPorMediaHora);

        return costoTotal;
    }

    public String getColor() {
        return color;
    }

    public String getMarca() {
        return marca;
    }

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
    }
}