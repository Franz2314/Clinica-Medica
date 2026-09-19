package utilidades;

import modelos.Cita;
import modelos.Consultorio;
import modelos.Medico;
import modelos.Paciente;

public class OrdenamientosBusqueda {

    public static void ordenarPacientesPorNombre(Paciente[] pacientes) {
        for (int i = 0; i < pacientes.length - 1; i++) {
            for (int j = 0; j < pacientes.length - 1 - i; j++) {
                if (pacientes[j].getNombreCompleto().compareToIgnoreCase(pacientes[j + 1].getNombreCompleto()) > 0) {
                    Paciente temporal = pacientes[j];
                    pacientes[j] = pacientes[j + 1];
                    pacientes[j + 1] = temporal;
                }
            }
        }
    }

    public static void ordenarPacientesPorCodigo(Paciente[] pacientes) {
        for (int i = 0; i < pacientes.length - 1; i++) {
            for (int j = 0; j < pacientes.length - 1 - i; j++) {
                if (pacientes[j].getCodigo().compareToIgnoreCase(pacientes[j + 1].getCodigo()) > 0) {
                    Paciente temporal = pacientes[j];
                    pacientes[j] = pacientes[j + 1];
                    pacientes[j + 1] = temporal;
                }
            }
        }
    }

    public static void ordenarPacientesPorDniInsercion(Paciente[] pacientes) {
        for (int i = 1; i < pacientes.length; i++) {
            Paciente actual = pacientes[i];
            int j = i - 1;
            while (j >= 0 && pacientes[j].getDni().compareToIgnoreCase(actual.getDni()) > 0) {
                pacientes[j + 1] = pacientes[j];
                j--;
            }
            pacientes[j + 1] = actual;
        }
    }

    public static void ordenarMedicosPorNombre(Medico[] medicos) {
        for (int i = 0; i < medicos.length - 1; i++) {
            int minimo = i;
            for (int j = i + 1; j < medicos.length; j++) {
                if (medicos[j].getNombreCompleto().compareToIgnoreCase(medicos[minimo].getNombreCompleto()) < 0) {
                    minimo = j;
                }
            }
            Medico temporal = medicos[i];
            medicos[i] = medicos[minimo];
            medicos[minimo] = temporal;
        }
    }

    public static void ordenarMedicosPorCmpSeleccion(Medico[] medicos) {
        for (int i = 0; i < medicos.length - 1; i++) {
            int minimo = i;
            for (int j = i + 1; j < medicos.length; j++) {
                if (medicos[j].getCmp().compareToIgnoreCase(medicos[minimo].getCmp()) < 0) {
                    minimo = j;
                }
            }
            Medico temporal = medicos[i];
            medicos[i] = medicos[minimo];
            medicos[minimo] = temporal;
        }
    }

    public static void ordenarCitasPorFecha(Cita[] citas) {
        for (int i = 1; i < citas.length; i++) {
            Cita actual = citas[i];
            int j = i - 1;
            while (j >= 0 && compararFechaHora(citas[j], actual) > 0) {
                citas[j + 1] = citas[j];
                j--;
            }
            citas[j + 1] = actual;
        }
    }

    public static void ordenarConsultoriosPorPiso(Consultorio[] consultorios) {
        for (int i = 0; i < consultorios.length - 1; i++) {
            for (int j = 0; j < consultorios.length - 1 - i; j++) {
                boolean intercambio = false;
                if (consultorios[j].getPiso() > consultorios[j + 1].getPiso()) {
                    intercambio = true;
                } else if (consultorios[j].getPiso() == consultorios[j + 1].getPiso()) {
                    if (consultorios[j].getCodigo().compareToIgnoreCase(consultorios[j + 1].getCodigo()) > 0) {
                        intercambio = true;
                    }
                }
                if (intercambio) {
                    Consultorio temporal = consultorios[j];
                    consultorios[j] = consultorios[j + 1];
                    consultorios[j + 1] = temporal;
                }
            }
        }
    }

    public static int busquedaLinealPacientePorDni(Paciente[] pacientes, String dni) {
        for (int i = 0; i < pacientes.length; i++) {
            if (pacientes[i].getDni().equalsIgnoreCase(dni)) {
                return i;
            }
        }
        return -1;
    }

    public static int busquedaBinariaPacientePorDni(Paciente[] pacientes, String dni) {
        ordenarPacientesPorDniInsercion(pacientes);
        int izquierda = 0;
        int derecha = pacientes.length - 1;
        while (izquierda <= derecha) {
            int medio = (izquierda + derecha) / 2;
            int comparacion = pacientes[medio].getDni().compareToIgnoreCase(dni);
            if (comparacion == 0) {
                return medio;
            }
            if (comparacion < 0) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }
        return -1;
    }

    public static int busquedaBinariaPacientePorCodigo(Paciente[] pacientes, String codigo) {
        ordenarPacientesPorCodigo(pacientes);
        int izquierda = 0;
        int derecha = pacientes.length - 1;
        while (izquierda <= derecha) {
            int medio = (izquierda + derecha) / 2;
            int comparacion = pacientes[medio].getCodigo().compareToIgnoreCase(codigo);
            if (comparacion == 0) {
                return medio;
            }
            if (comparacion < 0) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }
        return -1;
    }

    public static int busquedaLinealMedicoPorCmp(Medico[] medicos, String cmp) {
        for (int i = 0; i < medicos.length; i++) {
            if (medicos[i].getCmp().equalsIgnoreCase(cmp)) {
                return i;
            }
        }
        return -1;
    }

    public static int busquedaBinariaMedicoPorCmp(Medico[] medicos, String cmp) {
        ordenarMedicosPorCmpSeleccion(medicos);
        int izquierda = 0;
        int derecha = medicos.length - 1;
        while (izquierda <= derecha) {
            int medio = (izquierda + derecha) / 2;
            int comparacion = medicos[medio].getCmp().compareToIgnoreCase(cmp);
            if (comparacion == 0) {
                return medio;
            }
            if (comparacion < 0) {
                izquierda = medio + 1;
            } else {
                derecha = medio - 1;
            }
        }
        return -1;
    }

    private static int compararFechaHora(Cita a, Cita b) {
        return (a.getFecha() + " " + a.getHora()).compareTo(b.getFecha() + " " + b.getHora());
    }
}
