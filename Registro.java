public class Registro {
    public String tipoCliente;
    public String placa;
    public String horaEntrada;
    public String horaSalida;
    public double redondear;
    public double total;

    public Registro(String tipoCliente, String placa, String horaEntrada, String horaSalida, double redondear, double total){
        this.tipoCliente = tipoCliente;
        this.placa = placa;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
        this.redondear = redondear;
        this.total = total;
    }
}
