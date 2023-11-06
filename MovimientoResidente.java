import java.time.LocalDateTime;

public class MovimientoResidente extends Movimiento {

    private String placa;
    private LocalDateTime horaEntrada;
    private LocalDateTime horaSalida;

    public MovimientoResidente(String placa, LocalDateTime horaEntrada, LocalDateTime horaSalida){
        super(placa, horaEntrada, horaSalida);
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
        return null;
    }

    
}