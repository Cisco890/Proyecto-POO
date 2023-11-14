/** Juan Francisco Martínez 23617, Andrés Mazariegos 21749,  Daniela Ramirez 23053,  Antony Lou Schanwk 23410

  * MovimientoResidente
 
  * @param placa,horaEntrada,horaSalida
  * @throws encargado de construir el MovimientoResidente, extiende a movimiento

  */
import java.time.LocalDateTime;
//importar la libreria de time.LocalDateTime
public class MovimientoResidente extends Movimiento {

    private String placa;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;

    public MovimientoResidente(String placa, LocalDateTime horaEntrada, LocalDateTime horaSalida){//constructor de MovimientoResidente
        super(placa, horaEntrada, horaSalida);//super de la clase padre Movimiento
        this.placa = placa;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    public String getplaca(){
        return placa;
    }

    public LocalDateTime gethoraEntrada(){
        return horaEntrada;
    }

    public LocalDateTime gethoraSalida(){
        return horaSalida;
    }

    public void ingresoVehiculo(){
        
    }
    public void salidaVehiculo(){

    }

    public String getPlaca() {
        return placa;
    }
//getters y setters de MovimientoResidente
    
}