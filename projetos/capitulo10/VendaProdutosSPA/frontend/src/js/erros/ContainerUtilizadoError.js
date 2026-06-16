export class ContainerUtilizadoError extends Error {
    constructor( ...params ) {
        super( ...params );
        if ( Error.captureStackTrace ) {
            Error.captureStackTrace( this, ContainerUtilizadoError );
        }
    }
}
