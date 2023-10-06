/** Anthony Lou, Andres Mazariegos, Juan Francisco Martinez, Daniela Ramirez 

  * Cliente
 
  * @param placa,tarifa 
  * @throws Es la clase padre de las otras dos
  */
public class Residente extends Cliente{
    private String nombre;
    private String placa;
    private String Tipovehiculo;
    private String color;
    private String marca;
    
    public Residente(String nombre, String Tipovehiculo, String placa, String color, String marca) {
        super(Tipovehiculo, placa, color, marca);
        this.nombre = nombre;
        this.placa = placa;
        this.Tipovehiculo = Tipovehiculo;
        this.color = color;
        this.marca = marca;
    }

    public String getnombre(){
        return nombre;
    }
// se finalizan los getters del objeto ClienteRegular

}

