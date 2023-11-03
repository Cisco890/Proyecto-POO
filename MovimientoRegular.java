import java.util.*;
import java.time.LocalDateTime;

public class MovimientoRegular implements Movimiento {

    private String placa;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;
    private Date fecha;

    public MovimientoRegular(String placa, Date horaEntrada, Date horaSalida, Date fecha){
       this.placa = placa;
       this.fecha = fecha;
    }

    public String getplaca(){
        return placa;
    }

    public void horaEntrada(){
        LocalDateTime horaEntrada = LocalDateTime.now();
        this.horaEntrada = horaEntrada;
    }

    public LocalDateTime gethoraEntrada(){
        return this.horaEntrada;
    }

    public void horaSalida(){
        LocalDateTime horaSalida = LocalDateTime.now();
        this.horaSalida = horaSalida;
    }
    public LocalDateTime gethoraSalida(){
        return this.horaSalida;
    }

    public Date getfecha(){
        return fecha;
    }

    public void ingresoVehiculo(){

    }
    public void salidaVehiculo(){

    }

    
}
