package estructuras;

public class PilaArreglo<T> implements Pila<T> {
    private T[] elementos;
    private int tope;
    private static final int CAPACIDAD_INICIAL = 10;

    @SuppressWarnings("unchecked")
    public PilaArreglo() {
        elementos = (T[]) new Object[CAPACIDAD_INICIAL];
        tope = -1;
    }

    @Override
    public void push(T dato) {
        if (tope == elementos.length - 1) {
            duplicarCapacidad();
        }
        elementos[++tope] = dato;
    }

    @Override
    public T pop() {
        if (estaVacia()) {
            throw new java.util.EmptyStackException();
        }
        T dato = elementos[tope];
        elementos[tope] = null; // Evitar fugas de memoria
        tope--;
        return dato;
    }

    @Override
    public T peek() {
        if (estaVacia()) {
            throw new java.util.EmptyStackException();
        }
        return elementos[tope];
    }

    @Override
    public boolean estaVacia() {
        return tope == -1;
    }

    @Override
    public int tamanio() {
        return tope + 1;
    }

    @SuppressWarnings("unchecked")
    private void duplicarCapacidad() {
        T[] nuevoArreglo = (T[]) new Object[elementos.length * 2];
        System.arraycopy(elementos, 0, nuevoArreglo, 0, elementos.length);
        elementos = nuevoArreglo;
    }
}
