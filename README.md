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

Prometheus: as métricas já estão sendo geradas e podem ser acessadas na porta 9090


![](/readme-images/prometheus.jpg)


Grafana: é possível visualizar os gráficos gerados na porta 3000 -> agora falta ainda construir métricas customizadas e extrair dados importantes da API e tentar fazer alguns alertas via grafana. Pelo que pesquisei, seria importante cobrir as seguintes estatísticas:

    - uso de capacidade do processador - cpu
    - porcentagem de alocação de memória volátil - ram
    - número de resposta http na faixa dos 400 e 500 em relação ao total de requisições
    - tempo média das respostas 
    - número de threads dedicadas para determinados processos (foco nas requisições async)
    

![](/readme-images/grafana.jpg)



### Keycloak


Keycloak: todas as configurações já foram feitas e a geração do token está funcionando (testei com todos os grant-types e estão funcionando). Nos testes fica claro como está sendo feito o processo de autenticação e autorização via Keycloak. Primeiro, a autenticação é feita via POST para a url http://localhost:18080/auth/realms/nosso-cartao/protocol/openid-connect/token passando os dados como x-www-form-urlencoded. Essa requisição retorna um token e estamos autenticados.


```

    public String getToken() throws JSONException {

        Response response = given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "password")
                .formParam("username", "bootcamp")
                .formParam("password", "bootcampproposta")
                .formParam("client_id", "nosso-cartao")
                .formParam("client_secret", "")
                .when()
                .post("http://localhost:18080/auth/realms/nosso-cartao/protocol/openid-connect/token");


        JSONObject jsonObject = new JSONObject(response.getBody().asString());
        String accessToken = jsonObject.get("access_token").toString();

        String token = "Bearer " + accessToken;

        return token;
    }

```


Depois precisamos de autorização para acessar os recursos da API. Essa autorização é feita via headers da requisição como 'Authorization: Bearer token'. 


```

    @Test
    public void deveRetornarOkSolicitarAcompanhamentoDeProposta() throws JSONException {

        /* {idProposta} */

        given()
                .basePath("/api/propostas/55252cf8-e5ac-4f42-b1ea-f1cb419a2da7")
                .header("Authorization", getToken())
                .port(port)
                .when()
                .get()
                .then()
                .statusCode(HttpStatus.OK.value());

    }


```


### Jaeger


Jaeger: as requisições já estão passando pelo jaeger e todo o tracing tá funcionando


### OpenApi 3.0


OpenApi 3.0: aqui foi mais motivo de organização mesmo. Vou atualizando aqui os endpoints à medida que for desenvolvendo a API.

Ao rodar a aplicação, acessar:
> localhost:8080/swagger-ui.html


### CodeMR

Aqui já foi possível identificar duas classes que estão com as métricas de complexidade, acoplamento e coesão com nível médio, já sendo indicado tentar abaixar essa pontuação. As classes são Proposta e PropostaResource. O que tem algum sentido, tendo em vista que é o centro da API, mesmo assim, fica a tentativa de melhorar essa métrica.



![](/readme-images/analise_proposta.jpg)



