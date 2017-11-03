package com.wangkeke.kotlinnet

import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable


/**
 * Created on 2017/11/3 14:48.
 * @author by 王可可
 * @version 1.0
 */

interface GankIoService {

    @GET("福利/30/{page}")
    fun getSearchAndroid(@Path("page") page: Int): Observable<Girl>

}
