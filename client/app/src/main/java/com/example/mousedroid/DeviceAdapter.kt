package com.example.mousedroid

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class DeviceAdapter(
    private val context: Context?,
    private val devices: ArrayList<Pair<String, String>>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<DeviceAdapter.ViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(address: String)
        fun onItemLongClick(position: Int)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var wrapper: ConstraintLayout = view.findViewById(R.id.deviceWrapper)
        var deviceName: TextView = view.findViewById(R.id.device_name)
        var deviceAddress: TextView = view.findViewById(R.id.device_address)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.devicelistitem, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(vh: ViewHolder, position: Int) {
        val name = devices[position].first
        val address = devices[position].second

        vh.deviceName.text = name
        vh.deviceAddress.text = address

        vh.wrapper.setOnClickListener {
            listener.onItemClick(address)
        }

        vh.wrapper.setOnLongClickListener {
            listener.onItemLongClick(position)
            true
        }
    }

    override fun getItemCount(): Int {
        return devices.size
    }
}