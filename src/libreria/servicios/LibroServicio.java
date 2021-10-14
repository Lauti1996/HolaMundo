/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package libreria.servicios;

import java.util.Scanner;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import libreria.entidades.Autor;
import libreria.entidades.Editorial;
import libreria.entidades.Libro;

/**
 *
 * @author lauta
 */
public class LibroServicio {

    EntityManager em = Persistence.createEntityManagerFactory("LibreriaPU").createEntityManager();
    Scanner leer = new Scanner(System.in).useDelimiter("\n");
    AutorServicio as = new AutorServicio();
    EditorialServicio es = new EditorialServicio();

    //creación
    public void crearLibro() {
        try {
            Libro libro = new Libro();
            System.out.println("Ingrese ISBN del libro: (9 dígitos)");
            libro.setISBN(leer.nextLong());
            System.out.println("Ingrese el nombre del libro: ");
            libro.setTitulo(leer.next());
            System.out.println("Ingrese el año del libro:");
            libro.setAnio(leer.nextInt());
            System.out.println("Ingrese el núm de ejemplares del libro:");
            libro.setEjemplares(leer.nextInt());
            System.out.println("Ingrese el núm de ejemplares prestados:");
            libro.setEjemplaresPrestados(leer.nextInt());
            System.out.println("Ingrese el núm de ejemplares restantes:");
            libro.setEjemplaresRestantes(leer.nextInt());
            Autor a = as.autores();
            if (a != null) {
                libro.setAutor(a);
            } else {
                Autor autor = new Autor();
                System.out.println("Ingrese el nombre del Autor: ");
                autor.setNombre(leer.next());
                libro.setAutor(autor);
                as.agregarAutor(autor);
            }
            Editorial e = es.editoriales();
            if (e != null) {
                libro.setEditorial(e);
            } else {
                Editorial editorial = new Editorial();
                System.out.println("Ingrese el nombre de la editorial: ");
                editorial.setNombre(leer.next());
                libro.setEditorial(editorial);
                es.agregarEditorial(editorial);
            }
            em.getTransaction().begin();
            em.persist(libro);
            em.getTransaction().commit();
        } catch (Exception e) {
            System.out.println("Error al cargar libro");
        }

    }
}
