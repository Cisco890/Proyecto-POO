import java.util.*;

public class MovimientoRegular implements Movimiento {

    private String placa;
    private Date horaEntrada;
    private Date horaSalida;
    private Date fecha;

    public MovimientoRegular(String placa, Date horaEntrada, Date horaSalida, Date fecha){
       this.placa = placa;
       this.horaEntrada = horaEntrada;
       this.horaSalida = horaSalida;
       this.fecha = fecha;
    }

    public String getplaca(){
        return placa;
    }

    public Date gethoraEntrada(){
        return horaEntrada;
    }

    public Date gethoraSalida(){
        return horaSalida;
    }

    public Date getfecha(){
        return fecha;
    }

    public void ingresoVehiculo(){

    }
    public void salidaVehiculo(){

    }

    
}
