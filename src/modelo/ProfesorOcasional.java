package modelo;

import java.time.LocalDate;

public class ProfesorOcasional extends Profesor {

    private int horasContrato;

    public ProfesorOcasional(String identificacion, String nombres, String apellidos,
                             LocalDate fechaIngreso, double salarioBase,
                             String perfilAcademico, int horasContrato) {
        super(identificacion, nombres, apellidos, fechaIngreso,
              "Profesor Ocasional", salarioBase, perfilAcademico);
        this.horasContrato = horasContrato;
    }

    public int getHorasContrato() { return horasContrato; }
    public void setHorasContrato(int h) { this.horasContrato = h; }

    @Override
    public String getTipoEmpleado() { return "Profesor Ocasional"; }

    @Override
    public double calcularSalarioTotal() {
        // Ocasional: salario base + bonificación académica
        return getSalarioBase() + calcularBonificacion();
    }
}
