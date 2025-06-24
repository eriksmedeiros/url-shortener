package com.erik.urlshortener.repositorys;

import com.erik.urlshortener.models.UrlModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UrlRepository extends JpaRepository<UrlModel, String> {
}
