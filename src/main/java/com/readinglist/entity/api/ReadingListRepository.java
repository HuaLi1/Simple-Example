package com.readinglist.entity.api;

import com.readinglist.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * JpaRepository是个泛型接口，有两个参数：仓库操作的领域对象类型，及其ID属性的类型。
 *
 */
public interface ReadingListRepository extends JpaRepository<Book, Long> {
    List<Book> findByReader(String reader);}
