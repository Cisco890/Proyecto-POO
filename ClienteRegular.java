/** Anthony Lou, Andres Mazariegos, Juan Francisco Martinez, Daniela Ramirez 

  * ClienteRegular
 
  * @param calcularTarifa,getHoraEntrada,getHoraSalida 
  * @throws Es la clase que se encarga de registrar todo lo relacionado a los clientes del parqueo, su hora de netrada, cuanto deben,
  */
import java.util.*;
import java.util.concurrent.TimeUnit;


class ClienteRegular extends Cliente {
    private Date horaEntrada;
    private Date horaSalida;//atributos de la clase

    public ClienteRegular(String placa, String marca, String color, Date horaEntrada, Date horaSalida) {
        super(placa, marca, color);
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    public double calcularTarifa(Date horaSalida, Date horaEntrada) {//tarifa de cobro
        long tiempoEstacionadoMillis =  horaEntrada.getTime()-horaSalida.getTime() ;
        long tiempoEstacionadoHoras = TimeUnit.MILLISECONDS.toHours(tiempoEstacionadoMillis);

        double tarifaPorHora = 10.0;

        double costoTotal = tiempoEstacionadoHoras * tarifaPorHora;

        return costoTotal;
    }

    public Date getHoraEntrada() {
        return horaEntrada;
    }

    public Date getHoraSalida() {
        return horaSalida;
    }
}//fin de los getters de la clase
