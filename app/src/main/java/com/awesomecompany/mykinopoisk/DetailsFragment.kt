package com.awesomecompany.mykinopoisk

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.awesomecompany.mykinopoisk.data.Film
import kotlinx.android.synthetic.main.fragment_details.*

class DetailsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val film = arguments?.get("film") as Film
        details_toolbar.title = film.title
        details_poster.setImageResource(film.poster)
        details_description.text = film.description

        details_fab_favorites.setImageResource(
            if (film.isInFavorites) R.drawable.ic_favorits
            else R.drawable.ic_favorits_two
        )

        details_fab_favorites.setOnClickListener {
            if (!film.isInFavorites) {
                details_fab_favorites.setImageResource(R.drawable.ic_favorits)
                film.isInFavorites = true
            } else {
                details_fab_favorites.setImageResource(R.drawable.ic_favorits_two)
                film.isInFavorites = false
            }
        }

        details_fab_share.setOnClickListener {
            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.putExtra(
                Intent.EXTRA_TEXT,
                "Check out this film: ${film.title} \n\n ${film.description}"
            )
            intent.type = "text/plain"
            startActivity(Intent.createChooser(intent, "Share To:"))
        }
    }
}