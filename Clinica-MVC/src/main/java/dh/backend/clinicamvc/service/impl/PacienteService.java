package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.repository.IPacienteRepository;
import dh.backend.clinicamvc.service.IPacienteService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements IPacienteService {

    private static final Logger logger = LogManager.getLogger(PacienteService.class);
    private static final String REQUEST = "Request: {} {}";
    private static final String RESPONSE = "Response: {} {}";

    private final IPacienteRepository pacienteRepository;

    public PacienteService(IPacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public Paciente agregarPaciente(Paciente paciente) throws BadRequestException {
        if (pacienteNoEsValido(paciente, false)) throw new BadRequestException("{\"message\": \"solicitud erronea\"}");
        String metodo = "agregarOdontologo()";
        logger.info(REQUEST, metodo, paciente);
        Paciente p = pacienteRepository.save(paciente);
        logger.info(RESPONSE, metodo, p);
        return p;
    }

    public List<Paciente> buscarTodosPacientes() throws ResourceNotFoundException {
        String metodo = "buscarTodosPacientes()";
        logger.info(REQUEST, metodo, "OK");
        List<Paciente> lp = pacienteRepository.findAll();
        if (lp.isEmpty()) throw new ResourceNotFoundException("{\"message\": \"no se encontraron pacientes\"}");
        logger.info(RESPONSE, metodo, lp);
        return lp;
    }

    public Paciente buscarPacientePorId(Integer id) throws ResourceNotFoundException {
        String metodo = "buscarPacientePorId()";
        logger.info(REQUEST, metodo, id);
        Optional<Paciente> p = pacienteRepository.findById(id);
        if (p.isEmpty()) throw new ResourceNotFoundException("{\"message\": \"paciente no encontrado\"}");
        Paciente paciente = p.orElseThrow();
        logger.info(RESPONSE, metodo, paciente);
        return paciente;
    }

    @Override
    public void modificarPaciente(Paciente paciente) throws ResourceNotFoundException, BadRequestException {
        if (pacienteNoEsValido(paciente, true)) throw new BadRequestException("{\"message\": \"solicitud erronea\"}");
        buscarPacientePorId(paciente.getId());
        String metodo = "modificarPaciente()";
        logger.info(REQUEST, metodo, paciente);
        Paciente p = pacienteRepository.save(paciente);
        logger.info(RESPONSE, metodo, p);
    }

    @Override
    public void eliminarPaciente(Integer id) throws ResourceNotFoundException {
        buscarPacientePorId(id);
        String metodo = "eliminarPaciente()";
        logger.info(REQUEST, metodo, id);
        pacienteRepository.deleteById(id);
        logger.info(RESPONSE, metodo, "OK");
    }

    private boolean pacienteNoEsValido(Paciente paciente, boolean esModificacion) {
        if (esModificacion && enteroNoEsValido(paciente.getId())) return true;
        if (paciente.getNombre().isBlank()) return true;
        if (paciente.getApellido().isBlank()) return true;
        if (paciente.getDni().isBlank()) return true;
        if (paciente.getFechaIngreso() == null) return true;
        if (esModificacion && enteroNoEsValido(paciente.getDomicilio().getId())) return true;
        if (enteroNoEsValido(paciente.getDomicilio().getNumero())) return true;
        if (paciente.getDomicilio().getCalle().isBlank()) return true;
        if (paciente.getDomicilio().getLocalidad().isBlank()) return true;
        if (paciente.getDomicilio().getProvincia().isBlank()) return true;
        return false;
    }

    private boolean enteroNoEsValido(Integer entero) {
        if (entero == null) return true;
        if (entero < 1) return true;
        return false;
    }
}
