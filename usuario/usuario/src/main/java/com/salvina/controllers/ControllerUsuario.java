package com.salvina.controllers;

import com.salvina.entities.Usuario;
import com.salvina.services.ServicoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
public class ControllerUsuario {
    @Autowired
    private ServicoUsuario servicoUsuario;

    @GetMapping("/buscar")
    public List<Usuario> buscaUsuarios() {
        return servicoUsuario.listaTodosUsuarios();
    }

    @PostMapping("/cadastrar")
    public Usuario novoUsuario(@RequestBody Usuario usuario) {
        return servicoUsuario.novoUsuario(usuario);
    }

    @GetMapping("/buscar-id/{id}")
    public Boolean buscarUsuario(@PathVariable("id") Long id) {
        return servicoUsuario.buscarUsuario(id);
    }
}
