package br.insper.Simulado.jogador;


import br.insper.Simulado.time.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class JogadorController {

    @Autowired JogadorService jogadorService;

    @Autowired TimeService timeService;

    @PostMapping("/jogador")
    public Jogador CadastraJogador(@RequestBody Jogador jogador){
        return jogadorService.CadastraJogador(jogador);
    }

    @GetMapping("/jogador")
    public List<Jogador> listaJogadores(){
        return jogadorService.listaJogadores();
    }

    @PostMapping("/jogador/{IdJogador}")
    public Jogador addJogador(@PathVariable Integer IdJogador, @RequestParam(required = true) Integer identificador){
        return jogadorService.addJogador(IdJogador, identificador);
    }
}
