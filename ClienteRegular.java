import java.util.*;

class ClienteRegular extends Cliente {
    int hours;
    int minutes;
    int seconds;
    Date horaEntrada;
    Date horaSalida;

    public ClienteRegular(String placa, String marca, String color, Date horaEntrada, Date horaSalida) {
        super(placa, marca, color);
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    
    }

    public Date gethoraEntrada(){
        return horaEntrada;
    }

    public Date gethoraSalida(){
        return horaSalida;
    }

    public void CalcularTarifa(Date horaEntrada, Date horaSalida) {
        double tarifaPorHora = 10.0;
    
        double costoTotal = (horaSalida.getTime()-horaEntrada.getTime())* tarifaPorHora;
        System.out.println(costoTotal);
    }

}