package modelo;

public class Producto {

    public enum TipoProducto {
        ARTICULO("Artículo", 10),
        LIBRO("Libro", 20),
        SOFTWARE("Software", 15),
        INFORME_TECNICO("Informe Técnico", 8),
        PONENCIA("Ponencia", 12);

        private final String descripcion;
        private final int puntosPorDefecto;

        TipoProducto(String descripcion, int puntos) {
            this.descripcion = descripcion;
            this.puntosPorDefecto = puntos;
        }

        public String getDescripcion() { return descripcion; }
        public int getPuntosPorDefecto() { return puntosPorDefecto; }

        @Override
        public String toString() { return descripcion; }
    }

    private String titulo;
    private TipoProducto tipo;
    private int puntos;

    public Producto(String titulo, TipoProducto tipo) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.puntos = tipo.getPuntosPorDefecto();
    }

    public Producto(String titulo, TipoProducto tipo, int puntos) {
        this.titulo = titulo;
        this.tipo = tipo;
        this.puntos = puntos;
    }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public TipoProducto getTipo() { return tipo; }
    public void setTipo(TipoProducto tipo) { this.tipo = tipo; }

    public int getPuntos() { return puntos; }
    public void setPuntos(int puntos) { this.puntos = puntos; }

    @Override
    public String toString() {
        return tipo.getDescripcion() + ": " + titulo + " (" + puntos + " pts)";
    }
}
