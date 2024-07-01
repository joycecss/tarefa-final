package com.salvina.categoria.repositories;

import com.salvina.categoria.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioCategoria extends JpaRepository<Categoria, Long> {
}
