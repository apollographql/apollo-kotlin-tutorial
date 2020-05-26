package com.example.rocketreserver

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.rocketserver.LaunchListQuery

class LaunchListAdapter(val launches: List<LaunchListQuery.Launch>) :
    RecyclerView.Adapter<LaunchListAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val site = itemView.findViewById<TextView>(R.id.site)
        val missionName = itemView.findViewById<TextView>(R.id.missionName)
        val missionPatch = itemView.findViewById<ImageView>(R.id.missionPatch)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.launch_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return launches.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.site.text = launches[position].site
        holder.missionName.text = launches[position].mission?.name
        holder.missionPatch.load(launches[position].mission?.missionPatch) {
            placeholder(R.drawable.ic_placeholder)
        }
    }
}