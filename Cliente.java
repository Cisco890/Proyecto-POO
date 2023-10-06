/** Anthony Lou, Andres Mazariegos, Juan Francisco Martinez, Daniela Ramirez 

  * Cliente
 
  * @param placa,tarifa 
  * @throws Es la clase padre de las otras dos
  */
//import java.util.*;

public  class Cliente {
    protected String Tipovehiculo;
    protected String placa;
    protected  String color;
    protected  String marca;

    protected Cliente (String Tipovehiculo,String placa, String color, String marca){
        this.Tipovehiculo = Tipovehiculo;
        this.placa = placa;
        this.color = color;
        this.marca = marca;
    }
    protected String getTipovehiculo(){
        return Tipovehiculo;
    }

    protected String getPlaca( ) {
        return placa;
    }
    protected String getColor( ) {
        return color;
    }
    protected String getMarca(){
        return marca; 
    }


    //public double calcularTarifa(Date horaEntrada, Date horaSalida) {
      //  return 0.0; 
    }// se finalizan los getters del objeto ClienteRegular

