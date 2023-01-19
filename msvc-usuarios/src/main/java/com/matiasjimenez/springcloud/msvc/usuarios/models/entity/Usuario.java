package com.matiasjimenez.springcloud.msvc.usuarios.models.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;

@Entity
@Table(name = "Usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty
    @NotBlank
    private String nombre;

    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;

    @NotEmpty
    @NotBlank
    private String password;

    @Column(name = "create_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createAt;


    /* PrePersist */

    @PrePersist
    public void prePersist() {
        this.createAt = new Date();
    }

    /* Getters and Setters */

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

    /* */






}
