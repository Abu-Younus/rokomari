package com.younus.rokomari.repository;

import com.younus.rokomari.entity.CommentReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentReplyRepository extends JpaRepository<CommentReplyEntity,Long> {
}
