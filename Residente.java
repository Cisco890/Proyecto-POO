import java.time.LocalDateTime;

public class Residente extends Movimiento {
    boolean residenteVerificado;
    boolean pagoSolvente;
    int hours;
    int minutes;
    int seconds;
    LocalDateTime horaEntrada;
    LocalDateTime horaSalida;


    public Residente(String placa, String marca, String color, boolean residenteVerificado, boolean pagoSolvente) {
        super(placa, marca, color);
        this.placa=placa;
        this.marca=marca;
        this.color=color;
    }

    public LocalDateTime gethoraEntrada(){
        return horaEntrada;
    }

    public LocalDateTime gethoraSalida(){
        return horaSalida;
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

    public void residenteVerificado() {
        residenteVerificado=true;
    }

    public void pagoSolvente() {
        pagoSolvente=true;
    }

}
