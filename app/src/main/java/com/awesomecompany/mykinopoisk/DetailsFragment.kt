package com.awesomecompany.mykinopoisk

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
    }
}