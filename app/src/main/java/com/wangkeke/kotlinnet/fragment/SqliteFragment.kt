package com.wangkeke.kotlinnet.fragment

import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonioleiva.weatherapp.extensions.parseList
import com.wangkeke.kotlinnet.R
import com.wangkeke.kotlinnet.model.User
import com.wangkeke.kotlinnet.sqlite.MyDatabaseOpenHelper
import kotlinx.android.synthetic.main.fragment_anko_sqlite.*
import org.jetbrains.anko.db.*
import org.jetbrains.anko.support.v4.toast

/**
 * Anko-sqlite使用
 */
class SqliteFragment : Fragment() {

    private var database: SQLiteDatabase? = null

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
        return inflater!!.inflate(R.layout.fragment_anko_sqlite, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        database = MyDatabaseOpenHelper.getInstance(activity).writableDatabase

        btn_insert.setOnClickListener({
            /*val values = ContentValues()
            values.put("id", 5)
            values.put("name", "John Smith")
            values.put("email", "wangkeke@domain.org")
            database!!.insert("User", null, values)*/

            when {
                et_name.text.toString().isEmpty() -> toast("请输入姓名")
                et_email.text.toString().isEmpty() -> toast("请输入邮箱")
                else -> {
                    var insertID = database!!.insert("User",
                            "name" to et_name.text.toString(),
                            "email" to et_email.text.toString()
                    )
                    toast("" + insertID)
                }
            }

            selectAndShow()
        })

        /**
         *  parseSingle(rowParser): T	Parse exactly one row解析单行
         *  parseOpt(rowParser): T?	Parse zero or one row解析0行或单行
         *  parseList(rowParser): List<T>	Parse zero or more rows解析0或多行
         */
        btn_select.setOnClickListener({
            /*val user = database!!.select("User")
                    .parseOpt { User(HashMap(it)) }

            toast(user!!.name+"---email = "+user!!.email)*/


            selectAndShow()

        })

        btn_update.setOnClickListener({


            val listUser = database!!.select("User").parseList { User(HashMap(it)) }

            //更新最后一条数据名字为：小可爱
            if (listUser.isNotEmpty()) {
                val user = listUser[listUser.size - 1]
                database!!.update("User", "name" to "小可爱")
                        .whereArgs("id = {userId}", "userId" to user.id)
                        .exec()
            }

            selectAndShow()
        })

        btn_delete.setOnClickListener({
            //删除最后一条数据
            var listUser = database!!.select("User").parseList { User(HashMap(it)) }
            if (listUser.isNotEmpty()) {
                val user = listUser[listUser.size - 1]
                val delOfID = database!!.delete("User", "id = {delId}", "delId" to user.id)

                if (delOfID > 0) {
                    selectAndShow()
                } else {
                    toast("无可删除数据了呢")
                }
            }


        })

    }

    private fun selectAndShow() {
        val listUser = database!!.select("User").parseList { User(HashMap(it)) }

        val sb = StringBuilder()

        listUser.forEach {
            sb.append("id =" + it.id + ",name = " + it.name + ",email =" + it.email + "\n\n")
        }

        tv_show.text = sb.toString()
    }

    companion object {
        private val ARG_PARAM1 = "param1"

        fun newInstance(param1: String): SqliteFragment {
            val fragment = SqliteFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            fragment.arguments = args
            return fragment
        }
    }


}