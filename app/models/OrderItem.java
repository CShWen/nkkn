package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import play.db.ebean.Model;

@Entity
public class OrderItem extends Model {
	@Id
	public Long id;
	@OneToOne
	public Book book;
	public int num;
	public double price;

	public OrderItem(CartItem cartItem) {
		this.book = cartItem.book;
		this.num = cartItem.num;
		this.price = cartItem.price;
	}
}
