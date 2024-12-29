package com.mulook.pos.Service;

import com.mulook.pos.dto.DiningTableDto;
import com.mulook.pos.entity.DiningTable;
import com.mulook.pos.repository.DiningTableRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DiningTableService {

    private final DiningTableRepository diningTableRepository;

    @Transactional
    public List<DiningTable> allTableOrder() {
        return diningTableRepository.findAll();
    }

    public DiningTableDto findTableOrder(int name) {

        DiningTable byName = diningTableRepository.findByName(name);
        DiningTableDto from = DiningTableDto.from(byName);
        return  from;
    }

}