package com.gazatem.ekip.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gazatem.ekip.model.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	Role findByRole(String role);

	@Query("select role from Role role "
			+ "where role.id = :id ")
	List<Role> findById(@Param("id")Integer id);

}
