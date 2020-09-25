package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.PersonDTO;
import dto.PersonsDTO;
import entities.Person;
import exceptions.MissingInputException;
import exceptions.PersonNotFoundException;
import utils.EMF_Creator;
import facades.PersonFacade;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//Todo Remove or change relevant parts before ACTUAL use
@Path("person")
public class PersonResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    //An alternative way to get the EntityManagerFactory, whithout having to type the details all over the code
    //EMF = EMF_Creator.createEntityManagerFactory(DbSelector.DEV, Strategy.CREATE);
    private static final PersonFacade FACADE = PersonFacade.getFacadeExample(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String demo() {
        return "{\"msg\":\"Hello World\"}";
    }

    @Path("find/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String personByID(@PathParam("id") int id) throws PersonNotFoundException {
        PersonDTO person = FACADE.getPerson(id);
        return GSON.toJson(person);

    }

    @Path("all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String allPersons() {
        return GSON.toJson(FACADE.getAllPersons());
    }

    @Path("addPerson")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String addPerson(String person) throws MissingInputException {
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        PersonDTO pdto = FACADE.addPerson(personDTO.getfName(), personDTO.getlName(), personDTO.getPhone(),
                personDTO.getStreet(), personDTO.getZip(), personDTO.getCity());
        pdto.setId(personDTO.getId());
        return GSON.toJson(pdto);
    }

    @PUT
    @Path("update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public String updatePerson(@PathParam("id") int id, String person) throws PersonNotFoundException, MissingInputException {
        PersonDTO personDTO = GSON.fromJson(person, PersonDTO.class);
        PersonDTO editPerson = new PersonDTO(personDTO.getfName(), personDTO.getlName(), personDTO.getPhone(),
                personDTO.getStreet(), personDTO.getZip(), personDTO.getCity());
        editPerson.setId(id);
        FACADE.editPerson(editPerson);
        return GSON.toJson(editPerson);
    }

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("delete/{id}")
    public String deletePerson(@PathParam("id") int id) throws PersonNotFoundException {
        FACADE.deletePerson(id);
        return "{\"Deleted\":\"personWithID: " + id + "\"}";
    }

}
