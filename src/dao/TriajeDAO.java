package dao;

import modelos.Paciente;
import modelos.RegistroTriaje;

public interface TriajeDAO {
    void setModoPrioridad(boolean usarPrioridad);
    boolean isModoPrioridad();
    
    RegistroTriaje registrar(Paciente paciente, String fecha,
            double peso, double talla, double temperatura,
            String presionArterial, int frecuenciaCardiaca, String nivelPrioridad);
            
    RegistroTriaje atenderSiguiente();
    RegistroTriaje verSiguiente();
    RegistroTriaje[] listarHistorial();
    RegistroTriaje[] listarEnEspera();
    int totalEnEspera();
    
    void eliminarPorPaciente(String codigoPaciente);
}
