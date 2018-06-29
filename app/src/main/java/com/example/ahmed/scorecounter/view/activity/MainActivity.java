package com.example.ahmed.scorecounter.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.bluelinelabs.conductor.ChangeHandlerFrameLayout;
import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;
import com.example.ahmed.scorecounter.R;
import com.example.ahmed.scorecounter.view.controllers.TeamController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity {

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.main_team_a_container)
	ChangeHandlerFrameLayout aContainer;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.main_team_b_container)
	ChangeHandlerFrameLayout bContainer;

	@SuppressWarnings("WeakerAccess")
	@BindView(R.id.main_reset_button)
	Button resetButton;

	private Unbinder unbinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle(getString(R.string.app_name));

		unbinder = ButterKnife.bind(this);

		final List<TeamController> controllers = new ArrayList<>(2);

		controllers.add(addTeam(aContainer, getString(R.string.team_a), savedInstanceState));
		controllers.add(addTeam(bContainer, getString(R.string.team_b), savedInstanceState));

		resetButton.setOnClickListener(v -> {
			for (int i = 0; i < controllers.size(); ++i)
				controllers.get(i).resetPoints();
		});
	}

	private TeamController addTeam(ChangeHandlerFrameLayout container, String name, Bundle savedInstanceState) {

		final Router router = Conductor.attachRouter(this, container, savedInstanceState);

		if (!router.hasRootController()) {

			final TeamController controller = TeamController.newInstance(name);

			router.setRoot(RouterTransaction.with(controller));

			return controller;

		} else if (router.getBackstackSize() > 0) {

			return (TeamController) router.getBackstack().get(0).controller();

		} else {

			throw new IllegalStateException("Router has a root controller but it can't be found in the backstack!");
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		if (item.getItemId() == R.id.action_contact) {
			Intent intent = new Intent(Intent.ACTION_SENDTO);
			String[] emails = {"Dev.ahmedmourad73744@gmail.com"};

			intent.setData(Uri.parse("mailto:")); // only email apps should handle this
			intent.putExtra(Intent.EXTRA_EMAIL, emails);
			intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_subject));
			intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.feedback_text));

			if (intent.resolveActivity(getPackageManager()) != null)
				startActivity(intent);
		}

		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onDestroy() {
		unbinder.unbind();
		super.onDestroy();
	}
}
