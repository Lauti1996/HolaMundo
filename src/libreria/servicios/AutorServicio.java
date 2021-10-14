package libreria.servicios;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import libreria.entidades.Autor;


public class AutorServicio {

    EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();
    Scanner leer = new Scanner(System.in).useDelimiter("\n");

    //consulta
    public void buscarAutorPorNombre() {
        try {
            System.out.println("Ingrese el nombre del autor a buscar:");
            String nombre = leer.next();
            Autor autor = (Autor) em.createQuery("SELECT a FROM Autor a "
                    + "WHERE a.nombre = :nombre").setParameter("nombre", nombre)
                    .getSingleResult();

            System.out.println("El Id del autor: " + autor.getId());
            System.out.println("El nombre del autor: " + autor.getNombre());
        } catch (Exception e) {
            System.out.println("No se encontró al autor");
        }

    }

    //creación
    public void crearAutor() {
        try {
            Autor autor = new Autor();
            System.out.println("Ingrese el nombre del Autor: ");
            autor.setNombre(leer.next());
            em.getTransaction().begin();
            em.persist(autor);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al cargar autor");
        }

    }
    public void agregarAutor(Autor autor){
        try {
            em.getTransaction().begin();
            em.persist(autor);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al cargar autor dado por parámetro");
        }
    }

    //modificación
    public void modificarAutor() {
        try {
            System.out.println("Ingrese el nombre del autor a modificar");
            String nombre = leer.next();
            Autor autor = (Autor) em.createQuery("SELECT a FROM Autor a "
                    + "WHERE a.nombre = :nombre").setParameter("nombre", nombre)
                    .getSingleResult();
            System.out.println("Ingrese el nuevo nombre del autor");
            String nombreNuevo = leer.next();
            autor.setNombre(nombreNuevo);
            em.getTransaction().begin();
            em.merge(autor);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al modificar el autor");
        }

    }

    //eliminación
    public void eliminarAutor() {
        try {
            System.out.println("Ingrese el nombre del autor a eliminar");
            String nombre = leer.next();
            Autor autor = (Autor) em.createQuery("SELECT a FROM Autor a "
                    + "WHERE a.nombre = :nombre").setParameter("nombre", nombre)
                    .getSingleResult();
            autor.setAlta(false);
            em.getTransaction().begin();
            em.remove(autor);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al eliminar autor");
        }
        
    }
    
    public Autor autores(){
        List<Autor> autores = em.createQuery("SELECT a FROM Autor a")
                .getResultList();
        System.out.println("El autor del libro es alguno de los que tenemos "
                + "cargados en el sistema? si - no");
        for (int i = 1; i <= autores.size(); i++) {
            System.out.println(i+")"+autores.get(i-1).getNombre());
        }
        
        String r = leer.next();
        if(r.equals("si")){
            System.out.println("Indique el número");
            int n = leer.nextInt();
            return autores.get(n-1);
        } else {
            return null;
        }
    }
    
}
