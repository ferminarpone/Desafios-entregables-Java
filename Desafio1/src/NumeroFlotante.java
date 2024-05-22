import java.util.Scanner;

public class NumeroFlotante {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //Solicitar el dato de tipo float
        System.out.print("Ingrese un dato del tipo float: ");
        float dato = scanner.nextFloat();

        // Separar la parte entera de la parte decimal
        int entero = (int) Math.floor(dato);
        float decimal = dato - entero ;

        //Imprimir los datos por separado
        System.out.println("Dato ingresado: " + dato);
        System.out.println("Parte entera del dato ingresado: " + entero);
        System.out.println("Parte decimal del dato ingresado: " + decimal);
    }
}