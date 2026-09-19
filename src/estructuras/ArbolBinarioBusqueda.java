package estructuras;

public class ArbolBinarioBusqueda<K extends Comparable<K>, V> {
    private NodoArbol<K, V> raiz;

    public void insertar(K clave, V valor) {
        raiz = insertarRecursivo(raiz, clave, valor);
    }

    public V buscar(K clave) {
        NodoArbol<K, V> encontrado = buscarRecursivo(raiz, clave);
        return encontrado == null ? null : encontrado.valor;
    }

    private NodoArbol<K, V> insertarRecursivo(NodoArbol<K, V> actual, K clave, V valor) {
        if (actual == null) {
            return new NodoArbol<>(clave, valor);
        }
        int comparacion = clave.compareTo(actual.clave);
        if (comparacion < 0) {
            actual.izquierdo = insertarRecursivo(actual.izquierdo, clave, valor);
        } else if (comparacion > 0) {
            actual.derecho = insertarRecursivo(actual.derecho, clave, valor);
        } else {
            actual.valor = valor;
        }
        return actual;
    }

    private NodoArbol<K, V> buscarRecursivo(NodoArbol<K, V> actual, K clave) {
        if (actual == null) {
            return null;
        }
        int comparacion = clave.compareTo(actual.clave);
        if (comparacion == 0) {
            return actual;
        }
        if (comparacion < 0) {
            return buscarRecursivo(actual.izquierdo, clave);
        }
        return buscarRecursivo(actual.derecho, clave);
    }

    // ==================== OPERACIÓN DE ELIMINACIÓN ====================
    public void eliminar(K clave) {
        raiz = eliminarRecursivo(raiz, clave);
    }

    private NodoArbol<K, V> eliminarRecursivo(NodoArbol<K, V> actual, K clave) {
        if (actual == null) {
            return null;
        }

        int comparacion = clave.compareTo(actual.clave);
        if (comparacion < 0) {
            actual.izquierdo = eliminarRecursivo(actual.izquierdo, clave);
        } else if (comparacion > 0) {
            actual.derecho = eliminarRecursivo(actual.derecho, clave);
        } else {
            // Nodo encontrado
            // Caso 1 y 2: Sin hijos o un solo hijo
            if (actual.izquierdo == null) {
                return actual.derecho;
            } else if (actual.derecho == null) {
                return actual.izquierdo;
            }

            // Caso 3: Dos hijos. Obtener el sucesor (mínimo del subárbol derecho)
            NodoArbol<K, V> sucesor = encontrarMinimo(actual.derecho);
            actual.clave = sucesor.clave;
            actual.valor = sucesor.valor;
            actual.derecho = eliminarRecursivo(actual.derecho, sucesor.clave);
        }
        return actual;
    }

    private NodoArbol<K, V> encontrarMinimo(NodoArbol<K, V> actual) {
        while (actual.izquierdo != null) {
            actual = actual.izquierdo;
        }
        return actual;
    }

    // ==================== RECORRIDOS RECURSIVOS ====================
    public ListaEnlazada<V> recorridoInorden() {
        ListaEnlazada<V> lista = new ListaEnlazada<>();
        recorridoInordenRecursivo(raiz, lista);
        return lista;
    }

    private void recorridoInordenRecursivo(NodoArbol<K, V> actual, ListaEnlazada<V> lista) {
        if (actual != null) {
            recorridoInordenRecursivo(actual.izquierdo, lista);
            lista.agregar(actual.valor);
            recorridoInordenRecursivo(actual.derecho, lista);
        }
    }

    public ListaEnlazada<V> recorridoPreorden() {
        ListaEnlazada<V> lista = new ListaEnlazada<>();
        recorridoPreordenRecursivo(raiz, lista);
        return lista;
    }

    private void recorridoPreordenRecursivo(NodoArbol<K, V> actual, ListaEnlazada<V> lista) {
        if (actual != null) {
            lista.agregar(actual.valor);
            recorridoPreordenRecursivo(actual.izquierdo, lista);
            recorridoPreordenRecursivo(actual.derecho, lista);
        }
    }

    public ListaEnlazada<V> recorridoPostorden() {
        ListaEnlazada<V> lista = new ListaEnlazada<>();
        recorridoPostordenRecursivo(raiz, lista);
        return lista;
    }

    private void recorridoPostordenRecursivo(NodoArbol<K, V> actual, ListaEnlazada<V> lista) {
        if (actual != null) {
            recorridoPostordenRecursivo(actual.izquierdo, lista);
            recorridoPostordenRecursivo(actual.derecho, lista);
            lista.agregar(actual.valor);
        }
    }

    private static class NodoArbol<K, V> {
        private K clave;
        private V valor;
        private NodoArbol<K, V> izquierdo;
        private NodoArbol<K, V> derecho;

        private NodoArbol(K clave, V valor) {
            this.clave = clave;
            this.valor = valor;
        }
    }
}
