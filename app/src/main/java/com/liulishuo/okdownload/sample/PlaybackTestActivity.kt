package com.liulishuo.okdownload.sample

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import com.liulishuo.okdownload.DownloadContext
import com.liulishuo.okdownload.DownloadContextListener
import com.liulishuo.okdownload.DownloadListener
import com.liulishuo.okdownload.DownloadTask
import com.liulishuo.okdownload.core.breakpoint.BreakpointInfo
import com.liulishuo.okdownload.core.cause.EndCause
import com.liulishuo.okdownload.core.cause.ResumeFailedCause
import kotlinx.android.synthetic.main.activity_playback_test.*
import java.io.File
import java.lang.Exception

class PlaybackTestActivity : AppCompatActivity() {
    lateinit var builder:DownloadContext.Builder
    lateinit var context:DownloadContext
    private val listener = object :DownloadListener{
        override fun connectTrialEnd(
            task: DownloadTask,
            responseCode: Int,
            responseHeaderFields: MutableMap<String, MutableList<String>>
        ) {

        }

        override fun fetchEnd(task: DownloadTask, blockIndex: Int, contentLength: Long) {
        }

        override fun downloadFromBeginning(task: DownloadTask, info: BreakpointInfo, cause: ResumeFailedCause) {
        }

        override fun taskStart(task: DownloadTask) {
        }

        override fun taskEnd(task: DownloadTask, cause: EndCause, realCause: Exception?) {
        }

        override fun connectTrialStart(
            task: DownloadTask,
            requestHeaderFields: MutableMap<String, MutableList<String>>
        ) {
        }

        override fun downloadFromBreakpoint(task: DownloadTask, info: BreakpointInfo) {
        }

        override fun fetchStart(task: DownloadTask, blockIndex: Int, contentLength: Long) {
            Log.d("testdownload","fetchStart: download ${task.url} blockIndex:"+blockIndex+"contentLength:"+contentLength)

        }

        override fun fetchProgress(task: DownloadTask, blockIndex: Int, increaseBytes: Long) {
            Log.d("testdownload","fetchProgress: download ${task.url} blockIndex:"+blockIndex+"increaseBytes:"+increaseBytes)
        }

        override fun connectEnd(
            task: DownloadTask,
            blockIndex: Int,
            responseCode: Int,
            responseHeaderFields: MutableMap<String, MutableList<String>>
        ) {
        }

        override fun connectStart(
            task: DownloadTask,
            blockIndex: Int,
            requestHeaderFields: MutableMap<String, MutableList<String>>
        ) {
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playback_test)
        init()
        getData()
        btn_start.setOnClickListener {
        start()
        }
        btn_end.setOnClickListener {
            stop()
        }
        btn_add.setOnClickListener {
            addTask()
        }
    }


    fun init(){
        builder = DownloadContext.QueueSet().setParentPathFile(getFileParent())
            .setMinIntervalMillisCallbackProcess(150)
            .commit()
    }

    fun getFileParent():File{
        val state = Environment.getExternalStorageState()
        if (Environment.MEDIA_MOUNTED != state) {
            return this@PlaybackTestActivity.getExternalCacheDir()
        }
       val file = File(Environment.getExternalStorageDirectory(),"afd_download")
        if(file.exists()){
            return file
        }else{
           if(file.mkdir()){
               return file
           }else{
              return this@PlaybackTestActivity.getExternalCacheDir()
           }

        }
    }

    fun getData(){
        val urlArray = listOf<String>("7207465",
            "7207473",
            "7207157",
            "7207182")
        urlArray.forEach{
            val task = DownloadTask.Builder("http://testhfsfd-replayer.haofenshu.com/replayer_video/?sid="+it, getFileParent()).setFilename(it+".mp4")
                .build()
            builder.bindSetTask(task)
        }

        builder.setListener(object : DownloadContextListener {
            override fun taskEnd(
                context: DownloadContext,
                task: DownloadTask,
                cause: EndCause,
                realCause: Exception?,
                remainCount: Int
            ) {
               Log.d("testdownload:" ,"taskEnd"+task.url+"endCause:"+cause.toString()+if(cause.toString().equals("ERROR")){realCause.toString()}else{"end success"} )
            }

            override fun queueEnd(context: DownloadContext) {
                Log.d("testdownload:" ,"queueEnd" )
            }
        }
            )
        context = builder.build();
    }

    fun addTask(){
        val urlArray = listOf<String>("7207434",
            "7207452",
            "7207357",
            "7207352")
        urlArray.forEach{
            val task = DownloadTask.Builder("http://testhfsfd-replayer.haofenshu.com/replayer_video/?sid="+it, getFileParent()).setFilename(it+".mp4")
                .build()
            builder.bindSetTask(task)
        }

        builder.setListener(object : DownloadContextListener {
            override fun taskEnd(
                context: DownloadContext,
                task: DownloadTask,
                cause: EndCause,
                realCause: Exception?,
                remainCount: Int
            ) {
                Log.d("testdownload:" ,"taskEnd"+task.url+"endCause:"+cause.toString()+if(cause.toString().equals("ERROR")){realCause.toString()}else{"end success"} )
            }

            override fun queueEnd(context: DownloadContext) {
                Log.d("testdownload:" ,"queueEnd" )
            }
        }
        )
        context = builder.build()

        context.startOnParallel(listener)


    }

//    builder.bind(url2).addTag(key, value);
//    builder.bind(url3).setTag(tag);
//
//
//    DownloadTask task = new DownloadTask.Builder(url4, parentFile)
//    .setPriority(10).build();
//    builder.bindSetTask(task);

    fun start(){
        context.startOnParallel(listener)
    }

    fun stop(){
        context.stop()
    }


// stop



}
