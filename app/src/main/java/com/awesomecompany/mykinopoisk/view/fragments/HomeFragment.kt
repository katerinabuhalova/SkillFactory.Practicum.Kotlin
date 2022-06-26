package com.awesomecompany.mykinopoisk.view.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.awesomecompany.mykinopoisk.view.rv_adapters.FilmListRecyclerAdapter
import com.awesomecompany.mykinopoisk.view.MainActivity
import com.awesomecompany.mykinopoisk.databinding.FragmentHomeBinding
import com.awesomecompany.mykinopoisk.view.rv_adapters.TopSpacingItemDecoration
import com.awesomecompany.mykinopoisk.data.entity.Film
import com.awesomecompany.mykinopoisk.utils.AnimationHelper
import com.awesomecompany.mykinopoisk.utils.AutoDisposable
import com.awesomecompany.mykinopoisk.utils.addTo
import com.awesomecompany.mykinopoisk.viewmodel.HomeFragmentViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }

    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    private val recyclerViewPadding = 8
    private val startAnimationPosition = 1
    private var filmsDataBase = listOf<Film>()
        set(value) {
            if (field == value) return
            field = value
            filmsAdapter.addItems(field)
        }
    private val autoDisposable = AutoDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        autoDisposable.bindTo(lifecycle)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        AnimationHelper.performFragmentCircularRevealAnimation(
            home_fragment_root,
            requireActivity(),
            startAnimationPosition
        )

        initSearchView()
        initPullToRefresh()
        initializeRecycler()

        viewModel.filmsListData
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { list ->
                filmsAdapter.addItems(list)
                filmsDataBase = list
            }
            .addTo(autoDisposable)

        viewModel.showProgressBar
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                binding.progressBar.isVisible = it
            }
            .addTo(autoDisposable)
    }

    fun initPullToRefresh() {
        binding.pullToRefresh.setOnRefreshListener {
            filmsAdapter.items.clear()
            viewModel.getFilms()
            binding.pullToRefresh.isRefreshing = false
        }
    }

    private fun initSearchView() {
        search_view.setOnClickListener {
            search_view.isIconified = false
        }

        search_view.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    if (newText.isBlank()) {
                        filmsAdapter.addItems(filmsDataBase)
                        return true
                    }
                    val result = filmsDataBase.filter {
                        it.title.toLowerCase(Locale.getDefault())
                            .contains(newText.toLowerCase(Locale.getDefault()))
                    }
                    filmsAdapter.addItems(result)
                    return true
                }
            })
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
            val decorator = TopSpacingItemDecoration(recyclerViewPadding)
            addItemDecoration(decorator)
        }
        filmsAdapter.addItems(filmsDataBase)
    }
}