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
class ImportService {
 private final ImportWriter writer;
 private final StringRedisTemplate redis;
 private final ObjectMapper mapper;
 ImportService(ImportWriter writer, StringRedisTemplate redis, ObjectMapper mapper) { this.writer=writer; this.redis=redis; this.mapper=mapper; }
 ImportResponse create(ImportRequest request) throws Exception {
  Long id=writer.save(request);
  String json=mapper.writeValueAsString(new Alert("IMPORT","Đã nhập "+request.quantity()+" hộp "+request.medicineName()));
  redis.convertAndSend("pharmacy-alerts",json);
  return new ImportResponse(id,json);
 }
}
