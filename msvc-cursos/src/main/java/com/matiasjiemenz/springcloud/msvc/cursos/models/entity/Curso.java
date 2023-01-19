package com.matiasjiemenz.springcloud.msvc.cursos.models.entity;

import com.matiasjiemenz.springcloud.msvc.cursos.models.Usuario;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "cursos")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    private String nombre;

    @OneToMany(cascade = CascadeType.ALL,orphanRemoval = true)
    @JoinColumn(name = "curso_id")
    private List<CursoUsuario> cursoUsuarios;

    @Transient // Significa que NO esta mapeado a la persistencia | Solo para poblar los datos de usuarios dentro de curso
    private List<Usuario> usuarios;

    public Curso(){
        cursoUsuarios = new ArrayList<>();
        usuarios = new ArrayList<>();
    }

    /* Getter and Setter */

    public Long getId() {
        return id;
    }

    public Curso setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Curso setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public List<CursoUsuario> getCursoUsuarios() {
        return cursoUsuarios;
    }

    public Curso setCursoUsuarios(List<CursoUsuario> cursoUsuarios) {
        this.cursoUsuarios = cursoUsuarios;
        return this;
    }

    public void addCursoUsuario(CursoUsuario cursoUsuario){
        cursoUsuarios.add(cursoUsuario);
    }

    public void removeCursoUsuario(CursoUsuario cursoUsuario){
        cursoUsuarios.remove(cursoUsuario);
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public Curso setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        return this;
    }
}
