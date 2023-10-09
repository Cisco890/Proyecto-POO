/** Anthony Lou, Andres Mazariegos, Juan Francisco Martinez, Daniela Ramirez 

  * Residente
 
  * @param esResidenteVerificado,calcularT,getHoraEntrada 
  * @throws Es la clase que se encarga de registrar todo lo relacionado a los residentes del parqueo, 

  */
  
import java.util.*;

class Residente extends Cliente {
    private boolean residenteVerificado;//atributos de la clase

    public Residente(String placa, String marca, String color, boolean residenteVerificado) {
        super(placa, marca, color);
        this.residenteVerificado = residenteVerificado;
    }

    public boolean esResidenteVerificado() {
        return residenteVerificado;
    }

    public double calcularTarifa(int meses) {
        double costoPorMes = 500.0;
        double costoTotal = costoPorMes * meses;
        return costoTotal;
    }

    public Date getHoraEntrada() {
        return null;
    }
}//fin de los getters de la clase
