package br.insper.Simulado.jogador;

import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;

@Document
public class Jogador {
    @Id
    private String id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private Integer idade;

    @Column(nullable = false)
    private ArrayList<String> times = new ArrayList<>();

    public Jogador() {
    }

    public Jogador(Integer id, String nome, Integer idade, ArrayList<String> times) {
        this.id = id;
        this.nome = nome;
        this.idade = idade;
        this.times = times;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public ArrayList<String> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<String> times) {
        this.times = times;
    }
}
