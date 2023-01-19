package com.matiasjimenez.springcloud.msvc.usuarios.repositories;

import com.matiasjimenez.springcloud.msvc.usuarios.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
