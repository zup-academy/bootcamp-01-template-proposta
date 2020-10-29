## Proposta


- Tentei passar rapidamente pelo desafio para estruturar a dinâmica da API e tentar entender melhor as regras de negócio. Ainda vou refazer os tópicos do desafios algumas vezes. Aproveitei esse primeiro contato de reconhecimento para integrar a API com o Prometheus, Keycloak, Jaeger e adicionei o versionamento dos endpoints via OpenApi 3. O projeto ainda está em estágio inicial e falta bastante coisa ainda, mas já deu pra ver mais ou menos o desenho do sistema e agora o foco é deixar efetivamente funcionamento seguindo todos os fundamentos do bootcamp que estão no material de referência.



### O que já temos?


- Jaeger: o 'spring.application.name' ficou como proposta

![](/readme-images/jaeger.jpg)


- Jaeger: as requisições já estão passando pelo jaeger e o gráfico está sendo gerado

![](/readme-images/jaeger1.jpg)


- Keycloak: todas as configurações já foram feitas e a geração do token está funcionando (testei com grant type = authorization code).

![](/readme-images/keycloak.jpg)


- OpenApi 3.0: aqui foi mais motivo de organização mesmo. Vou atualizando aqui os endpoints à medida que for desenvolvendo a API.

![](/readme-images/endpointsproposta.jpg)


- Sobre algumas das métricas que estamos tomando como fundamento para o desenvolvimento, o 'peso' da minha aplicação está muito na camada de controllers. É algo que preciso ajustar desse ponto em diante. Tentarei focar bastante na coesão.

![](/readme-images/metricas.jpg)



### Comentários


- Fiquei com dúvidas em relação ao Autowired e ao Construtor. Pelas referências que busquei, o construtor parece ter algumas vantagens. Por isso coloquei todas as injeções de depências nas classes controller via construtor e usarei o Autowired apenas quando for necessário. Não estou certo se é a melhor estratégia nem se faz tanta diferença, mas resolvi deixar assim para testar.