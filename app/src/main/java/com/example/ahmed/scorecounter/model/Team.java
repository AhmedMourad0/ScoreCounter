package com.example.ahmed.scorecounter.model;

public class Team {

	private final String name;
	private int points = 0;
	private int goals = 0;

	private OnPointsChangedListener pointsListener;
	private OnGoalsChangedListener goalsListener;

	public Team(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void addPoints(final int addition) {
		points += addition;
		pointsListener.onPointsChangedListener(points);
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(final int points) {
		this.points = points;
		pointsListener.onPointsChangedListener(points);
	}

	public void addGoal() {
		goals += 1;
		goalsListener.onGoalsChangedListener(goals);
	}

	public int getGoals() {
		return goals;
	}

	public void setGoals(final int goals) {
		this.goals = goals;
		goalsListener.onGoalsChangedListener(goals);
	}

	public void reset() {
		points = 0;
		goals = 0;
		pointsListener.onPointsChangedListener(points);
		goalsListener.onGoalsChangedListener(goals);
	}

	public void setOnPointsChangedListener(OnPointsChangedListener listener) {
		this.pointsListener = listener;
	}

	public void setOnGoalsChangedListener(OnGoalsChangedListener listener) {
		this.goalsListener = listener;
	}

	@FunctionalInterface
	public interface OnPointsChangedListener {
		void onPointsChangedListener(int newValue);
	}

	@FunctionalInterface
	public interface OnGoalsChangedListener {
		void onGoalsChangedListener(int newValue);
	}
}
