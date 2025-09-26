/**
 * Módulo de funções utilitárias.
 * 
 * @author David Buzatto
 */

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

    let headers = {
        "Accept": "application/json",
        "Content-Type": "application/json"
    };

    if ( propToken ) {
        headers[ "Authorization" ] = "Bearer " + localStorage.getItem( propToken );
    }

    if ( dados ) {
        return await fetch( url, {
            method: metodo,
            headers: headers,
            body: JSON.stringify( dados )
        });
    } else {
        return await fetch( url, {
            method: metodo,
            headers: headers
        });
    }

}

export function criarTd( dados ) {
    let td = document.createElement( "td" );
    td.innerHTML = dados;
    return td;
}

export function criarOption( valor, label ) {
    let opt = document.createElement( "option" );
    opt.value = valor;
    opt.innerHTML = label;
    return opt;
}

/**
 * Executa um fetch na URL e usa os dados para popular um select.
 * 
 * @param {*} url A url que será requisitada.
 * @param {*} select O select alvo.
 * @param {*} propriedades As propriedades que serão lidas dos objetos resultantes.
 * Os objetos devem vir na resposta codificados na propriedade items.
 */
export async function carregarSelect( url, select, propriedades ) {

    const response = await customFetch( url, "GET" );
    const data = await response.json();

    if ( response.ok ) {
        select.innerHTML = "";
        data.forEach( ( dado, index ) =>{
            select.append( criarOption( dado[propriedades.id], dado[propriedades.label] ) );
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
        });

        item.campo.addEventListener( "blur", event => {
            configurarMensagemValidacao( item );
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
 * Limpa as mensagens de validação de um conjunto de campos de formulário
 * de suas respectivas divs.
 * 
 * @param {*} form o formulário.
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
            item.div.innerHTML = "";
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
                        div.innerHTML = mensagem;
                        return;
                    }
                }
            }

            div.innerHTML = campo.validationMessage;

            campo.setAttribute( "aria-invalid", "true" );
            campo.setAttribute( "aria-describedby", div.id );

        } else {
            campo.removeAttribute( "aria-invalid" );
            div.innerHTML = item.valid || "";
        }

    } catch ( error ) {
        console.error( "Erro ao configurar mensagem de validação:", error );
    }

}

export function resetarSelect( select, textoPadrao, value = "" ) {
    
    select.innerHTML = "";
    
    if ( textoPadrao ) {
        const option = document.createElement( "option" );
        option.value = value;
        option.innerHTML = textoPadrao;
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

export function configurarMensagemErro( divErros, mensagem ) {
    divErros.style.display = "block";
    divErros.innerHTML = mensagem;
}

export function limparMensagemErro( divErros ) {
    divErros.style.display = "none";
    divErros.innerHTML = "";
}
