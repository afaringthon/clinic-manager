package logico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public final class Datos {

	private Datos() {}
	
	public static void guardar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("datos.dat"))) {
            oos.writeObject(Clinica.getInstancia());
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
    public static void cargar() {
        File f = new File("datos.dat");
        if (!f.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof Clinica) {
                Clinica loaded = (Clinica) obj;
                Clinica.setInstancia(loaded);
            } else {
                System.err.println("El archivo no contiene un objeto Clinica válido.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	

}


/*//al inicio del main (antes de crear la UI)
logico.Datos.load();

//opcional: hook de respaldo para guardar después si JVM finaliza


//luego crear y mostrar la UI:
javax.swing.SwingUtilities.invokeLater(() -> {
 login frame = new login();
 frame.setVisible(true);
});*/