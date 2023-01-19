package com.matiasjiemenz.springcloud.msvc.cursos.models;

import java.util.Date;

public class Usuario {

    private Long id;

    private String nombre;

    private String email;

    private String password;

    private Date createAt;

    /* ---------------------------- */

    public Long getId() {
        return id;
    }

    public Usuario setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNombre() {
        return nombre;
    }

    public Usuario setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public Usuario setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Usuario setPassword(String password) {
        this.password = password;
        return this;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public Usuario setCreateAt(Date createAt) {
        this.createAt = createAt;
        return this;
    }
}
