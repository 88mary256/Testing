package main.java.marines.controller;

import java.util.Random;

import main.java.marines.data.Item;

/**
 * @author Marines Lopez
 */
public abstract class Rocket implements SpaceShip {

	protected int cost;
	protected int weight;
	protected int maxWeight;
	protected int probLaunch;
	protected int probLand;

	public Rocket(int cost, int weight, int maxWeight, int probLaunch, int probLand) {
		super();
		this.cost = cost;
		this.weight = weight;
		this.maxWeight = maxWeight;
		this.probLaunch = probLaunch;
		this.probLand = probLand;
	}

	public int getCost() {
		return cost;
	}

	public void setCost(int cost) {
		this.cost = cost;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getMaxWeight() {
		return maxWeight;
	}

	public void setMaxWeight(int maxWeight) {
		this.maxWeight = maxWeight;
	}

	@Override
	public boolean canCarry(Item item) {
		return (this.weight + item.getWeight()) <= this.maxWeight;
	}

	@Override
	public void carry(Item item) {
		if (canCarry(item)) {
			this.weight = this.weight + item.getWeight();
		}
	}

	@Override
	public boolean launch() {
		int chance = (probLaunch / 100) * (this.weight / this.maxWeight);
		return chance < ((new Random()).nextInt(100));
	}

	@Override
	public boolean land() {
		int chance = (probLand / 100) * (this.weight / this.maxWeight);
		return chance < (Math.random() * 100);
	}
}
