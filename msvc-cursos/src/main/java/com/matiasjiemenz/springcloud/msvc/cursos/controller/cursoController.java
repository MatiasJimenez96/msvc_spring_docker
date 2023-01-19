package com.matiasjiemenz.springcloud.msvc.cursos.controller;


import com.matiasjiemenz.springcloud.msvc.cursos.models.Usuario;
import com.matiasjiemenz.springcloud.msvc.cursos.models.entity.Curso;
import com.matiasjiemenz.springcloud.msvc.cursos.services.CursoService;
import feign.FeignException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class cursoController {

    @Autowired
    private CursoService service;

    @GetMapping("/")
    public ResponseEntity<List<Curso>> listar(){
        return ResponseEntity.ok(service.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalles(@PathVariable Long id){
        Optional<Curso> o = service.buscarPorId(id);
        if (o.isPresent()){
            return ResponseEntity.ok(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result){
        if (result.hasErrors()){
            return validarBinding(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(curso));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return validarBinding(result);
        }
        Optional<Curso> o = service.buscarPorId(id);
        if (o.isPresent()){
            Curso cursoDB = o.get();
            cursoDB.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(cursoDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?>eliminar(@PathVariable Long id){
        Optional<Curso> o = service.buscarPorId(id);
        if (o.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    /* --------------  Comunicacion con msvc_usuario   ----------------------- */

    @PutMapping("/asisgnar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> o;
        try {
        o = service.asignarUsuario(usuario, cursoId);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por " +
                            "el ID o error en la comunicacion: " + e.getMessage()));
        }
        if (o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> o;
        try {
            o = service.crearUsuario(usuario, cursoId);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No se pudo crear el usuario " +
                            "o error en la comunicacion: " + e.getMessage()));
        }
        if (o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> o;
        try {
            o = service.eliminarUsuario(usuario, cursoId);
        } catch (FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("mensaje", "No existe el usuario por " +
                            "el ID o error en la comunicacion: " + e.getMessage()));
        }
        if (o.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }


    /* ----------------------------------- */

    private static ResponseEntity<Map<String, String>> validarBinding(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(error -> {
            errores.put(error.getField(), "El campo " + error.getField() + " " + error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
