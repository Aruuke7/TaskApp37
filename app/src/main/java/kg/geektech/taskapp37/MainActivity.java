package kg.geektech.taskapp37;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import java.util.Objects;
import kg.geektech.taskapp37.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        kg.geektech.taskapp37.databinding.ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);



        Prefs prefs = new Prefs(this);
        if (!prefs.isBoardShown())
            navController.navigate(R.id.boardFragment);
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (destination.getId()==R.id.navigation_home|| destination.getId()==R.id.navigation_dashboard ||
            destination.getId()==R.id.navigation_notifications || destination.getId()==R.id.navigation_profile){
                binding.navView.setVisibility(View.VISIBLE);
            }else {
                binding.navView.setVisibility(View.GONE);
            }


            if (destination.getId()==R.id.boardFragment){
                Objects.requireNonNull(getSupportActionBar()).hide();
            }else {
                Objects.requireNonNull(getSupportActionBar()).show();
            }
        });
    }
}