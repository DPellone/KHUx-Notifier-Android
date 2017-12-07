package it.lexedian.unionnotifier

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.job.JobParameters
import android.app.job.JobService
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.support.v4.app.NotificationCompat
import android.widget.Toast
import org.jsoup.Jsoup
import android.os.Handler

/**
 * Created by Daniele Pellone on 05/12/2017.
 */
class MyService : JobService() {

    private var list: IntArray? = null

    override fun onStopJob(params: JobParameters?): Boolean {
        return false
    }

    override fun onStartJob(params: JobParameters?): Boolean {

        if(list == null)
            list = params?.extras?.getIntArray("ID_LIST")

        if(list != null)
            object : Thread() {
                override fun run() {
                    checkOnline(params)
                }
            }.start()

        return true
    }

    private fun checkOnline(params: JobParameters?) {
        val page = Jsoup.connect(LIST_URL).get()
        val listAnnouncements = page.select("li")
        val newAnnouncements = mutableListOf<Announcement>()
        for (html_ann in listAnnouncements){
            val id = html_ann.selectFirst("a").attr("href").split('/')[1].toInt()
            if(list!!.contains(id))
                continue
            val title = html_ann.selectFirst("a").text()
            val catName = html_ann.selectFirst("span.news_cat").text()
            newAnnouncements.add(Announcement(id, catName, title))
        }
        if(!newAnnouncements.isEmpty()) {
            var message = ""
            for (index in newAnnouncements.indices) {
                if (index != 0)
                    message += "\n"
                message += newAnnouncements[index].cat + ": " + newAnnouncements[index].title
            }

            val notificationBuilder = NotificationCompat.Builder(this, "defaultChannel")
                    .setSmallIcon(R.drawable.ic_star_black_24dp)
                    .setContentTitle("Nuovi annunci")
                    .setContentText("Apri per i dettagli")
                    .setStyle(NotificationCompat.BigTextStyle().bigText(message))
                    .setAutoCancel(true)

            val intent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            notificationBuilder.setContentIntent(pendingIntent)

            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).notify(110, notificationBuilder.build())
        }
        jobFinished(params,false)
    }
}