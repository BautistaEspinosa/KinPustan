package com.kinpustan.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import java.time.Instant;
import lombok.Data;

@Entity
@Data
public class CartSession {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Instant createdAt;
  private Instant updatedAt;

  @PrePersist
  public void prePersist(){
    createdAt = Instant.now();
    updatedAt = Instant.now();
  }
}
