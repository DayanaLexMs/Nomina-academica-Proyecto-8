package modelo;

import java.util.ArrayList;
import java.util.List;

public class Proyecto {

    public enum TipoProyecto {
        INVESTIGACION("Investigación"),
        EXTENSION("Extensión");

        private final String descripcion;
        TipoProyecto(String d) { this.descripcion = d; }
        public String getDescripcion() { return descripcion; }

        @Override
        public String toString() { return descripcion; }
    }

    private String codigo;
    private String nombre;
    private TipoProyecto tipo;
    private List<Producto> productos;

    public Proyecto(String codigo, String nombre, TipoProyecto tipo) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.productos = new ArrayList<>();
    }

    public void agregarProducto(Producto producto) { productos.add(producto); }

    public int calcularPuntosTotales() {
        int total = 0;
        for (Producto p : productos) total += p.getPuntos();
        return total;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public TipoProyecto getTipo() { return tipo; }
    public void setTipo(TipoProyecto tipo) { this.tipo = tipo; }

    public List<Producto> getProductos() { return productos; }

    @Override
    public String toString() {
        return "[" + codigo + "] " + nombre + " (" + tipo.getDescripcion() + ")";
    }
}
