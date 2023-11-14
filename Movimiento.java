/** Juan Francisco Martínez 23617, Andrés Mazariegos 21749,  Daniela Ramirez 23053,  Antony Lou Schanwk 23410

  * Movimiento
  * @param placa,horaEntrada,horaSalida
  * @throws Es la clase padre del programa, se encarga de ser extendida por las otras dos

  */
import java.time.LocalDateTime;
//importar la libreria de time.LocalDateTime

public class Movimiento {
    protected String placa;
    protected LocalDateTime horaEntrada;
    protected LocalDateTime horaSalida;

    public Movimiento(String placa, LocalDateTime horaEntrada, LocalDateTime horaSalida) {//constructor de Movimiento
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
    //Stters y getters de la clase Movimientos
}
