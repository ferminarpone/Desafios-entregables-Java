package listar_personas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ListaPersonas {
    private List<Persona> listaPersonas;

    public ListaPersonas() {
        this.listaPersonas = new ArrayList<>();
    }

    public List<Persona> getLista() {
        return listaPersonas;
    }


    public void agregarPersona(Persona persona) {
        this.listaPersonas.add(persona);
        System.out.println("La persona con nombre " + persona.nombre + " fue agregada exitosamente.");
    }

    // Mostrar lista sin sort
    public void mostrarLista() {
        System.out.println("Lista sin ordenar: ");
        for (Persona persona : listaPersonas) {
            System.out.println(persona.getNombre() + " " + persona.getApellido());
        }
        System.out.println(" ");
    }

    //Ordenar lista por nombre
    public void sortByName() {
        Collections.sort(listaPersonas, new Comparator<Persona>() {
            @Override
            public int compare(Persona p1, Persona p2) {
                return p1.getNombre().compareTo(p2.getNombre());
            }
        });
        System.out.println("Lista ordenada alfabeticamente por nombre: ");
        for (Persona persona : listaPersonas) {
            System.out.println(persona.getNombre() + " " + persona.getApellido());
        }
        System.out.println(" ");
    }

    //Ordenar lista por apellido
    public void sortByLastName() {
        Collections.sort(listaPersonas, new Comparator<Persona>() {
            @Override
            public int compare(Persona p1, Persona p2) {
                return p1.getApellido().compareTo(p2.getApellido());
            }
        });
        System.out.println("Lista ordenada alfabeticamente por apellido: ");
        for (Persona persona : listaPersonas) {
            System.out.println(persona.getApellido() + " " + persona.getNombre());
        }

        System.out.println(" ");
    }

    //Ordenar lista inversamente por apellido
    public void reverseSortByLastName() {
        Collections.sort(listaPersonas, new Comparator<Persona>() {
            @Override
            public int compare(Persona p1, Persona p2) {
                return p2.getApellido().compareTo(p1.getApellido());
            }
        });
        System.out.println("Lista ordenada alfabeticamente por apellido: ");
        for (Persona persona : listaPersonas) {
            System.out.println(persona.getApellido() + " " + persona.getNombre());
        }
    }
}
