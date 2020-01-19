package com.example.roomexample_yena

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sms_autotransapp.R
import org.w3c.dom.Text

class LogAdapter(val logItemClick: (Log) -> Unit, val logItemLongClick: (Log) -> Unit)
    : RecyclerView.Adapter<LogAdapter.ViewHolder>() {

    private var logs: List<Log> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sub_log_test, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return logs.size
    }

    override fun onBindViewHolder(viewHolder:ViewHolder , position: Int) {
        viewHolder.bind(logs[position])
    }


    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val txtLog = itemView.findViewById<TextView>(R.id.txtLog)

        fun bind(log: Log) {
            txtLog.text = log.message

            itemView.setOnClickListener {
                logItemClick(log)
            }

            itemView.setOnLongClickListener {
                logItemLongClick(log)
                true
            }
        }
    }

    fun setContacts(logs: List<Log>) {
        this.logs = logs
        notifyDataSetChanged()
    }


}