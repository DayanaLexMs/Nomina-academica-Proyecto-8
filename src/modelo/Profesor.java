package modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Profesor extends Empleado {

    private String perfilAcademico;
    private List<Asignatura> asignaturas;
    private List<Proyecto> proyectos;

    public Profesor(String identificacion, String nombres, String apellidos,
                    LocalDate fechaIngreso, String cargo, double salarioBase,
                    String perfilAcademico) {
        super(identificacion, nombres, apellidos, fechaIngreso, cargo, salarioBase);
        this.perfilAcademico = perfilAcademico;
        this.asignaturas = new ArrayList<>();
        this.proyectos = new ArrayList<>();
    }

    public void agregarAsignatura(Asignatura a) { asignaturas.add(a); }
    public void agregarProyecto(Proyecto p)     { proyectos.add(p); }

    public int calcularPuntosAcumulados() {
        int total = 0;
        for (Proyecto p : proyectos) total += p.calcularPuntosTotales();
        return total;
    }

    public double calcularBonificacion() {
        int puntos = calcularPuntosAcumulados();
        if (puntos >= 1  && puntos <= 20) return getSalarioBase() * 0.05;
        if (puntos >= 21 && puntos <= 40) return getSalarioBase() * 0.10;
        if (puntos > 40)                  return getSalarioBase() * 0.15;
        return 0;
    }

    @Override
    public double calcularSalarioTotal() {
        return getSalarioBase() + calcularBonificacion();
    }

    public String getPerfilAcademico() { return perfilAcademico; }
    public void setPerfilAcademico(String p) { this.perfilAcademico = p; }

    public List<Asignatura> getAsignaturas() { return asignaturas; }
    public List<Proyecto>   getProyectos()   { return proyectos; }
}
