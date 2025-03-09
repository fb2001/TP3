package com.example.tp3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AffichageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_affichage, container, false);

        // Récupérer les vues
        TextView tvLogin = view.findViewById(R.id.tv_login);
        TextView tvNomPrenom = view.findViewById(R.id.tv_nom_prenom);
        ImageView ivGenre = view.findViewById(R.id.iv_genre);
        TextView tvDateNaissance = view.findViewById(R.id.tv_date_naissance);
        TextView tvTelephone = view.findViewById(R.id.tv_telephone);
        TextView tvEmail = view.findViewById(R.id.tv_email);

        // Conteneur pour les centres d'intérêt
        LinearLayout layoutInterets = view.findViewById(R.id.layout_interets);

        // Récupérer les données depuis la base de données
        AppDatabase.getDatabase(getActivity()).userDao().getAllUsers().observe(getViewLifecycleOwner(), users -> {
            if (users != null && !users.isEmpty()) {
                User user = users.get(users.size() - 1); // Récupérer le dernier utilisateur inséré

                // Afficher les informations de base
                tvLogin.setText(user.login);
                tvNomPrenom.setText(user.nom + " " + user.prenom);
                tvDateNaissance.setText(user.dateNaissance);
                tvTelephone.setText(user.telephone);
                tvEmail.setText(user.email);

                // Définir l'image du genre
                if (user.genre.equals("Homme")) {
                    ivGenre.setImageResource(R.drawable.ic_male);
                } else if (user.genre.equals("Femme")) {
                    ivGenre.setImageResource(R.drawable.ic_female);
                }

                // Afficher les centres d'intérêt avec leurs icônes
                String[] interets = user.centresInteret.split(" ");
                layoutInterets.removeAllViews(); // Nettoyer le conteneur

                for (String interet : interets) {
                    if (!interet.isEmpty() && !interet.equals("Aucun")) {
                        View interetView = inflater.inflate(R.layout.item_interet, layoutInterets, false);
                        ImageView ivInteret = interetView.findViewById(R.id.iv_interet);
                        TextView tvInteret = interetView.findViewById(R.id.tv_interet);

                        // Définir le texte
                        tvInteret.setText(interet);

                        // Définir l'icône en fonction du centre d'intérêt
                        switch (interet) {
                            case "Sport":
                                ivInteret.setImageResource(R.drawable.ic_sport);
                                break;
                            case "Musique":
                                ivInteret.setImageResource(R.drawable.ic_music);
                                break;
                            case "Lecture":
                                ivInteret.setImageResource(R.drawable.ic_book);
                                break;
                            case "Voyage":
                                ivInteret.setImageResource(R.drawable.ic_travel);
                                break;
                            case "Cinéma":
                                ivInteret.setImageResource(R.drawable.ic_cinema);
                                break;
                            case "Cuisine":
                                ivInteret.setImageResource(R.drawable.ic_cooking);
                                break;
                        }

                        layoutInterets.addView(interetView);
                    }
                }

                // Si aucun centre d'intérêt n'est sélectionné
                if (layoutInterets.getChildCount() == 0) {
                    TextView tvAucunInteret = new TextView(getContext());
                    tvAucunInteret.setText("Aucun centre d'intérêt sélectionné");
                    tvAucunInteret.setPadding(16, 16, 16, 16);
                    layoutInterets.addView(tvAucunInteret);
                }
            }
        });

        return view;
    }
}