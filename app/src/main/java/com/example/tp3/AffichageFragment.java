package com.example.tp3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AffichageFragment extends Fragment {

    private TextView textViewData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_affichage, container, false);

        // Récupérer les TextView
        TextView tvLogin = view.findViewById(R.id.tv_login);
        TextView tvNom = view.findViewById(R.id.tv_nom);
        TextView tvPrenom = view.findViewById(R.id.tv_prenom);
        TextView tvGenre = view.findViewById(R.id.tv_genre);
        TextView tvDateNaissance = view.findViewById(R.id.tv_date_naissance);
        TextView tvTelephone = view.findViewById(R.id.tv_telephone);
        TextView tvEmail = view.findViewById(R.id.tv_email);
        TextView tvCentresInteret = view.findViewById(R.id.tv_centres_interet);

        // Récupérer les données depuis la base de données
        AppDatabase.getDatabase(getActivity()).userDao().getAllUsers().observe(getViewLifecycleOwner(), users -> {
            if (users != null && !users.isEmpty()) {
                User user = users.get(users.size() - 1); // Récupérer le dernier utilisateur inséré

                // Afficher les données
                tvLogin.setText("Login: " + user.login);
                tvNom.setText("Nom: " + user.nom);
                tvPrenom.setText("Prénom: " + user.prenom);
                tvGenre.setText("Genre: " + user.genre);
                tvDateNaissance.setText("Date de naissance: " + user.dateNaissance);
                tvTelephone.setText("Téléphone: " + user.telephone);
                tvEmail.setText("Email: " + user.email);
                tvCentresInteret.setText("Centres d'intérêt: " + user.centresInteret);
            }
        });

        return view;
    }
}