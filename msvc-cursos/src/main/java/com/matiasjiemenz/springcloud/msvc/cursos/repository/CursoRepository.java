package com.matiasjiemenz.springcloud.msvc.cursos.repository;

import com.matiasjiemenz.springcloud.msvc.cursos.models.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {

}
