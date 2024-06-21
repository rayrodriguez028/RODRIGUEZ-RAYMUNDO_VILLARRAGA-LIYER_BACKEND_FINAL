package dh.backend.clinicamvc.service;

import dh.backend.clinicamvc.dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.util.List;

public interface ITurnoService {

    TurnoResponseDto agregarTurno(TurnoRequestDto turnoRequestDto) throws ResourceNotFoundException, BadRequestException;

    List<TurnoResponseDto> buscarTodosTurnos() throws ResourceNotFoundException;

    TurnoResponseDto buscarTurnoPorId(Integer id) throws ResourceNotFoundException;

    void modificarTurno(Integer id, TurnoRequestDto turnoRequestDto) throws BadRequestException, ResourceNotFoundException;

    void eliminarTurno(Integer id) throws ResourceNotFoundException;

    // Metodos con HQL
    List<TurnoResponseDto> buscarTurnoEntreFechas(String inicio, String fin) throws ResourceNotFoundException;
}
