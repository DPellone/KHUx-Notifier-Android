package it.lexedian.unionnotifier

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.util.MutableInt
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

val MESSAGE_URL = "MESSAGE"

class MainActivity : AppCompatActivity() {

    lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel()

        val list = findViewById<RecyclerView>(R.id.webList)
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(this)
        var array = mutableListOf<Announcement>()
        adapter = MyAdapter(array)
        Parser(applicationContext).execute(adapter)
        list.adapter = adapter
        list.addOnItemTouchListener(ItemClickListener(this, object : ItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                view.findViewById<ImageView>(R.id.star).setImageDrawable(null)
                val intent = Intent(baseContext, WebActivity::class.java)
                intent.putExtra(MESSAGE_URL, adapter.announcements[position].url)
                baseContext.startActivity(intent)
            }
        }))

        refresh_button.setOnClickListener { Parser(applicationContext).execute(adapter) }
    }

    override fun onStop() {
        super.onStop()
        createJob()
    }

    private fun createJob() {
        val builder = JobInfo.Builder(99, ComponentName(this, MyService::class.java))
                .setPeriodic(3600000)
                .setPersisted(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
        Log.i("LOG", "Job creato")

        val idList = adapter.announcements.map { it.id } as MutableList<Int>
        val bundle = PersistableBundle()
        bundle.putIntArray("ID_LIST", idList.toIntArray())
        builder.setExtras(bundle)

        (getSystemService(Context.JOB_SCHEDULER_SERVICE) as JobScheduler).schedule(builder.build())
    }

    private fun createNotificationChannel() {
        val notifManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val id = "defaultChannel"
        val name = "Canale di default"
        val notifChannel = NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT)
        notifManager.createNotificationChannel(notifChannel)
    }
}
