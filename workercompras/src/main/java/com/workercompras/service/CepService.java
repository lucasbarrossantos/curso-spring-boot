package com.workercompras.service;

import com.workercompras.model.Endereco;
import com.workercompras.repository.CepRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CepService {

    Logger log = LogManager.getLogger(CepService.class);

    @Autowired
    private CepRepository cepRepository;

    public void buscarCep(String cep) {
        Endereco endereco = cepRepository.buscarPorCep(cep);
        log.info("Endereco montado com sucesso: {}", endereco);
    }

}
