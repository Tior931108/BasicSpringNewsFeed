package com.example.basicspringnewsfeed.block.repository;

import com.example.basicspringnewsfeed.block.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
