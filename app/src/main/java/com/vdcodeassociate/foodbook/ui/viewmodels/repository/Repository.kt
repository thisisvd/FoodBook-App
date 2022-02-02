package com.vdcodeassociate.foodbook.ui.viewmodels.repository

import com.vdcodeassociate.foodbook.restapi.RemoteDataSource
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class Repository @Inject constructor(
    remoteDataSource: RemoteDataSource
) {

    val remote = remoteDataSource

}