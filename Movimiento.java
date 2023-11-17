/** Juan Francisco Martínez 23617, Andrés Mazariegos 21749,  Daniela Ramirez 23053,  Antony Lou Schanwk 23410

  * Movimiento
  * @param placa,horaEntrada,horaSalida
  * @throws Es la clase padre del programa, se encarga de ser extendida por las otras dos

  */
import java.time.LocalDateTime;
//importar la libreria de time.LocalDateTime

public class Movimiento {
    protected String tipoCliente;
    protected String placa;
    protected LocalDateTime horaEntrada;
    protected LocalDateTime horaSalida;
    protected double horasEstacionado;
    protected double total;

    public Movimiento(String tipoCliente, String placa, LocalDateTime horaEntrada, LocalDateTime horaSalida, double redonder, double total) {//constructor de Movimiento
        this.tipoCliente = tipoCliente;
        this.placa = placa;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.horasEstacionado = redonder;
        this.total = total;
    }

    public String getTipoCliente() {
        return this.tipoCliente;
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

    public double getHorasEstacionado() {
        return this.horasEstacionado;
    }

    public double getTotal() {
        return this.total;
    }

    public void setTipoCliente(String tipoCliente) {
        this.tipoCliente = tipoCliente;
    }

    public void setHoraSalida(LocalDateTime horaSalida) {
        this.horaSalida = horaSalida;
    }

    public void setHoraEntrada(LocalDateTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public void setHorasEstacionado(double horasEstacionado) {
        this.horasEstacionado = horasEstacionado;
    }

    public void setTotal(double total) {
        this.total = total;
    }
    //Stters y getters de la clase Movimientos
}
