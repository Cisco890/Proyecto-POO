/** Anthony Lou, Andres Mazariegos, Juan Francisco Martinez, Daniela Ramirez 

  * Cliente
 
  * @param placa,marca,color 
  * @throws Es la clase padre de las otras dos
  */


import java.util.*;


abstract class Cliente {
    protected String placa;
    protected String marca;
    protected String color;
//atributos de la clase
    public Cliente(String placa, String marca, String color) {
        this.placa = placa;
        this.marca = marca;
        this.color = color;//setters de los atributos
    }

    public abstract Date getHoraEntrada();//get hora de entrada
}
