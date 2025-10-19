/**
 * Módulo de funções utilitárias.
 * 
 * @author Prof. Dr. David Buzatto
 */
import { ContainerUtizadoError } from "../erros/ContainerUtizadoError.js";

// formatadores
export const fmtNumeroBrasil = new Intl.NumberFormat( "pt-BR", { minimumFractionDigits: 2, maximumFractionDigits: 2 });
export const fmtNumeroUS = new Intl.NumberFormat( "en-US", { minimumFractionDigits: 2, maximumFractionDigits: 2 });
export const fmtMonetario = new Intl.NumberFormat( "pt-BR", { style: "currency", currency: "BRL", minimumFractionDigits: 2, maximumFractionDigits: 2 });
export const fmtDataBrasil = new Intl.DateTimeFormat( "pt-BR" );

export function formatarNumeroBrasil( numero ) {
    return fmtNumeroBrasil.format( numero );
}

export function formatarNumeroUS( numero ) {
    return fmtNumeroUS.format( numero );
}

export function formatarDinheiro( valor ) {
    return fmtMonetario.format( valor );
}

export function formatarDataBrasil( data ) {
    return fmtDataBrasil.format( Date.parse( data.replace( /-/g, '\/' ) ) );
}

/**
 * Função customizada de "fetch" que carrega o token JWT e envia na requisição.
 * 
 * @param {*} url A url do recurso.
 * @param {*} metodo O método que será utilizado.
 * @param {*} dados Dados quer serão codificados na requisição.
 * @param {*} propToken Nome da propriedade do token JWT no local storage.
 * 
 * @returns A promise da requisição.
 */
export async function customFetch( url, metodo, dados = null, propToken = "token" ) {

    const controller = new AbortController();
    const timeoutId = setTimeout( () => controller.abort(), 30000 ); // 30 segundos

    try {

        let headers = {
            "Accept": "application/json",
            "Content-Type": "application/json"
        };

        if ( propToken ) {
            const token = localStorage.getItem( propToken );
            if ( token ) {
                headers[ "Authorization" ] = "Bearer " + token;
            }
        }

        const config = {
            method: metodo,
            headers: headers,
            signal: controller.signal
        };

        if ( dados ) {
            config.body = JSON.stringify( dados );
        }

        const response = await fetch( url, config );
        clearTimeout( timeoutId );
        return response;

    } catch ( error ) {
        clearTimeout( timeoutId );
        if ( error.name === "AbortError" ) {
            throw new Error( "A requisição excedeu o tempo limite" );
        }
        throw error;
    }

}

export function criarTd( dados ) {
    let td = document.createElement( "td" );
    td.classList.add( "align-middle", "ps-3" );
    td.textContent = dados;
    return td;
}

export function criarTdBoolean( objeto, propriedade, funcaoClicar, textoTrue = "sim", textoFalse = "não", corTrue = "#198754", corFalse = "#dc3545" ) {
    
    let td = criarTd( "" );
    td.style.textAlign = "center";
    td.style.cursor = "pointer";
    
    let span = document.createElement( "span" );
    span.classList.add( "badge" );
    
    if ( objeto[propriedade] ) {
        span.style.backgroundColor = corTrue;
        span.textContent = textoTrue;
    } else {
        span.style.backgroundColor = corFalse;
        span.textContent = textoFalse;
    }
    
    td.append( span );
    td.addEventListener( "click", funcaoClicar );
    
    return td;
    
}

export function criarOption( valor, label, objeto = null, propriedadesDataset = null ) {
    let opt = document.createElement( "option" );
    opt.value = valor;
    opt.textContent = label;
    if ( objeto && propriedadesDataset ) {
        propriedadesDataset.forEach( propriedade => {
            opt.dataset[propriedade] = objeto[propriedade] ? objeto[propriedade] : null;
        });
    }
    return opt;
}

/**
 * Carrega dados de um fragmento para um container.
 * 
 * @param {*} idContainer id do container que receberá o código HTML carregado.
 * @param {*} url URL do fragmento.
 * @param {*} replaceObj objeto que contém propriedades quer serão utilizadas para substituir itens dentro do fragmento carregado.
 * No fragmento as posições quer serão substituídas devem ter o formato {{propriedade}}.
 * 
 * @returns a promise de carregamento.
 */
export async function carregarFragmento( idContainer, url, replaceObj = null ) {    

    const node = document.getElementById( idContainer );

    if ( !node ) {
        throw new Error( `O nó de id=${idContainer} não existe!` );
    }

    // previne carregamento sucessivo em um container que já foi usado.
    if ( !node.dataset.usado ) {
        
        return await fetch( url ).then( response => { 
            if ( response.ok ) {
                return response.text();
            } else {
                throw new Error( `Erro ao carregar recurso ${url}` );
            }
        }).then( html => {
            if ( replaceObj ) {
                for ( const property in replaceObj ) {
                    html = html.replaceAll( `{{${property}}}`, replaceObj[property] );
                }
            }
            node.dataset.usado = true;
            node.innerHTML = html;
        }).catch( error => {
            throw error;
        });

    } else {
        throw new ContainerUtizadoError( `O nó de id=${idContainer} já foi usado!` );
    }

}

/**
 * Esconde todos os nós de um nó.
 * 
 * @param {*} id id do nó que terá os filhos escondidos.
 */
export function esconderFilhos( id ) {

    const container = document.getElementById( id );

    Array.from( container.children ).forEach( element => {
        esconder( element );
    });

}

/**
 * Mostra um nó.
 * 
 * @param {*} idOuNo id do nó ou o nó em si.
 */
export function mostrar( idOuNo ) {
    if ( typeof idOuNo === "string" ) {
        idOuNo = document.getElementById( idOuNo );
    }
    idOuNo.classList.remove( "escondido" );
}

/**
 * Esconde um nó.
 * 
 * @param {*} idOuNo id do nó ou o nó em si.
 */
export function esconder( idOuNo ) {
    if ( typeof idOuNo === "string" ) {
        idOuNo = document.getElementById( idOuNo );
    }
    idOuNo.classList.add( "escondido" );
}

/**
 * Esconde todos os filhos do elemento que tem id igual à idEsconder e exibe
 * o elemento de idMostrar.
 * 
 * @param {*} idEsconder Id do nó que terá seus filhos escondidos.
 * @param {*} idMostrar Id do nó que será mostrado.
 */
export function esconderMostrar( idEsconder, idMostrar ) {
    esconderFilhos( idEsconder );
    mostrar( idMostrar );
}

/**
 * Executa um fetch na URL e usa os dados para popular um select.
 * 
 * @param {*} url A url que será requisitada.
 * @param {*} select O select alvo.
 * @param {*} propriedades As propriedades que serão lidas dos objetos resultantes.
 * Os objetos devem vir na resposta codificados na propriedade items.
 */
export async function carregarSelect( url, select, propriedades, funcaoLabel = null, propriedadesDataset = null ) {

    const response = await customFetch( url, "GET" );
    const data = await response.json();

    if ( response.ok ) {
        select.innerHTML = "";
        data.forEach( ( dado, index ) =>{
            if ( funcaoLabel ) {
                select.append( criarOption( dado[propriedades.id], funcaoLabel( dado ), dado, propriedadesDataset ) );
            } else {
                select.append( criarOption( dado[propriedades.id], dado[propriedades.label], dado, propriedadesDataset ) );
            }
        });
    } else {
        Utils.abrirModalMensagem( "ERRO", Utils.montarMensagemErro( data ) );
    }

}

/**
 * Configura as mensagens de validação dos campos de formulário a cada interação
 * do usuário.
 * 
 * @example
 * const campos = [
 *   { campo: emailInput, div: emailDiv, typeMismatch: "Email inválido" },
 *   { campo: nomeInput, div: nomeDiv, valueMissing: "Nome obrigatório" }
 * ];
 * configurarValidacaoCamposAoMudarEstado(campos);
 * 
 * @param {Array<{campo: HTMLElement, div: HTMLElement, valueMissing?: string, typeMismatch?: string, patternMismatch?: string, valid?: string, tooLong?: string, tooShort?: string, rangeOverflow?: string, rangeUnderflow?: string}>} camposValidacao
 * um array de itens de validação, compostos por um campo que é um componente de
 * formulário e uma div onde será configurada a mensagem de validação. Cada item
 * de validação pode também ser configurado com uma ou mais mensagens
 * personalizadas para serem usadas na exibição do status da validação daquele
 * componente, sendo elas:
 *     - patternMismatch: padrão inválido
 *     - tooLong: muito longo (maior que maxlength)
 *     - tooShort: muito curto (menor que minlength)
 *     - rangeOverflow: valor maior (maior que max)
 *     - rangeUnderflow: valor menor (menor que min)
 *     - typeMismatch: o tipo não bate (url, email etc)
 *     - valid: se o campo é válido
 *     - valueMissing: se o campo for obrigatório (required)
 */
export function configurarValidacaoCamposAoMudarEstado( camposValidacao ) {

    camposValidacao.forEach( item => {

        if ( !item.campo || !item.div ) {
            console.warn( "Campo ou div não encontrados:", item );
            return;
        }
        
        item.campo.addEventListener( "input", event => {
            configurarMensagemValidacao( item );
            configurarMensagemValidacaoCustomizada( item );
        });

        item.campo.addEventListener( "blur", event => {
            configurarMensagemValidacao( item );
            configurarMensagemValidacaoCustomizada( item );
        });

    });

}

/**
 * Executa a validação de um formulário. Primeiramente, limpa todas as mensagens
 * de erro e posteriormente executa a verificação usando a Constraint Validation API nativa.
 * 
 * @example
 * if ( validarFormulario( camposValidacao ) ) {
 *     // Formulário válido - pode enviar
 * } else {
 *     // Há erros - mensagens já foram exibidas
 * }
 * 
 * @param {Array<{campo: HTMLElement, div: HTMLElement, valueMissing?: string, typeMismatch?: string, patternMismatch?: string, valid?: string, tooLong?: string, tooShort?: string, rangeOverflow?: string, rangeUnderflow?: string}>} camposValidacao
 * um array de itens de validação, compostos por um campo que é um componente de
 * formulário e uma div onde será configurada a mensagem de validação. Cada item
 * de validação pode também ser configurado com uma ou mais mensagens
 * personalizadas para serem usadas na exibição do status da validação daquele
 * componente, sendo elas:
 *     - patternMismatch: padrão inválido
 *     - tooLong: muito longo (maior que maxlength)
 *     - tooShort: muito curto (menor que minlength)
 *     - rangeOverflow: valor maior (maior que max)
 *     - rangeUnderflow: valor menor (menor que min)
 *     - typeMismatch: o tipo não bate (url, email etc)
 *     - valid: se o campo é válido
 *     - valueMissing: se o campo for obrigatório (required)
 * 
 * @returns {boolean} Verdadeiro se o formulário for válido, falso caso contrário.
 */
export function validarFormulario( form, camposValidacao ) {

    limparMensagensValidacao( form, camposValidacao );
    configurarMensagensValidacaoCustomizada( camposValidacao );

    if ( !form.checkValidity() ) {
        form.classList.add( "was-validated" );
        configurarMensagensValidacao( camposValidacao );
        return false;
    }

    return true;

}

/**
 * Limpa o formulário, resetando todos os componentes e limpando as mensagens
 * de validação.
 * 
 * @param {*} form O formulário.
 * @param {*} camposValidacao Os campos de validação caso o formulário possua campos que precisam ser validados.
 */
export function limparFormulario( form, camposValidacao = null ) {
    if ( camposValidacao ) {
        limparMensagensValidacao( form, camposValidacao );
    }
    form.reset();
}

/**
 * Configura as mensagens de validação de um conjunto de campos de formulário
 * em suas respectivas divs.
 * 
 * @param {Array<{campo: HTMLElement, div: HTMLElement, valueMissing?: string, typeMismatch?: string, patternMismatch?: string, valid?: string, tooLong?: string, tooShort?: string, rangeOverflow?: string, rangeUnderflow?: string}>} camposValidacao
 * um array de itens de validação, compostos por um campo que é um componente de
 * formulário e uma div onde será configurada a mensagem de validação. Cada item
 * de validação pode também ser configurado com uma ou mais mensagens
 * personalizadas para serem usadas na exibição do status da validação daquele
 * componente, sendo elas:
 *     - patternMismatch: padrão inválido
 *     - tooLong: muito longo (maior que maxlength)
 *     - tooShort: muito curto (menor que minlength)
 *     - rangeOverflow: valor maior (maior que max)
 *     - rangeUnderflow: valor menor (menor que min)
 *     - typeMismatch: o tipo não bate (url, email etc)
 *     - valid: se o campo é válido
 *     - valueMissing: se o campo for obrigatório (required)
 */
export function configurarMensagensValidacao( camposValidacao ) {
    camposValidacao.forEach( item => {
        configurarMensagemValidacao( item );
    });
}

/**
 * Configura as mensagens de validação customizadas para um conjunto de campos
 * de formulário.
 * 
 * @param {*} camposValidacao 
 */
export function configurarMensagensValidacaoCustomizada( camposValidacao ) {
    camposValidacao.forEach( item => {
        configurarMensagemValidacaoCustomizada( item );
    });
}

/**
 * Limpa as mensagens de validação de um conjunto de campos de formulário
 * de suas respectivas divs.
 * 
 * @param {Array<{campo: HTMLElement, div: HTMLElement, valueMissing?: string, typeMismatch?: string, patternMismatch?: string, valid?: string, tooLong?: string, tooShort?: string, rangeOverflow?: string, rangeUnderflow?: string}>} camposValidacao
 * um array de itens de validação, compostos por um campo que é um componente de
 * formulário e uma div onde será configurada a mensagem de validação. Cada item
 * de validação pode também ser configurado com uma ou mais mensagens
 * personalizadas para serem usadas na exibição do status da validação daquele
 * componente, sendo elas:
 *     - patternMismatch: padrão inválido
 *     - tooLong: muito longo (maior que maxlength)
 *     - tooShort: muito curto (menor que minlength)
 *     - rangeOverflow: valor maior (maior que max)
 *     - rangeUnderflow: valor menor (menor que min)
 *     - typeMismatch: o tipo não bate (url, email etc)
 *     - valid: se o campo é válido
 *     - valueMissing: se o campo for obrigatório (required)
 */
export function limparMensagensValidacao( form, camposValidacao ) {

    form.classList.remove( "was-validated" );

    try {
        camposValidacao.forEach( item => {
            if ( !item.campo || !item.div ) {
                console.warn( "Campo ou div não encontrados:", item );
                return;
            }
            item.campo.setCustomValidity( "" );
            item.campo.removeAttribute( "aria-invalid" );
            item.div.textContent = "";
        });
    } catch ( error ) {
        console.error( "Erro ao limpar mensagens de validação:", error );
    }

}

/**
 * Configura a mensagem de validação de um campo de formulário em uma div.
 * 
 * Se o campo for válido e houver mensagem de sucesso configurada (propriedade 'valid'),
 * ela será exibida. Caso contrário, a div ficará vazia.
 * 
 * Para campos inválidos, prioriza mensagens personalizadas sobre a mensagem
 * padrão do navegador (validationMessage).
 * 
 * Automaticamente gerencia atributos ARIA para acessibilidade:
 * - aria-invalid="true" para campos inválidos
 * - aria-describedby conecta o campo com a mensagem de erro
 * 
 * @param {{campo: HTMLElement, div: HTMLElement, valueMissing?: string, typeMismatch?: string, patternMismatch?: string, valid?: string, tooLong?: string, tooShort?: string, rangeOverflow?: string, rangeUnderflow?: string}} item
 * um item de validação, composto por um campo que é um componente de
 * formulário e uma div onde será configurada a mensagem de validação. Cada item
 * de validação pode também ser configurado com uma ou mais mensagens
 * personalizadas para serem usadas na exibição do status da validação daquele
 * componente, sendo elas:
 *     - patternMismatch: padrão inválido
 *     - tooLong: muito longo (maior que maxlength)
 *     - tooShort: muito curto (menor que minlength)
 *     - rangeOverflow: valor maior (maior que max)
 *     - rangeUnderflow: valor menor (menor que min)
 *     - typeMismatch: o tipo não bate (url, email etc)
 *     - valid: se o campo é válido
 *     - valueMissing: se o campo for obrigatório (required)
 */
function configurarMensagemValidacao( item ) {

    try {

        const campo = item.campo;
        const div = item.div;

        if ( !campo || !div ) {
            console.warn( "Campo ou div não encontrados:", item );
            return;
        }
        
        if ( !campo.valid ) {

            for ( const k in campo.validity ) {
                if ( campo.validity[k] ) {
                    const mensagem = item[k];
                    if ( mensagem ) {
                        div.textContent = mensagem;
                        return;
                    }
                }
            }

            div.textContent = campo.validationMessage;

            campo.setAttribute( "aria-invalid", "true" );
            campo.setAttribute( "aria-describedby", div.id );

        } else {
            campo.removeAttribute( "aria-invalid" );
            div.textContent = item.valid || "";
        }

    } catch ( error ) {
        console.error( "Erro ao configurar mensagem de validação:", error );
    }

}

/**
 * Configura validações customizadas para um campo de formulário.
 * 
 * Atualmente suporta:
 *  - igualA: verifica se o valor de um campo é igual ao valor de outro campo;
 *  - cpf: verifica se o valor de um campo é um CPF válido; 
 *  - cnpj: verifica se o valor de um campo é um CNPJ válido.
 * 
 * @param {Object} item Item de validação com propriedades customizadas
 */
function configurarMensagemValidacaoCustomizada( item ) {

    const campo = item.campo;
    const div = item.div;

    if ( !campo || !div ) {
        console.warn( "Campo ou div não encontrados:", item );
        return;
    }

    let valido = true;
    let mensagem = "";

    // validação customizada para campos com o mesmo valor
    if ( item.igualA && valido ) {
        if ( campo.value !== item.igualA.campo.value ) {
            valido = false;
            mensagem = item.igualA.mensagem || "Os valores não coincidem.";
        }
    }
    
    // validação customizada para CPF válido
    if ( item.cpf && valido ) {
        if ( !validarCPF( item.campo.value ) ) {
            valido = false;
            mensagem = item.cpf.mensagem || "CPF inválido.";
        }
    }

    // validação customizada para CNPJ válido
    if ( item.cnpj && valido ) {
        if ( !validarCNPJ( item.campo.value ) ) {
            valido = false;
            mensagem = item.cnpj.mensagem || "CNPJ inválido.";
        }
    }

    if ( !valido ) {

        campo.setCustomValidity( mensagem );

        campo.setAttribute( "aria-invalid", "true" );
        campo.setAttribute( "aria-describedby", div.id );

        div.textContent = mensagem;

    } else {
        campo.setCustomValidity( "" );
        campo.removeAttribute( "aria-invalid" );
        div.textContent = item.valid || "";
    }

}

/**
 * Valida uma string verificando se é um CPF válido.
 * 
 * @param {*} cpf O CPF a ser validado.
 * @returns {boolean} Verdadeiro caso o CPF seja válido, falso caso contrário.
 */
export function validarCPF( cpf ) {
    
    const mult = [ 10, 9, 8, 7, 6, 5, 4, 3, 2 ];
    cpf = cpf.replace( /\D/g, "" );

    // rejeitar CPFs com todos os dígitos iguais
    if ( cpf.length !== 11 || /^(\d)\1{10}$/.test( cpf ) ) {
        return false;
    }
        
    let soma1 = 0;
    let soma2 = 0;

    soma2 = +cpf.charAt(0) * 11;
    for ( let i = 0; i < 9; i++ ) {
        soma1 += +cpf.charAt(i) * mult[i];
        soma2 += +cpf.charAt(i + 1) * mult[i];
    }

    let digito1 = soma1 * 10 % 11;
    let digito2 = soma2 * 10 % 11;

    digito1 = digito1 === 10 ? 0 : digito1;
    digito2 = digito2 === 10 ? 0 : digito2;

    return digito1 === +cpf.charAt(9) && digito2 === +cpf.charAt(10);

}

/**
 * Valida uma string verificando se é um CNPJ válido.
 * 
 * @param {string} cnpj O CNPJ a ser validado.
 * @returns {boolean} Verdadeiro caso o CNPJ seja válido, falso caso contrário.
 */
export function validarCNPJ( cnpj ) {
    
    const mult = [ 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2 ];
    cnpj = cnpj.replace( /\D/g, "" );
    
    // rejeitar CNPJs com todos os dígitos iguais
    if ( cnpj.length !== 14 || /^(\d)\1{13}$/.test( cnpj ) ) {
        return false;
    }

    let soma1 = 0;
    let soma2 = 0;

    soma2 = +cnpj.charAt(0) * 6;
    for ( let i = 0; i < mult.length; i++ ) {
        soma1 += +cnpj.charAt(i) * mult[i];
        soma2 += +cnpj.charAt(i + 1) * mult[i];
    }

    let digito1 = soma1 % 11;
    let digito2 = soma2 % 11;
    
    digito1 = digito1 < 2 ? 0 : 11 - digito1;
    digito2 = digito2 < 2 ? 0 : 11 - digito2;

    return digito1 === +cnpj.charAt(12) && digito2 === +cnpj.charAt(13);

}

export function resetarSelect( select, textoPadrao, value = "" ) {
    
    select.innerHTML = "";
    
    if ( textoPadrao ) {
        const option = document.createElement( "option" );
        option.value = value;
        option.textContent = textoPadrao;
        select.append( option );
    }
    
}

export function selecionaLinha( linha, tbody ) {
    desselecionarLinhas( tbody );
    linha.classList.add( "linhaSelecionada" );
}

export function desselecionarLinhas( tbody ) {
    tbody.querySelectorAll( "tr" ).forEach( tr => {
        tr.classList.remove( "linhaSelecionada" );
    });
}

export function montarMensagemErro( objErro ) {

    let html = "<p>Houve um ou mais erros ao processar sua requisição:</p>";

    html += "<ul>";

    for ( const key in objErro ) {
        html += `<li><span class="campoErro">${key}: </span><span>${objErro[key]}</span></li>`;
    }

    html += "</ul>";
    return html;

}

/**
 * A partir de uma URL base gera uma URL com parâmetros codificados para uso do método GET.
 * 
 * @param {*} urlBase A URL base que será usada.
 * @param {*} parametros Os parâmetros que serão adicionados.
 * 
 * @returns Uma URL com parâmetros, não codificada.
 */
export function gerarUrlComParametros( urlBase, parametros ) {

    const [base, queryExistente] = urlBase.split( "?" );

    const parametrosBusca = new URLSearchParams( queryExistente || "" );

    if ( parametros ) {
        Object.keys( parametros ).forEach( key => {
            parametrosBusca.append( key, parametros[key] );
        });
    }

    const queryString = parametrosBusca.toString();
    return queryString ? `${base}?${queryString}` : base;

}
