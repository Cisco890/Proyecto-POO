import java.time.LocalDateTime;

public class Movimiento {
    protected String placa;
    protected LocalDateTime horaEntrada;
    protected LocalDateTime horaSalida;

    public Movimiento(String placa, LocalDateTime horaEntrada, LocalDateTime horaSalida) {
        this.placa = placa;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    public String getPlaca() {
        return this.placa;
    }

    public LocalDateTime getHoraEntrada() {
        return this.horaEntrada;
    }

    public LocalDateTime getHoraSalida() {
        return this.horaSalida;
    }

    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }
}
