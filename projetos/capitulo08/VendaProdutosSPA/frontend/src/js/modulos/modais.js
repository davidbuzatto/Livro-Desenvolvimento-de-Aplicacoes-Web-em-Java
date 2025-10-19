/**
 * Módulo de gerenciamento de modais.
 * 
 * @author Prof. Dr. David Buzatto
 */
export class Modal {
    
    constructor( container, fecharAoClicarOverlay = false ) {
        this.container = container;
        this.fecharAoClicarOverlay = fecharAoClicarOverlay;
        this.funcaoOk = null;
        this.funcaoCancelar = null;
        this.divContainer = document.getElementById( container );
        this.aberto = false;
        this.iniciar();
    }
    
    iniciar() {
        
        const btnFechar = this.divContainer.querySelector( ".btnFechar" );
        const btnOk = this.divContainer.querySelector( ".btnOk" );
        const btnCancelar = this.divContainer.querySelector( ".btnCancelar" );
        
        if ( btnFechar ) {
            btnFechar.addEventListener( "click", event => {
                this.fechar();
                if ( this.funcaoCancelar ) {
                    this.funcaoCancelar();
                }
            });
        }
        
        if ( btnOk ) {
            btnOk.addEventListener( "click", event => {
                this.fechar();
                if ( this.funcaoOk ) {
                    this.funcaoOk();
                }
            });
        }
        
        if ( btnCancelar ) {
            btnCancelar.addEventListener( "click", event => {
                this.fechar();
                if ( this.funcaoCancelar ) {
                    this.funcaoCancelar();
                }
            });
        }
        
        if ( this.fecharAoClicarOverlay ) {
            const overlay = this.divContainer.querySelector( ".overlay" );
            overlay.addEventListener( "click", event => {
                if ( event.target === overlay ) {
                    this.fechar();
                }
            });
        }
        
    }
    
    abrir( titulo = null, mensagem = null, funcaoOk = null, funcaoCancelar = null ) {
        
        if ( !this.aberto ) {
            
            const tituloModal = this.divContainer.querySelector( ".tituloModal" );
            const corpoModal = this.divContainer.querySelector( ".corpoModal" );
            this.funcaoOk = funcaoOk;
            this.funcaoCancelar = funcaoCancelar;
            
            if ( tituloModal && titulo ) {
                tituloModal.innerHTML = titulo;
            }
            
            if ( corpoModal && mensagem ) {
                corpoModal.innerHTML = mensagem;
            }
            
            this.divContainer.classList.remove( "escondido" );
            this.aberto = true;
            
        }
        
    }

    fechar() {
        if ( this.aberto ) {
            this.divContainer.classList.add( "escondido" );
            this.aberto = false;
        }
    }
    
}

export let modalAguarde;
export let modalMensagem;
export let modalConfirmacao;

let inicializado = false;

export function iniciar() {
    if ( !inicializado ) {
        modalAguarde = new Modal( "modalAguarde" );
        modalMensagem = new Modal( "modalMensagem", true );
        modalConfirmacao = new Modal( "modalConfirmacao", true );
        inicializado = true;
    }
}