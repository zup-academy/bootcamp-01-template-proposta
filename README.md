## Proposta


### Prometheus e Grafana

Prometheus: as métricas já estão sendo geradas e podem ser acessadas na porta 9090. Com folga, o Prometheus e o Grafana foram as ferramentas mais legais de usar nesse desafio e com certeza são ferramentas para estar constantemente estudando e usando em projetos.


![](/readme-images/prometheus.jpg)


Grafana: é possível visualizar os gráficos gerados na porta 3000. Pelo que pesquisei, seria importante cobrir as seguintes estatísticas:


- uso da capacidade de processamento - cpu

![](/readme-images/cpu-grafana.jpg)



- uso da memória pela jvm

![](/readme-images/jvm-grafana.jpg)


- número de resposta http na faixa dos 400 e 500 em relação ao total de requisições

Essa aqui ainda tenho que elaborar a query



- tempo média das respostas 

Também preciso montar a query aqui


- número de threads dedicadas para determinados processos (foco nas requisições async)

![](/readme-images/jvm-threads.jpg)



### CDD

- Como o contador de complexidade desenvolvido no desafio anterior ainda não está muito preciso, optei por fazer a contagem manual mesmo. Plotei três gráficos, um com uma visão geral dos pontos de dificuldade de entendimento e outros dois com uma análise das classes por critérios (esses dois são basicamente iguais, só mudei a visualização: sendo que um foca no valor absoluto de pontos por classe e o outro na variação desse total de pontos por classe).


1) Visão geral

![](/readme-images/pontos-cdd-geral.png)



2) Pontuação por critérios - 1

![](/readme-images/pontos-cdd-criterio.png)



3) Pontuação por critérios - 2

![](/readme-images/pontos-cdd-criterio-variacao.png)


Observação sobre as configurações: Tive problema com as configurações do Prometheus. Na documentação consta um arquivo de configuração inicial 'prometheus.yml', local onde deveria definir os targets e todas as outras configurações, como rules etc. Da primeira vez que criei o container prom/prometheus, só foi reconhecido o próprio prometheus como target em localhost:9090. Tentei diversas coisas, removi e subi denovo o container diversas vezes, tentei copiar um arquivo 'prometheus.yml' do host para o container (fiz um docker cp para tentar transferir o arquivo do host para o container), deletei e recriei (recriação automática ao subir os containers) os volumes criados diversas vezes até que funcionou. Sinceramente não sei em qual etapa a configuração foi ajustada corretamente e fez o prometheus pegar o localhost:8080 como target. Vi que ter criado o 'prometheus.yml' na pasta do projeto teve algum efeito (tendo em vista que a primeira vez configurei o target como HOST_IP:8080 e depois 127.0.0.1:8080 - da primeira não funcionou, da segunda sim), mas não consegui identificar exatamente em qual momento o container utilizou o yml do host como configurações do prometheus instalado no container (obs1.: não sei se foi pelo docker cp ou se colocando na pasta raíz do projeto já está funciona como pasta default para o prometheus in docker procurar as configurações. obs2.: exclui o arquivo de configurações yml, mas está exatamente igual ao do artigo do medium abaixo - o qual está seguindo precisamente a documentação).

Utilizei essa artigo do Medium e a documentação como auxílio: https://medium.com/aeturnuminc/configure-prometheus-and-grafana-in-dockers-ff2a2b51aa1d



### Docker

Docker: Já é possível criar um container para a API 

Como consta na documentação do Spring (link no material do apoio do bootcamp): "This command builds an image and tags it as..." - no caso aqui, 'bootcamp/proposta'.


> docker build -t bootcamp/proposta .


Depois, para rodar a API dentro do container


> docker run -p 8080:8080 bootcamp/proposta



### Testes - Pitest, RestAssured, JUnit



![](/readme-images/cobertura-testes-proposta.jpg)




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



