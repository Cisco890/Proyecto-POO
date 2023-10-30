import java.util.*; 

abstract class Cliente {
    protected String placa;
    protected String marca;
    protected String color;
    protected Date horaEntrada;
    protected Date horaSalida;

    public Cliente(String placa, String marca, String color) {
        this.placa = placa;
        this.marca = marca;
        this.color = color;
    }

    public abstract void CalcularTarifa(Date horaEntrada, Date horaSalida);
}