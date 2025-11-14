package logico;

public class Vacuna {
    private String id;
    private String nombre;
    private boolean vacunado;
    
    public Vacuna(String nombre) {
        this.nombre = nombre;
        this.vacunado = false;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public boolean estaVacunado() {
        return vacunado;
    }
    
    public void setVacunado(boolean vacunado) {
        this.vacunado = vacunado;
    }
}