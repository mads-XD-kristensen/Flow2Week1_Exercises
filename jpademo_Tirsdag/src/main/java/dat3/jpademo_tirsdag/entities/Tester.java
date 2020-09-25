/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dat3.jpademo_tirsdag.entities;

import dto.PersonStyleDTO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author madsa
 */
public class Tester {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pu");
        EntityManager em = emf.createEntityManager();

        Person p1 = new Person("Mads", 1997);
        Person p2 = new Person("Signe", 2002);

        Address a1 = new Address("Hjemme", 123, "i huset");
        Address a2 = new Address("Ude", 321, "væk");

        p1.setAddress(a1);
        p2.setAddress(a2);

        Fee f1 = new Fee(100);
        Fee f2 = new Fee(200);
        Fee f3 = new Fee(200);

        p1.addFees(f1);
        p1.addFees(f3);
        p2.addFees(f2);

        SwimStyle s1 = new SwimStyle("Crawl");
        SwimStyle s2 = new SwimStyle("Butterfly");
        SwimStyle s3 = new SwimStyle("Breast Stroke");

        p1.addSwimStyle(s1);
        p1.addSwimStyle(s2);
        p2.addSwimStyle(s3);

        em.getTransaction().begin();
        em.persist(p1);
        em.persist(p2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        p1.removeSwimStyle(s2);
        em.getTransaction().commit();

        System.out.println("p1: " + p1.getP_id() + ", " + p1.getName());
        System.out.println("p2: " + p2.getP_id() + ", " + p2.getName());

        System.out.println("Mads gade: " + p1.getAddress().getStreet());

        System.out.println("Virker to-vejs: " + a1.getPerson().getName());

        System.out.println("Hvem har betalt f2? det har: " + f2.getPerson().getName());
        System.out.println("Hvad er der blevet betalt i alt?");

        TypedQuery<Fee> q1 = em.createQuery("SELECT f FROM Fee f", Fee.class);
        List<Fee> fees = q1.getResultList();

        for (Fee f : fees) {
            System.out.println(f.getPerson().getName() + ": " + f.getAmount() + " kr. Den: " + f.getPayDate() + " Adr: " + f.getPerson().getAddress().getCity());
        }

        
        
        System.out.println("***JPQL JOINS***");
        Query q3 = em.createQuery("SELECT new dto.PersonStyleDTO(p.name, p.year, s.styleName) FROM Person p JOIN p.styles s");
        List<PersonStyleDTO> personDetails = q3.getResultList();
        for (PersonStyleDTO ps : personDetails) {
            System.out.println("Navn: " + ps.getName() + ", " + ps.getYear() + ", " + ps.getSwimStyle());
        }

    }
}
