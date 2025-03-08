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

        TextView textViewData = view.findViewById(R.id.tv_data);

        // Récupérer les données depuis la base de données
        AppDatabase.getDatabase(getActivity()).userDao().getAllUsers().observe(getViewLifecycleOwner(), users -> {
            if (users != null && !users.isEmpty()) {
                User user = users.get(users.size() - 1); // Récupérer le dernier utilisateur inséré
                String userData = "Login: " + user.login + "\nNom: " + user.nom + "\nPrénom: " + user.prenom +
                        "\nDate de Naissance: " + user.dateNaissance + "\nTéléphone: " + user.telephone +
                        "\nEmail: " + user.email + "\nCentres d'intérêt: " + user.centresInteret;
                textViewData.setText(userData);
            }
        });

        return view;
    }
}