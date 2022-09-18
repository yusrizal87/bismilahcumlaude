package Adapter

import Database.GlobalVar
import Interface.CardListener
import Model.User
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.bismilahcumlaude.R
import com.example.bismilahcumlaude.guambar
import com.example.bismilahcumlaude.databinding.CardandBinding


class ListDataRV(val listUser: ArrayList<User>, val cardListener: CardListener) :
    RecyclerView.Adapter<ListDataRV.viewHolder>() {

    class viewHolder(val itemView: View, val cardListener: CardListener) :
        RecyclerView.ViewHolder(itemView) {

        val binding =CardandBinding.bind(itemView)


        fun setData(data: User) {
            binding.namahewan.text = data.thename
            binding.jenisHewan.text = data.type
            binding.usia.text = "Usia : "+ data.umr
            if (data.imageUri!!.isNotEmpty()) {
                binding.picCard.setImageURI(Uri.parse(data.imageUri))
            }


        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): viewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.cardand, parent, false)
        return viewHolder(view, cardListener)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.setData(GlobalVar.listi[position])
        with(holder) {
            binding.editbtnn.setOnClickListener {
                val myintent = Intent(it.context, guambar::class.java).apply {
                    putExtra("position", position)
                }
                it.context.startActivity(myintent)
            }
            binding.delbtnn.setOnClickListener {
                val builder = AlertDialog.Builder(it.context)
                builder.setTitle("Delete Animal")
                builder.setMessage("Are you sure you want to delete this animal?")

                builder.setPositiveButton(android.R.string.yes) { function, which ->

                    Toast.makeText(it.context,"Animal Deleted", Toast.LENGTH_SHORT).show()

                    GlobalVar.listi.removeAt(position)
                    notifyItemRemoved(position)
                    notifyItemChanged(position, itemCount)
                }

                builder.setNegativeButton(android.R.string.no) { dialog, which ->
                    Toast.makeText(it.context,
                        android.R.string.no, Toast.LENGTH_SHORT).show()
                }
                builder.show()

            }
        }
    }

    override fun getItemCount(): Int {
        return listUser.size
    }

}
