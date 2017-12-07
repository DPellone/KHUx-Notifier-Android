package it.lexedian.unionnotifier

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup

val LIST_URL = "http://api.sp.kingdomhearts.com/information/list"

/**
 * Created by Daniele Pellone on 04/12/2017.
 */

class Parser(val context: Context) : AsyncTask<MyAdapter, Void, List<Announcement>>() {

    lateinit var adapter: MyAdapter

    override fun doInBackground(vararg params: MyAdapter): List<Announcement> {
        adapter = params.first()
        val page = Jsoup.connect(LIST_URL).get()
        val listAnnouncements = page.select("li")
        val db = AppDatabase.getInstance(context)
        val list = arrayListOf<Announcement>()
        for (html_ann in listAnnouncements){
            val span = html_ann.selectFirst("span.news_cat")
            val catName = span.text()

            val link = html_ann.selectFirst("a")
            val title = link.text()
            val href = link.attr("href")
            val id = href.split('/')[1]

            val ann = Announcement(id.toInt(), catName, title)
            if (adapter.announcements.contains(ann))
                continue
            list.add(ann)

            if(db.announcementsDAO().get(id.toInt()).isEmpty()) {
                db.announcementsDAO().insertAll(ann)
                ann.isNew = true
                Log.i("Announcement", id + ": " + title)
            }
        }
        return list
    }

    override fun onPostExecute(result: List<Announcement>) {
        adapter.announcements.addAll(result)
        adapter.notifyDataSetChanged()
    }
}