export class ContainerUtizadoError extends Error {
    constructor( ...params ) {
        super( ...params );
        if ( Error.captureStackTrace ) {
            Error.captureStackTrace( this, ContainerUtizadoError );
        }
    }
}