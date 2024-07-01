package com.salvina.meme.controllers;

import com.salvina.meme.entities.Meme;
import com.salvina.meme.services.ServicoMeme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meme")
public class ControllerMeme {
    @Autowired
    private ServicoMeme servicoMeme;

    @GetMapping("/buscar")
    public List<Meme> buscaMemes() {
        return servicoMeme.listaTodosMemes();
    }

    @PostMapping("/cadastrar")
    public Meme novoMeme(@RequestBody Meme meme) {
        return servicoMeme.novoMeme(meme);
    }

    @GetMapping("/meme-dia")
    public Meme memeDia(){ return servicoMeme.memeDia(); }
}
