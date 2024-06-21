package dh.backend.clinicamvc.service;


import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;

import java.util.List;

public interface IOdontologoService {

    Odontologo agregarOdontologo(Odontologo odontologo) throws BadRequestException;

    List<Odontologo> buscarTodosOdontologos() throws ResourceNotFoundException;

    Odontologo buscarOdontologoPorId(Integer id) throws ResourceNotFoundException;

    void modificarOdontologo(Odontologo odontologo) throws ResourceNotFoundException, BadRequestException;

    void eliminarOdontologo(Integer id) throws ResourceNotFoundException;

    // Metodos con HQL
    List<Odontologo> buscarOdontologosPorApellido(String apellido) throws ResourceNotFoundException;

    List<Odontologo> buscarOdontologosPorNombre(String nombre) throws ResourceNotFoundException;
}
