package com.gazatem.ekip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gazatem.ekip.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	 User findByEmail(String email);

	@Query("select user from User user "
			+ "where user.id = :id ")
	User findById (@Param("id") Integer id);

}
