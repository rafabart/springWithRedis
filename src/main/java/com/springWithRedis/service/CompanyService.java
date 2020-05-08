package com.springWithRedis.service;

import com.springWithRedis.entity.Company;
import com.springWithRedis.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;


    /*
     * Após a primeira requisição será alocado um espaço em memória no Redis com o
     * identificador “Company::findAll” para armazenar todos os registros retornados na consulta
     * */
    @Cacheable(cacheNames = "Company", key = "#root.method.name")
    public List<Company> findAll() {
        log.info("Consultou o Repositório!");
        return companyRepository.findAll();
    }


    /*
     * Podemos ter consultas que buscam por alguma chave única (ID), com isso podemos configurar o cache
     * para armazenar esses registros para que não façamos as consultas em banco de dados a todo momento.
     * */
    @Cacheable(cacheNames = "Company", key = "#id")
    public Company findById(final Long id) {
        log.info("Consultou o Repositório!");
        return companyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Id not found: " + id));
    }


    /*
     * Nem sempre os dados que armazenamos em cache são imutáveis, assim fica a nosso cargo invalidar os dados em cache.
     * Exemplo, na criação de um registro no banco de dados podemos invalidar todo cache.
     *
     * allEntries = true -> faz com que todos dados armazenados o no cache “Company” serão expirados no Redis.
     * As próximas requisições de consuta acessarão o banco de dados novamente.
     * */
    @CacheEvict(cacheNames = "Company", allEntries = true)
    public Company create(final Company company) {
        return companyRepository.save(company);
    }


    /*
     * Otimizar caches, onde em atualizações expiramos apenas o registro alterado e não toda região de cache,
     * desse modo, utilizamos o @CachePut que faz expiração do cache após a atualização do registro de acordo com a chave.
     * */
    @CacheEvict(cacheNames = "Company", allEntries = true)
    public Company update(final Company company) {

        Optional.of(company.getId()).orElseThrow(() -> new EntityNotFoundException("Id is empty"));

        return companyRepository.save(company);
    }


    /*
     * Precisamos limpar nossos caches quando os registros vão ser removidos do banco de dados,
     * com isso podemos usar a anotação @CacheEvict passando uma chave para remover um único registro do cache.
     * */
    @CacheEvict(cacheNames = "Company", key = "#id")
    public void delete(final Long id) {

        Optional.of(id).orElseThrow(() -> new EntityNotFoundException("Id is empty"));

        companyRepository.deleteById(id);
    }


    @CacheEvict(cacheNames = "Company", allEntries = true)
    public void cleanCache() {

    }
}
