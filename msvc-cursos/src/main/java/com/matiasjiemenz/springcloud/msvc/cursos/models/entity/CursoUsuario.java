package com.matiasjiemenz.springcloud.msvc.cursos.models.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "curso_usuario")
public class CursoUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "usuario_id",unique = false)
    private Long usuarioId;

    /* ------------------ */

    public Long getId() {
        return id;
    }

    public CursoUsuario setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public CursoUsuario setUsuarioId(Long usuarioId) {
        this.usuarioId = usuarioId;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        if (!(obj instanceof CursoUsuario)){
            return false;
        }
        CursoUsuario o = (CursoUsuario) obj;
        return this.usuarioId != null && this.usuarioId.equals(o.usuarioId);
    }
}
