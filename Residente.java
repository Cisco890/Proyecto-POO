public class Residente {
    boolean residenteVerificado;
    boolean pagoSolvente;
    private String placa;
    private String marca;
    private String color;
    private String modelo;


    public Residente(String placa, String marca, String color, String modelo, boolean pagoSolvente) {
        this.placa=placa;
        this.marca=marca;
        this.color=color;
        this.modelo=modelo;
        this.pagoSolvente=pagoSolvente;
    }

    public String getPlaca(){
        return this.placa;
    }

    public String getMarca(){
        return this.marca;
    }

    public String getColor(){
        return this.color;
    }

    public String getModelo(){
        return this.modelo;
    }

   

    public boolean tienePagoSolvente(){
        return pagoSolvente;
    }

}
