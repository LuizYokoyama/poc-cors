### Explicações sobre o CSRF

* O ***gateway*** foi configurado com a seguinte configuração no arquivo *SecurityConfig.java*:

![img_11.png](img_11.png)

* Com essa configuração do ***csrf***, é necessário enviar os *cookies* da sessão e o token csrf
em todas as requisições HTTP que modificam o estado dos dados do *backend* (PATCH, POST, PUT e DELETE):

![img_12.png](img_12.png)

* É necessário configurar o ***cors*** do *gateway*, no arquivo *SecurityConfig.java*, para permitir o envio dos *cookies* da sessão:

![img_13.png](img_13.png)

* Para incluir estes *cookies* na requisição, pode-se usar o jQuery.ajax ou 
se usar ***fetch***, deve-se incluir o parâmetro ***credentials: 'include'***.
* Também é necessário enviar o ***token csrf*** no *header* das requisições:

![img_14.png](img_14.png)

* Para usar o ***Swagger***, basta a seguinte configuração no arquivo *application.yml* do serviço que possui o método **POST**:

![img_15.png](img_15.png)

Funcionamento do ***Swagger***:

![img_18.png](img_18.png)

Funcionamento do ***frontend Svelte***:

![img_17.png](img_17.png)

## Instruções gerais de execução:

* Entre em cada subdiretorio: 'read' e 'write' executando o seguinte comando dentro de cada um:

***mvn package -Dmaven.test.skip***

* Depois, volte ao diretorio raiz "poc-cors" e execute o seguinte comando:

***docker-compose up -d --build***


* Terminado tudo sem erros, acesse com um browser a configuração do Keycloack em:

***http://localhost:8080/auth/admin/***

user: admin 
pass: admin

Crie um Realm chamado "my" e crie um usuário com senha para prosseguir com os testes:

![img.png](img.png)

Crie também um 'client' com as seguintes propriedades:

![img_1.png](img_1.png)

Valid Redirect URIs = *

Web Origins = *

Clique no botão 'Save'.


Após colocar 'Access Type' = 'Confidential', aprerecerá a guia 'Credentials', nela 
em 'Client Authenticator' escolha 'Client Id and Secret', gere e copie o 'secret'.

Com esse secret você pode cola-lo na tela de login do Swagger:

![img_2.png](img_2.png)

ou cola-lo no arquivo 'application.yml' dos serviços 'read' e 'write' e refazer os dois primeiros passos.

* Execute o ***"gateway"*** separadamente, por exemplo pela IDE;

* Depois acesse com um browser as páginas do Swagger para realizar os testes:

***Pelo GateWay:***

***http://localhost:5001/read/swagger-ui.html***
***http://localhost:5001/write/swagger-ui.html***

***Sem passar pelo Gateway:***

***http://localhost:3001/read/swagger-ui.html***
***http://localhost:3002/write/swagger-ui.html***

## Funcionamento do 'cors'

Cada serviço tem sua própria configuração de ***cors*** e funcionam em conjunto:

* *FilterChain* do Gateway:
![img_8.png](img_8.png)

* *Application.yml* do Gateway:

![img_10.png](img_10.png)

* Writeservice:

![img_4.png](img_4.png)

* ReadService:

![img_5.png](img_5.png)

* Keycloack:

![img_6.png](img_6.png)

Neste caso, foram liberados todos os acessos no ***gateway*** e no ***Keycloack*** e restringido o ***"cors"*** somente nos serviços **read** 
e **write**.


## Usando o svelte

* Crie no Keycloak um cliente "svelte" com acesso público:

![img_7.png](img_7.png)

* No diretório ***vite-svelte*** execute: 

***npm run dev***

* Abra o navegador para testar a aplicação (o gateway e o docker-compose devem estar rodando também):

http://localhost:5173/

## Explicações sobre o ***CORS***

* No ***CORS***, a porta ***5001*** foi liberada para permitir que se possa acessar o swagger pelo ***gateway***.
* No *application.yml* do *Gateway*, o ***cors*** tem **precedência** sobre o ***cors*** do *httpSecurity*, e funciona mesmo desabilitando o ***CORS*** lá no *httpSecurity*.
* Tanto os cors do gateway, quanto dos seviços devem estar habilitados e configurados para se evitar que o navegador bloqueie o javascript de acessá-los.
* É preciso se adicionar o *filter* no *application.yml* do ***gateway***: ***DedupeResponseHeader=Access-Control-Allow-Origin*** para remover o ***header duplicado*** *(um gerado pelo gateway e outro pelo serviço)*, porque os navegadores tambem bloqueiam se tiver mais de um ***Origin*** e também bloqueiam se não tiver.
* Sem o ***cors*** **configurado**, o navegador também **bloqueia por padrão** o ***header 'Content-Type': 'application/json'*** e o *preflight request* com o *httpMethod Options*:

![img_9.png](img_9.png)