package com.example.beans;

public class Car {
	
	private String id;    //轿车型号
	private int count;    //库存轿车数量
	private double price; //轿车价格
	
	public Car() {	}
	
	public Car(String id, int count, double price) {
		this.id = id;
		this.count = count;
		this.price = price;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	@Override
	public String toString() {
		return "cars [id=" + id + ", count=" + count + ", price=" + price + "]";
	}
	
	
	
}
