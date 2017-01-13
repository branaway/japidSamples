package models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import play.data.validation.Email;
import play.data.validation.Required;
import play.db.jpa.Model;

@Entity
public class Book extends Model {
    
    @Required
    public String title;
    
    @Required
    public String year;
    
    @Required
    public Integer votes;
    
    @Required
    public Integer rank;
    
    @Required
    public Float rating;
    
}

