package controller;
import entities.Acabado;
import entities.Cita;
import entities.Usuario;
import entities.Modelo;
import entities.Modeloacabado;
import entities.Tipo;
import java.awt.Image;
import java.io.IOException;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.servlet.RequestDispatcher;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;
import javax.xml.bind.ValidationException;
import util.JPAUtil;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        /*
		 * Crear el singleton con
         */
        
        List<Modelo> modelos;
        List<Modelo> modelosAll;
        List<Tipo> tipos;
        List<Modeloacabado> modeloAcabados;
        Usuario usuario = null;
        Query q;
        Query q2;
        Usuario user = null;
        EntityTransaction t;
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");  
        LocalDateTime fechaCita = LocalDateTime.now();

        EntityManager em = (EntityManager) session.getAttribute("em");
        if (em == null) {
            em = JPAUtil.getEntityManagerFactory().createEntityManager();
            session.setAttribute("em", em);
        }

        String op = request.getParameter("op");
        if (op.equals("inicio")) {
            q = em.createNamedQuery("Modelo.findAll");
            modelos = q.getResultList();
            q = em.createNamedQuery("Modelo.findAll");
            modelosAll = q.getResultList();
            q2 = em.createNamedQuery("Tipo.findAll");
            tipos = q2.getResultList();
            session.setAttribute("modelos", modelos);
            session.setAttribute("modelosAll", modelosAll);
            session.setAttribute("tipos", tipos);
            request.getRequestDispatcher("home.jsp").forward(request, response);
        } else if(op.equals("vatipo")) {
            Short idtipo = Short.valueOf(request.getParameter("tipo"));
            Tipo tipo = em.find(Tipo.class, idtipo);
            modelos = tipo.getModeloList();
            session.setAttribute("modelos", modelos);
            request.getRequestDispatcher("home.jsp").forward(request, response);
            
        } else if(op.equals("login")) {
            String nick = request.getParameter("nick");
            String password = request.getParameter("pass");
            q = em.createQuery("Select u.id from Usuario u Where u.nick='"+nick+"' and u.pass='"+password+"'");
            try {
                Short id = (Short)q.getSingleResult();
                usuario = em.find(Usuario.class, id);
            } catch (NoResultException e) {
                if (usuario==null) { // Login incorrecto procedemos a insertarlo
                    usuario = new Usuario(Short.valueOf("1"));
                    usuario.setNick(nick);
                    usuario.setPass(password);
                    
                    t = em.getTransaction();
                    t.begin();
                    em.persist(usuario);
                    t.commit();
                }
            } catch (RollbackException e) {
                
            } 
            session.setAttribute("usuario",usuario);
            session.setAttribute("msg", "Bienvenido "+usuario.getNick()+"!");
            request.getRequestDispatcher("home.jsp").forward(request, response);
            
        } else if(op.equals("logout")) {
            session.removeAttribute("usuario");
            session.setAttribute("msg", "Logout satisfactorio");
            request.getRequestDispatcher("home.jsp").forward(request, response);
            
        } else if(op.equals("cita")) {
            Usuario usuariocita = (Usuario)session.getAttribute("usuario");
            String idmodeloParam = request.getParameter("selectmodelo");
            if (usuariocita == null || idmodeloParam == null || idmodeloParam.isEmpty()) {
                session.setAttribute("msg", "Inicia sesión y elige un modelo para pedir cita");
            } else {
                Modelo modelo = em.find(Modelo.class, Short.valueOf(idmodeloParam));
                Cita newcita = new Cita(siguienteId(em, "Cita"));
                newcita.setUsuario(usuariocita);
                newcita.setModelo(modelo);
                newcita.setFecha(String.valueOf(dtf.format(fechaCita)));

                t = em.getTransaction();
                t.begin();
                em.persist(newcita);
                t.commit();

                session.setAttribute("msg", "Cita solicitada con éxito");
            }

            request.getRequestDispatcher("home.jsp").forward(request, response);
            
        } else if(op.equals("acabados")) {
            Short idmodelo = Short.valueOf(request.getParameter("modelo"));
            Modelo modelo = em.find(Modelo.class, idmodelo);
            session.setAttribute("modelo", modelo);
            modeloAcabados = modelo.getModeloacabadoList();
            session.setAttribute("modeloAcabados", modeloAcabados);
            request.getRequestDispatcher("acabados.jsp").forward(request, response);
        }
    }
    
    /**
     * Devuelve el siguiente id libre de una entidad (las tablas no tienen autoincremento)
     */
    private Short siguienteId(EntityManager em, String entidad) {
        Number max = (Number) em.createQuery("SELECT MAX(e.id) FROM " + entidad + " e").getSingleResult();
        return (short) (max == null ? 1 : max.intValue() + 1);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        doGet(request, response);
    }

}

