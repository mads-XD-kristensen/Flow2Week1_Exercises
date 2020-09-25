/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author madsa
 */
public class PersonStyleDTO {
    
    private String name;
    private int year;
    private String swimStyle;

    public PersonStyleDTO(String name, int year, String swimStyle) {
        this.name = name;
        this.year = year;
        this.swimStyle = swimStyle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSwimStyle() {
        return swimStyle;
    }

    public void setSwimStyle(String swimStyle) {
        this.swimStyle = swimStyle;
    }
    
    
}
