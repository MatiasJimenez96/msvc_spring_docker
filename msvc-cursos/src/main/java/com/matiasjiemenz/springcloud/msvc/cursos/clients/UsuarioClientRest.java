package com.matiasjiemenz.springcloud.msvc.cursos.clients;

import com.matiasjiemenz.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "msvc-usuarios", url = "localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    public Usuario detalle(@PathVariable Long id);

    @PostMapping("/")
    public Usuario crear(@RequestBody Usuario usuario);
}
