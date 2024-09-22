package br.insper.Simulado.JogadorService;


import br.insper.Simulado.jogador.Jogador;
import br.insper.Simulado.jogador.JogadorRepository;
import br.insper.Simulado.jogador.JogadorService;
import br.insper.Simulado.time.Time;
import br.insper.Simulado.time.TimeNaoEncontradaException;
import br.insper.Simulado.time.TimeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class JogadorServiceTest {

    @InjectMocks
    private JogadorService jogadorService;

    @Mock
    private JogadorRepository jogadorRepository;

    @Mock
    private TimeService timeService;

    @Test
    public void testListarTodosJogadores() {

        //preparação
        Mockito.when(jogadorRepository.findAll()).thenReturn(new ArrayList<>());

        //chamada do codigo testado
        List<Jogador> jogadores = jogadorService.listaJogadores();

        //verificação
        assert jogadores.isEmpty();


    }

    @Test
    public void testCadastrarJogadorNothingNull(){

        Jogador jogador = new Jogador();
        jogador.setNome("Jogador 1");
        jogador.setIdade(20);
        ArrayList<String> times = new ArrayList<>();
        times.add("Time 1");
        jogador.setTimes(times);


        Mockito.when(jogadorRepository.save(jogador)).thenReturn(jogador);

        Jogador jogadorRetorno = jogadorService.CadastraJogador(jogador);

        assert jogadorRetorno.getNome().equals("Jogador 1");
        assert jogadorRetorno.getIdade().equals(20);
        assert jogadorRetorno.getTimes().getFirst().equals("Time 1");
    }

    @Test
    public void testCadastrarJogadorWhenNameEmpty(){

        Jogador jogador = new Jogador();
        jogador.setNome("");
        jogador.setIdade(20);
        ArrayList<String> times = new ArrayList<>();
        times.add("Time 1");
        jogador.setTimes(times);

        Assertions.assertThrows(RuntimeException.class, () -> {
            jogadorService.CadastraJogador(jogador);
        });
    }

    @Test
    public void testCadastrarJogadorWhenIdadeEmpty(){

        Jogador jogador = new Jogador();
        jogador.setNome("Jogador 1");
        jogador.setIdade(null);
        ArrayList<String> times = new ArrayList<>();
        times.add("Time 1");
        jogador.setTimes(times);

        Assertions.assertThrows(RuntimeException.class, () -> {
            jogadorService.CadastraJogador(jogador);
        });
    }
    @Test
    public void testCadastrarJogadorWhenNotValid(){

        Jogador jogador = new Jogador();

        ArrayList<String> times = new ArrayList<>();
        times.add("Time 1");
        jogador.setTimes(times);

        Assertions.assertThrows(RuntimeException.class, () -> {
            jogadorService.CadastraJogador(jogador);
        });
    }

    @Test
    public void testAddJogadorWhenDadosInvalidos(){
        Jogador jogador = new Jogador();
        jogador.setNome("Antonio");
        jogador.setIdade(20);
        jogador.setId("id");

        Mockito.when(jogadorRepository.findById(1)).thenReturn(java.util.Optional.of(jogador));

        Assertions.assertThrows(RuntimeException.class, () -> {
            jogadorService.addJogador(1, null);
        });
    }

    @Test
    public void testAddJogadorWhenJogadorNaoExiste(){


        Mockito.when(jogadorRepository.findById(1)).thenReturn(Optional.empty());

        Assertions.assertThrows(RuntimeException.class, () -> {
            jogadorService.addJogador(1, null);
        });
    }

    @Test
    public void testAddJogadorWhenTimeNaoExiste(){
        Jogador jogador = new Jogador();
        jogador.setNome("Antonio");
        jogador.setIdade(20);
        jogador.setId("id");

        Time time = new Time();

        ResponseEntity<Time> responseEntity = new ResponseEntity<>(time, HttpStatus.NOT_FOUND);

        Mockito.when(jogadorRepository.findById(1)).thenReturn(java.util.Optional.of(jogador));

        Mockito.when(timeService.getTime(1)).thenReturn(responseEntity);


        Assertions.assertThrows(TimeNaoEncontradaException.class, () -> {
            jogadorService.addJogador(1, 1);
        });
    }

    @Test
    public void testAddJogadorWhenTimeNaoTemNome(){
        Jogador jogador = new Jogador();
        jogador.setNome("Antonio");
        jogador.setIdade(20);
        jogador.setId("id");

        Time time = new Time();
        time.setNome(null);

        ResponseEntity<Time> responseEntity = new ResponseEntity<>(time, HttpStatus.OK);

        Mockito.when(jogadorRepository.findById(1)).thenReturn(java.util.Optional.of(jogador));

        Mockito.when(timeService.getTime(1)).thenReturn(responseEntity);


        Assertions.assertThrows(TimeNaoEncontradaException.class, () -> {
            jogadorService.addJogador(1, 1);
        });
    }

    @Test
    public void testAddJogadorWhenValid(){
        Jogador jogador = new Jogador();
        jogador.setNome("Antonio");
        jogador.setIdade(20);
        jogador.setId("id");
        ArrayList<String> times = new ArrayList<>();
        times.add("Santos");
        jogador.setTimes(times);


        Time time = new Time();
        time.setNome("Santos");
        time.setId(1);

        ResponseEntity<Time> responseEntity = new ResponseEntity<>(time, HttpStatus.OK);

        Mockito.when(jogadorRepository.findById(1)).thenReturn(java.util.Optional.of(jogador));

        Mockito.when(timeService.getTime(1)).thenReturn(responseEntity);

        Mockito.when(jogadorRepository.save(jogador)).thenReturn(jogador);

        Jogador jogadorRetorno = jogadorService.addJogador(1, 1);

        assert jogadorRetorno.getTimes().getFirst().equals("Santos");

    }





}
