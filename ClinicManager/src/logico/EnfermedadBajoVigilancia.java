package logico;

import java.util.UUID;

public class EnfermedadBajoVigilancia {
    private String id;
    private String nombre;
    
    public EnfermedadBajoVigilancia(String nombre) {
        this.id = UUID.randomUUID().toString();
        this.nombre = nombre;
    }
    
    public String getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}