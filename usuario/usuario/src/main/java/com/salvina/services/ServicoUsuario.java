package com.salvina.services;

import com.salvina.entities.Usuario;
import com.salvina.repositories.RepositorioUsuario;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class ServicoUsuario {

    private static final Logger logger = LogManager.getLogger(ServicoUsuario.class);

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Transactional
    public Usuario novoUsuario(Usuario usuario) {
        logger.info("Criando novo usuário: {}", usuario);

        try {
            return repositorioUsuario.save(usuario);
        } catch (Exception e) {
            logger.error("Erro ao salvar novo usuário: {}", usuario, e);
            throw new RuntimeException("Erro ao salvar novo usuário", e);
        }
    }

    @Transactional(readOnly = true)
    public List<Usuario> listaTodosUsuarios() {
        logger.info("Listando todos os usuários");

        try {
            return repositorioUsuario.findAll();
        } catch (Exception e) {
            logger.error("Erro ao listar todos os usuários", e);
            throw new RuntimeException("Erro ao listar todos os usuários", e);
        }
    }

    @Transactional(readOnly = true)
    public Boolean buscarUsuario(Long id) {
        logger.info("Buscando usuário");

        try {
            return repositorioUsuario.findById(id).isPresent();
        } catch (Exception e) {
            logger.error("Erro ao buscar usuário", e);
            throw new RuntimeException("Erro ao buscar usuário", e);
        }
    }
}
