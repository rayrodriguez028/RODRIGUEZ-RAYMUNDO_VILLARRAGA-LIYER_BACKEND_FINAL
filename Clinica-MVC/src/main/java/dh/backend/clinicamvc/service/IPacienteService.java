package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;

import java.util.List;

public interface IPacienteService {
    Paciente agregarPaciente(Paciente paciente) throws BadRequestException;

    List<Paciente> buscarTodosPacientes() throws ResourceNotFoundException;

    Paciente buscarPacientePorId(Integer id) throws ResourceNotFoundException;

    void modificarPaciente(Paciente paciente) throws ResourceNotFoundException, BadRequestException;

    void eliminarPaciente(Integer id) throws ResourceNotFoundException;
}
