package modelo;

import java.util.ArrayList;
import java.util.List;

public class Nomina {

    private List<Empleado> empleados;

    public Nomina() {
        this.empleados = new ArrayList<>();
    }

    public void agregarEmpleado(Empleado e) { empleados.add(e); }

    public boolean eliminarEmpleado(String identificacion) {
        return empleados.removeIf(e -> e.getIdentificacion().equals(identificacion));
    }

    public Empleado buscarEmpleado(String identificacion) {
        for (Empleado e : empleados) {
            if (e.getIdentificacion().equals(identificacion)) return e;
        }
        return null;
    }

    public List<Empleado> getEmpleados() { return empleados; }

    public List<Profesor> getProfesores() {
        List<Profesor> lista = new ArrayList<>();
        for (Empleado e : empleados) {
            if (e instanceof Profesor) lista.add((Profesor) e);
        }
        return lista;
    }

    public List<Administrativo> getAdministrativos() {
        List<Administrativo> lista = new ArrayList<>();
        for (Empleado e : empleados) {
            if (e instanceof Administrativo) lista.add((Administrativo) e);
        }
        return lista;
    }

    public double calcularTotalNomina() {
        double total = 0;
        for (Empleado e : empleados) total += e.calcularSalarioTotal();
        return total;
    }
}
