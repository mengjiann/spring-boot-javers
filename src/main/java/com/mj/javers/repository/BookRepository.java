package com.mj.javers.repository;

import com.mj.javers.domain.Book;
import org.javers.spring.annotation.JaversSpringDataAuditable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@JaversSpringDataAuditable
public interface BookRepository extends JpaRepository<Book,Integer> {
}
