package com.example.tp3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class InscriptionFragment extends Fragment {

    private EditText editLogin, editPassword, editNom, editPrenom, editDateNaissance, editPhone, editEmail;
    private CheckBox checkSport, checkMusique, checkLecture, checkVoyage, checkCinema, checkCuisine;
    private Button btnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inscription, container, false);

        // Initialisation des composants
        editLogin = view.findViewById(R.id.et_login);
        editPassword = view.findViewById(R.id.et_password);
        editNom = view.findViewById(R.id.et_nom);
        editPrenom = view.findViewById(R.id.et_prenom);
        editDateNaissance = view.findViewById(R.id.et_date_naissance);
        editPhone = view.findViewById(R.id.et_telephone);
        editEmail = view.findViewById(R.id.et_email);

        checkSport = view.findViewById(R.id.cb_sport);
        checkMusique = view.findViewById(R.id.cb_musique);
        checkLecture = view.findViewById(R.id.cb_lecture);
        checkVoyage = view.findViewById(R.id.cb_voyage);
        checkCinema = view.findViewById(R.id.cb_cinema);
        checkCuisine = view.findViewById(R.id.cb_cuisine);

        btnSubmit = view.findViewById(R.id.btn_soumettre);

        btnSubmit.setOnClickListener(v -> {
            // Récupération des données
            String login = editLogin.getText().toString().trim();
            String password = editPassword.getText().toString().trim();
            String nom = editNom.getText().toString().trim();
            String prenom = editPrenom.getText().toString().trim();
            String dateNaissance = editDateNaissance.getText().toString().trim();
            String phone = editPhone.getText().toString().trim();
            String email = editEmail.getText().toString().trim();

            // Récupération du genre sélectionné
            RadioGroup radioGroupGenre = view.findViewById(R.id.radioGroupGenre);
            int selectedId = radioGroupGenre.getCheckedRadioButtonId();
            String genre = "";
            if (selectedId == R.id.radioHomme) {
                genre = "Homme";
            } else if (selectedId == R.id.radioFemme) {
                genre = "Femme";
            }

            // Centres d’intérêt sélectionnés
            StringBuilder centresInteret = new StringBuilder();
            if (checkSport.isChecked()) centresInteret.append("Sport ");
            if (checkMusique.isChecked()) centresInteret.append("Musique ");
            if (checkLecture.isChecked()) centresInteret.append("Lecture ");
            if (checkVoyage.isChecked()) centresInteret.append("Voyage ");
            if (checkCinema.isChecked()) centresInteret.append("Cinéma ");
            if (checkCuisine.isChecked()) centresInteret.append("Cuisine ");

            if (centresInteret.length() == 0) {
                centresInteret.append("Aucun centre d'intérêt sélectionné");
            }

            // Création d'un nouvel utilisateur
            User user = new User(login, password, nom, prenom, dateNaissance, phone, email, centresInteret.toString(), genre);

            // Insertion dans la base de données
            new Thread(() -> {
                AppDatabase.getDatabase(getActivity()).userDao().insert(user);
            }).start();

            // Passer au Fragment 2
            AffichageFragment affichageFragment = new AffichageFragment();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, affichageFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        });
        return view;
    }
}