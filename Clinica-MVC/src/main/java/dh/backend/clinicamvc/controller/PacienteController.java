package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.entity.Paciente;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.service.IPacienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/paciente")
public class PacienteController {

    public IPacienteService pacienteService;

    public PacienteController(IPacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }

    @PostMapping
    public ResponseEntity<Paciente> agregarPaciente(@RequestBody Paciente paciente) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.agregarPaciente(paciente));
    }

    @GetMapping
    public ResponseEntity<List<Paciente>> buscarTodosPacientes() throws ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.buscarTodosPacientes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPacientePorId(@PathVariable Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.buscarPacientePorId(id));
    }

    @PutMapping
    public ResponseEntity<String> modificarPaciente(@RequestBody Paciente paciente) throws BadRequestException, ResourceNotFoundException {
        pacienteService.modificarPaciente(paciente);
        return ResponseEntity.ok("{\"message\": \"paciente actualizado\"}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPaciente(@PathVariable Integer id) throws ResourceNotFoundException {
        pacienteService.eliminarPaciente(id);
        return ResponseEntity.ok("{\"message\": \"paciente eliminado\"}");
    }
}
