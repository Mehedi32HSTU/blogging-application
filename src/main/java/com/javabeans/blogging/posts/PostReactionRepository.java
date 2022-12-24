package com.javabeans.blogging.posts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostReactionRepository extends JpaRepository<PostReaction, Long>{

	List<PostReaction> findByReactorId(Long reactorId);
}
