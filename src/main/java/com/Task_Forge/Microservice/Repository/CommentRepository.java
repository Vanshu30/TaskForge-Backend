package com.Task_Forge.Microservice.Repository;

import com.Task_Forge.Microservice.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CommentRepository extends JpaRepository<Comment, UUID> {
}
