package estructuras;

/**
 * Estructura de Lista Doblemente Enlazada.
 * Permite la navegacion en ambas direcciones (hacia adelante y hacia atras).
 */
public class ListaDoblementeEnlazada<T> {

    private NodoDoble<T> cabeza;
    private NodoDoble<T> cola;
    private NodoDoble<T> actual;
    private int tamanio;

    public ListaDoblementeEnlazada() {
        cabeza = null;
        cola = null;
        actual = null;
        tamanio = 0;
    }

    // Agrega un elemento al final de la lista
    public void agregarAlFinal(T dato) {
        NodoDoble<T> nuevo = new NodoDoble<>(dato);
        if (cabeza == null) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            nuevo.anterior = cola;
            cola.siguiente = nuevo;
            cola = nuevo;
        }
        actual = nuevo;
        tamanio++;
    }

    // Ubica el cursor al inicio de la lista
    public void irAlInicio() {
        actual = cabeza;
    }

    // Ubica el cursor al final de la lista
    public void irAlFinal() {
        actual = cola;
    }

    // Retrocede al nodo anterior y retorna su dato
    public T anterior() {
        if (actual == null || actual.anterior == null) {
            return null;
        }
        actual = actual.anterior;
        return actual.dato;
    }

    // Avanza al nodo siguiente y retorna su dato
    public T siguiente() {
        if (actual == null || actual.siguiente == null) {
            return null;
        }
        actual = actual.siguiente;
        return actual.dato;
    }

    // Retorna el dato en la posicion actual del cursor
    public T obtenerActual() {
        return (actual != null) ? actual.dato : null;
    }

    // Verifica si hay un nodo anterior al cursor actual
    public boolean hayAnterior() {
        return actual != null && actual.anterior != null;
    }

    // Verifica si hay un nodo siguiente al cursor actual
    public boolean haySiguiente() {
        return actual != null && actual.siguiente != null;
    }

    // Retorna el tamaño total de la lista
    public int tamanio() {
        return tamanio;
    }

    // Verifica si la lista no contiene elementos
    public boolean estaVacia() {
        return tamanio == 0;
    }
}
