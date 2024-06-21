package dh.backend.clinicamvc.service.impl;


import dh.backend.clinicamvc.entity.Domicilio;
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
class PacienteServiceTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PacienteServiceTest.class);

    @Autowired
    private PacienteService pacienteService;
    private Paciente paciente;

    @BeforeEach
    void setUp() {
        paciente = new Paciente();
        paciente.setNombre("Jose Luis");
        paciente.setApellido("Troncoso");
        paciente.setDni("464646");
        paciente.setFechaIngreso(LocalDate.of(2024, 01, 12));
        Domicilio domicilio = new Domicilio();
        domicilio.setCalle("Calle Sin destino");
        domicilio.setNumero(555);
        domicilio.setLocalidad("San Pedro");
        domicilio.setProvincia("Garza García");
        paciente.setDomicilio(domicilio);
    }


    @Test
    @DisplayName("Testear que un paciente fue agregado")
    void testPacienteGuardado() throws BadRequestException {
        Paciente pacienteAgregado = pacienteService.agregarPaciente(paciente);
        assertNotNull(pacienteAgregado);
    }

    @Test
    @DisplayName("Testear busqueda paciente por id")
    void testPacientePorId() throws ResourceNotFoundException {
        Integer id = 8;
        Paciente pacienteEncontrado = pacienteService.buscarPacientePorId(id);
        assertEquals(id, pacienteEncontrado.getId());
    }

    @Test
    @DisplayName("Testear busqueda todos los pacientes")
    void testBusquedaTodos() throws ResourceNotFoundException {
        List<Paciente> pacientes = pacienteService.buscarTodosPacientes();
        assertTrue(!pacientes.isEmpty());

    }


}