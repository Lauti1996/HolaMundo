/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.servicios;

import java.util.List;
import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import libreria.entidades.Editorial;

/**
 *
 * @author lauta
 */
public class EditorialServicio {

    EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();
    Scanner leer = new Scanner(System.in).useDelimiter("\n");

    //consulta
    public void buscarEditorialPorNombre() {
        try {
            System.out.println("Ingrese el nombre de la editorial a buscar:");
            String nombre = leer.next();
            Editorial editorial = (Editorial) em.createQuery("SELECT e FROM Editorial e "
                    + "WHERE e.nombre = :nombre").setParameter("nombre", nombre)
                    .getSingleResult();

            System.out.println("El Id de la editorial es: " + editorial.getId());
            System.out.println("El nombre del autor: " + editorial.getNombre());
        } catch (Exception e) {
            System.out.println("No se encontró la editorial");
        }

    }

    //creación
    public void crearEditorial() {
        try {
            Editorial editorial = new Editorial();
            System.out.println("Ingrese el nombre de la editorial: ");
            editorial.setNombre(leer.next());
            em.getTransaction().begin();
            em.persist(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al cargar editorial");
        }

    }

    public void agregarEditorial(Editorial editorial) {
        try {

            em.getTransaction().begin();
            em.persist(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al cargar editorial");
        }
    }

    //modificación
    public void modificarEditorial() {
        try {
            System.out.println("Ingrese el nombre de la editorial a modificar");
            String nombre = leer.next();
            Editorial editorial = (Editorial) em.createQuery("SELECT e FROM Editorial e "
                    + "WHERE e.nombre = :nombre").setParameter("nombre", nombre)
                    .getSingleResult();
            System.out.println("Ingrese el nuevo nombre de la editorial");
            String nombreNuevo = leer.next();
            editorial.setNombre(nombreNuevo);
            em.getTransaction().begin();
            em.merge(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al modificar la editorial");
        }

    }

    //eliminación
    public void eliminarEditorial() {
        try {
            System.out.println("Ingrese el nombre de la editorial a eliminar");
            String nombre = leer.next();
            Editorial editorial = (Editorial) em.createQuery("SELECT e FROM Editorial e "
                    + "WHERE e.nombre = :nombre").setParameter("nombre", nombre)
                    .getSingleResult();
            editorial.setAlta(false);
            em.getTransaction().begin();
            em.remove(editorial);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al eliminar editorial");
        }

    }
    public Editorial editoriales(){
        List<Editorial> editoriales = em.createQuery("SELECT e FROM Editorial e")
                .getResultList();
        System.out.println("La editorial del libro es alguna de las que tenemos "
                + "cargadas en el sistema? si - no");
        for (int i = 1; i <= editoriales.size(); i++) {
            System.out.println(i+")"+editoriales.get(i-1).getNombre());
        }
        
        String r = leer.next();
        if(r.equals("si")){
            System.out.println("Indique el número");
            int n = leer.nextInt();
            return editoriales.get(n-1);
        } else {
            return null;
        }
    }
}
