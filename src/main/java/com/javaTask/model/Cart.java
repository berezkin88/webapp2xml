package main.java.com.javaTask.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import main.java.com.javaTask.model.enums.Status;

@Entity
@Table(name = "cart")
public class Cart {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "userid")
	private int userId;
	
	@Column(name = "status")
	private Status status;
	
	@Column(name = "timestamp")
	private long time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public String toString() {
		return "Cart [id=" + id + ", userId=" + userId + ", status=" + status + ", time=" + time + "]";
	}

	public Cart() {
	}

	public Cart(int userId, Status status, long time) {
		this.userId = userId;
		this.status = status;
		this.time = time;
	}
}
