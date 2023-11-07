import java.time.LocalDateTime;

public class Movimiento{
    protected String placa;
    protected LocalDateTime horaEntrada;
    protected LocalDateTime horaSalida;

    public Movimiento(String placa, LocalDateTime horaEntrada, LocalDateTime horaSalida) {  
        this.placa = placa;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }
    

    public void ingresoVehiculo(){

    }

    public void salidaVehiculo(){

    }

    public void CalcularTarifa(LocalDateTime horaEntrada, LocalDateTime horaSalida) {
    }
    public LocalDateTime getHoraEntrada(){
        return this.horaEntrada;
    }
}
