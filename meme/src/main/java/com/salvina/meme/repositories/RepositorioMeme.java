package com.salvina.meme.repositories;

import com.salvina.meme.entities.Meme;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositorioMeme extends JpaRepository<Meme, Long> {
}
