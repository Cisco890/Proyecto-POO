/** Anthony Lou, Andres Mazariegos, Juan Francisco Martinez, Daniela Ramirez 

  * Cliente
 
  * @param placa,tarifa 
  * @throws Es la clase padre de las otras dos
  */
import java.util.*;

class Cliente {
    protected String placa;

    public Cliente(String placa) {
        this.placa = placa;
    }

    public double calcularTarifa(Date horaEntrada, Date horaSalida) {
        return 0.0; 
    }// se finalizan los getters del objeto ClienteRegular
}
