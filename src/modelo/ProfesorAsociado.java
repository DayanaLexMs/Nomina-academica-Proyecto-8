package modelo;

import java.time.LocalDate;

public class ProfesorAsociado extends Profesor {

    private boolean tieneDedicacionExclusiva;

    public ProfesorAsociado(String identificacion, String nombres, String apellidos,
                            LocalDate fechaIngreso, double salarioBase,
                            String perfilAcademico, boolean tieneDedicacionExclusiva) {
        super(identificacion, nombres, apellidos, fechaIngreso,
              "Profesor Asociado", salarioBase, perfilAcademico);
        this.tieneDedicacionExclusiva = tieneDedicacionExclusiva;
    }

    public boolean isTieneDedicacionExclusiva() { return tieneDedicacionExclusiva; }
    public void setTieneDedicacionExclusiva(boolean b) { this.tieneDedicacionExclusiva = b; }

    @Override
    public String getTipoEmpleado() { return "Profesor Asociado"; }

    @Override
    public double calcularSalarioTotal() {
        double salario = getSalarioBase() + calcularBonificacion();
        // Asociado con dedicación exclusiva tiene adicional del 10%
        if (tieneDedicacionExclusiva) salario += getSalarioBase() * 0.10;
        return salario;
    }
}
