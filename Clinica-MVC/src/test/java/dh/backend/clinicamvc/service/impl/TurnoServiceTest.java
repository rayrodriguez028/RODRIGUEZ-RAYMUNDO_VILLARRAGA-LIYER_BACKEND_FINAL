package dh.backend.clinicamvc.service.impl;

import dh.backend.clinicamvc.dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.entity.Domicilio;
import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TurnoServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TurnoServiceTest.class);

    @Autowired
    private TurnoService turnoService;

    private TurnoRequestDto turnoRequestDto;


    @BeforeEach
    void setUp() {
        turnoRequestDto = new TurnoRequestDto();
        turnoRequestDto.setPaciente_id(8);
        turnoRequestDto.setOdontologo_id(123);
        turnoRequestDto.setFecha("2024-06-05");
    }

    @Test
    @DisplayName("Testear que un turno fue agregado")
    void agregarTurno() throws BadRequestException, ResourceNotFoundException {
        TurnoResponseDto turnoAgregado = turnoService.agregarTurno(turnoRequestDto);
        assertNotNull(turnoAgregado);
    }

    @Test
    @DisplayName("Testear busqueda todos los turnos")
    void buscarTodosTurnos() throws ResourceNotFoundException {
        List<TurnoResponseDto> turnos = turnoService.buscarTodosTurnos();
        assertTrue(!turnos.isEmpty());
    }

    @Test
    @DisplayName("Testear busqueda turno por id")
    void buscarTurnoPorId() throws ResourceNotFoundException {
        Integer id = 2;
        TurnoResponseDto turnoEncontrado = turnoService.buscarTurnoPorId(id);
        assertEquals(id, turnoEncontrado.getId());
    }

}