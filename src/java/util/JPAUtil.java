/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import entities.Acabado;
import entities.Cita;
import entities.Itemacabado;
import entities.Modelo;
import entities.Modeloacabado;
import entities.Tipo;
import entities.Usuario;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Usuario
 */
public class JPAUtil {
    private static final EntityManagerFactory emf;
    static{
        try{
            emf=Persistence.createEntityManagerFactory("ExamenOpel_IvanGomezPU");
            seedIfEmpty();
        } catch (Throwable ex) {
            System.err.println("Error al crear SessionFactoty."+ ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    public static EntityManagerFactory getEntityManagerFactory(){
        return emf;
    }
     private static void seedIfEmpty() {
        EntityManager em = emf.createEntityManager();
        try {
            Long total = (Long) em.createQuery("SELECT COUNT(m) FROM Modelo m").getSingleResult();
            if (total > 0) {
                return;
            }

            EntityTransaction t = em.getTransaction();
            t.begin();

            // ---------- TIPOS ----------
            Tipo turismo = nuevoTipo(1, "Turismo");
            Tipo suv = nuevoTipo(2, "SUV");
            Tipo comercial = nuevoTipo(3, "Comercial");
            Tipo electrico = nuevoTipo(4, "Eléctrico");
            Tipo hibrido = nuevoTipo(5, "Híbrido");
            Tipo[] tipos = {turismo, suv, comercial, electrico, hibrido};
            for (Tipo tipo : tipos) {
                em.persist(tipo);
            }

            // ---------- ACABADOS ----------
            Acabado edition = nuevoAcabado(1, "Edition");
            Acabado elegance = nuevoAcabado(2, "Elegance");
            Acabado gsLine = nuevoAcabado(3, "GS Line");
            Acabado ultimate = nuevoAcabado(4, "Ultimate");
            for (Acabado a : new Acabado[]{edition, elegance, gsLine, ultimate}) {
                em.persist(a);
            }

            // ---------- ITEMS DE ACABADO ----------
            String[] nombresItems = {
                /* 1 */ "Aire acondicionado manual",
                /* 2 */ "Pantalla táctil de 7\"",
                /* 3 */ "Apple CarPlay y Android Auto",
                /* 4 */ "Faros delanteros LED",
                /* 5 */ "Control de crucero con limitador",
                /* 6 */ "Asistente de mantenimiento de carril",
                /* 7 */ "Reconocimiento de señales de tráfico",
                /* 8 */ "Sensores de aparcamiento traseros",
                /* 9 */ "Climatizador automático bizona",
                /* 10 */ "Navegador GPS con pantalla de 10\"",
                /* 11 */ "Cámara de visión trasera 180º",
                /* 12 */ "Sensores de aparcamiento delanteros",
                /* 13 */ "Llantas de aleación de 17\"",
                /* 14 */ "Acceso y arranque sin llave",
                /* 15 */ "Cargador inalámbrico para smartphone",
                /* 16 */ "Asientos delanteros calefactables",
                /* 17 */ "Llantas de aleación de 18\" negras",
                /* 18 */ "Techo bitono en negro",
                /* 19 */ "Asientos deportivos",
                /* 20 */ "Cuadro de instrumentos digital Pure Panel",
                /* 21 */ "Faros IntelliLux LED Matrix",
                /* 22 */ "Asientos ergonómicos certificados AGR",
                /* 23 */ "Volante calefactable",
                /* 24 */ "Control de crucero adaptativo con Stop&Go",
                /* 25 */ "Head-up display",
                /* 26 */ "Sistema de sonido premium",
                /* 27 */ "Portón trasero eléctrico"
            };
            Itemacabado[] items = new Itemacabado[nombresItems.length + 1];
            for (int i = 0; i < nombresItems.length; i++) {
                Itemacabado item = new Itemacabado((short) (i + 1));
                item.setNombre(nombresItems[i]);
                item.setModeloacabadoList(new ArrayList<Modeloacabado>());
                items[i + 1] = item;
            }

            // Equipamiento de cada acabado (los superiores incluyen lo de los inferiores)
            Map<Acabado, int[]> equipamiento = new LinkedHashMap<>();
            equipamiento.put(edition, new int[]{1, 2, 3, 4, 5, 6, 7, 8});
            equipamiento.put(elegance, new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16});
            equipamiento.put(gsLine, new int[]{2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 15, 17, 18, 19, 20});
            equipamiento.put(ultimate, new int[]{3, 4, 6, 7, 9, 10, 11, 12, 14, 15, 16, 17, 20, 21, 22, 23, 24, 25, 26, 27});

            // ---------- MODELOS ----------
            // id, nombre, imagen (en web/img/), tipos, acabados disponibles
            Object[][] datosModelos = {
                {1, "Opel Corsa", "corsa.webp", new Tipo[]{turismo}, new Acabado[]{edition, elegance, gsLine}},
                {2, "Opel Corsa-e", "corsa-e.png", new Tipo[]{turismo, electrico}, new Acabado[]{edition, elegance, gsLine}},
                {3, "Opel Astra", "astra.png", new Tipo[]{turismo}, new Acabado[]{edition, elegance, gsLine, ultimate}},
                {4, "Opel Astra Hybrid", "astra-hybrid.png", new Tipo[]{turismo, hibrido}, new Acabado[]{elegance, gsLine, ultimate}},
                {5, "Opel Insignia", "insignia.png", new Tipo[]{turismo}, new Acabado[]{elegance, gsLine, ultimate}},
                {6, "Opel Crossland", "crossland.png", new Tipo[]{suv}, new Acabado[]{edition, elegance, gsLine}},
                {7, "Opel Mokka", "mokka.webp", new Tipo[]{suv}, new Acabado[]{edition, elegance, gsLine, ultimate}},
                {8, "Opel Mokka-e", "mokka-e.png", new Tipo[]{suv, electrico}, new Acabado[]{edition, elegance, gsLine, ultimate}},
                {9, "Opel Grandland", "grandland.png", new Tipo[]{suv}, new Acabado[]{edition, elegance, gsLine, ultimate}},
                {10, "Opel Grandland Hybrid", "grandland-hybrid.png", new Tipo[]{suv, hibrido}, new Acabado[]{gsLine, ultimate}},
                {11, "Opel Combo-e", "combo-e.png", new Tipo[]{comercial, electrico}, new Acabado[]{edition, elegance}},
                {12, "Opel Zafira-e", "zafira-e.png", new Tipo[]{comercial, electrico}, new Acabado[]{edition, elegance}}
            };

            List<Modelo> modelos = new ArrayList<>();
            short idMA = 1;
            for (Object[] fila : datosModelos) {
                Modelo modelo = new Modelo(((Integer) fila[0]).shortValue());
                modelo.setNombre((String) fila[1]);
                modelo.setImagen((String) fila[2]);
                modelo.setTipoList(new ArrayList<Tipo>());
                modelo.setModeloacabadoList(new ArrayList<Modeloacabado>());
                modelo.setCitaList(new ArrayList<Cita>());

                // Modelo <-> Tipo (los dos lados)
                for (Tipo tipo : (Tipo[]) fila[3]) {
                    modelo.getTipoList().add(tipo);
                    tipo.getModeloList().add(modelo);
                }
                em.persist(modelo);
                modelos.add(modelo);

                // Modelo <-> Modeloacabado <-> Acabado <-> Itemacabado
                for (Acabado acabado : (Acabado[]) fila[4]) {
                    Modeloacabado ma = new Modeloacabado(idMA++);
                    ma.setModelo(modelo);
                    ma.setAcabado(acabado);
                    ma.setItemacabadoList(new ArrayList<Itemacabado>());
                    modelo.getModeloacabadoList().add(ma);
                    acabado.getModeloacabadoList().add(ma);

                    for (int idItem : equipamiento.get(acabado)) {
                        Itemacabado item = items[idItem];
                        ma.getItemacabadoList().add(item);
                        item.getModeloacabadoList().add(ma);
                    }
                    em.persist(ma);
                }
            }

            for (int i = 1; i < items.length; i++) {
                em.persist(items[i]);
            }

            // ---------- USUARIOS ----------
            Usuario admin = nuevoUsuario(1, "admin", "admin");
            Usuario ivan = nuevoUsuario(2, "ivan", "1234");
            Usuario navarrata = nuevoUsuario(3, "navarrata", "navarrata");
            em.persist(admin);
            em.persist(ivan);
            em.persist(navarrata);

            // ---------- CITAS ----------
            Object[][] datosCitas = {
                {1, "02/10/2026", modelos.get(2), admin},
                {2, "05/10/2026", modelos.get(7), ivan},
                {3, "09/10/2026", modelos.get(9), navarrata},
                {4, "14/10/2026", modelos.get(1), ivan}
            };
            for (Object[] fila : datosCitas) {
                Cita cita = new Cita(((Integer) fila[0]).shortValue());
                cita.setFecha((String) fila[1]);
                Modelo modelo = (Modelo) fila[2];
                Usuario usuario = (Usuario) fila[3];
                cita.setModelo(modelo);
                cita.setUsuario(usuario);
                modelo.getCitaList().add(cita);
                usuario.getCitaList().add(cita);
                em.persist(cita);
            }

            t.commit();
        } finally {
            em.close();
        }
    }
     
     private static Tipo nuevoTipo(int id, String nombre) {
        Tipo tipo = new Tipo((short) id);
        tipo.setNombre(nombre);
        tipo.setModeloList(new ArrayList<Modelo>());
        return tipo;
    }

    private static Acabado nuevoAcabado(int id, String nombre) {
        Acabado acabado = new Acabado((short) id);
        acabado.setNombre(nombre);
        acabado.setModeloacabadoList(new ArrayList<Modeloacabado>());
        return acabado;
    }

    private static Usuario nuevoUsuario(int id, String nick, String pass) {
        Usuario usuario = new Usuario((short) id);
        usuario.setNick(nick);
        usuario.setPass(pass);
        usuario.setCitaList(new ArrayList<Cita>());
        return usuario;
    }
}
