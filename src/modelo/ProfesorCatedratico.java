package modelo;

import java.time.LocalDate;

public class ProfesorCatedratico extends Profesor {

    private double valorHora;

    public ProfesorCatedratico(String identificacion, String nombres, String apellidos,
                               LocalDate fechaIngreso, double salarioBase,
                               String perfilAcademico, double valorHora) {
        super(identificacion, nombres, apellidos, fechaIngreso,
              "Profesor Catedrático", salarioBase, perfilAcademico);
        this.valorHora = valorHora;
    }

    public double getValorHora() { return valorHora; }
    public void setValorHora(double valorHora) { this.valorHora = valorHora; }

    @Override
    public String getTipoEmpleado() { return "Profesor Catedrático"; }

    @Override
    public double calcularSalarioTotal() {
        // Catedrático: salario base por horas + bonificación
        return getSalarioBase() + calcularBonificacion();
    }
}
