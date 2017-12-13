package com.pwc.us.common.model;

/**
 * A list of all the counties in Oklahoma.
 * @author Roger Turnau - <roger.turnau@us.pwc.com>
 */
public enum OklahomaCounty {
    Adair("Adair"),
    Alfalfa("Alfalfa"),
    Atoka("Atoka"),
    Beaver("Beaver"),
    Beckham("Beckham"),
    Blaine("Blaine"),
    Bryan("Bryan"),
    Caddo("Caddo"),
    Canadian("Canadian"),
    Carter("Carter"),
    Cherokee("Cherokee"),
    Choctaw("Choctaw"),
    Cimarron("Cimarron"),
    Cleveland("Cleveland"),
    Coal("Coal"),
    Comanche("Comanche"),
    Cotton("Cotton"),
    Craig("Craig"),
    Creek("Creek"),
    Custer("Custer"),
    Delaware("Delaware"),
    Dewey("Dewey"),
    Ellis("Ellis"),
    Garfield("Garfield"),
    Garvin("Garvin"),
    Grady("Grady"),
    Grant("Grant"),
    Greer("Greer"),
    Harmon("Harmon"),
    Harper("Harper"),
    Haskell("Haskell"),
    Hughes("Hughes"),
    Jackson("Jackson"),
    Jefferson("Jefferson"),
    Johnston("Johnston"),
    Kay("Kay"),
    Kingfisher("Kingfisher"),
    Kiowa("Kiowa"),
    Latimer("Latimer"),
    LeFlore("Le Flore"),
    Lincoln("Lincoln"),
    Logan("Logan"),
    Love("Love"),
    Major("Major"),
    Marshall("Marshall"),
    Mayes("Mayes"),
    McClain("McClain"),
    McCurtain("McCurtain"),
    McIntosh("McIntosh"),
    Murray("Murray"),
    Muskogee("Muskogee"),
    Noble("Noble"),
    Nowata("Nowata"),
    Okfuskee("Okfuskee"),
    Oklahoma("Oklahoma"),
    Okmulgee("Okmulgee"),
    Osage("Osage"),
    Ottawa("Ottawa"),
    Pawnee("Pawnee"),
    Payne("Payne"),
    Pittsburgh("Pittsburgh"),
    Pontotoc("Pontotoc"),
    Pottawatomie("Pottawatomie"),
    Pushmataha("Pushmataha"),
    RogersMills("Rogers Mills"),
    Rogers("Rogers"),
    Seminole("Seminole"),
    Sequoyah("Sequoyah"),
    Stephens("Stephens"),
    Texas("Texas"),
    Tillman("Tillman"),
    Tulsa("Tulsa"),
    Wagoner("Wagoner"),
    Washington("Washington"),
    Washita("Washita"),
    Woods("Woods"),
    Woodward("Woodward"),
    OutOfState("Out Of State");
    
    private String name;
    
    private OklahomaCounty(String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
