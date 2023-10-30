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

    public Date getHoraEntrada() {
        return null;
    }

    public double calcularTarifa(Date horaSalida) {
        int costoTotal=ParqueoMain.meses*500;
        return costoTotal; // Cambia esto según el cálculo real
    }
}
