package com.springWithRedis.resource;

import com.springWithRedis.entity.Company;
import com.springWithRedis.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyResource {


    @Autowired
    private CompanyService companyService;


    @GetMapping
    public ResponseEntity<List<Company>> findAll() {

        return ResponseEntity.ok(companyService.findAll());
    }


    @GetMapping("/{id}")
    public ResponseEntity<Company> findById(@PathVariable("id") final Long id) {

        return ResponseEntity.ok(companyService.findById(id));
    }


    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Company company) {

        companyService.create(company);
        return ResponseEntity.noContent().build();
    }


    @PutMapping
    public ResponseEntity<Void> update(@RequestBody Company company) {

        companyService.update(company);
        return ResponseEntity.noContent().build();
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") final Long id) {

        companyService.delete(id);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/clean")
    public ResponseEntity<Void> cleanCache() {

        companyService.cleanCache();
        return ResponseEntity.noContent().build();
    }
}
