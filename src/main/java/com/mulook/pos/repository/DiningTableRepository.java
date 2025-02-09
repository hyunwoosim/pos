package com.mulook.pos.repository;

import com.mulook.pos.entity.DiningTable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiningTableRepository extends JpaRepository<DiningTable, Long> {

    boolean existsByName(int name);

    DiningTable findByName(int name);

}