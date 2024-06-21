package dh.backend.clinicamvc.controller;

import dh.backend.clinicamvc.entity.Odontologo;
import dh.backend.clinicamvc.exception.BadRequestException;
import dh.backend.clinicamvc.exception.ResourceNotFoundException;
import dh.backend.clinicamvc.service.impl.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/odontologo")
public class OdontologoController {

    @Autowired
    private OdontologoService odontologoService;

    //    public OdontologoController(OdontologoService odontologoService) {
//        this.odontologoService = odontologoService;
//    }
    @PostMapping
    public ResponseEntity<Odontologo> agregarOdontologo(@RequestBody Odontologo odontologo) throws BadRequestException {
        return ResponseEntity.status(HttpStatus.CREATED).body(odontologoService.agregarOdontologo(odontologo));
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> buscarTodosOdontologos() throws ResourceNotFoundException {
        return ResponseEntity.ok(odontologoService.buscarTodosOdontologos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Odontologo> buscarOdontologoPorId(@PathVariable Integer id) throws ResourceNotFoundException {
        return ResponseEntity.ok(odontologoService.buscarOdontologoPorId(id));
    }

    @PutMapping
    public ResponseEntity<String> modificarOdontologo(@RequestBody Odontologo odontologo) throws ResourceNotFoundException, BadRequestException {
        odontologoService.modificarOdontologo(odontologo);
        return ResponseEntity.ok("{\"message\": \"odontologo modificado\"}");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarOdontologo(@PathVariable Integer id) throws ResourceNotFoundException {
        odontologoService.eliminarOdontologo(id);
        return ResponseEntity.ok("{\"message\": \"odontologo eliminado\"}");
    }

    @GetMapping("/apellido/{apellido}")
    public ResponseEntity<List<Odontologo>> buscarOdontologosPorApellido(@PathVariable String apellido) throws ResourceNotFoundException {
        return ResponseEntity.ok(odontologoService.buscarOdontologosPorApellido(apellido));
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Odontologo>> buscarOdontologosPorNombre(@PathVariable String nombre) throws ResourceNotFoundException {
        return ResponseEntity.ok(odontologoService.buscarOdontologosPorNombre(nombre));
    }
}
