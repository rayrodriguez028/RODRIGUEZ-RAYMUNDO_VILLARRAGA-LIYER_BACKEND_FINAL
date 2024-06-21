package dh.backend.clinicamvc.service.impl;


//import dh.backend.clinicamvc.dto.response.OdontologoResponseDto;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.repository.IOdontologoRepository;
import dh.backend.clinicamvc.service.IOdontologoService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService implements IOdontologoService {

    private static final Logger logger = LogManager.getLogger(OdontologoService.class);
    private static final String REQUEST = "Request: {} {}";
    private static final String RESPONSE = "Response: {} {}";

    private final IOdontologoRepository odontologoRepository;
    //private final ModelMapper modelMapper;

    public OdontologoService(IOdontologoRepository odontologoRepository/*, ModelMapper modelMapper*/) {
        this.odontologoRepository = odontologoRepository;
        //this.modelMapper = modelMapper;
    }

    public Odontologo agregarOdontologo(Odontologo odontologo) throws BadRequestException {
        if (odontologoNoEsValido(odontologo, false))
            throw new BadRequestException("{\"message\": \"solicitud erronea\"}");
        String metodo = "agregarOdontologo()";
        logger.info(REQUEST, metodo, odontologo);
        Odontologo o = odontologoRepository.save(odontologo);
        logger.info(RESPONSE, metodo, o);
        return o;
    }

    public List<Odontologo> buscarTodosOdontologos() throws ResourceNotFoundException {
        String metodo = "buscarTodosOdontologos()";
        logger.info(REQUEST, metodo, "OK");
        List<Odontologo> lo = odontologoRepository.findAll();
        if (lo.isEmpty()) throw new ResourceNotFoundException("{\"message\": \"no se encontraron resultados\"}");
        logger.info(RESPONSE, metodo, lo);
        return lo;
    }

    public Odontologo buscarOdontologoPorId(Integer id) throws ResourceNotFoundException {
        String metodo = "buscarOdontologoPorId()";
        logger.info(REQUEST, metodo, id);
        Optional<Odontologo> o = odontologoRepository.findById(id);
        if (o.isEmpty()) throw new ResourceNotFoundException("{\"message\": \"odontologo no encontrado\"}");
        Odontologo odontologo = o.orElseThrow();
        logger.info(RESPONSE, metodo, odontologo);
        return odontologo;
    }

    @Override
    public void modificarOdontologo(Odontologo odontologo) throws ResourceNotFoundException, BadRequestException {
        if (odontologoNoEsValido(odontologo, true))
            throw new BadRequestException("{\"message\": \"solicitud erronea\"}");
        buscarOdontologoPorId(odontologo.getId());
        String metodo = "modificarOdontologo()";
        logger.info(REQUEST, metodo, odontologo);
        Odontologo o = odontologoRepository.save(odontologo);
        logger.info(RESPONSE, metodo, o);
    }

    @Override
    public void eliminarOdontologo(Integer id) throws ResourceNotFoundException {
        buscarOdontologoPorId(id);
        String metodo = "eliminarOdontologo()";
        logger.info(REQUEST, metodo, id);
        odontologoRepository.deleteById(id);
        logger.info(RESPONSE, metodo, "OK");
    }

    @Override
    public List<Odontologo> buscarOdontologosPorApellido(String apellido) throws ResourceNotFoundException {
        String metodo = "buscarOdontologosPorApellido()";
        logger.info(REQUEST, metodo, apellido);
        List<Odontologo> lo = odontologoRepository.buscarOdontologosPorApellido(apellido);
        if (lo.isEmpty()) throw new ResourceNotFoundException("{\"message\": \"no se encontraron odontologos\"}");
        logger.info(RESPONSE, metodo, lo);
        return lo;
    }

    @Override
    public List<Odontologo> buscarOdontologosPorNombre(String nombre) throws ResourceNotFoundException {
        String metodo = "buscarOdontologosPorNombre()";
        logger.info(REQUEST, metodo, nombre);
        List<Odontologo> lo = odontologoRepository.buscarOdontologosPorNombre(nombre);
        if (lo.isEmpty()) throw new ResourceNotFoundException("{\"message\": \"no se encontraron odontologos\"}");
        logger.info(RESPONSE, metodo, lo);
        return lo;
    }

    // metodo que mapea turno en turnoResponseDto
    /*private OdontologoResponseDto mapToResponseDto(Odontologo odontologo) {
        return modelMapper.map(odontologo, OdontologoResponseDto.class);
    }*/

    private boolean odontologoNoEsValido(Odontologo odontologo, boolean esModificacion) {
        if (esModificacion && (odontologo.getId() == null || odontologo.getId() < 1)) return true;
        if (odontologo.getNombre().isBlank()) return true;
        if (odontologo.getApellido().isBlank()) return true;
        if (odontologo.getNroMatricula().isBlank()) return true;
        return false;
    }
}
