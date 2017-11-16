package com.wangkeke.kotlinnet.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wangkeke.kotlinnet.IntentActivity
import com.wangkeke.kotlinnet.R
import kotlinx.android.synthetic.main.fragment_anko_intent.*
import org.jetbrains.anko.*
import org.jetbrains.anko.support.v4.*

/**
 * Anko-intent,log使用
 */
class IntentLogFragment : Fragment() ,AnkoLogger{

    private var mParam1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_anko_intent, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        btn_intent_jump.setOnClickListener({
            startActivity<IntentActivity>()
//            startActivity(intentFor<IntentActivity>().singleTop().putExtra("data","你好，我来自上个页面"))
        })

        btn_make_call.setOnClickListener({
            makeCall("10086")
        })

        btn_browse.setOnClickListener({
            browse("http://www.baidu.com")
        })

        btn_send_email.setOnClickListener({
            email("1039163285@qq.com","来自己测试发邮件")
        })

        btn_share.setOnClickListener({
            share("分享的文本内容")
        })

        /**
         *
         * android.util.Log	    AnkoLogger
            v()	                verbose()
            d()	                debug()
            i()	                info()
            w()	                warn()
            e()	                error()
            wtf()	            wtf()
         *
         */
        btn_log_test.setOnClickListener({
            verbose { "hello,你好" }
            debug { "hello,你好" }
            info { "hello,你好" }
            warn { "hello,你好" }
            error { "hello,你好" }
//            wtf(Message(),Throwable())
        })
    }

    companion object {
        private val ARG_PARAM1 = "param1"

        fun newInstance(param1: String): IntentLogFragment {
            val fragment = IntentLogFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }


}