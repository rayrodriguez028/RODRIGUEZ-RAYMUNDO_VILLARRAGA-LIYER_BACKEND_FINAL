package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.dto.response.OdontologoResponseDto;
import dh.backend.clinicamvc.dto.response.PacienteResponseDto;
import dh.backend.clinicamvc.dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.entity.Turno;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
//import dh.backend.clinicamvc.repository.IOdontologoRepository;
//import dh.backend.clinicamvc.repository.IPacienteRepository;
import dh.backend.clinicamvc.repository.ITurnoRepository;
import dh.backend.clinicamvc.service.IOdontologoService;
import dh.backend.clinicamvc.service.IPacienteService;
import dh.backend.clinicamvc.service.ITurnoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurnoService implements ITurnoService {

    private static final Logger logger = LogManager.getLogger(TurnoService.class);
    private static final String REQUEST = "Request: {} {}";
    private static final String RESPONSE = "Response: {} {}";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    //private final IOdontologoRepository odontologoRepository;
    //private final IPacienteRepository pacienteRepository;
    private final ITurnoRepository turnoRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public IPacienteService pacienteService;
    @Autowired
    public IOdontologoService odontologoService;

    public TurnoService(/*IOdontologoRepository odontologoRepository, IPacienteRepository pacienteRepository,*/ ITurnoRepository turnoRepository, ModelMapper modelMapper) {
        //this.odontologoRepository = odontologoRepository;
        //this.pacienteRepository = pacienteRepository;
        this.turnoRepository = turnoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TurnoResponseDto agregarTurno(TurnoRequestDto turnoRequestDto) throws ResourceNotFoundException, BadRequestException {
        if (turnoNoEsValido(turnoRequestDto, 0, false))
            throw new BadRequestException("{\"message\": \"solicitud erronea\"}");
        Paciente paciente = pacienteService.buscarPacientePorId(turnoRequestDto.getPaciente_id());
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(turnoRequestDto.getOdontologo_id());
        String metodo = "agregarTurno()";
        logger.info(REQUEST, metodo, turnoRequestDto);
        Turno turnoARegistrar = new Turno(null, paciente, odontologo, LocalDate.parse(turnoRequestDto.getFecha()));
        /*turnoARegistrar.setOdontologo(odontologo);
        turnoARegistrar.setPaciente(paciente);
        turnoARegistrar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));*/
        TurnoResponseDto turnoADevolver = mapToResponseDto(turnoRepository.save(turnoARegistrar));
        logger.info(RESPONSE, metodo, turnoADevolver);
        return turnoADevolver;
    }

    @Override
    public List<TurnoResponseDto> buscarTodosTurnos() throws ResourceNotFoundException {
        String metodo = "buscarTodosTurnos()";
        logger.info(REQUEST, metodo, "OK");
        List<Turno> lt = turnoRepository.findAll();
        if (lt.isEmpty()) throw new ResourceNotFoundException("{\"message\": \"no se encontraron turnos\"}");
        List<TurnoResponseDto> turnosADevolver = new ArrayList<>();
        TurnoResponseDto ta;
        for (Turno t : lt) {
            ta = mapToResponseDto(t);
            turnosADevolver.add(ta);
        }
        logger.info(RESPONSE, metodo, turnosADevolver);
        return turnosADevolver;
    }

    @Override
    public TurnoResponseDto buscarTurnoPorId(Integer id) throws ResourceNotFoundException {
        String metodo = "buscarTurnoPorId()";
        logger.info(REQUEST, metodo, id);
        Optional<Turno> t = turnoRepository.findById(id);
        if (t.isEmpty()) throw new ResourceNotFoundException("{\"message\": \"turno no encontrado\"}");
        TurnoResponseDto turnoADevolver = mapToResponseDto(t.orElseThrow());
        logger.info(RESPONSE, metodo, turnoADevolver);
        return turnoADevolver;
    }

    @Override
    public void modificarTurno(Integer id, TurnoRequestDto turnoRequestDto) throws BadRequestException, ResourceNotFoundException {
        if (turnoNoEsValido(turnoRequestDto, id, true))
            throw new BadRequestException("{\"message\": \"solicitud erronea\"}");
        Paciente paciente = pacienteService.buscarPacientePorId(turnoRequestDto.getPaciente_id());
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(turnoRequestDto.getOdontologo_id());
        buscarTurnoPorId(id);
        String metodo = "modificarTurno()";
        logger.info(REQUEST.concat("{}"), metodo, id, turnoRequestDto);
        Turno turnoAModificar = new Turno(id, paciente, odontologo, LocalDate.parse(turnoRequestDto.getFecha()));
        /*turnoAModificar.setId(id);
        turnoAModificar.setOdontologo(odontologo);
        turnoAModificar.setPaciente(paciente);
        turnoAModificar.setFecha(LocalDate.parse(turnoRequestDto.getFecha()));*/
        Turno t = turnoRepository.save(turnoAModificar);
        logger.info(RESPONSE, metodo, t);
    }

    @Override
    public void eliminarTurno(Integer id) throws ResourceNotFoundException {
        buscarTurnoPorId(id);
        String metodo = "eliminarTurno()";
        logger.info(REQUEST, metodo, id);
        turnoRepository.deleteById(id);
        logger.info(RESPONSE, metodo, "OK");
    }

    @Override
    public List<TurnoResponseDto> buscarTurnoEntreFechas(String inicio, String fin) throws ResourceNotFoundException {
        LocalDate fechaInicio = LocalDate.parse(inicio, formatter);
        LocalDate fechaFinal = LocalDate.parse(fin, formatter);
        String metodo = "modificarTurno()";
        logger.info(REQUEST.concat("{}"), metodo, fechaInicio, fechaFinal);
        List<Turno> lt = turnoRepository.buscarTurnoEntreFechas(fechaInicio, fechaFinal);
        if (lt.isEmpty()) throw new ResourceNotFoundException("{\"message\": \"no se encontraron turnos\"}");
        List<TurnoResponseDto> turnosADevolver = new ArrayList<>();
        TurnoResponseDto ta;
        for (Turno t : lt) {
            ta = mapToResponseDto(t);
            turnosADevolver.add(ta);
        }
        logger.info(RESPONSE, metodo, turnosADevolver);
        return turnosADevolver;
    }

    // metodo que mapea turno en turnoResponseDto
    private TurnoResponseDto mapToResponseDto(Turno turno) {
        TurnoResponseDto turnoResponseDto = modelMapper.map(turno, TurnoResponseDto.class);
        turnoResponseDto.setOdontologo(modelMapper.map(turno.getOdontologo(), OdontologoResponseDto.class));
        turnoResponseDto.setPaciente(modelMapper.map(turno.getPaciente(), PacienteResponseDto.class));
        return turnoResponseDto;
    }

    private boolean turnoNoEsValido(TurnoRequestDto turno, Integer id, boolean esModificacion) {
        if (esModificacion && enteroNoEsValido(id)) return true;
        if (enteroNoEsValido(turno.getPaciente_id())) return true;
        if (enteroNoEsValido(turno.getOdontologo_id())) return true;
        if (turno.getFecha().isBlank()) return true;
        return false;
    }

    private boolean enteroNoEsValido(Integer entero) {
        if (entero == null) return true;
        if (entero < 1) return true;
        return false;
    }
}
