package it.lexedian.unionnotifier

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

/**
 * Created by Daniele Pellone on 05/12/2017.
 */
class MyAdapter(var announcements : MutableList<Announcement>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
    override fun getItemCount(): Int {
        return announcements.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val l = LayoutInflater.from(parent.context)
                .inflate(R.layout.list_element, parent,false)
        return ViewHolder(l as LinearLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.layout.findViewById<TextView>(R.id.cat)?.text = announcements[position].cat
        when (announcements[position].cat) {
            "CAMPAIGN"  ->  holder.layout.findViewById<TextView>(R.id.cat)?.setTextColor(Color.parseColor("#872bc4"))
            "EVENT"     ->  holder.layout.findViewById<TextView>(R.id.cat)?.setTextColor(Color.parseColor("#14caf7"))
            "UPDATE"    ->  holder.layout.findViewById<TextView>(R.id.cat)?.setTextColor(Color.parseColor("#9dc668"))
            "ERROR"     ->  holder.layout.findViewById<TextView>(R.id.cat)?.setTextColor(Color.parseColor("#bc3846"))
            "FIXED"     ->  holder.layout.findViewById<TextView>(R.id.cat)?.setTextColor(Color.parseColor("#f249dc"))
            "IMPORTANT" ->  holder.layout.findViewById<TextView>(R.id.cat)?.setTextColor(Color.parseColor("#ed5b60"))
            else        ->  holder.layout.findViewById<TextView>(R.id.cat)?.setTextColor(Color.parseColor("#000000"))
        }
        if(announcements[position].isNew)
            holder.layout.findViewById<ImageView>(R.id.star)?.setImageResource(R.drawable.ic_star_black_24dp)

        holder.layout.findViewById<TextView>(R.id.title)?.text = announcements[position].title
    }

    class ViewHolder(var layout : LinearLayout) : RecyclerView.ViewHolder(layout)

}