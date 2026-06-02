package modelo;

import java.time.LocalDate;

public class Administrativo extends Empleado {

    private String area;
    private String nivelEscalafon;

    public Administrativo(String identificacion, String nombres, String apellidos,
                          LocalDate fechaIngreso, String cargo, double salarioBase,
                          String area, String nivelEscalafon) {
        super(identificacion, nombres, apellidos, fechaIngreso, cargo, salarioBase);
        this.area = area;
        this.nivelEscalafon = nivelEscalafon;
    }

    @Override
    public double calcularSalarioTotal() {
        // Administrativo: salario base + auxilio de transporte fijo
        double auxilioTransporte = 162000;
        return getSalarioBase() + auxilioTransporte;
    }

    @Override
    public String getTipoEmpleado() { return "Administrativo"; }

    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }

    public String getNivelEscalafon() { return nivelEscalafon; }
    public void setNivelEscalafon(String nivel) { this.nivelEscalafon = nivel; }
}
