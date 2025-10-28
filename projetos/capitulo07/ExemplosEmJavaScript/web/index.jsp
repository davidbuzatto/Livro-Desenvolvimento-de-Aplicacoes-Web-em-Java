<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="cp" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Exemplos em JavaScript</title>
    <script src="${cp}/js/libs/jquery/jquery.min.js"></script>
    <script src="${cp}/js/exemplo01.js"></script>
    <script src="${cp}/js/exemplo02.js"></script>
    <script src="${cp}/js/exemplo03.js"></script>
    <script src="${cp}/js/exemplo04.js"></script>
    <script src="${cp}/js/exemplo05.js"></script>
    <script src="${cp}/js/exemplo06.js"></script>
    <script src="${cp}/js/exemplo07.js"></script>
    <script src="${cp}/js/exemplo08.js"></script>
    <script src="${cp}/js/exemplo09.js"></script>
    <script src="${cp}/js/exemplo10.js"></script>
    <script src="${cp}/js/exemplo11.js"></script>
    <script src="${cp}/js/exemplo12.js" type="module"></script>
    <link rel="stylesheet" href="${cp}/css/estilos.css"/>
  </head>
  <body>
    <div>
      
      <h1>Construções da Linguagem</h1>
      
      <p>
        <button onclick="executarExemplo01(event)">
          Exemplo 01 - Funções de E/S e Operadores Aritméticos
        </button>
      </p>
      
      <p>
        <button onclick="executarExemplo02(event)">
          Exemplo 02 - Declarações de Variáveis e Suas Implicações
        </button>
      </p>
      
      <p>
        <button onclick="executarExemplo03(event)">
          Exemplo 03 - Estruturas Condicionais e Operadores
        </button>
      </p>
      
      <p>
        <button onclick="executarExemplo04(event)">
          Exemplo 04 - Estruturas de Repetição e Arrays
        </button>
      </p>
      
      <p>
        <button onclick="executarExemplo05(event)">
          Exemplo 05 - "Classes" e Objetos e JSON
        </button>
      </p>
    </div>
    
    <hr>
    
    <div>
      
      <h1>Manipulação do DOM</h1>
      
      <div>
        <p>
          <button onclick="executarExemplo06(event)">
            Exemplo 06 - JavaScript Puro
          </button>
        </p>
        <div id="divExemplo06" class="divExemplo"></div>
      </div>
      
      <div>
        <p>
          <button onclick="executarExemplo07(event)">
            Exemplo 07 - Usando jQuery
          </button>
        </p>
        <div id="divExemplo07" class="divExemplo"></div>
      </div>
      
      <div>
        <h2>Exemplo 08 - Manipulação de Formulários</h2>
        <div id="divExemplo08" class="divExemplo">
          
          <form id="form08">
            
            Campo 1:
            <input id="campo01" 
                   type="text" 
                   name="campo01" 
                   value="valor campo 01"/>
            <br/>
            
            Campo 2:
            <input id="campo02" 
                   type="text" 
                   name="campo02" 
                   value="valor campo 02"/>
            <br/>
            
            Select 3:
            <select id="select03" name="select03">
              <option value="o1">Opção 1</option>
              <option value="o2">Opção 2</option>
              <option value="o3">Opção 3</option>
            </select>
            <br/>
            
            Select 4:
            <select id="select04" name="select04" size="4">
              <option value="o1">Opção 1</option>
              <option value="o2" selected>Opção 2</option>
              <option value="o3">Opção 3</option>
            </select>
            
            Área 5:
            <textarea id="area05" 
                      name="area05" 
                      rows="5" cols="10">
                valor da área de texto 05
            </textarea>
          </form>
          <hr>
          <button onclick="lerDadosFormulario(event)">
            Ler dados
          </button> 
          <button onclick="lerDadosFormularioJQuery(event)">
            Ler dados jQuery
          </button>
          <hr>
          <button onclick="inserirDadosFormulario(event)">
            Inserir dados
          </button>
          <button onclick="inserirDadosFormularioJQuery(event)">
            Inserir dados jQuery
          </button>
          <hr>
          <button onclick="inserirNovaOpcao(event)">
            Inserir nova opção (Select 03)
          </button>
          <button onclick="inserirNovaOpcaoJQuery(event)">
            Inserir nova opção jQuery (Select 04)
          </button>
          <hr>
        </div>
      </div>
      
      <div>
        <p>
          <h2>Exemplo 09 - Eventos</h2>
        </p>
        <div id="divExemplo09" class="divExemplo">
            
          Campo (digite algo e veja o console):
          <input id="campoExemplo09"/>
          <br/>
          
          <select id="selectExemplo09">
            <option value="o1">Opção 1</option>
            <option value="o2">Opção 2</option>
            <option value="o3">Opção 3</option>
          </select>
          
        </div>
        <!-- precisa executar depois das
             tags estarem prontas! -->
        <script>registrarEventosExemplo09();</script>
      </div>
      
    </div>
    
    <hr>
    
    <div>
      
      <h1>Requisições Assíncronas e Intercâmbio de Dados</h1>
      
      <div>
        <p>
          <button onclick="executarExemplo10jQuery(event)">
            Exemplo 10 - AJAX com jQuery
          </button>
          <button onclick="executarExemplo10Fetch(event)">
            Exemplo 10 - AJAX com Fetch API
          </button>
        </p>
        <div id="divExemplo10" class="divExemplo"></div>
      </div>
      
      <div>
        <p>
          <button onclick="executarExemplo11jQuery(event)">
            Exemplo 11 - AJAX com jQuery e JSON
          </button>
          <button onclick="executarExemplo11Fetch(event)">
            Exemplo 11 - AJAX com Fetch API e JSON
          </button>
        </p>
        <div id="divExemplo11" class="divExemplo"></div>
      </div>
      
    </div>
    
    <hr>
    
    <div>
      <p>
        <h2>Exemplo 12 - Simulação Usando a Canvas API</h2>
      </p>
      <div id="divExemplo12" class="divExemploCanvas">
        <canvas id="canvasExemplo12"
                width="800"
                height="600"
                class="canvasExemplo10"/>
      </div>
    </div>
    
  </body>
</html>
