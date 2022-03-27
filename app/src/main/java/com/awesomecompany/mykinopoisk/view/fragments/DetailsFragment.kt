package com.awesomecompany.mykinopoisk.view.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.awesomecompany.mykinopoisk.R
import com.awesomecompany.mykinopoisk.data.ApiConstants
import com.awesomecompany.mykinopoisk.domain.Film
import com.awesomecompany.mykinopoisk.utils.AnimationHelper
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment() {

    companion object {
        private const val FILM_ARGUMENT_ID = "film"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(details_root, requireActivity(), 1)

        val film = arguments?.get(FILM_ARGUMENT_ID) as Film
        details_toolbar.title = film.title
        Glide.with(this)
            .load(ApiConstants.IMAGES_URL + "w780" + film.poster)
            .centerCrop()
            .into(details_poster)
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