import java.util.*;


abstract class Cliente {
    protected String placa;
    protected String marca;
    protected String color;

    public Cliente(String placa, String marca, String color) {
        this.placa = placa;
        this.marca = marca;
        this.color = color;
    }

    public abstract Date getHoraEntrada();
}