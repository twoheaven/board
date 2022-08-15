package com.study.board.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.study.board.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {


    //'Title'(에)서 'searchKeyword'(이)가 포함되어있는 글을 찾을 것이다
    Page<Board> findByTitleContaining(String searchKeyword,Pageable pageable);

}
