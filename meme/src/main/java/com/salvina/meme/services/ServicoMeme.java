package com.salvina.meme.services;

import com.salvina.meme.entities.Categoria;
import com.salvina.meme.entities.Meme;
import com.salvina.meme.entities.Usuario;
import com.salvina.meme.repositories.RepositorioMeme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Random;

@Component
public class ServicoMeme {

    private static final Logger logger = LogManager.getLogger(ServicoMeme.class);

    @Autowired
    private RepositorioMeme repositorioMeme;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${usuario.servico.url}")
    private String usuarioServicoUrl;

    @Value("${categoria.servico.url}")
    private String categoriaServicoUrl;

    @Transactional()
    public Meme novoMeme(Meme meme) {
        logger.info("Criando novo meme: {}", meme);

        if(!validarUsuario(meme.getUsuario())){
            logger.error("Não existe esse usuário: {}", meme.getUsuario());
            throw new IllegalArgumentException("Não existe esse usuário");
        }

        if(!validarCategoria(meme.getCategoria())){
            logger.error("Não existe esse categoria: {}", meme.getCategoria());
            throw new IllegalArgumentException("Não existe essa categoria");
        }

        try {
            return repositorioMeme.save(meme);
        } catch (Exception e) {
            logger.error("Erro ao salvar novo meme: {}", meme, e);
            throw new RuntimeException("Erro ao salvar novo meme", e);
        }
    }

    @Transactional(readOnly = true)
    public List<Meme> listaTodosMemes() {
        logger.info("Listando todos os memes");

        try {
            return repositorioMeme.findAll();
        } catch (Exception e) {
            logger.error("Erro ao listar todos os memes", e);
            throw new RuntimeException("Erro ao listar todos os memes", e);
        }
    }

    @Transactional(readOnly = true)
    public Meme memeDia(){
        List<Meme> listaMeme = listaTodosMemes();

        if (listaMeme == null || listaMeme.isEmpty()) {
            logger.info("A lista de meme está vazia.");
            throw new IllegalArgumentException("A lista de meme está vazia.");
        }

        Random random = new Random();
        int randomIndex = random.nextInt(listaMeme.size());
        Long randomItem = listaMeme.get(randomIndex).getId();

        return repositorioMeme.findById(randomItem).get();
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

    @Transactional(readOnly = true)
    public Boolean validarCategoria(Categoria categoria){
        logger.info("Iniciando a chamada para o microsserviço de categoria");
        String url = STR."\{categoriaServicoUrl}/buscar-id/" + categoria.getId();
        try {
            Boolean resposta = restTemplate.getForObject(url, Boolean.class);
            return resposta != null && resposta;
        } catch (Exception e) {
            logger.error("Erro ao chamar método que valida categoria", e);
            throw new RuntimeException("Erro ao chamar método que valida categoria", e);
        }
    }

}
