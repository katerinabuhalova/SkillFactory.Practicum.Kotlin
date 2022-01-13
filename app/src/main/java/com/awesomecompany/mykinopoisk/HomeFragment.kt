package com.awesomecompany.mykinopoisk

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.awesomecompany.mykinopoisk.data.Film
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    val filmsDataBase = listOf(
        Film("Joker", R.drawable.joker, "Film1"),
        Film("Titanic", R.drawable.titanic, "Film2"),
        Film("Christmas Story", R.drawable.christmasstory, "Film3"),
        Film("Car", R.drawable.car, "Film4"),
        Film("Monsters", R.drawable.monstersinc24_500x749, "Film5"),
        Film("Nemo", R.drawable.nemo, "Film6"),
        Film("Ratatouille", R.drawable.ratatouille, "Film7"),
        Film("Star Wars", R.drawable.picter3, "Film8")
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializeRecycler()
    }

    private fun initializeRecycler() {
        main_recycler.apply {
            filmsAdapter =
                FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
                    override fun click(film: Film) {
                        (requireActivity() as MainActivity).launchDetailsFragment(film)
                    }
                })
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }
        filmsAdapter.addItems(filmsDataBase)
    }
}