package me.amitshekhar.mvvm.ui.topheadline

import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import me.amitshekhar.mvvm.data.model.Game
import me.amitshekhar.mvvm.databinding.GameItemBinding
import java.text.DecimalFormat

class GameAdapter(
    private val gameList: ArrayList<Game>
) : RecyclerView.Adapter<GameAdapter.DataViewHolder>() {

    class DataViewHolder(private val binding: GameItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(game: Game) {
            binding.textViewTitle.text = game.name
            Glide.with(binding.imageViewBanner.context)
                .load(game.images)
                .into(binding.imageViewBanner)
            binding.textViewGenre.text = game.genre

            val input = game.price
            val firstPart = input.substring(0, 3)
            val secondPart = input.substring(3)
            val formattedString = "$firstPart.$secondPart VND"
            binding.buttonPrice.text = formattedString
            binding.buttonPrice.setOnClickListener {
                val builder = CustomTabsIntent.Builder()
                val customTabsIntent = builder.build()
                customTabsIntent.launchUrl(it.context, Uri.parse(game.videos))
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            GameItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount(): Int = gameList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(gameList[position])
    }

    fun addData(list: List<Game>) {
        gameList.addAll(list)
    }

    fun clearData() {
        gameList.clear()
        notifyDataSetChanged()
    }

}