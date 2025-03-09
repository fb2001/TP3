package com.example.tp3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class InscriptionFragment extends Fragment {

    private EditText editLogin, editPassword, editNom, editPrenom, editDateNaissance, editPhone, editEmail;
    private CheckBox checkSport, checkMusique, checkLecture, checkVoyage, checkCinema, checkCuisine;
    private Button btnSubmit;
    private ImageView icMale, icFemale;
    private ImageView selectedImage = null; // Garde une référence à l'image sélectionnée
    private String selectedGender = ""; // Stocke le genre sélectionné

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
        icMale = view.findViewById(R.id.ic_male);
        icFemale = view.findViewById(R.id.ic_female);

        // Gestion des clics sur les images
        icMale.setOnClickListener(v -> onGenderSelected(icMale, "Homme", R.drawable.ic_male_red, R.drawable.ic_male));
        icFemale.setOnClickListener(v -> onGenderSelected(icFemale, "Femme", R.drawable.ic_female_red, R.drawable.ic_female));

        // Gestion du clic sur le bouton Soumettre
        btnSubmit.setOnClickListener(v -> {
            // Création d'un nouvel utilisateur
            User user = new User(
                    editLogin.getText().toString().trim(),
                    editPassword.getText().toString().trim(),
                    editNom.getText().toString().trim(),
                    editPrenom.getText().toString().trim(),
                    editDateNaissance.getText().toString().trim(),
                    editPhone.getText().toString().trim(),
                    editEmail.getText().toString().trim(),
                    getSelectedHobbies(),
                    selectedGender
            );

            // Insertion dans la base de données (Thread séparé)
            new Thread(() -> AppDatabase.getDatabase(getActivity()).userDao().insert(user)).start();

            // Passer au Fragment 2
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, new AffichageFragment());
            transaction.addToBackStack(null);
            transaction.commit();
        });

        return view;
    }

    private void onGenderSelected(ImageView clickedImage, String gender, int selectedDrawable, int defaultDrawable) {
        if (selectedImage == clickedImage) {
            // Si l'image cliquée est déjà sélectionnée, on la désélectionne
            clickedImage.setImageResource(defaultDrawable);
            selectedImage = null;
            selectedGender = "";
            Log.d("GenderSelection", "Deselected: " + gender);
        } else {
            // Si une autre image est déjà sélectionnée, on la réinitialise
            if (selectedImage != null) {
                // Déterminer quelle image réinitialiser et avec quel drawable
                if (selectedImage == icMale) {
                    selectedImage.setImageResource(R.drawable.ic_male);
                } else if (selectedImage == icFemale) {
                    selectedImage.setImageResource(R.drawable.ic_female);
                }
                Log.d("GenderSelection", "Reset previous selection");
            }

            // On met à jour l'image cliquée avec le drawable sélectionné
            clickedImage.setImageResource(selectedDrawable);
            selectedImage = clickedImage;
            selectedGender = gender;
            Log.d("GenderSelection", "Selected: " + gender);
        }
    }

    private String getSelectedHobbies() {
        StringBuilder centresInteret = new StringBuilder();
        if (checkSport.isChecked()) centresInteret.append("Sport ");
        if (checkMusique.isChecked()) centresInteret.append("Musique ");
        if (checkLecture.isChecked()) centresInteret.append("Lecture ");
        if (checkVoyage.isChecked()) centresInteret.append("Voyage ");
        if (checkCinema.isChecked()) centresInteret.append("Cinéma ");
        if (checkCuisine.isChecked()) centresInteret.append("Cuisine ");

        return centresInteret.length() > 0 ? centresInteret.toString().trim() : "Aucun centre d'intérêt sélectionné";
    }
}