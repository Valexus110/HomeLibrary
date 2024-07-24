package com.valexus.homelibrary.ui.main.adapters

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.valexus.homelibrary.R
import com.valexus.homelibrary.data.models.Book
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class BookItemAdapter:
    RecyclerView.Adapter<BookItemAdapter.BookItemViewHolder>() {
    var data = listOf<Book>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookItemViewHolder =
        BookItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: BookItemViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    class BookItemViewHolder @Inject constructor(
        @ApplicationContext var context: Context,
        rootView: ConstraintLayout
    ) :
        RecyclerView.ViewHolder(rootView) {
        private val title: TextView = rootView.findViewById(R.id.title_text)
        private val author: TextView = rootView.findViewById(R.id.author_text)
        private val genre: TextView = rootView.findViewById(R.id.genre_text)
        private val cover: ImageView = rootView.findViewById(R.id.cover)

        companion object {
            fun inflateFrom(parent: ViewGroup): BookItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view =
                    layoutInflater.inflate(R.layout.book_item, parent, false) as ConstraintLayout
                return BookItemViewHolder(parent.context, view)
            }
        }

        fun bind(item: Book) {
            title.text = item.title
            author.text = item.author
            genre.text = item.genre
            cover.setImageDrawable(ContextCompat.getDrawable(context, item.cover))
        }
    }

    @BindingAdapter(value = ["src", "placeholderImage"], requireAll = false)
    fun loadImage(imageView: ImageView, src: Int?, placeholderImage: Drawable?) {
        if (placeholderImage != null) {
            Glide.with(imageView.context).load(src).placeholder(placeholderImage).into(imageView)
        } else {
            Glide.with(imageView.context).load(src).into(imageView)
        }
    }
}