package org.d3if4118.asesmen.mvvm.crud

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.d3if3118.asesmen.R
import com.d3if3118.asesmen.databinding.ListItemNoteBinding
import org.d3if4118.asesmen.model.Note

class ListNoteAdapter : RecyclerView.Adapter<ListNoteAdapter.ViewHolder>() {

    private var noteList = emptyList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemNoteBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return noteList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(noteList[position])
    }

    fun setData(note: List<Note>) {
        this.noteList = note
        notifyDataSetChanged()
    }

    class ViewHolder(private val binding: ListItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Note) {
            binding.apply {
                tvTitleItem.text = data.title
                tvNoteItem.text = data.note
                tvDateItem.text = data.date
                itemView.setOnClickListener {
                    val action =
                        org.d3if4118.asesmen.mvvm.crud.ListFragmentDirections.actionListFragmentToUpdateFragment(
                            data
                        )
                    itemView.findNavController().navigate(action)
                }

                val status = data.status
                if (status == "Yes") {
                    imgStatus.setImageResource(R.drawable.ic_important)
                } else {
                    imgStatus.setImageResource(R.drawable.ic_not_important)
                }
            }

        }

    }

}