package com.matiasjiemenz.springcloud.msvc.cursos.services;

import com.matiasjiemenz.springcloud.msvc.cursos.clients.UsuarioClientRest;
import com.matiasjiemenz.springcloud.msvc.cursos.models.Usuario;
import com.matiasjiemenz.springcloud.msvc.cursos.models.entity.Curso;
import com.matiasjiemenz.springcloud.msvc.cursos.models.entity.CursoUsuario;
import com.matiasjiemenz.springcloud.msvc.cursos.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService{

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioClientRest usuarioClientRest;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return cursoRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> buscarPorId(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porIdConUsuarios(Long id) {
        Optional<Curso> cursoOptional = cursoRepository.findById(id);
        if (cursoOptional.isPresent()){
            Curso curso = cursoOptional.get();
            if (!curso.getCursoUsuarios().isEmpty()){
                List<Long> ids = curso
                        .getCursoUsuarios()
                        .stream()
                        .map(cu -> cu.getUsuarioId()).toList();
                List<Usuario> usuarios = usuarioClientRest.obtenerAlumnosPorCursos(ids);
                curso.setUsuarios(usuarios);
            }
            return Optional.of(curso);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Curso guardar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    @Transactional
    public void eliminar(Long id) {
        cursoRepository.deleteById(id);
    }

    @Override
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if (o.isPresent()){
            Curso curso = o.get();
            Usuario usuarioMsvc = usuarioClientRest.detalle(usuario.getId());

            boolean existeUsuarioEnElCurso =  curso.getCursoUsuarios().stream().anyMatch(u-> u.getUsuarioId().equals(usuarioMsvc.getId()));

            if (existeUsuarioEnElCurso){
                System.out.println("EL USUARIO YA EXISTE EN ESTE CURSO");
                return Optional.empty();
            }
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if (o.isPresent()){
            Usuario usuarioNuevoMsvc = usuarioClientRest.crear(usuario);

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioNuevoMsvc.getId());

            curso.addCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioNuevoMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = cursoRepository.findById(cursoId);
        if (o.isPresent()){
            Usuario usuarioMsvc = usuarioClientRest.detalle(usuario.getId());

            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.removeCursoUsuario(cursoUsuario);
            cursoRepository.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }
}
