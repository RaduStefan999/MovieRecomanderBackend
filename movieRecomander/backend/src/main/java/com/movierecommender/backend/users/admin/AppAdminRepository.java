package com.movierecommender.backend.users.admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppAdminRepository extends JpaRepository<AppAdmin, Long> {
}
