import java.util.*;

class Residente extends Cliente {
    private boolean residenteVerificado;

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
}
