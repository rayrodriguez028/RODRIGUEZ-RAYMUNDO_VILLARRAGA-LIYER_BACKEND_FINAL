package dh.backend.clinicamvc.service.impl;

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
class OdontologoServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(OdontologoServiceTest.class);

    @Autowired
    private OdontologoService odontologoService;
    private Odontologo odontologo;

    @BeforeEach
    void setUp() {
        odontologo = new Odontologo();
        odontologo.setNombre("Jose Luis");
        odontologo.setApellido("Troncoso");
        odontologo.setNroMatricula("464646");
    }


    @Test
    @DisplayName("Testear que un odontologo fue agregado")
    void testPacienteGuardado() throws BadRequestException {
        Odontologo odontologoAgregado = odontologoService.agregarOdontologo(odontologo);
        assertNotNull(odontologoAgregado);
    }

    @Test
    @DisplayName("Testear busqueda todos los odontologos")
    void buscarTodosOdontologos() throws ResourceNotFoundException {
        List<Odontologo> odontologos = odontologoService.buscarTodosOdontologos();
        assertTrue(!odontologos.isEmpty());
    }

    @Test
    @DisplayName("Testear busqueda odontologo por id")
    void buscarOdontologoPorId() throws ResourceNotFoundException {
        Integer id = 84;
        Odontologo odontologoEncontrado = odontologoService.buscarOdontologoPorId(id);
        assertEquals(id, odontologoEncontrado.getId());
    }
}