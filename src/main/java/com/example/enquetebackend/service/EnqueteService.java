package com.example.enquetebackend.service;

import com.example.enquetebackend.dto.EnqueteDTO;
import com.example.enquetebackend.dto.ResultadoDTO;
import com.example.enquetebackend.entity.Enquete;
import com.example.enquetebackend.entity.Voto;
import com.example.enquetebackend.exceptions.ErroPadrao;
import com.example.enquetebackend.repository.EnqueteRepository;
import com.example.enquetebackend.util.RespostasEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnqueteService {
    private final EnqueteRepository repository;

    @Autowired
    public EnqueteService(EnqueteRepository repository) {
        this.repository = repository;
    }

    public List<Enquete> listarEnquetesEncerradas() {
        return repository.findAll()
                .stream()
                .filter(enquete -> enquete.getAtivo() == 0)
                .sorted((enquete1, enquete2) -> enquete2.getData_hora().compareTo(enquete1.getData_hora()))
                .collect(Collectors.toList());
    }

    public Enquete obterEnqueteAtiva() {
        Optional<Enquete> enqueteAtivaOptional = repository.findByAtivo(1);
        return enqueteAtivaOptional.orElse(null);
    }

    public ResultadoDTO obterResultadoEnquete() {
        Optional<Enquete> enqueteComResultadoAtivoOptional = repository.findByExibirResultado(1);
        if (enqueteComResultadoAtivoOptional.isEmpty()) return null;
        Enquete enquete = enqueteComResultadoAtivoOptional.get();
        return resultadoEnquete(enquete);
    }

    public ResultadoDTO obterResultadoEnquetePorId(Integer id) {
        Optional<Enquete> enqueteComResultadoAtivoOptional = repository.findById(id);
        if (enqueteComResultadoAtivoOptional.isEmpty()) return null;
        Enquete enquete = enqueteComResultadoAtivoOptional.get();
        return resultadoEnquete(enquete);
    }

    public void mudarStatusResultadoEnquete(Integer id, Integer status) {
        Optional<Enquete> resultadoAtivo = repository.findByExibirResultado(1);
        if(resultadoAtivo.isPresent() && status.equals(1)){
            Enquete enq = resultadoAtivo.get();
            enq.setExibirResultado(0);
            repository.save(enq);
        }
        //throw new ErroPadrao("Já existe um resultado em exibição. Finalize-o para exibir outro.", HttpStatus.CONFLICT);
        Optional<Enquete> foundEnquete = repository.findById(id);
        if (foundEnquete.isEmpty())
            throw new ErroPadrao("Enquete não encontrada.", HttpStatus.NOT_FOUND);
        Enquete enquete = foundEnquete.get();
        enquete.setExibirResultado(status);
        repository.save(enquete);
    }

    public void encerrarEnquete() {
        Enquete enquete = obterEnqueteAtiva();
        if (enquete == null)
            throw new ErroPadrao("Nenhuma enquete no momento.", HttpStatus.NOT_FOUND);
        enquete.setAtivo(0);
        enquete.setExibirResultado(1);
        repository.save(enquete);
    }

    public void atualizarrEnquete(Integer tempo) {
        Enquete enquete = obterEnqueteAtiva();
        if (enquete == null)
            throw new ErroPadrao("Nenhuma enquete no momento.", HttpStatus.NOT_FOUND);
        enquete.setTempo(tempo);
        enquete.setData_cronometro((LocalDateTime.now()).plusMinutes(tempo));
        repository.save(enquete);
    }

    public void novaEnquete(EnqueteDTO enqueteDTO) {
        if (obterEnqueteAtiva() != null)
            throw new ErroPadrao("Há uma enquete em votação. Encerre-a para poder criar outra.", HttpStatus.FORBIDDEN);
        LocalDateTime agora = LocalDateTime.now();
        Enquete enquete = new Enquete();
        enquete.setId(Math.toIntExact(repository.count()) + 1);
        enquete.setPergunta(enqueteDTO.getPergunta());
        enquete.setTempo(0);
        enquete.setAtivo(1);
        enquete.setExibirResultado(0);
        enquete.setData_hora(agora);
        enquete.setData_cronometro(agora);
        repository.save(enquete);
    }

    private ResultadoDTO resultadoEnquete(Enquete enquete){
        List<Voto> votos = enquete.getVotos();

        int aprovar = votos.stream()
                .filter(item -> item.getResposta().equals(RespostasEnum.getById(1).getDescricao()))
                .collect(Collectors.toList())
                .size();

        int reaprovar = votos.stream()
                .filter(item -> item.getResposta().equals(RespostasEnum.getById(2).getDescricao()))
                .collect(Collectors.toList())
                .size();

        int abster = votos.stream()
                .filter(item -> item.getResposta().equals(RespostasEnum.getById(3).getDescricao()))
                .collect(Collectors.toList())
                .size();

        ResultadoDTO resultadoDTO =  new ResultadoDTO(
                enquete.getPergunta(),
                aprovar,
                reaprovar,
                abster,
                enquete.getVotos().size(),
                encontrarResultado(aprovar, reaprovar)
        );
        return resultadoDTO;
    }
    private static String encontrarResultado(int a, int b) {
        if (a > b) {
            return RespostasEnum.getById(1).getDescricao();
        } else if (b > a) {
            return RespostasEnum.getById(2).getDescricao();
        } else {
            return "EMPATE";
        }
    }
}
