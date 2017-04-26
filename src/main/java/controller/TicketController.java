package controller;

import metier.GestionProjet;
import metier.GestionTicket;
import model.Ticket;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TicketController extends GestionController
{
    private GestionTicket gestionTicket;
    private GestionProjet gestionProjet;

    @RequestMapping(value = "/projet/{idProjet}/ticket", method = RequestMethod.GET)
    public String index(@PathVariable(value = "idProjet") int idProjet, HttpServletRequest request, Model model)
    {
        EntityManager em = createEntityManager();
        gestionTicket = new GestionTicket(em);

        try
        {
            this.request = request;
            this.model = model;

            //On récup la liste des tickets du projet
            List<Ticket> tickets = gestionTicket.getAllTickets(idProjet);

            model.addAttribute("tickets", tickets);
            model.addAttribute("idUserConnecte", getIdUser());

            return "ticket/index";
        }
        finally
        {
            gestionTicket.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/ticket/add", method = RequestMethod.GET)
    public String add(@PathVariable(value = "idProjet") int idProjet, HttpServletRequest request, Model model)
    {
        EntityManager em = createEntityManager();
        gestionProjet = new GestionProjet(em);
        gestionTicket = new GestionTicket(em);

        try
        {
            this.request = request;
            this.model = model;

            //On crée un ticket
            Ticket ticket = new Ticket();

            remplirModelTicket(ticket, model, idProjet);

            return "ticket/add";
        }
        finally
        {
            gestionProjet.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/ticket/add", method = RequestMethod.POST)
    public String add(@ModelAttribute("ticket") Ticket ticket, @PathVariable(value = "idProjet") int idProjet,
                      HttpServletRequest request, Model model, RedirectAttributes redirectAttributes)
    {
        EntityManager em = createEntityManager();
        gestionTicket = new GestionTicket(em);

        try
        {
            this.request = request;
            this.model = model;
            this.redirectAttributes = redirectAttributes;

            if(ticket != null)
            {
                gestionTicket.beginTransaction();

                ticket = gestionTicket.addTicket(ticket, idProjet, getIdUser());
                if(ticket != null && gestionTicket.commit())
                {
                    addMessage(GestionController.SUCCESS_MESSAGE, "Le ticket a bien été ajouté");
                }
                else
                {
                    gestionTicket.rollback();
                    addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de l'ajout du ticket");
                }
            }

            return "redirect:/projet/"+idProjet+"/ticket";
        }
        finally
        {
            gestionTicket.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/ticket/edit/{idTicket}", method = RequestMethod.GET)
    public String edit(@PathVariable(value = "idProjet") int idProjet, @PathVariable(value = "idTicket") int idTicket,
                       HttpServletRequest request, Model model)
    {
        EntityManager em = createEntityManager();
        gestionTicket = new GestionTicket(em);
        gestionProjet = new GestionProjet(em);

        try
        {
            this.request = request;
            this.model = model;

            //On récup le ticket
            Ticket ticket = gestionTicket.getTicket(idProjet, idTicket);
            if(ticket == null)
            {
                return "redirect:/projet/"+idProjet+"/ticket";
            }

            //On remplit le modèle du ticket
            remplirModelTicket(ticket, model, idProjet);

            //On regarde si la personne connectée peut modifier le ticket
            model.addAttribute("canUpdate", getIdUser() == ticket.getUser().getId());

            return "ticket/edit";
        }
        finally
        {
            gestionTicket.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/ticket/edit/{idTicket}", method = RequestMethod.POST)
    public String edit(@ModelAttribute("ticket") Ticket ticket, @PathVariable(value = "idProjet") int idProjet,
                       @PathVariable(value="idTicket") int idTicket, HttpServletRequest request, Model model,
                       RedirectAttributes redirectAttributes)
    {
        EntityManager em = createEntityManager();
        gestionTicket = new GestionTicket(em);

        try
        {
            this.request = request;
            this.model = model;
            this.redirectAttributes = redirectAttributes;

            if(ticket != null)
            {
                gestionTicket.beginTransaction();

                //On update le ticket
                ticket = gestionTicket.updateTicket(ticket, idProjet, getIdUser(), idTicket);
                if(ticket != null && gestionTicket.commit())
                {
                    addMessage(GestionController.SUCCESS_MESSAGE, "Le ticket a bien été mis à jour");
                }
                else
                {
                    gestionTicket.rollback();
                    addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de la modification du ticket");
                }
            }

            return "redirect:/projet/"+idProjet+"/ticket";
        }
        finally
        {
            gestionTicket.close();
        }
    }

    @RequestMapping(value = "/projet/{idProjet}/ticket/delete/{idTicket}", method = RequestMethod.GET)
    public String delete(@PathVariable(value = "idProjet") int idProjet,
                         @PathVariable(value="idTicket") int idTicket, HttpServletRequest request, Model model,
                         RedirectAttributes redirectAttributes)
    {
        EntityManager em = createEntityManager();
        gestionTicket = new GestionTicket(em);

        try
        {
            this.request = request;
            this.model = model;
            this.redirectAttributes = redirectAttributes;

            gestionTicket.beginTransaction();

            boolean delete = gestionTicket.deleteTicket(idProjet, idTicket);
            if(delete && gestionTicket.commit())
            {
                addMessage(GestionController.SUCCESS_MESSAGE, "Ticket bien supprimé");
            }
            else
            {
                gestionTicket.rollback();
                addMessage(GestionController.ERROR_MESSAGE, "Erreur lors de la suppression du ticket");
            }

            return "redirect:/projet/"+idProjet+"/ticket";
        }
        finally
        {
            gestionTicket.close();
        }
    }

    private boolean remplirModelTicket(Ticket ticket, Model model, int idProjet)
    {
        try
        {
            if (ticket == null || model == null) {
                return false;
            }

            model.addAttribute("ticket", ticket);
            model.addAttribute("trackers", gestionTicket.getAlltracker());
            model.addAttribute("prioriteTickets", gestionTicket.getAllPrioriteTickets());
            model.addAttribute("statutTickets", gestionTicket.getAllStatutTickets());
            model.addAttribute("users", gestionProjet.getAllUsersProjet(idProjet));

            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }
}
