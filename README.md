## Proposta


### Docker

Docker: Já é possível criar um container para a API 

Como consta na documentação do Spring (link no material do apoio do bootcamp): "This command builds an image and tags it as..." - no caso aqui, 'bootcamp/proposta'.


> docker build -t bootcamp/proposta .


Depois, para rodar a API dentro do container


> docker run -p 8080:8080 bootcamp/proposta



### Testes - Pitest, RestAssured, JUnit



![](/readme-images/cobertura-testes-proposta.jpg)



### CDD




### Prometheus e Grafana

Prometheus: as métricas já estão sendo geradas na porta 9090



![](/readme-images/prometheus1.jpg)



Grafana: é possível visualizar os gráficos gerados na porta 3000 -> agora falta ainda construir métricas customizadas e extrair dados importantes da API.



![](/readme-images/grafana1.jpg)



![](/readme-images/grafana2.jpg)



### Keycloak


Keycloak: todas as configurações já foram feitas e a geração do token está funcionando (testei com grant type = authorization code).





![](/readme-images/keycloak.jpg)




### Jaeger

Jaeger: as requisições já estão passando pelo jaeger e o gráfico está sendo gerado




![](/readme-images/jaeger1.jpg)




### OpenApi 3.0

OpenApi 3.0: aqui foi mais motivo de organização mesmo. Vou atualizando aqui os endpoints à medida que for desenvolvendo a API.





![](/readme-images/endpointsproposta.jpg)





### CodeMR

Aqui já foi possível identificar duas classes que estão com as métricas de complexidade, acoplamento e coesão com nível médio, já sendo indicado tentar abaixar essa pontuação. As classes são Proposta e PropostaResource. O que tem algum sentido, tendo em vista que é o centro da API, mesmo assim, fica a tentativa de melhorar essa métrica.



![](/readme-images/analise_proposta.jpg)



