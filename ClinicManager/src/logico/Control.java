package logico;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Control implements Serializable {

    private static final long serialVersionUID = 1L;

    private static Control control; //Instancia
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private static Usuario loggedUsuario;

    private Control() {
    	//Se inizializa arriba
    }

    public static Control getInstance() {
    	
        if (control == null) 
        {
            control = new Control();
        }
        return control;
    }
    
    public static void setInstancia(Control c)
    {
    	control = c;
    }

    public ArrayList<Usuario> getMisUsuarios() {
        return usuarios;
    }

    public void setMisUsers(ArrayList<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public static Control getControl() {
        return control;
    }

    public static void setControl(Control control) {
        Control.control = control;
    }

    public static Usuario getLoggedUsuario() {
        return loggedUsuario;
    }

    public static void setLoginUser(Usuario loggedUsuario) {
        Control.loggedUsuario = loggedUsuario;
    }

    public void regUser(Usuario usuario) {
        usuarios.add(usuario);
    }

    public boolean userNameExists(String username) {
        if (username == null) return false;
        for (Usuario u : usuarios) {
            if (username.equalsIgnoreCase(u.getNombreUsuario())) {
            	return true;
            }
        }
        return false;
    }

    public boolean confirmLogin(String nombreUsuario, String clave) {
        if (nombreUsuario == null || clave == null) return false;
        for (Usuario u : usuarios) {
            if (u.getNombreUsuario().equals(nombreUsuario)) {
            	if(u == null) continue;
            	String claveStored = u.getClave();
            	String hashedClave = md5(clave);
            	
            	if(hashedClave.equals(claveStored))
            	{
            		loggedUsuario = u;
            		return true;
            	}
            }
        }
        return false;
    }
    
    public boolean guardarAlDisco()
    {
    	File file = new File("usuarios.dat");
    	try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file)))
    	{
    		oos.writeObject(getInstance());
            return true;

    	}catch(IOException e)
    	{
    		e.printStackTrace();
            return false;
    	}
    	
    }
    
    public String buscarUsuarioId(String nombreUsuario)
    {
    	for (Usuario u : usuarios)
    	{
    		if(u.getNombreUsuario().equalsIgnoreCase(nombreUsuario))
    		{
    			return u.getLinkId();
    			
    		}
    	}
    	return null;
    }
    
    public Usuario buscarUsuario(String nombreUsuario)
    {
    	for (Usuario u : usuarios)
    	{
    		if(u.getNombreUsuario().equalsIgnoreCase(nombreUsuario))
    		{
    			return u;
    		}
    	}
    	
    	return null;
    }
    
    public static boolean cargarDelDisco()
    {
    	File file = new File("usuarios.dat");
    	if (!file.exists()) return false;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Control c = (Control) ois.readObject();
            setControl(c);
            return true;
        }
        catch(IOException | ClassNotFoundException e)
        {
        	e.printStackTrace();
            return false;
        }
    	
    }
    
    public static String md5(String clave)
    {
    	try
    	{
    		MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(clave.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) sb.append(String.format("%02x", b & 0xff));
            return sb.toString();
    	}
    	catch(Exception e)
    	{
    		throw new RuntimeException("Error calculando MD5", e);
    	}
    	
    }
    
    public void borrarUsuarioPorLinkId(String linkId)
    {
    	for (Usuario u : usuarios)
    	{
    		if(u.getLinkId().equalsIgnoreCase(linkId))
    		{
    			usuarios.remove(u);
    		}
    	}
    }
}