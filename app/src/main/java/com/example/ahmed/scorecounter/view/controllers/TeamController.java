package com.example.ahmed.scorecounter.view.controllers;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bluelinelabs.conductor.Controller;
import com.example.ahmed.scorecounter.R;
import com.example.ahmed.scorecounter.model.Team;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class TeamController extends Controller {

	private static final String ARG_TEAM_NAME = "arg_name";

	private static final String STATE_POINTS = "state_points";
	private static final String STATE_GOALS = "state_goals";

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.team_name)
	TextView nameTextView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.team_points)
	TextView pointsTextView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.team_goals)
	TextView goalsTextView;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.team_add_three)
	Button addThreeButton;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.team_add_one)
	Button addOneButton;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.team_add_goal)
	Button addGoalButton;

	private Team team;

	private Unbinder unbinder;

	public static TeamController newInstance(final String teamName) {

		final Bundle args = new Bundle();

		args.putString(ARG_TEAM_NAME, teamName);

		return new TeamController(args);
	}

	@SuppressWarnings("unused")
	private TeamController() {
		super();
	}

	@SuppressWarnings("WeakerAccess")
	public TeamController(@Nullable Bundle args) {
		super(args);
	}

	@NonNull
	@Override
	protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {

		final View view = inflater.inflate(R.layout.controller_team, container, false);

		unbinder = ButterKnife.bind(this, view);

		team = new Team(getArgs().getString(ARG_TEAM_NAME, view.getContext().getString(R.string.team)));

		team.setOnPointsChangeListener(newValue -> pointsTextView.setText(String.valueOf(team.getPoints())));
		team.setOnGoalsChangeListener(newValue -> goalsTextView.setText(String.valueOf(team.getGoals())));

		team.reset();

		nameTextView.setText(team.getName());

		addThreeButton.setOnClickListener(v -> team.addPoints(3));
		addGoalButton.setOnClickListener(v -> team.addGoal());
		addOneButton.setOnClickListener(v -> team.addPoints(1));

		return view;
	}

	public void resetPoints() {
		team.reset();
	}

	@Override
	protected void onSaveViewState(@NonNull View view, @NonNull Bundle outState) {
		super.onSaveViewState(view, outState);
		outState.putInt(STATE_POINTS, team.getPoints());
		outState.putInt(STATE_GOALS, team.getGoals());
	}

	@Override
	protected void onRestoreViewState(@NonNull View view, @NonNull Bundle savedViewState) {
		super.onRestoreViewState(view, savedViewState);
		team.setPoints(savedViewState.getInt(STATE_POINTS, 0));
		team.setGoals(savedViewState.getInt(STATE_GOALS, 0));
	}

	@Override
	protected void onDestroy() {
		unbinder.unbind();
		super.onDestroy();
	}
}
