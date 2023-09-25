package com.example.drink_order_system;

import java.util.ArrayList;

public class Drinks {
	private int number;
	private String name;
	private String type;
	private float price;
	private String introduction;
	private int ImageResId;

	static private ArrayList<Drinks> all_drinks = new ArrayList<>();//用于存储所有饮品对象

	//该构造函数包含type这个属性，用于初始化包含类别小标题的饮品，即每个类别中的第一个饮品
	Drinks(String name, String type, float price, String introduction, int ImageResId)
	{
		this.number = all_drinks.size();
		this.name = name;
		this.type = type;
		this.price = price;
		this.introduction = introduction;
		this.ImageResId = ImageResId;
		all_drinks.add(this);//每项饮品在被初始化后即加入all_drinks列表
	}

	//该构造函数不包含type属性，用于初始化普通不包含小标题的饮品
	Drinks(String name, float price, String introduction, int ImageResId)
	{
		this.number = all_drinks.size();
		this.type = null; //type属性直接设为null，在与adapter的布局文件进行绑定时，即不会显示类别小标题
		this.name = name;
		this.price = price;
		this.introduction = introduction;
		this.ImageResId = ImageResId;
		all_drinks.add(this);
	}

	Drinks(int i){
		this.number = i-1;
		Drinks temp = all_drinks.get(i-1);
		this.name = temp.name;
		this.type = temp.type;
		this.price = temp.price;
		this.introduction = temp.introduction;
		this.ImageResId = temp.ImageResId;
	}

	public int get_number()
	{
		return number;
	}
	
	public String get_name()
	{
		return name;
	}
	
	public String get_type()
	{
		return type;
	}
	
	public float get_price() { return price;}
	
	public String get_introduction()
	{
		return introduction;
	}

	public int getImageResId(){return ImageResId;}

	public void set_name(String name)
	{
		this.name = name;
	}
	
	public void set_type(String type)
	{
		this.type = type;
	}
	
	public void set_price(float price)
	{
		this.price = price;
	}
	
	public void set_Introduction(String introduction)
	{
		this.introduction = introduction;
	}

	public void setImageResId(int id){this.ImageResId = id;}
}
