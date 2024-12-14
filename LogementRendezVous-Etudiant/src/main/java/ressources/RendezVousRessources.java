package ressources;

import entities.RendezVous;
import metiers.RendezVousBusiness;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import java.util.List;

@Path("rendezvous")
public class RendezVousRessources {

    public static RendezVousBusiness rendezVousMetier = new RendezVousBusiness();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addRendezVous(RendezVous r) {
        if (rendezVousMetier.addRendezVous(r))
            return Response.status(Status.CREATED).build();
        return Response.status(Status.NOT_ACCEPTABLE).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRendezVous(@QueryParam("refLogement") String refLogement) {
        List<RendezVous> liste;
        if (refLogement != null) {
            liste = rendezVousMetier.getListeRendezVousByLogementReference(Integer.parseInt(refLogement));
        } else {
            liste = rendezVousMetier.getListeRendezVous();
        }

        if (liste.isEmpty())
            return Response.status(Status.NOT_FOUND).build();
        return Response.status(Status.OK).entity(liste).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRdv(@PathParam("id") int id, RendezVous updatedRendezVous) {
        if (rendezVousMetier.updateRendezVous(id, updatedRendezVous)) {
            return Response.status(Status.OK).build();
        } else {
            return Response.status(Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteRendezVous(@PathParam("id") int id) {
        if (rendezVousMetier.deleteRendezVous(id))
            return Response.status(Status.OK).build();

        return Response.status(Status.NOT_FOUND).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRendezVousbyId(@PathParam("id") int id) {
        RendezVous rendezVous = rendezVousMetier.getRendezVousById(id);
        if (rendezVous != null)
            return Response.status(Status.OK).entity(rendezVous).build();

        return Response.status(Status.NOT_FOUND).build();
    }
}
