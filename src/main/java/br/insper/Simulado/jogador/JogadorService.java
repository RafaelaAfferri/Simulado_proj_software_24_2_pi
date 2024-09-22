package br.insper.Simulado.jogador;

import br.insper.Simulado.time.Time;
import br.insper.Simulado.time.TimeNaoEncontradaException;
import br.insper.Simulado.time.TimeService;
import org.hibernate.jdbc.Expectation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JogadorService {

    @Autowired JogadorRepository jogadorRepository;

    @Autowired TimeService timeService;

    public Jogador CadastraJogador(Jogador jogador) {
        if (jogador.getNome().equals("")
                || jogador.getIdade()==(null)){
            throw  new RuntimeException("Dados invalidos");
        } else {
            return jogadorRepository.save(jogador);
        }
    }

    public List<Jogador> listaJogadores() {
        return jogadorRepository.findAll();
    }

    public Jogador addJogador(Integer Idjogador, Integer indentificador) {
        Optional<Jogador> op = jogadorRepository.findById(Idjogador);
        if(op.isEmpty()){
            throw new RuntimeException("Jogador não encontrado");
        }
        Jogador jogador = op.get();
        if (indentificador == null) {
            throw new RuntimeException("Dados invalidos");
        }
        ResponseEntity<Time> time = timeService.getTime(indentificador);
        if(time.getStatusCode().is2xxSuccessful()){
            ArrayList<String> times = jogador.getTimes();
            if(time.getBody().getNome() != null){
                times.add(time.getBody().getNome());
                jogador.setTimes(times);
                return jogadorRepository.save(jogador);
            }
            else {
                throw new TimeNaoEncontradaException("Time " + indentificador + " não tem nome");
            }

        }else{
            throw new TimeNaoEncontradaException("Time " + indentificador + " não encontrado");
        }

    }

}
