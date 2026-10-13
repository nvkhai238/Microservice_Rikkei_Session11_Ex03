package com.rikkei.session11.ex03;

import java.nio.charset.StandardCharsets;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.*;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@Service
class ImportWriter {
 private final ImportRepository repository;
 private final MedicineStockRepository stockRepository;
 ImportWriter(ImportRepository repository,MedicineStockRepository stockRepository) { this.repository=repository; this.stockRepository=stockRepository; }
 @Transactional public Long save(ImportRequest request) {
  MedicineStock stock=stockRepository.findById(request.medicineName()).orElseGet(()->new MedicineStock(request.medicineName()));
  stock.quantity+=request.quantity();
  stockRepository.save(stock);
  return repository.save(new MedicineImport(request.medicineName(),request.quantity())).id;
 }
}
