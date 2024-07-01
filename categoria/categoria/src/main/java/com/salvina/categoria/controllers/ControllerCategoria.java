package com.salvina.categoria.controllers;

import com.salvina.categoria.entities.Categoria;
import com.salvina.categoria.services.ServicoCategoria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class ControllerCategoria {
    @Autowired
    private ServicoCategoria servicoCategoria;

    @GetMapping("/buscar")
    public List<Categoria> buscaCategorias() {
        return servicoCategoria.listaTodasCategorias();
    }

    @PostMapping("/cadastrar")
    public Categoria novaCategoria(@RequestBody Categoria categoriaMeme) {
        return servicoCategoria.novaCategoria(categoriaMeme);
    }

    @GetMapping("/buscar-id/{id}")
    public Boolean buscarCategoria(@PathVariable("id") Long id) {
        return servicoCategoria.buscarCategoria(id);
    }
}
