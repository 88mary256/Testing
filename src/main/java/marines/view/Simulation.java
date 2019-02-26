package main.java.marines.view;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import main.java.marines.data.*;
import main.java.marines.controller.*;

/**
 * @author Marines Lopez
 */
public class Simulation {

	public List<Item> loadItems(String fileName) {
		// File file2 = new File("Phase-2.txt");"Phase-1.txt"
		List<Item> items = new ArrayList<Item>();
		Scanner s;
		try {
			s = new Scanner(new File(fileName));
			while (s.hasNextLine()) {
				String line = s.nextLine();
				String[] parts = line.split("=");
				items.add(new Item(parts[0], getWeight(parts[1])));
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return items;
	}

	private int getWeight(String weight) {
		return Integer.parseInt(weight) / 1000;
	}

	public List<SpaceShip> loadRokets(List<Item> items, Class clazz) {
		List<SpaceShip> rockets = new ArrayList<SpaceShip>();
		// fill the rockets with items
		SpaceShip rocket = createSpaceShip(clazz);
		for (Item item : items) {
			if (rocket.canCarry(item))
				rocket.carry(item);
			else {
				rockets.add(rocket);
				rocket = createSpaceShip(clazz);
			}
		}
		return rockets;
	}

	private SpaceShip createSpaceShip(Class clazz) {
		return clazz.equals(U1.class) ? new U1() : new U2();
	}
	
	public int runSimulation(List<SpaceShip> rockets) {
		int count = 0;
		int totalCost = 0;
		for (SpaceShip rocket : rockets) {
			while (!(rocket.land() && rocket.launch())) {
				count++;
				totalCost += ((Rocket) rocket).getCost();
			}
			count++;
			totalCost += ((Rocket) rocket).getCost();
		}
		System.out.println(count + " Rockets launched of type " + rockets.get(0).getClass().getName() + ". Total cost "
				+ totalCost + "$us");
		return totalCost;
	}

	public static void main(String[] args) {
		Simulation s = new Simulation();
		s.simulatePhase("resources/Phase-1.txt");
		s.simulatePhase("resources/Phase-2.txt");
	}

	private void simulatePhase(String filepath) {
		List<Item> items = loadItems(filepath);
		System.out.println(items);
		runSimulation(loadRokets(items, U1.class));
		runSimulation(loadRokets(items, U2.class));
	}
}