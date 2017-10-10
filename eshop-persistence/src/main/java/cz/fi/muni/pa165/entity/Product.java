package cz.fi.muni.pa165.entity;

import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "ESHOP_PRODUCTS")
public class Product {

	//TODO: column should be named identity
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Basic @Column(nullable = false, unique = true)
	private String name;
	
	@Enumerated
	private Color color;
	
    @Temporal(TemporalType.DATE)
	private Date  addedDate;
    
    public Product() {
    	
    }
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Date getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Date addedDate) {
		this.addedDate = addedDate;
	}
}
