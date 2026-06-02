package controlador;

import modelo.*;
import java.time.LocalDate;
import java.util.List;

public class NominaControlador {

    private Nomina nomina;

    public NominaControlador() {
        this.nomina = new Nomina();
        cargarDatosDemostracion();
    }

    // ─────────────────────── EMPLEADOS ───────────────────────

    public void registrarAdministrativo(String id, String nombres, String apellidos,
                                        LocalDate fechaIngreso, String cargo,
                                        double salario, String area, String escalafon) {
        Administrativo a = new Administrativo(id, nombres, apellidos,
                fechaIngreso, cargo, salario, area, escalafon);
        nomina.agregarEmpleado(a);
    }

    public void registrarProfesorCatedratico(String id, String nombres, String apellidos,
                                              LocalDate fechaIngreso, double salario,
                                              String perfil, double valorHora) {
        ProfesorCatedratico p = new ProfesorCatedratico(id, nombres, apellidos,
                fechaIngreso, salario, perfil, valorHora);
        nomina.agregarEmpleado(p);
    }

    public void registrarProfesorOcasional(String id, String nombres, String apellidos,
                                           LocalDate fechaIngreso, double salario,
                                           String perfil, int horasContrato) {
        ProfesorOcasional p = new ProfesorOcasional(id, nombres, apellidos,
                fechaIngreso, salario, perfil, horasContrato);
        nomina.agregarEmpleado(p);
    }

    public void registrarProfesorAsociado(String id, String nombres, String apellidos,
                                          LocalDate fechaIngreso, double salario,
                                          String perfil, boolean dedicacionExclusiva) {
        ProfesorAsociado p = new ProfesorAsociado(id, nombres, apellidos,
                fechaIngreso, salario, perfil, dedicacionExclusiva);
        nomina.agregarEmpleado(p);
    }

    public boolean eliminarEmpleado(String id) {
        return nomina.eliminarEmpleado(id);
    }

    public Empleado buscarEmpleado(String id) {
        return nomina.buscarEmpleado(id);
    }

    public List<Empleado> getEmpleados() {
        return nomina.getEmpleados();
    }

    public List<Profesor> getProfesores() {
        return nomina.getProfesores();
    }

    // ─────────────────────── ASIGNATURAS ───────────────────────

    public boolean agregarAsignaturaAProfesor(String idProfesor, String codigo,
                                               String nombre, int creditos, int horas) {
        Empleado e = nomina.buscarEmpleado(idProfesor);
        if (e instanceof Profesor) {
            Asignatura a = new Asignatura(codigo, nombre, creditos, horas);
            ((Profesor) e).agregarAsignatura(a);
            return true;
        }
        return false;
    }

    // ─────────────────────── PROYECTOS ───────────────────────

    public boolean agregarProyectoAProfesor(String idProfesor, String codProy,
                                             String nombreProy, Proyecto.TipoProyecto tipo) {
        Empleado e = nomina.buscarEmpleado(idProfesor);
        if (e instanceof Profesor) {
            Proyecto p = new Proyecto(codProy, nombreProy, tipo);
            ((Profesor) e).agregarProyecto(p);
            return true;
        }
        return false;
    }

    public boolean agregarProductoAProyecto(String idProfesor, String codProyecto,
                                             String titulo, Producto.TipoProducto tipo, int puntos) {
        Empleado e = nomina.buscarEmpleado(idProfesor);
        if (e instanceof Profesor) {
            for (Proyecto proy : ((Profesor) e).getProyectos()) {
                if (proy.getCodigo().equals(codProyecto)) {
                    Producto prod = new Producto(titulo, tipo, puntos);
                    proy.agregarProducto(prod);
                    return true;
                }
            }
        }
        return false;
    }

    // ─────────────────────── NÓMINA ───────────────────────

    public double calcularTotalNomina() {
        return nomina.calcularTotalNomina();
    }

    // ─────────────────────── DATOS DEMO ───────────────────────

    private void cargarDatosDemostracion() {
        // Profesor Asociado
        ProfesorAsociado p1 = new ProfesorAsociado("1010101010", "Laura", "Martínez",
                LocalDate.of(2018, 3, 15), 4500000, "Ingeniería de Software", true);
        Asignatura a1 = new Asignatura("IS101", "Programación I", 3, 48);
        Asignatura a2 = new Asignatura("IS202", "POO", 4, 64);
        p1.agregarAsignatura(a1);
        p1.agregarAsignatura(a2);
        Proyecto proy1 = new Proyecto("INV-001", "IA en Educación", Proyecto.TipoProyecto.INVESTIGACION);
        proy1.agregarProducto(new Producto("Artículo IEEE 2024", Producto.TipoProducto.ARTICULO));
        proy1.agregarProducto(new Producto("Software Educativo", Producto.TipoProducto.SOFTWARE));
        p1.agregarProyecto(proy1);
        nomina.agregarEmpleado(p1);

        // Profesor Catedrático
        ProfesorCatedratico p2 = new ProfesorCatedratico("2020202020", "Carlos", "Pérez",
                LocalDate.of(2021, 8, 1), 2800000, "Matemáticas Aplicadas", 35000);
        Asignatura a3 = new Asignatura("MAT301", "Cálculo Diferencial", 4, 64);
        p2.agregarAsignatura(a3);
        Proyecto proy2 = new Proyecto("EXT-001", "Matemáticas para la Comunidad", Proyecto.TipoProyecto.EXTENSION);
        proy2.agregarProducto(new Producto("Ponencia LACLO 2024", Producto.TipoProducto.PONENCIA));
        p2.agregarProyecto(proy2);
        nomina.agregarEmpleado(p2);

        // Profesor Ocasional
        ProfesorOcasional p3 = new ProfesorOcasional("3030303030", "Ana", "Gómez",
                LocalDate.of(2023, 1, 10), 3200000, "Redes y Telecomunicaciones", 160);
        Asignatura a4 = new Asignatura("RED401", "Redes I", 3, 48);
        p3.agregarAsignatura(a4);
        nomina.agregarEmpleado(p3);

        // Administrativo
        Administrativo adm1 = new Administrativo("4040404040", "Juan", "López",
                LocalDate.of(2015, 6, 20), "Coordinador Académico", 3800000,
                "Decanatura de Ingeniería", "Nivel 3");
        nomina.agregarEmpleado(adm1);

        // Otro Administrativo
        Administrativo adm2 = new Administrativo("5050505050", "María", "Torres",
                LocalDate.of(2020, 2, 5), "Secretaria", 2500000,
                "Rectoría", "Nivel 1");
        nomina.agregarEmpleado(adm2);
    }
}
