package com.phitran.services.auth.repository;

import com.phitran.services.auth.domain.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, String> {

}
