package com.salvina.categoria.services;

import com.salvina.categoria.entities.Categoria;
import com.salvina.categoria.entities.Usuario;
import com.salvina.categoria.repositories.RepositorioCategoria;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ServicoCategoria {

    private static final Logger logger = LogManager.getLogger(ServicoCategoria.class);

    @Autowired
    private RepositorioCategoria repositorioCategoria;

    @Value("${usuario.servico.url}")
    private String usuarioServicoUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Transactional()
    public Categoria novaCategoria(Categoria categoria) {
        logger.info("Criando nova categoria: {}", categoria);

        if(!validarUsuario(categoria.getUsuario())){
            logger.error("Não existe esse usuário: {}", categoria.getUsuario());
            throw new IllegalArgumentException("Não existe esse usuário");
        }

        try {
            return repositorioCategoria.save(categoria);
        } catch (Exception e) {
            logger.error("Erro ao salvar nova categoria: {}", categoria, e);
            throw new RuntimeException("Erro ao salvar nova categoria", e);
        }

    }

    @Transactional(readOnly = true)
    public List<Categoria> listaTodasCategorias() {
        logger.info("Listando todas as categorias");

        try {
            return repositorioCategoria.findAll();
        } catch (Exception e) {
            logger.error("Erro ao listar todas as categorias", e);
            throw new RuntimeException("Erro ao listar todas as categorias", e);
        }
    }

    @Transactional(readOnly = true)
    public Boolean buscarCategoria(Long id) {
        logger.info("Buscando categoria");

        try {
            return repositorioCategoria.findById(id).isPresent();
        } catch (Exception e) {
            logger.error("Erro ao buscar categoria", e);
            throw new RuntimeException("Erro ao buscar categoria", e);
        }
    }

    @Transactional(readOnly = true)
    public Boolean validarUsuario(Usuario usuario){
        logger.info("Iniciando a chamada para o microsserviço de usuario");
        String url = STR."\{usuarioServicoUrl}/buscar-id/" + usuario.getId();
        try {
            Boolean resposta = restTemplate.getForObject(url, Boolean.class);
            return resposta != null && resposta;
        } catch (Exception e) {
            logger.error("Erro ao chamar método que valida usuario", e);
            throw new RuntimeException("Erro ao chamar método que valida usuario", e);
        }
    }
}
