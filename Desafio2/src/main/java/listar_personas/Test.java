package listar_personas;

public class Test {
    public static void main(String[] args) {
        Persona persona1 = new Persona("Santiago", "Gomez");
        Persona persona2 = new Persona("Camilo", "Vazques");
        Persona persona3 = new Persona("Matias", "Salas");
        Persona persona4 = new Persona("Nicolas", "Camel");
        Persona persona5 = new Persona("Alberto", "Latorre");

        ListaPersonas lista = new ListaPersonas();
        lista.agregarPersona(persona1);
        lista.agregarPersona(persona2);
        lista.agregarPersona(persona3);
        lista.agregarPersona(persona4);
        lista.agregarPersona(persona5);

        lista.mostrarLista();

        lista.sortByName();

        lista.sortByLastName();

        lista.reverseSortByLastName();
    }
}
