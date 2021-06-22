package com.heroes.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.heroes.repository.HeroRepository;
import java.util.List;
import java.util.Optional;

import com.heroes.entities.Hero;

@RestController
@CrossOrigin
@RequestMapping("/heroes")          //localhost:8080/heroes

public class HeroController {
    
    @Autowired
    private HeroRepository repo;

    //EndPoint 1
    //Devolve todos os Heroes    
    @GetMapping
    public List<Hero> getHeroes(){

        List<Hero> lista = repo.findAll();

        return lista;
    }

    //EndPoint 2
    //Devolve um heroi pelo id
    //localhost:8080/heroes/{id} 
    //Exemplo //localhost:8080/heroes/1     (Devolve o herou com id 1) 
    @GetMapping("{id}")
    public Hero getHero(@PathVariable Long id){

    Optional<Hero> op = repo.findById(id); 
    Hero hero = op.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));

    return hero;
    }

    //EndPoint 3
    //Salva um heroi, é necessário passar os dados do heroi no formato json
    @PostMapping
    public Hero salvar(@RequestBody Hero hero){
        hero = repo.save(hero);
        return hero;
    }

    //EndPoint 4
    //Altera um heroi, é necessário passar os dados do heroi no formato json e tambem o id
    @PutMapping("{id}")
    public Hero alterar(@RequestBody Hero updateHero, @PathVariable Long id){        
        Optional<Hero> op = repo.findById(id);
        Hero hero = op.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        hero.setName(updateHero.getName());
        repo.save(hero);
        return hero;
    }

    //EndPoint 5
    //Remove um heroi, é necessário passar o id
    @DeleteMapping("{id}")
    public void remover(@PathVariable Long id){
        Optional<Hero> op = repo.findById(id);
        Hero hero = op.orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        repo.delete(hero);
    }

}
