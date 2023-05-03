import { Medio, Tipo } from "./notificacion";

export class NotificacionNueva {
    public asunto: string | undefined;
    public cuerpo: string | undefined;
    public emailDestino: string | undefined;
    public telefonoDestino: string | undefined;
    public tipo: Tipo | undefined;
    public medios: Medio[] | undefined;
    public programacionEnvio: Date | undefined;
}