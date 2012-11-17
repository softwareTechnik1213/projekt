package de.htwg.madn.model;

import java.util.Random;

public final class Dice {
	
	private final int min;
	private final int max;
	private int throwsCount;
	private int lastNumber;
	private Player lastThrower;
	
	public Dice(final int min, final int max) {
		this.min = min;
		this.max = max;
		resetThrowsCount();
	}
	
	public int throwDice(Player thrower) {
		this.lastThrower = thrower;
		this.throwsCount++;
		Random rand = new Random();
		lastNumber = min + Math.abs(rand.nextInt()) % max;
		return lastNumber;
	}
	
	public int getLastNumber() {
		if (this.throwsCount == 0) {
			throw new IllegalAccessError("dice has not been thrown before!");
		}
		return this.lastNumber;
	}
	
	public Player getLastThrower() {
		return lastThrower;
	}
	
	public int getThrowsCount() {
		return throwsCount;
	}
	
	public void resetThrowsCount() {
		this.throwsCount = 0;
	}
}
