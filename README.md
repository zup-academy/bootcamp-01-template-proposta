## Proposta


### Docker

- Docker: Já é possível criar um container para a API 


- Como diz na documentação do Spring: "This command builds an image and tags it as..." - no caso aqui, 'bootcamp/proposta'.

> docker build -t bootcamp/proposta .


- Depois, para rodar a API dentro do container

> docker run -p 8080:8080 bootcamp/proposta


### Testes - Pitest, RestAssured, JUnit


### CDD


### Prometheus e Grafana


### Keycloak


- Keycloak: todas as configurações já foram feitas e a geração do token está funcionando (testei com grant type = authorization code).



![](/readme-images/keycloak.jpg)


### Jaeger

- Jaeger: o 'spring.application.name' ficou como proposta



![](/readme-images/jaeger.jpg)


- Jaeger: as requisições já estão passando pelo jaeger e o gráfico está sendo gerado



![](/readme-images/jaeger1.jpg)



### OpenApi 3.0


- OpenApi 3.0: aqui foi mais motivo de organização mesmo. Vou atualizando aqui os endpoints à medida que for desenvolvendo a API.



![](/readme-images/endpointsproposta.jpg)


### CodeMR

- Sobre algumas das métricas que estamos tomando como fundamento para o desenvolvimento, o 'peso' da minha aplicação está muito na camada de controllers. É algo que preciso ajustar desse ponto em diante. Tentarei focar bastante na coesão.



![](/readme-images/metricas.jpg)



