package com.example.techpharma.controller;

import com.example.techpharma.model.Mercadoria;
import com.example.techpharma.repository.MercadoriaRepository;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@CrossOrigin(origins = "*")
@OpenAPIDefinition(info = @Info(title = "Mercadoria API", version = "1.0", description = "Mercadoria Information"))
public class MercadoriaController {
    @Autowired
    MercadoriaRepository MercadoriaRepository;

    @GetMapping("/MercadoriasList")
    public ResponseEntity<List<Mercadoria>> getAllProducts() {
        return new ResponseEntity<>(MercadoriaRepository.findAll(), HttpStatus.OK);

    }

    @GetMapping("/MercadoriasPage")
    public ResponseEntity<Page<Mercadoria>> getAll(@PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) final Pageable pageable) {
        return new ResponseEntity<>(MercadoriaRepository.findAll(pageable), HttpStatus.OK);

    }

    @GetMapping("/Mercadorias/{id}")
    public ResponseEntity<Mercadoria> getOneProduct(@PathVariable(value = "id") UUID id) {
        Optional<Mercadoria> Mercadoria0 = MercadoriaRepository.findById(id);
        if (Mercadoria0.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<>(Mercadoria0.get(), HttpStatus.OK);

    }

    @PostMapping("/Mercadorias/{id}")
    public ResponseEntity<Mercadoria> saveProduct(@RequestBody @Valid Mercadoria Mercadoria) {
        return new ResponseEntity<>(MercadoriaRepository.save(Mercadoria), HttpStatus.CREATED);

    }

    @DeleteMapping("/Mercadorias/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable(value = "id") UUID id) {
        Optional<Mercadoria> product0 = MercadoriaRepository.findById(id);
        if (product0.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }

        MercadoriaRepository.delete(product0.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/Mercadorias/{id}")
    public ResponseEntity<Mercadoria> updateProduct(@PathVariable(value = "id") UUID id,
            @RequestBody @Valid Mercadoria Mercadoria) {
        Optional<Mercadoria> Mercadoria0 = MercadoriaRepository.findById(id);
        if (Mercadoria0.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        }
        Mercadoria.setId(id);
        return new ResponseEntity<>(MercadoriaRepository.save(Mercadoria), HttpStatus.OK);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> erros = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            erros.put(fieldName, errorMessage);

        });
        return erros;
    }
}