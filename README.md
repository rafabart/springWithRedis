Poc de cache com Spring, Docker e Redis.

Usando:

* JDK 11
* Springboot 2.2.1
* IntelliJ
* Rest
* Lombok
* Gradle
* Docker
* Redis
* H2

Subindo um container docker com Redis:
```
  docker run -it \  
    --name redis \  
    -p 6379:6379 \
    redis:5.0.3
 ```

Este projeto possui cache com Redis, fazendo com que toda requisição de consulta dos recursos seja feita primeiramente
no cache (Redis), e no caso de alteração dos recursos, uma nova requisição será feita ao banco de dados (H2).

<p>1) Endpoint</p>
Usando o Postman para criar um novo recurso:

URL: http://localhost:8080/companies

Verbo: POST usando JSON
```
{
 	"name": "Company 1" 
}
```

<p>2) Endpoint</p>

URL: http://localhost:8080/companies

Verbo: GET
