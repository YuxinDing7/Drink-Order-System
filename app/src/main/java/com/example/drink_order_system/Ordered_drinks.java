package com.example.drink_order_system;

import java.util.ArrayList;

public class Ordered_drinks{
	private Drinks drink;
	private Flavor flavor;
	private int drink_number;

	private static ArrayList<Ordered_drinks> ordered_array = new ArrayList<>();
	Ordered_drinks(Drinks drink, Flavor flavor, int drink_number)
	{
		this.drink = drink;
		this.flavor = flavor;
		this.drink_number = drink_number;
		boolean in = false;
		for(int i = 0; i < ordered_array.size(); i++)
		{
			Drinks temp_drink = ordered_array.get(i).get_drink();
			Flavor temp_flavor = ordered_array.get(i).get_flavor();
			if(drink.get_name() == temp_drink.get_name() && temp_flavor.equals(flavor))
			{
				ordered_array.get(i).drink_number += drink_number;
				in = true;
				break;
			}
		}
		if(!in)
		{
			ordered_array.add(this);
			for(int i = 0; i < ordered_array.size(); i++) {
				System.out.println(ordered_array.get(i).get_drink().get_name());
			}
		}
	}
	
	public Drinks get_drink()
	{
		return drink;
	}
	
	public Flavor get_flavor()
	{
		return flavor;
	}
	
	public int get_drink_number()
	{
		return drink_number;
	}

	public static ArrayList<Ordered_drinks> getOrdered_array()
	{
		return ordered_array;
	}

	public static int getDrinkCost()
	{
		int sum = 0;
		for (Ordered_drinks od:ordered_array)
		{
			if(od.get_flavor().getSize().equals("中杯"))
			{
				sum += od.get_drink().get_price()*od.get_drink_number();
			} else if (od.get_flavor().getSize().equals("小杯")) {
				sum += (od.get_drink().get_price()-2)*od.get_drink_number();
			}
			else
			{
				sum += (od.get_drink().get_price()+2)*od.get_drink_number();
			}
		}
		return sum;
	}

	public static void clearOrdered_array()
	{
		ordered_array.clear();
	}

	public static void addDrink(int i)
	{
		ordered_array.get(i).drink_number++;
		for(int j = 0; j < ordered_array.size(); j++) {
			System.out.println(ordered_array.get(j).get_drink().get_name());
		}
	}

	public static void subtractDrink(int i)
	{
		if(ordered_array.get(i).drink_number<=1)
		{
			ordered_array.remove(i);
			for(int j = 0; j < ordered_array.size(); j++) {
				System.out.println(ordered_array.get(j).get_drink().get_name());
			}
		}
		else {
			ordered_array.get(i).drink_number--;
		}
	}
}