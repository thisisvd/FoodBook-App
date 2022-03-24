package com.vdcodeassociate.foodbook.ui.viewmodels.repository

import com.vdcodeassociate.foodbook.data.restapi.RemoteDataSource
import com.vdcodeassociate.foodbook.data.room.LocalDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource,
    localDataSource: LocalDataSource
) {

    // data source from API
    val remote = remoteDataSource

    // data source from ROOM
    val local = localDataSource

}