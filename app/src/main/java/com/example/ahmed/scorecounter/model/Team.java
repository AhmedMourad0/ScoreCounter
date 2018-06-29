package com.example.ahmed.scorecounter.model;

public class Team {

	private final String name;
	private int points = 0;
	private int goals = 0;

	private OnPointsChangeListener pointsListener;
	private OnGoalsChangeListener goalsListener;

	public Team(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addPoints(final int addition) {
		points += addition;
		pointsListener.onPointsChangeListener(points);
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(final int points) {
		this.points = points;
		pointsListener.onPointsChangeListener(points);
	}

	public void addGoal() {
		goals += 1;
		goalsListener.onGoalsChangeListener(goals);
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(final int goals) {
		this.goals = goals;
		goalsListener.onGoalsChangeListener(goals);
	}

	public void reset() {
		points = 0;
		goals = 0;
		pointsListener.onPointsChangeListener(points);
		goalsListener.onGoalsChangeListener(goals);
	}

	public void setOnPointsChangeListener(OnPointsChangeListener listener) {
		this.pointsListener = listener;
	}

	public void setOnGoalsChangeListener(OnGoalsChangeListener listener) {
		this.goalsListener = listener;
	}

	@FunctionalInterface
	public interface OnPointsChangeListener {
		void onPointsChangeListener(int newValue);
	}

	@FunctionalInterface
	public interface OnGoalsChangeListener {
		void onGoalsChangeListener(int newValue);
	}
}
