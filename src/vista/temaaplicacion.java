package vista;

import java.awt.Color;

public final class temaaplicacion {
    public final Color fondoVelo;
    public final Color logoPanel;
    public final Color logoSubtitulo;
    public final Color menuTitulo;
    public final Color barraEstado;
    public final Color barraBorde;
    public final Color estadoTexto;
    public final Color estadoSeparador;
    public final Color bannerTitulo;
    public final Color bannerSubtitulo;
    public final Color bannerTarjeta;
    public final Color formularioFondo;
    public final Color tarjetaTexto;
    public final Color tarjetaTextoSuave;
    public final Color tarjetaFondo;
    public final Color tarjetaSombra;
    public final Color tablaHeaderFondo;
    public final Color tablaHeaderTexto;
    public final Color tablaSeleccion;
    public final Color tablaTexto;
    public final Color tablaFilaPar;
    public final Color tablaFilaImpar;
    public final Color chartBackground;
    public final Color chartGrid;
    public final Color chartLabel;
    public final Color colorPacientes;
    public final Color colorMedicos;
    public final Color colorConsultorios;
    public final Color colorCitas;
    public final Color colorTriaje;
    public final Color colorHistorial;
    public final Color colorFacturacion;
    public final Color colorReportes;

    private temaaplicacion(
            Color fondoVelo,
            Color logoPanel,
            Color logoSubtitulo,
            Color menuTitulo,
            Color barraEstado,
            Color barraBorde,
            Color estadoTexto,
            Color estadoSeparador,
            Color bannerTitulo,
            Color bannerSubtitulo,
            Color bannerTarjeta,
            Color formularioFondo,
            Color tarjetaTexto,
            Color tarjetaTextoSuave,
            Color tarjetaFondo,
            Color tarjetaSombra,
            Color tablaHeaderFondo,
            Color tablaHeaderTexto,
            Color tablaSeleccion,
            Color tablaTexto,
            Color tablaFilaPar,
            Color tablaFilaImpar,
            Color chartBackground,
            Color chartGrid,
            Color chartLabel,
            Color colorPacientes,
            Color colorMedicos,
            Color colorConsultorios,
            Color colorCitas,
            Color colorTriaje,
            Color colorHistorial,
            Color colorFacturacion,
            Color colorReportes) {
        this.fondoVelo = fondoVelo;
        this.logoPanel = logoPanel;
        this.logoSubtitulo = logoSubtitulo;
        this.menuTitulo = menuTitulo;
        this.barraEstado = barraEstado;
        this.barraBorde = barraBorde;
        this.estadoTexto = estadoTexto;
        this.estadoSeparador = estadoSeparador;
        this.bannerTitulo = bannerTitulo;
        this.bannerSubtitulo = bannerSubtitulo;
        this.bannerTarjeta = bannerTarjeta;
        this.formularioFondo = formularioFondo;
        this.tarjetaTexto = tarjetaTexto;
        this.tarjetaTextoSuave = tarjetaTextoSuave;
        this.tarjetaFondo = tarjetaFondo;
        this.tarjetaSombra = tarjetaSombra;
        this.tablaHeaderFondo = tablaHeaderFondo;
        this.tablaHeaderTexto = tablaHeaderTexto;
        this.tablaSeleccion = tablaSeleccion;
        this.tablaTexto = tablaTexto;
        this.tablaFilaPar = tablaFilaPar;
        this.tablaFilaImpar = tablaFilaImpar;
        this.chartBackground = chartBackground;
        this.chartGrid = chartGrid;
        this.chartLabel = chartLabel;
        this.colorPacientes = colorPacientes;
        this.colorMedicos = colorMedicos;
        this.colorConsultorios = colorConsultorios;
        this.colorCitas = colorCitas;
        this.colorTriaje = colorTriaje;
        this.colorHistorial = colorHistorial;
        this.colorFacturacion = colorFacturacion;
        this.colorReportes = colorReportes;
    }

    public static temaaplicacion actual() {
        return claroClinico();
    }

    private static temaaplicacion claroClinico() {
        return new temaaplicacion(
                new Color(248, 252, 255, 51),
                new Color(16, 82, 147, 228),
                new Color(220, 237, 255),
                new Color(74, 96, 120),
                new Color(255, 255, 255, 186),
                new Color(216, 229, 241, 160),
                new Color(84, 104, 124),
                new Color(122, 152, 181),
                new Color(12, 76, 138),
                new Color(54, 88, 118),
                new Color(255, 255, 255, 142),
                new Color(255, 255, 255, 188),
                new Color(72, 95, 118),
                new Color(113, 132, 149),
                new Color(255, 255, 255, 235),
                new Color(62, 98, 128, 18),
                new Color(214, 228, 243),
                new Color(44, 67, 92),
                new Color(213, 231, 249),
                new Color(46, 66, 88),
                new Color(255, 255, 255, 235),
                new Color(246, 250, 253, 235),
                new Color(233, 241, 248),
                new Color(198, 214, 229),
                new Color(74, 96, 120),
                new Color(32, 131, 219),
                new Color(32, 170, 155),
                new Color(77, 132, 226),
                new Color(0, 175, 204),
                new Color(241, 155, 64),
                new Color(128, 92, 212),
                new Color(52, 176, 93),
                new Color(230, 102, 147)
        );
    }
}
