package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.dto.request.TurnoRequestDto;
import dh.backend.clinicamvc.dto.response.TurnoResponseDto;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.service.ITurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/turno")
public class TurnoController {

    private final ITurnoService turnoService;



    public TurnoController(ITurnoService turnoService) {
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<TurnoResponseDto> agregarTurno(@RequestBody TurnoRequestDto turnoRequestDto) throws BadRequestException, ResourceNotFoundException {

            return ResponseEntity.status(HttpStatus.CREATED).body(turnoService.agregarTurno(turnoRequestDto));

    }

    @GetMapping
    public ResponseEntity<List<TurnoResponseDto>> buscarTodosTurnos() throws ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.buscarTodosTurnos());
    }

    //falta turno por id
    @GetMapping("/{id}")
    public ResponseEntity<TurnoResponseDto> buscarTurnoPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.buscarTurnoPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> modificarTurno(@PathVariable Integer id, @RequestBody TurnoRequestDto turnoRequestDto) throws BadRequestException, ResourceNotFoundException {
        turnoService.modificarTurno(id, turnoRequestDto);
        return ResponseEntity.ok("{\"message\": \"turno modificado\"}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarTurno(@PathVariable Integer id) throws ResourceNotFoundException {
        turnoService.eliminarTurno(id);
        return ResponseEntity.ok("{\"message\": \"turno eliminado\"}");
    }

    @GetMapping("/fechas")
    public ResponseEntity<List<TurnoResponseDto>> buscarTurnoEntreFechas(@RequestParam String inicio, @RequestParam String fin) throws ResourceNotFoundException {
        return ResponseEntity.ok(turnoService.buscarTurnoEntreFechas(inicio, fin));
    }

}
