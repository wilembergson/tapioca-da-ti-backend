package com.example.enquetebackend.service;

import com.example.enquetebackend.dto.VotoDTO;
import com.example.enquetebackend.entity.Enquete;
import com.example.enquetebackend.entity.Voto;
import com.example.enquetebackend.exceptions.ErroPadrao;
import com.example.enquetebackend.repository.EnqueteRepository;
import com.example.enquetebackend.repository.VotoRespository;
import com.example.enquetebackend.util.RespostasEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VotoService {

    private final VotoRespository votoRepository;
    private final EnqueteRepository enqueteRepository;

    @Autowired
    public VotoService(VotoRespository respostaRepository, EnqueteRepository enqueteRepository){
        this.votoRepository = respostaRepository;
        this.enqueteRepository = enqueteRepository;
    }

    public void novoVoto(VotoDTO votoDTO){
        Optional<Enquete> enqueteop = enqueteRepository.findByAtivo(1);
        if(enqueteop.isEmpty()) throw new ErroPadrao("Nenhuma enquete em votação no momento.", HttpStatus.NOT_FOUND);
        Enquete enquete = enqueteop.get();
        Voto voto;
        List<Voto> listaVotos = votosPorEnqueteId(enquete.getId());
        Optional<Voto> votoExistente = listaVotos
                .stream()
                .filter(item -> item.getCrm().equals(votoDTO.getCrm()))
                .findFirst();
        //Um caso especial foi encontrado, validação feita para impedir que ocorra novamente
        Integer idNew = Math.toIntExact(votoRepository.count()) + 1;
        if(idNew == 2 && !(votoRepository.findById(2).isEmpty()))
            idNew += 1;
        if(votoExistente.isPresent()){
            voto = votoExistente.get();
            voto.setResposta(votoDTO.getIdResposta());
        }else{
            voto = new Voto(
                    idNew,
                    RespostasEnum.getById(votoDTO.getIdResposta()).getDescricao(),
                    votoDTO.getCrm(),
                    votoDTO.getNome(),
                    LocalDateTime.now(),
                    enquete
            );
        }
        votoRepository.save(voto);
    }

    public List<Voto>  votosPorEnqueteId(Integer enquete_id){
        Optional<Enquete> enquete = enqueteRepository.findById(enquete_id);
        if(enquete.isEmpty()) throw new ErroPadrao("Enquete não encontrada.", HttpStatus.NOT_FOUND);
        List<Voto> respostas = votoRepository.findAll()
                .stream()
                .filter(resposta -> resposta.getEnquete().getId().equals(enquete_id))
                .collect(Collectors.toList());
        return respostas;
    }
}
