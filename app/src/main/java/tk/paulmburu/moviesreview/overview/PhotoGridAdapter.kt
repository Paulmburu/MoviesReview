package tk.paulmburu.moviesreview.overview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tk.paulmburu.moviesreview.databinding.MovieItemBinding
import tk.paulmburu.moviesreview.models.MovieResult

/**
 * This class implements a [RecyclerView] [ListAdapter] which uses Data Binding to present [List]
 * data, including computing diffs between lists.
 */
//Have PhotoGridAdapter take the OnClickListener class as a constructor property parameter
class PhotoGridAdapter(val onClickListener: OnClickListener) : ListAdapter<MovieResult, PhotoGridAdapter.MovieResultViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<MovieResult>() {
        override fun areItemsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: MovieResult, newItem: MovieResult): Boolean {
            return oldItem.title == newItem.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieResultViewHolder {
        return MovieResultViewHolder(MovieItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: MovieResultViewHolder, position: Int) {
        val movieResult = getItem(position)

//        Call the onClick Function from the onClickListener in a lambda from setOnClickListener
                holder.itemView.setOnClickListener {
                    onClickListener.onClick(movieResult)
                }

        holder.bind(movieResult)
    }

    class MovieResultViewHolder(private var binding: MovieItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(movie_result: MovieResult) {
            binding.movieResult = movie_result
            binding.executePendingBindings()
        }
    }

//    Create an OnClickListener class with a lambda in its constructor that initializes a matching onClick function
    class OnClickListener(val clickListener: (movieResult: MovieResult) -> Unit) {
        fun onClick(movieResult: MovieResult) = clickListener(movieResult)
    }

}