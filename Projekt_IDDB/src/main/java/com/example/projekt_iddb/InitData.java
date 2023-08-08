package com.example.projekt_iddb;

import com.example.projekt_iddb.repositories.*;
import jakarta.transaction.Transactional;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
@Transactional
public class InitData implements ApplicationRunner {

    @Autowired
    private LekarzRepository lekarzRepository;

    @Autowired
    private LekNaRecepcieRepository  lekNaRecepcieRepository;

    @Autowired
    private LekRepository lekRepository;

    @Autowired
    private PacjentRepository pacjentRepository;

    @Autowired
    private ReceptaRepository receptaRepository;

    @Autowired
    private WizytaRepository wizytaRepository;


    private void initializeData() {
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        initializeData();
    }
}
