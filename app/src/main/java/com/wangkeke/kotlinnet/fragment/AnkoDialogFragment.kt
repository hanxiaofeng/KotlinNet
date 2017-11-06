package com.wangkeke.kotlinnet.fragment

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wangkeke.kotlinnet.R
import com.wangkeke.kotlinnet.adapter.GirlAdapter
import kotlinx.android.synthetic.main.fragment_anko_dialog.*
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.runBlocking
import org.jetbrains.anko.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.support.v4.*
import java.util.concurrent.TimeUnit
import kotlin.concurrent.thread

/**
 * Anko-Dialog使用
 */
class AnkoDialogFragment : Fragment() {

    var dialog: DialogInterface? = null

    private var mParam1: String? = null

    private var adapter: GirlAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_anko_dialog, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        btn_toast.setOnClickListener({
            //            toast(R.string.toast_test)
//            longToast(R.string.toast_test)
            toast("我是点击Toast显示的消息")
        })

        btn_snackbar.setOnClickListener({
            snackbar(btn_snackbar, "你好,SnackBar!")
//            longSnackbar(btn_snackbar, R.string.toast_test)
        })

        btn_alert.setOnClickListener({
            /*alert("看到这个你是否感觉非常激动呢?", "温馨提示") {
                yesButton { toast("当然很激动了") }
                noButton { toast("没啥感觉")}
            }.show()*/

//            activity.alert(Appcompat, "春风十里吹不动你").show()

            dialog = alert {
                customView {
                    verticalLayout {
                        padding = dip(15)
                        gravity = Gravity.START
                        textView("标题") {
                            textSize = 16f
                            textColor = Color.parseColor("#234456")
                        }
                        textView("我只是一串孤独的文字而已") {
                            textSize = 16f
                            textColor = Color.parseColor("#263443")
                        }.lparams {
                            topMargin = dip(20)
                            bottomMargin = dip(20)
                        }

                        relativeLayout {

                            button("取消") {
                                textSize = 18f
                                textColor = Color.parseColor("#352265")
                                onClick {
                                    toast("click btn")
                                    if (dialog!! is AlertDialog) {
                                        dialog!!.dismiss()
                                    }
                                }
                            }
                            button("确认") {

                                textSize = 18f
                                textColor = Color.parseColor("#352265")
                            }.lparams {
                                alignParentRight()
                                centerVertically()
                            }
                        }
                    }
                }
            }.show()


            /*val pwAlert = activity.alert(Appcompat) {
                titleResource = R.string.my_message
                messageResource = R.string.my_message
                okButton {}
            }.build()
            pwAlert.setCancelable(false)
            pwAlert.setCanceledOnTouchOutside(false)
            pwAlert.show()*/

        })

        btn_selectors.setOnClickListener({
            val fruits = listOf("苹果", "香蕉", "菠萝", "西瓜")
            selector("请选择", fruits, { _, i ->
                toast("我喜欢吃 ${fruits[i]}")
            })
        })

        btn_progress_dialog.setOnClickListener({
            val dialog = progressDialog(message = "加载中···", title = "提示")
            dialog.setCanceledOnTouchOutside(false)
            dialog.progress = 56
        })
    }

    companion object {
        private val ARG_PARAM1 = "param1"

        fun newInstance(param1: String): AnkoDialogFragment {
            val fragment = AnkoDialogFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }


}