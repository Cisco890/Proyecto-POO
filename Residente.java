public class Residente {
    private String nombre;
    private String placa;
    private String Tipovehiculo;
    private String color;
    private String marca;
    
    public Residente(String nombre, String Tipovehiculo, String placa, String color, String marca) {
        this.nombre = nombre;
        this.placa = placa;
        this.Tipovehiculo = Tipovehiculo;
        this.color = color;
        this.marca = marca;
    }

    public String getnombre(){
        return nombre;
    }

    public String getPlaca() {
        return placa;
    }// se finalizan los getters del objeto ClienteRegular

    public String getTipovehiculo() {
        return Tipovehiculo;
    }

    public String getcolor() {
        return color;
    }
    
    public String getmarca() {
        return marca;
    }

}
