export enum Medio {
    SMS = 'SMS',
    EMAIL = 'EMAIL'
  }
  
  export enum Tipo {
    ANUNCIO_NOTA_ESTUDIANTE = 'ANUNCIO_NOTA_ESTUDIANTE',
    ANUNCIO_AULA_ESTUDIANTE = 'ANUNCIO_AULA_ESTUDIANTE',
    ANUNCIO_AULA_VIGILANTE = 'ANUNCIO_AULA_VIGILANTE',
    PASSWORD_RESET = 'PASSWORD_RESET'
  }
  
  export enum Estado {
    PENDIENTE = 'PENDIENTE',
    ENVIADO = 'ENVIADO',
    ABORTADA = 'ABORTADA',
    ERROR = 'ERROR'
}

export class Notificacion {
    id!: number;
    asunto: string | undefined;
    cuerpo: string | undefined;
    emailDestino: string | undefined;
    telefonoDestino: string | undefined;
    programacionEnvio!: string;
    medios: Medio[] | undefined;
    tipo: Tipo | undefined;
    estado: Estado | undefined;
    mensajeError: string | undefined;
    momentoRealEnvio: string | undefined;
}