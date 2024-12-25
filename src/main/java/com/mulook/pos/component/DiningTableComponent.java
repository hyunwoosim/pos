package com.mulook.pos.component;

import com.mulook.pos.entity.DiningTable;
import com.mulook.pos.repository.DiningTableRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class DiningTableComponent implements CommandLineRunner {

    private final DiningTableRepository diningTableRepository;

    public DiningTableComponent(DiningTableRepository diningTableRepository) {
        this.diningTableRepository = diningTableRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        for (int i = 1; i <=10; i++) {
            if (!diningTableRepository.existsByName(i)) {
                diningTableRepository.save(new DiningTable(i));
            }
        }
    }
}